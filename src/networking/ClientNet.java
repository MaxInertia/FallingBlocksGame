package networking;

import io.Standard;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javafx.application.Platform;
import javax.swing.SwingUtilities;
import networking.ops.ConnectOperation;
import networking.ops.PassOperation;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 * @param <C> The class of the objects which will be streamed
 */
public class ClientNet <C extends Serializable> implements Runnable{

	public static Thread networkThread;
	private static ClientNet instance;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket connection;
 
	private final String SERVER_IP;
	private final int PORT;
	
	private boolean hold_connection;
	private boolean is_connected;
	
	private Standard.IOMode mode;
	private PassOperation<C> passer;
	private ConnectOperation connectionNotifier;
	
	private final boolean printStackTraces = false;
	
	/**
	 * ClientNet constructor with custom host (Server IP) and port
	 * @param host The IP of the Server
	 * @param port The port number required to make the connection
	 */
	public ClientNet(String host, int port){
		this.SERVER_IP = host;
		this.PORT = port;
		this.hold_connection = true;
		this.is_connected = false;
		instance = this;
		Standard.out("[ClientNet]\tClientNet created!");
	}
	
	/**
	 * ClientNet constructor which uses 127.0.0.1 (LocalHost)
	 * @param port The port number required to make the connection
	 */
	public ClientNet(int port){
		this.SERVER_IP = "127.0.0.1";
		this.PORT = port;
		this.hold_connection = true;
		this.is_connected = false;
		instance = this;
		Standard.out("[ClientNet]\tClientNet created!");
	}
	
	/**
	 * Causes the connection to the server to be closed. 
	 * Currently will only close the connection after the next received message.
	 */
	public void disconnect(){
		hold_connection = false;
	}
	
	/**
	 * Checks if the connection to the Server is open.
	 * @return <b>true</b> if connected to the Server,
	 *		   else <b>false</b>.
	 */
	public boolean isConnected(){
		return is_connected;
	}
	
	/**
	 * Sends an object of class <b>C</b> up the stream.
	 * @param object The object to be delivered. Assuming
	 * the class of the objects being sent are the same as
	 * those being received, <b>object</b> should be of class <b>C</b>.
	 */
	public static void sendObject(Object object){
		try{
			if(instance.connection!=null && !instance.connection.isClosed()){
				instance.out.writeObject(object);
				instance.out.flush();
				instance.out.reset();
				Standard.out("[ClientNet > "+instance.connection.getInetAddress().getHostName()+"]\t: "+object.toString());
			}else{
				Standard.out("[ClientNet]\tAttempted sending object when not connected!");
			}
		}catch(IOException ioException){
			if(instance.printStackTraces) ioException.printStackTrace();
			Standard.out("[ClientNet]\t(IOException) When attempting to send the object");
		}
	}
	
	/**
	 * Initializes the networking 
	 */
	public void start(){
		networkThread = new Thread(this);
		networkThread.setDaemon(true);
		networkThread.start();
	}
	
	/**
	 * Starts connection attempt listener and object 
	 * transaction streams if a connection is made.
	 */
	@Override
	public void run(){
		try{
			// 1. Wait for connection
			connectToServer();
			// 2. Setup streams
			setupStreams();
			// 3. Application running
			whileConnected();
		} catch(ConnectException connectException){
			if(printStackTraces) connectException.printStackTrace();
			Standard.out("[ClientNet]\t(ConnectionException) Failed to connect, the server may be down");
		} catch(EOFException eofException){
			if(printStackTraces) eofException.printStackTrace();
			Standard.out("[ClientNet]\t(EOFException) Server ended the connection!");
		} catch(IOException ioException){
			if(printStackTraces) ioException.printStackTrace();
			Standard.out("[ClientNet]\t(IOException) Server ended the connection!");
		} finally {
			closeConnection();
		}
	}
	
	public void setConnectionAction(Standard.IOMode modeRequired, ConnectOperation passOperation){
		mode = modeRequired;
		connectionNotifier = passOperation;
	}
	
	/**
	 * Sets the operation to be performed when a message is received from the stream.
	 * Expects a runnable with a public field of type C with name object.
	 * @param modeRequired The IOMode for the operation (CONSOLE/SWING/JFX)
	 * @param passOperation The operation performed
	 */
	public void setPassAction(Standard.IOMode modeRequired, PassOperation<C> passOperation){
		mode = modeRequired;
		passer = passOperation;
	}
	
	//public void setOnConnectionStatusChangeAction(){}
	
	/**
	 * Thread blocked until a connection is made,
	 * connection made is assigned to field <b>connection</b>.
	 * @throws IOException An I/O Exception occurred while waiting for the connection.
	 */
	private void connectToServer() throws IOException, ConnectException, UnknownHostException{
		Standard.out("[ClientNet]\tAttempting connection...");
		connection = new Socket(InetAddress.getByName(SERVER_IP),PORT);
		//System.out.println("remote: "+connection.getRemoteSocketAddress().toString());
		is_connected = true;
		if(connectionNotifier != null) connectionStateChanged(true);
		Standard.out("[ClientNet]\tConnected to: " + connection.getInetAddress().getHostName());
	}
	
	/**
	 * Creates the streams required to send and receive objects.
	 */
	private void setupStreams() throws IOException{
		out = new ObjectOutputStream(connection.getOutputStream());
		out.flush();
		in = new ObjectInputStream(connection.getInputStream());
		Standard.out("[ClientNet]\tStreams are now setup!");
	}

	/**
	 * Waits for objects to come down the stream, then reads them
	 * and returns them to the application thread.
	 */
	@SuppressWarnings("unchecked")
	private void whileConnected() throws IOException {
		Standard.out("[ClientNet]\tWaiting for object to come down the stream..."); // Haha. A pun. I'm hilarious.
		do {
			C objectIn = null;
			try {
				Object rawObject = in.readObject();
				objectIn = (C) rawObject;
			} catch(SocketException socketException) {
				//socketException.printStackTrace();
				Standard.out("[ClientNet]\t(SocketException) The socket failed, disconnecting");
				hold_connection = false;
			} catch(ClassNotFoundException classNotFoundException) {
				//classNotFoundException.printStackTrace();
				Standard.out("[ClientNet]\t(ClassNotFoundException) Unknown object type received");
				// TODO: Determine if the connection should be dropped (See end of javaDoc for readObject())
			}
			// TODO: Hand off objectIn to appropriate thread
			if(passer!=null) passObject(objectIn);
		} while(hold_connection);
	}
	
	/**
	 * Calls the runnable given to setConnectionAction()
	 * 
	 * @param tof whether the connection what successful
	 */
	private void connectionStateChanged(boolean tof){
		switch (mode) {
			case SWING:
				// For updating Swing GUI
				connectionNotifier.isConnected = tof;
				SwingUtilities.invokeLater(connectionNotifier);
				break;
			case JFX:
				// For updating JFX GUI
				connectionNotifier.isConnected = tof;
				Platform.runLater(connectionNotifier);
				break;
			case CONSOLE: 
				// Console output only... Nothing interesting to see here
				Standard.out("[ClientNet]\tConnected: "+tof);
				break;
			default:
				break;
		}
	}
	
	/**
	 * Calls the runnable given to setPassAction()
	 * 
	 * @param object
	 */
	private void passObject(final C object){
		if (object==null){ 
			Standard.out("[ClientNet]\t Object received is null."); 
			return; 
		}
		
		switch (mode) {
			case SWING:
				// For updating Swing GUI
				passer.object = object;
				SwingUtilities.invokeLater(passer);
				break;
			case JFX:
				// For updating JFX GUI
				passer.object = object;
				Platform.runLater(passer);
				break;
			case CONSOLE: 
				// Console output only
				Standard.out(object.toString());
				break;
			default:
				break;
		}
	}
	
	/**
	 * Closes the streams and the socket.
	 */
	private void closeConnection(){
		Standard.out("[ClientNet]\tClosing connection to Server...");
		is_connected = false;
		connectionStateChanged(false);
		try {
			if(out!=null){ 
				out.close();  
				Standard.out("[ClientNet]\t\tObjectOutputStream closed."); 
			}
			if(in!=null){
				in.close();  
				Standard.out("[ClientNet]\t\tObjectinputStream closed.");
			}
			if(connection!=null){
				connection.close();  
				Standard.out("[ClientNet]\t\tSocket closed.");
			}
		} catch(IOException ioException) {
			if(printStackTraces) ioException.printStackTrace();
			Standard.out("[ClientNet]\t(IOException) THIS SHOULD NOT HAPPEN - method closeConnection()");
		}
	}
	
}

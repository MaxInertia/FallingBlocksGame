package game.online.netops;

import game.MainController;
import networking.GenUpdate;
import networking.ops.ConnectOperation;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class OnConnectedOperation extends ConnectOperation<GenUpdate>{

	@Override
	public void run() {
		System.out.println("OnConnectedOperation.run() called!");
		MainController.setOnlineStatus(isConnected);
	}

}

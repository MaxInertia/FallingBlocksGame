package io;

import java.io.IOException;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Standard {
	
	/**
	 * Input/Output modes: 
	 * The ways in which application messages are displayed to the user
	 */
	public enum IOMode{
		CONSOLE , SWING, JFX
	}
	
	/**
	 * Log types: specifies the severity of a message pushed to the log
	 */
	public enum LogType{
		NORMAL, ERROR
	}
	
	/**
	 * The current method by which application 
	 * messages are displayed to the user
	 */
	private static IOMode outputMode = Standard.IOMode.CONSOLE;
	
	/**
	 * If logs are written to a file
	 */
	private static boolean fileLogging = false;
	
	/**
	 * Sets the output mode for the application
	 * @post the output mode will be <b>desiredOutputMode</b>
	 * @param desiredOutputMode the new output mode.<br> Possible values:<br>
	 * Standard.IOMode.CONSOLE
	 */
	public static void setOutputMode(IOMode desiredOutputMode) {
		outputMode = desiredOutputMode;
	}
	
	/**
	 * Gets the applications output mode.
	 * @return The current output mode.<br>
	 * Possible values:<br>
	 * Standard.IOMode.CONSOLE
	 */
	public static IOMode getOutputMode() {
		return outputMode;
	}
	
	/**
	 * Outputs text in using the output mode
	 * assigned to field <b>outputMode</b>.
	 * @param text 
	 */
	public static void out(String text) {
		if(outputMode.equals(Standard.IOMode.CONSOLE)) {
			System.out.println(text);
		}
		// add new output methods here...
	}
	
	/**
	 * Enables log-to-file feature;
	 * Log messages are stored in a file which is created with the given path and name.
	 * 
	 * @param pathname Path and name of the desired log file.
	 * @throws java.io.IOException File creation failed.
	 */
	public static void enableLoggingToFile(String pathname) throws IOException {
		Logger.initializeLogger(pathname);
		fileLogging = true;
	}

	/**
	 * Disables log-to-file feature;
	 * Log messages will not be stored in a log file.
	 */
	public static void disableLoggingToFile(){
		fileLogging = false;
	}
	
	/**
	 * Add a log message with the given type to the log file
	 * @param message The log message
	 * @param type The severity of the log (NORMAL/ERROR)
	 */
	public static void log(String message, LogType type) {
		if(!fileLogging){ return; }
		switch(type){
			case NORMAL:
				Logger.addNormalLog(message);
				break;
			case ERROR:
				Logger.addErrorLog(message);
				break;
		}
	}
	
	
	
}

package io;

import java.io.File;
import java.io.IOException;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Logger{
	
	private static File logFile;
	
	/**
	 * Creates the log file with the given pathname. If pathname is null
	 * a pathname will be created using the default pathname creation 
	 * format as defined in the method <i>createFilePathname</i>.
	 * 
	 * @param pathname The path+name for the log file which is to be created.
	 * @throws java.io.IOException Error occurred when creating file
	 */
	protected static void initializeLogger(String pathname) throws IOException{
		if(pathname==null){ 
			pathname = createFilePathname(); 
		}
		logFile = new File(pathname);
		if(!logFile.exists()){
			logFile.createNewFile();
		}
	}
	
	/**
	 * Appends a message of type 'Normal' to the log file.
	 * @param message Log message to be appended to log file.
	 */
	protected static void addNormalLog(String message) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

		/**
	 * Appends a message of type 'ERROR' to the log file.
	 * @param message log message to be appended to log file.
	 */
	protected static void addErrorLog(String message) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}
	
	protected static String createFilePathname(){
		int num = 0;
		while(true){
			if( !(new File("log"+num+".log").exists()) ){
				return "log"+num+".log";
			}
		}
	}
	
}
	


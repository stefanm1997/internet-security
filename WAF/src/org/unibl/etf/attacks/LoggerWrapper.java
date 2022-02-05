package org.unibl.etf.attacks;


import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerWrapper {
	
	private static Logger logger = Logger.getLogger("errorLogger");
	private static FileHandler fileHandler;
	private static boolean firstTime = true;
	private static String loggerPath = System.getProperty("user.home")+File.separator+"error.log";
	//private static String loggerPath="C:\\Users\\milsL\\WAF\\error.log";
	public synchronized static Logger getLogger(){
		if(firstTime) {
			try {
		        fileHandler = new FileHandler(loggerPath, true);
		       	fileHandler.setFormatter(new SimpleFormatter());
		       	logger.addHandler(fileHandler);
		       	firstTime = false;
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return logger;
	}
}

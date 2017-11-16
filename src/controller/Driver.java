package controller;

import java.io.File;
import java.util.ArrayList;

import view.View;

public class Driver {

	/**
	 * Entry-point into the application
	 * 
	 * @param args		Not used
	 * 
	 * @precondition 	none
	 * 
	 * @postcondition 	program is running
	 */
	public static void main(String[] args) {
		View view = new View();
		view.run();
	}
	
	public static void restartApplication() {
		try {
			final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
			final File currentJar = new File(Driver.class.getProtectionDomain().getCodeSource().getLocation().toURI());
	
			/* is it a jar file? */
			if(!currentJar.getName().endsWith(".jar"))
			return;
	
			/* Build command: java -jar application.jar */
			final ArrayList<String> command = new ArrayList<String>();
			command.add(javaBin);
			command.add("-jar");
			command.add(currentJar.getPath());
	
			final ProcessBuilder builder = new ProcessBuilder(command);
			builder.start();
		} catch (Exception e) {
			System.out.println("ERROR RESTARTING PROJECT");
			e.printStackTrace();
		}
		System.exit(0);
	}

}

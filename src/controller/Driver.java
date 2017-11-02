package controller;

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

}

package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import display.Window;
import logic.Game;

/**
 *
 * @author Doria
 */
public class ClickQuick {

	private static final int COLUMNS = 8;
	public static final int ROWS = 10;
	private static final int CUBE_SIDE_LENGTH = 60;
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Game clickQuickLogic = new Game(COLUMNS,ROWS,CUBE_SIDE_LENGTH);
		Window clickQuickWindow = new Window(COLUMNS,ROWS,CUBE_SIDE_LENGTH);
	}
	
}

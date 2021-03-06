package lab4.data;

import java.util.Observable;

import lab4.gui.GamePanel;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	/*
	 * A gridMatrix that holds the playing field. Holds ints that reprecents ME = 1, OTHER = 2 or NONE = 0;
	 */
	
	private GamePanel panel;
	
	private int gridMatrix[][];
	private int size;

	public final int NONE = 0;
	public final static int ME = 1;
	public final static int OTHER = 2;
	private final static int INROW = 3;

	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		
		this.size = size;
		
		/*
		 * Inits the gridMatrix and sets it to NONE
		 */
		gridMatrix = new int[size][size];
		this.clearGrid();
		
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return gridMatrix[x][y];
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return this.size;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player){
		
		/*
		 * Safeguard to not place random shit
		 */
		if((player == this.ME || player == this.OTHER) && this.getLocation(x,  y) == this.NONE) {
			gridMatrix[x][y] = player;
			return true;
		}
		return false;
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		for(int row = 0; row < this.size; row++ ) {
			for(int collumn = 0; collumn < this.size; collumn++ ) {
				gridMatrix[collumn][row] = 0;
			}
		}
		
		// Notifies observers of clearing
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		
		//Goes through every row and column
		
		for(int row = 0; row < size; row++ ) {
			for(int column = 0; column < size; column++ ) {
				
				//Checks the status of the current tile
				
				if(this.getLocation(column, row) == player) {
					
					/*If the player is on the current tile, it loopes through the 5 tiles in line with the current tile,
					 * and checks if the player is on all 5. The following is the vectors tested:
					 * 
					 * 1, 0:	|x x x x x
					 * 			|
					 * 			|
					 * 			|
					 * 			|
					 * 
					 * 0, 1		|x
					 * 			|x
					 * 			|x
					 * 			|x
					 * 			|x
					 * 
					 * 1, 1		|x
					 * 			|  x
					 * 			|    x
					 * 			|      x
					 * 			|        x
					 * 
					 * -1, -1	|        x
					 * 			|      x
					 * 			|    x
					 * 			|  x
					 * 			|x
					 * 
					 * 
					 */
					
					if(loopThroughSeries(player, column, row, 1, 0) ||
					   loopThroughSeries(player, column, row, 0, 1) ||
					   loopThroughSeries(player, column, row, 1, 1) ||
					   loopThroughSeries(player, column, row, -1, 1) 
					   ) {
						
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/*
	 * Loops through 5 tiles, starting at position x, y and increments every step by deltaX, deltaY.
	 * If every tile contains the players pieces, it returns true
	 */
	private boolean loopThroughSeries(int player, int x, int y, int deltaX, int deltaY) {
		
		
		for(int i = 0; i < INROW; i++ ) {
			
			//Makes sure the getLocation won't get any IndexOutOfBounds-error, and checks the tile.
			if(x + (deltaX >= 0 ? 1 : -1) *  (Math.abs(deltaX) * i) < 0 || 
			   y + (deltaY >= 0 ? 1 : -1) *  (Math.abs(deltaY) * i) < 0 || 
			   x + (deltaX >= 0 ? 1 : -1) *  (Math.abs(deltaX) * i) >= this.size ||
		       y + (deltaY >= 0 ? 1 : -1) *  (Math.abs(deltaY) * i) >= this.size ||
			   this.getLocation(x + (deltaX >= 0 ? 1 : -1) *  (Math.abs(deltaX) * i), y + (deltaY >= 0 ? 1 : -1) *  (Math.abs(deltaY) * i)) != player) {
				
				return false;
			}
		}
		
		return true;
	}

	
	/**
	 * Sets the current gamePanel. 
	 */
	public void setGamePanel(GamePanel panel) {
		this.panel = panel;
	}
	
	/**
	 * 
	 * @return The gamePanel
	 */
	public GamePanel getGamePanel() {
		return this.panel;
	}
	
	
	
	
	
}
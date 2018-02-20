package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	/*
	 * A gridMatrix that holds the playing field. Holds ints that reprecents ME = 1, OTHER = 2 or NONE = 0;
	 */
	private int gridMatrix[][];
	private int size;
	public static int NONE = 0;
	public static int ME = 1;
	public static int OTHER = 2;
	private static int INROW = 5;
	
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
		return gridMatrix[y][x];
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
		if((player == 1 || player == 2) && this.getLocation(x,  y) == 0) {
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
				gridMatrix[row][collumn] = 0;
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
					   loopThroughSeries(player, column, row, -1, -1) 
					   ) {
						
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/*
	 * Loopes through 5 tiles, starting at position x, y and increments every step by deltaX, deltaY.
	 * If every tile contains the players pieces, it returns true
	 */
	private boolean loopThroughSeries(int player, int x, int y, int deltaX, int deltaY) {
		
		for(int i = 0; i < INROW; i++ ) {
			
			//Makes sure the getLocation won't get any IndexOutOfBounds-error, and checks the tile.
			if(x + deltaX * i < 0 || 
			   y + deltaY * i < 0 || 
			   x + deltaX * i >= this.size ||
			   y + deltaY * i >= this.size ||
			   this.getLocation(x + deltaX * i, y + deltaY * i) != player) {
				
				return false;
			}
		}
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
}
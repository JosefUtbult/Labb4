package lab4.gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.Position;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20;
	private int preferedUnitSize;
	private GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		
		/**
		 * Makes a preferedUnitSize that is used instead of the UNIT_SIZE. It is sized after the current screen-size
		 */
		this.preferedUnitSize = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 50;
		/**
		 * Building the grid
		 */
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*this.getUnitSize()+1, grid.getSize()*this.getUnitSize()+101);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		
		return new int[] {0};
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		/*
		 * Makes a delta point, that is relative to the current screenSize. This acts like a zero position for the grid
		 */
		
		Point delta = new Point(this.getWidth() / 2 - (grid.getSize() * this.getUnitSize()) / 2, (this.getHeight() / 2 - (grid.getSize() * this.getUnitSize()) / 2) - 50);
		
		/*
		 * Draws the grid
		 */
		for(int posY = 0; posY < grid.getSize(); posY++ ) {
			
			for(int posX = 0; posX < grid.getSize(); posX++ ){
				
				g.drawRect((int) (posX * this.getUnitSize() + delta.getX()), (int) (posY * this.getUnitSize() + delta.getY()), this.getUnitSize(), this.getUnitSize());
				
			}
		}
		
		
		
	}
	
	public int getUnitSize() {
		
		return preferedUnitSize;
		
	}
	
	
}





















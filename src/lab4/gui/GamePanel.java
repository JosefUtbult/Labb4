package lab4.gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.text.Position;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20;
	private int playerSize;
	private int screenSize;
	private int preferedUnitSize;
	private GameGrid grid;
	private Point delta;
	
	private BufferedImage player1;
	private BufferedImage player2;
	
	
	/**
	 * Stores the GameGrid, stores a screenSize that is generated from the width of the screen the
	 * GUI is rendered on, stores some other sizes derived from the screenSize, and tries to read the players
	 * tile-images.
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		
		grid.setGamePanel(this);
		/**
		 * Makes a preferedUnitSize that is used instead of the UNIT_SIZE. It is sized after the current screen-size
		 */
		this.screenSize = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		this.preferedUnitSize = this.screenSize / 40;
		this.playerSize = this.screenSize / 45;
		/**
		 * Building the grid
		 */
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*this.getUnitSize()+101, grid.getSize()*this.getUnitSize()+101);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
		
		this.player1 = null;
		this.player2 = null;
		
		try {
			player1 = ImageIO.read(new File("res/player1.png"));
			player2 = ImageIO.read(new File("res/player2.png"));
			
		} catch (IOException e) {
			System.out.println("Could not load image.");
			return;
		}
		
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
	/**
	 * Runs as the Observable (GameGrid) notifies it. Repaints the screen
	 */
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	/**
	 * Overrides JPanels paintComponent. Paints the GameGrid.
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		/*
		 * Makes a delta point, that is relative to the current screenSize. This acts like a zero position for the grid
		 */
		
		delta = new Point(this.getWidth() / 2 - (grid.getSize() * this.getUnitSize()) / 2, (this.getHeight() / 2 - (grid.getSize() * this.getUnitSize()) / 2));
		JLayeredPane jLayeredPane = new JLayeredPane();
		/*
		 * Draws the grid
		 */
		for(int posY = 0; posY < grid.getSize(); posY++ ) {
			
			for(int posX = 0; posX < grid.getSize(); posX++ ){
				
				g.drawRect((int) (posX * this.getUnitSize() + delta.getX()), (int) (posY * this.getUnitSize() + delta.getY()), this.getUnitSize(), this.getUnitSize());
				
				/*
				 * Checks location and renders a piece if necessary
				 */
				switch (this.grid.getLocation(posX, posY)) {
					
				case 1:
					
					g.drawImage(this.player1, 
								(int)(posX * this.getUnitSize() + delta.getX() + ((this.getUnitSize() / 2) - (this.playerSize / 2))), 
								(int)(posY * this.getUnitSize() + delta.getY() + ((this.getUnitSize() / 2) - (this.playerSize / 2))), 
								(int)this.playerSize, 
								(int)this.playerSize, 
								jLayeredPane);
					
					
					break;
				case 2:
					
					g.drawImage(this.player2, 
								(int)(posX * this.getUnitSize() + delta.getX() + ((this.getUnitSize() / 2) - (this.playerSize / 2))), 
								(int)(posY * this.getUnitSize() + delta.getY() + ((this.getUnitSize() / 2) - (this.playerSize / 2))), 
								(int)this.playerSize, 
								(int)this.playerSize, 
								jLayeredPane);
					
					
					break;
				case 0:
					break;
				default:
					break;
				}
			}
		}
		
		this.add(jLayeredPane);
		
		
	}
	
	/**
	 * Returns the preferedUnitSize, a size derived from screenSize.
	 * @return preferedUnitSize
	 */
	public int getUnitSize() {
		
		return preferedUnitSize;
		
	}
	
	/**
	 * Returns the delta value, an offset for the zero point of the grid. Its purpose is to center the
	 * grid in whatever size the current window is in.
	 * @return the zero point offset
	 */
	public Point getDelta() {
		return this.delta;
	}
	
	/**
	 * Returns the gameGrid.
	 * @return GameGrid
	 */
	public GameGrid getGameGrid() {
		return this.grid;
	}
	
	
}





















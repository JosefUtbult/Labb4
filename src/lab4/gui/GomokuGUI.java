package lab4.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GamePanel gameGridPanel;
	private GomokuGUI guiHolder;
	private GomokuClient client;
	private GomokuGameState gamestate;
	private JLabel messageLabel;
	private JButton connectButton;
	private JButton newGameButton;
	private JButton disconnectButton;
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		
		/**
		 * Holding a client and a gamestate, and the GUI as an observer to both of them.
		 */
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		guiHolder = this;
		
		/**
		 * Creating a Frame (Window)
		 */
		JFrame frame = new JFrame("Gomoku Mothafucka!!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		/**
		 * Creating a gameGridPanel (the view kind of
		 */
		gameGridPanel = new GamePanel(g.getGameGrid());
		SpringLayout layout = new SpringLayout();

		/**
		 * Some buttons and a label is created and added to the gameGridPanel. 
		 */
		messageLabel = new JLabel("This is how to text.");
		
		connectButton = new JButton("Connect");
		newGameButton = new JButton("New Game");
		disconnectButton = new JButton("Disconnect");
		
		
		gameGridPanel.add(connectButton);
		gameGridPanel.add(newGameButton);
		gameGridPanel.add(disconnectButton);
		
		
		gameGridPanel.add(messageLabel);
		/**
		 * Puting constrains on the buttons and labels. The Connect button is constrained to the gameGrid, and the rest of the components are 
		 * constrained to the button.
		 */

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, connectButton, 0, SpringLayout.HORIZONTAL_CENTER, gameGridPanel);
		layout.putConstraint(SpringLayout.SOUTH, connectButton, -20, SpringLayout.SOUTH, gameGridPanel);

		layout.putConstraint(SpringLayout.EAST, newGameButton, -10, SpringLayout.WEST, connectButton);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, newGameButton, 0, SpringLayout.VERTICAL_CENTER, connectButton);
		
		layout.putConstraint(SpringLayout.WEST, disconnectButton, 10, SpringLayout.EAST, connectButton);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, disconnectButton, 0, SpringLayout.VERTICAL_CENTER, connectButton);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, messageLabel, 0, SpringLayout.HORIZONTAL_CENTER, connectButton);
		layout.putConstraint(SpringLayout.NORTH, messageLabel, 3, SpringLayout.SOUTH, connectButton);

		/**
		 * Applying layout and seting gameGridPanel as the current screen. Then sets it all to visible, packs it and centers it.
		 */
		gameGridPanel.setLayout(layout);
		
        frame.setContentPane(gameGridPanel);
        frame.setVisible(true);
        /*
        frame.setLocation(new Point((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - frame.getWidth() / 2, 
        							(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - frame.getHeight() / 2));
        */
        frame.pack();
        frame.setLocationRelativeTo(null);

        
        /**
         * Enables/disables the buttons
         */
        connectButton.setEnabled(true);
        newGameButton.setEnabled(false);
        disconnectButton.setEnabled(false);
        
        gameGridPanel.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(e.getButton() == e.BUTTON1) {
        			g.move(e.getX() / guiHolder.getGameGridPanel().getUnitSize(), e.getY() / guiHolder.getGameGridPanel().getUnitSize());
        		}
        	
        	}
        });
        
        connectButton.addActionListener(new ActionListener() {

        	ConnectionWindow connectionWindow;
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				connectionWindow = new ConnectionWindow(c);
				
			}
        	
        });
        
        newGameButton.addActionListener(new ActionListener() {

        	
			@Override
			public void actionPerformed(ActionEvent e) {
				g.newGame();
			}
        	
        });
        
        disconnectButton.addActionListener(new ActionListener() {

        	
			@Override
			public void actionPerformed(ActionEvent e) {
				g.disconnect();
			}
        	
        });
		
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
	protected GamePanel getGameGridPanel() {
		return this.gameGridPanel;
	}
	
	
	
}

/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer {

	// Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;

	// Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED = 3;
	private int currentState;

	private GomokuClient client;

	private String message;

	/**
	 * The constructor takes in the GomokuClient and stores it. It also takes the port
	 * number and prints it as a message on the screen.
	 * @param gc The client used to communicate with the other player
	 * @param port The port of the client
	 * 
	 */
	public GomokuGameState(GomokuClient gc, int port) {
		this.message = "Begining at port " + String.valueOf(port);
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString() {
		return message;
	}

	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid() {
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public void move(int x, int y) {

		System.out.format("currentState: %d\n", currentState);
		if (this.currentState == MY_TURN) { // Checks if its "my" turn

			System.out.println("Moved");

			/*
			 * Checks if its a valid move, if it is: Other player gets notified its his
			 * move, or that he lost Notifies the client Tells you that the move was valid
			 */
			if (gameGrid.move(x, y, GameGrid.ME)) {

				if (!this.checkWinnerStatus()) {
					this.message = "You made a move";

					currentState = OTHER_TURN;
				}

				client.sendMoveMessage(x, y);

			} else {
				this.message = "The place is already occupied, make a valid move";

			}

		} else if (currentState == FINISHED) {

			message = "The game is over. Accept it";
		} else if (currentState == OTHER_TURN) {
			message = "It is not your turn";
		} else if (currentState == NOT_STARTED) {
			message = "Game is not started";
		} else { // Incase something unexpected happens
			message = "WTF? This should not be happening";
		}

		System.out.format("currentState: %d\n", currentState);

		gameGrid.getGamePanel().repaint();
		this.setChanged();
		this.notifyObservers();

	}

	/**
	 * Starts a new game with the current client
	 */
	public void newGame() {
		gameGrid.clearGrid();
		message = "A new game has been started by you";
		currentState = MY_TURN;
		client.sendNewGameMessage();
		setChanged();
		notifyObservers();
	}

	/**
	 * Other player has requested a new game, so the game state is changed
	 * accordingly
	 */
	public void receivedNewGame() {
		gameGrid.clearGrid();
		message = "A new game has been started by the other player";
		currentState = OTHER_TURN;
		setChanged();
		notifyObservers();
	}

	/**
	 * The connection to the other player is lost, so the game is interrupted
	 */
	public void otherGuyLeft() {
		gameGrid.clearGrid();
		message = "The other player disconnected";
		currentState = NOT_STARTED;
		setChanged();
		notifyObservers();
	}

	/**
	 * The player disconnects from the client, notifies the client and observers
	 */
	public void disconnect() {
		gameGrid.clearGrid(); // Clearing the grid
		message = "You are now getting disconnected";
		currentState = NOT_STARTED;
		client.disconnect(); // calls disconnect method in client
		setChanged();
		notifyObservers();
	}

	/**
	 * The player receives a move from the other player
	 * 
	 * @param x
	 *            The x coordinate of the move.
	 * @param y
	 *            The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {

		System.out.format("Received a move: X: %d		Y: %d\n", x, y);

		gameGrid.move(x, y, gameGrid.OTHER);

		if (!this.checkWinnerStatus()) {
			this.message = "The other player made a move.";
			this.currentState = this.MY_TURN;
		}

		gameGrid.getGamePanel().repaint();
		this.setChanged();
		this.notifyObservers();

	}

	/**
	 * Checks if you or the other player won
	 * 
	 * @return true if the other player won, else false
	 * 
	 */

	private boolean checkWinnerStatus() {
		if (gameGrid.isWinner(GameGrid.OTHER)) {
			message = "Other player has won, u sukk";
			this.currentState = FINISHED;
			return true;
		} else if (gameGrid.isWinner(GameGrid.ME)) {
			this.message = "You won and u sukk";
			this.currentState = FINISHED;
			return true;

		} else {
			return false;
		}
	}
	/**
	 * Determines the initial state of the game.
	 * Runs as an Observable (GomokuClient) notifies it. Checks the connection status
	 * from the GomokuClient, ands sets the currentState accordingly.
	 */
	public void update(Observable o, Object arg) {

		switch (client.getConnectionStatus()) {
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();

	}	
	/**
	 * Returns the objects client
	 * @return The objects client
	 */

	public GomokuClient getClient() {
		return this.client;
	}
}
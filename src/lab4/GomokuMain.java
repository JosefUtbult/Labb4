package lab4;

import java.net.BindException;

import lab4.client.*;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {

		System.out.println("Begin");
		GomokuClient client = new GomokuClient(8005);
		
		/*
		 * Humanize is a flag that changes the appearance of the player-bricks
		 */
		GomokuGameState gameState = new GomokuGameState(client, args.length > 0 && args[0].equals("-humanize"));
		
		/*gameState.getGameGrid().move(1, 3, 1);
		gameState.getGameGrid().move(3, 3, 2);
		gameState.getGameGrid().move(10, 5, 1);
		*/
		
		
		GomokuGUI gui = new GomokuGUI(gameState, client);
	}
}
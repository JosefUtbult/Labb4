package lab4;

import java.net.BindException;

import lab4.client.*;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {

		System.out.println("Begin");
		GomokuClient client = new GomokuClient(8000);
		
		GomokuGameState gameState = new GomokuGameState(client);
		
		GomokuGUI gui = new GomokuGUI(gameState, client);
	}
}
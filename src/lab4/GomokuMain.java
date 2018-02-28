package lab4;

/**
 * @author Josef Utbult & Oscar Rosberg
 */

import java.net.BindException;

import lab4.client.*;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {

		int port = 8000 + (int) (Math.random() * 100);
		int port2 = 4000;
				
		GomokuClient client = new GomokuClient(port);
		new GomokuGUI(new GomokuGameState(client, port,port2), client);
	}
}
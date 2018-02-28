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

<<<<<<< HEAD
		int port = 8000 + (int) (Math.random() * 100);
		int port2 = 4000;
				
		GomokuClient client = new GomokuClient(port);
		new GomokuGUI(new GomokuGameState(client, port,port2), client);
=======
		int port[] = new int[] {}; //{8000 + (int) (Math.random() * 100), 9000};
				
		if(port.length < 1) {
			port = new int[] {8000};
		}
		GomokuClient client = new GomokuClient(port[0]);
		new GomokuGUI(new GomokuGameState(client, port[0]), client);
>>>>>>> 94db2b0a93bc708786e51cee17e7c0196ee54ad5
	}
}
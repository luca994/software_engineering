/**
 * 
 */
package client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author Luca
 *
 */
public class ClientInHandler implements Runnable {

	private ObjectInputStream socketIn;
	
	public ClientInHandler (ObjectInputStream socketIn){
		this.socketIn=socketIn;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true){
			try {
				Object object=socketIn.readObject();
				// Arrivano in ingresso gli oggetti dal socket, devo decidere come usarli
				
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
}
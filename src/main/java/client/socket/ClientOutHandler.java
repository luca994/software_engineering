/**
 * 
 */
package client.socket;

import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * @author Luca
 *
 */
public class ClientOutHandler implements Runnable {

	private ObjectOutputStream socketOut;
	
	public ClientOutHandler(ObjectOutputStream socketOut) {
		this.socketOut = socketOut;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		Scanner stdIn = new Scanner(System.in);
		
		while(true){
			
			String inputLine=stdIn.nextLine();
			
		}

	}

}

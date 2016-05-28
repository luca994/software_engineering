package client;

import java.io.IOException;

public interface Connessione extends Runnable{ 
	
	/**
	 * sends an object through the socket
	 * @param oggetto the object to send
	 * @throws IOException if there is an error in the socket connection
	 */
	public void inviaOggetto(Object oggetto) throws IOException;
}

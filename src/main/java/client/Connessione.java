package client;

import java.io.IOException;

/**
 * 
 * the connection interface
 *
 */
public interface Connessione{ 
	
	/**
	 * sends an object through the socket
	 * @param oggetto the object to send
	 * @throws IOException if there is an error in the socket connection
	 */
	public void inviaOggetto(Object oggetto);
}

package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.zip.DataFormatException;

public class ConnessioneFactory {

	private static final int CONNESSIONE_SOCKET=0;
	private static final int CONNESSIONE_RMI=1;
	private View view;
	
	/**
	 * builds a connection factory
	 * @param view the view to which the connection is related
	 */
	public ConnessioneFactory(View view){
		this.view=view;
	}
	
	/**
	 * creates a connection based on the type
	 * @param tipoConnessione the type of the connection
	 * @param host the host of the server
	 * @param port the port of the connection
	 * @return a connection (RMI or Socket)
 	 * @throws UnknownHostException if the host isn't correct or is unreachable
	 * @throws DataFormatException if the type of connection (tipoConnessione) isn't correct
	 * @throws IOException if there is a problem in the socket connection
	 */
	public Connessione createConnessione(int tipoConnessione, String host, int port) throws UnknownHostException, DataFormatException, IOException{	
		switch(tipoConnessione){
		case CONNESSIONE_SOCKET : return new ConnessioneSocket(view, host, port);
		case CONNESSIONE_RMI : return new ConnessioneRMI();
		default : throw new DataFormatException("il tipo di connessione inserito non è corretto");
		}
	}
}

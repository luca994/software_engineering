package client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.zip.DataFormatException;

import eccezione.NomeGiaScelto;

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
	 * @throws DataFormatException if the type of connection (tipoConnessione) isn't correct
	 * @throws IOException if there is a problem in the socket connection
	 * @throws NotBoundException if the name of the reference in the RMI registry is not currently bound
	 * @throws NomeGiaScelto if the name of the player is already taken
	 */
	public Connessione createConnessione(int tipoConnessione, String host, int port, String nome, String mappa) throws DataFormatException, IOException, NotBoundException, NomeGiaScelto{	
		switch(tipoConnessione){
		case CONNESSIONE_SOCKET : return new ConnessioneSocket(view, host, port, nome, mappa);
		case CONNESSIONE_RMI : return new ConnessioneRMI(view, host, port, nome, mappa);
		default : throw new DataFormatException("il tipo di connessione inserito non è corretto");
		}
	}
}

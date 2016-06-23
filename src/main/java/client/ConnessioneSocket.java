package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.view.View;

public class ConnessioneSocket implements Connessione, Runnable {

	private static final Logger LOG = Logger.getLogger(ConnessioneSocket.class.getName());

	private Socket socket;

	private ObjectInputStream streamIn;

	private ObjectOutputStream streamOut;

	private View view;

	/**
	 * creates a socket connection with the server and initializes the stream of
	 * object
	 * 
	 * @param view
	 *            the view to which the connection is related
	 * @param host
	 *            the host of the server
	 * @param port
	 *            the port of the connection
	 * @throws IOException
	 *             if there is a problem in the socket connection
	 */
	public ConnessioneSocket(View view, String host, int port, String nome, String mappa) throws IOException{
		this.view = view;
		socket = new Socket(host, port);
		streamIn = new ObjectInputStream(socket.getInputStream());
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		new Thread(this).start();
		inviaOggetto(nome);
		inviaOggetto(mappa);

	}

	/**
	 * reads the socket and calls the method riceviOggetto of the view to pass
	 * the object to the view
	 */
	@Override
	public void run() {
		while (socket != null && !socket.isClosed()) {
			try {
				Object oggetto = streamIn.readObject();
				view.riceviOggetto(oggetto);
			} catch (IOException | ClassNotFoundException e) {
				LOG.log(Level.SEVERE, "Server Disconnesso");
				disconnetti();
			}
		}
	}

	/**
	 * writes an object on the socket, then sends it out
	 * 
	 * @param oggetto
	 *            the object to send
	 * @throws IOException
	 *             if there is a problem with the connection
	 */
	@Override
	public synchronized void inviaOggetto(Object oggetto) {
		try {
			streamOut.writeObject(oggetto);
			streamOut.flush();
			streamOut.reset();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Server Disconnesso");
			disconnetti();
		}
	}
	
	public synchronized void disconnetti(){
		if (streamOut != null) {
			try {
				streamOut.close();
			} catch (IOException e) {
				LOG.log(Level.FINE, e.toString(), e);
			}
		}
		try {
			streamIn.close();
			socket.close();
		} catch (IOException e) {
			LOG.log(Level.FINE, "Il socket è già chiuso: " + e.toString(), e);
		}
		socket = null;
	}

}

package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 * @throws UnknownHostException
	 *             if the host isn't correct or is unreachable
	 * @throws IOException
	 *             if there is a problem in the socket connection
	 */
	public ConnessioneSocket(View view, String host, int port, String nome, String mappa) {
		try {
			this.view = view;
			socket = new Socket(host, port);
			streamIn = new ObjectInputStream(socket.getInputStream());
			streamOut = new ObjectOutputStream(socket.getOutputStream());
			new Thread(this).start();
			inviaOggetto(nome);
			inviaOggetto(mappa);
		} catch (IOException e) {
			LOG.log(Level.FINEST, "Server Disconnesso");
		}
	}

	/**
	 * reads the socket and calls the method riceviOggetto of the view to pass
	 * the object to the view
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Object oggetto = streamIn.readObject();
				view.riceviOggetto(oggetto);
			} catch (IOException | ClassNotFoundException e) {
				LOG.log(Level.FINEST, "Server Disconnesso");
				break;
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
	public synchronized void inviaOggetto(Object oggetto) throws IOException {
		streamOut.writeObject(oggetto);
		streamOut.flush();
		streamOut.reset();
	}

}

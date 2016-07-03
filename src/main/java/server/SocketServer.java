package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.config.Configurazione;

/**
 * the socket server class , opens the socket server on port set and receives
 * client socket connections
 *
 */
public class SocketServer {

	private static final Logger LOG = Logger.getLogger(SocketServer.class.getName());

	private final ServerSocket serverSocket;

	private boolean segnaleStop = false;

	/**
	 * builds a server on port "PORT"
	 * 
	 * @throws IOException
	 *             if an I/O error occurs when opening the socket
	 */
	public SocketServer() throws IOException {
		serverSocket = new ServerSocket(Configurazione.SOCKET_PORT);
	}

	/**
	 * Waits until a connection is established, then return the socket.
	 * 
	 * @return the socket of the connection
	 * @throws IOException
	 *             if an I/O error occurs when opening the socket
	 */
	public void startSocket() {
		try {
			LOG.log(Level.INFO, "SERVER SOCKET PRONTO NELLA PORTA: " + Configurazione.SOCKET_PORT);
			while (!segnaleStop)
				accettaConnessioni();
			serverSocket.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "SERVER SOCKET GIA CHIUSO", e);
		}
	}

	private void accettaConnessioni() {
		Socket nuovoSocket;
		try {
			nuovoSocket = serverSocket.accept();
			if (nuovoSocket != null && !segnaleStop) {
				GestorePartite.getGestorePartite().getGiocatoriAttesa().add(nuovoSocket);
				LOG.log(Level.INFO, "NUOVO GIOCATORE CONNESSO AL SERVER SOCKET");
			}
		} catch (IOException e) {
			LOG.log(Level.INFO, "CONNESSIONE SOCKET CHIUSA", e);
		}
	}

	/**
	 * closes connections to the server
	 */
	public synchronized void spegni() {
		segnaleStop = true;
		try {
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException e) {
			LOG.log(Level.FINEST, e.toString(), e);
		}
	}

	/**
	 * checks if the server is up and running
	 * 
	 */
	public synchronized boolean isAttivo() {
		if (serverSocket == null)
			return true;
		if (serverSocket.isClosed())
			return true;
		return false;
	}

	public synchronized boolean isSpento() {
		if (serverSocket == null)
			return false;
		if (serverSocket.isBound() && !serverSocket.isClosed())
			return true;
		return false;
	}
}
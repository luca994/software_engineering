package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Luca
 *
 */
public class SocketServer {

	private static final Logger LOG = Logger.getLogger(SocketServer.class.getName());

	private static final int PORT = 29999;
	
	private final ServerSocket serverSocket;

	private boolean segnaleStop=false;

	/**
	 * builds a server on port "PORT"
	 * 
	 * @throws IOException
	 *             if an I/O error occurs when opening the socket
	 */
	public SocketServer() throws IOException {
		serverSocket = new ServerSocket(PORT);
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
			LOG.log(Level.INFO, "SERVER SOCKET PRONTO NELLA PORTA: " + PORT);
			while(!segnaleStop)
				accettaConnessioni();
			serverSocket.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "SERVER SOCKET GIA CHIUSO",e);
		}
	}

	public void accettaConnessioni() {
		Socket nuovoSocket;
		try {
			nuovoSocket = serverSocket.accept();
			if (nuovoSocket != null && !segnaleStop){
				GestorePartite.getGestorePartite().getGiocatoriAttesa().add(nuovoSocket);
				LOG.log(Level.INFO, "NUOVO GIOCATORE CONNESSO AL SERVER SOCKET");
			}
		} catch (IOException e) {
			LOG.log(Level.INFO, "CONNESSIONE SOCKET CHIUSA",e);
		}
	}
	
	
	public synchronized void spegni(){
		segnaleStop = true;
        try {
            if(serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            LOG.log(Level.FINEST, e.toString(), e);
        }
    }	
	
    public synchronized boolean isAttivo() {
        if(serverSocket == null)
            return true;
        if(serverSocket.isClosed())
            return true;
        return false;
    }

    
    public synchronized boolean isSpento() {
        if(serverSocket == null)
            return false;
        if(serverSocket.isBound() && !serverSocket.isClosed())
            return true;
        return false;
    }	
}
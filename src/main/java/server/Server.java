package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.jdom2.JDOMException;

/**
 * @author Luca
 *
 */
public class Server {

	private static final int PORT = 29999;
	private final ServerSocket serverSocket;

	/**
	 * builds a server on port "PORT"
	 * 
	 * @throws IOException
	 *             if an I/O error occurs when opening the socket
	 */
	public Server() throws IOException {
		serverSocket = new ServerSocket(PORT);
	}

	/**
	 * Waits until a connection is established, then return the socket.
	 * 
	 * @return the socket of the connection
	 * @throws IOException
	 *             if an I/O error occurs when opening the socket
	 * @throws ClassNotFoundException
	 * @throws JDOMException
	 */
	public Socket startSocket() throws IOException {
		System.out.println("SERVER SOCKET READY ON PORT: " + PORT);
		return serverSocket.accept();
	}
}
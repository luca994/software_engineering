/**
 * 
 */
package client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Luca
 *
 */
public class ClientSocket {

	private final static int PORT = 29999;
	
	private final static String IP = "127.0.0.1";

	private Socket socket;
	
	public void startClient() throws UnknownHostException, IOException {
		
		socket = new Socket(IP, PORT);
		
		System.out.println("Connection created");

		ExecutorService executor = Executors.newFixedThreadPool(2);

		executor.submit(new ClientOutHandler(new ObjectOutputStream(socket.getOutputStream())));

		executor.submit(new ClientInHandler(new ObjectInputStream(socket.getInputStream())));
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		ClientSocket client=new ClientSocket();
		client.startClient();
	}
}

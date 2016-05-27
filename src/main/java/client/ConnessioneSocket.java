package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnessioneSocket implements Connessione, Runnable {

	private Socket socket;
	private ObjectInputStream streamIn;
	private ObjectOutputStream streamOut;
	private View view;
	
	/**
	 * creates a socket connection with the server and initializes the stream of object
	 * @param view the view to which the connection is related
 	 * @param host the host of the server
	 * @param port the port of the connection
	 * @throws UnknownHostException if the host isn't correct or is unreachable
	 * @throws IOException if there is a problem in the socket connection
	 */
	public ConnessioneSocket(View view, String host, int port) throws IOException{
		this.view=view;
		socket = new Socket(host, port);
		streamIn=new ObjectInputStream(socket.getInputStream());
		streamOut=new ObjectOutputStream(socket.getOutputStream());
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Object oggetto = streamIn.readObject();
				view.riceviOggetto(oggetto);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * writes an object on the socket, then sends it out 
	 * @param oggetto the object to send
	 * @throws IOException if there is a problem with the connection
	 */
	@Override
	public void inviaOggetto(Object oggetto) throws IOException {
		streamOut.writeObject(oggetto);
		streamOut.flush();
	}
	
	
}

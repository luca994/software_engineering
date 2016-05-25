/**
 * 
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jdom2.JDOMException;

import client.Login;
import server.view.ServerSocketView;
import server.controller.Controller;
import server.model.Giocatore;
import server.model.Gioco;

/**
 * @author Luca
 *
 */
public class Server {

	private final static int PORT = 29999;
	private final String NAME = "Consiglio dei quattro";
	
	private ControllerConnessioni controllerConnessioni;
	
	public Server(){
	}
	
	
	private void startSocket() throws IOException, ClassNotFoundException, JDOMException  {
		
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("SERVER SOCKET READY ON PORT: " + PORT);
	
		controllerConnessioni = new ControllerConnessioni();
		
		while (true) {
			
			Socket socket = serverSocket.accept();
			
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}

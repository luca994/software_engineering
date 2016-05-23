/**
 * 
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	
	private List<Gioco> giochi;
	private List<Controller> controllers;
	
	public Server(){
		this.giochi= new ArrayList<>();
		this.controllers= new ArrayList<>();
	}
	
	
	private void startSocket() throws IOException {
		
		List<Giocatore> giocatori= new ArrayList<>();
		
		ExecutorService executor = Executors.newCachedThreadPool();

		ServerSocket serverSocket = new ServerSocket(PORT);

		System.out.println("SERVER SOCKET READY ON PORT" + PORT);
		
		while (true) {
			int NumGiocatori=0;
			Gioco gioco = new Gioco();
			while(NumGiocatori<2){
				Socket socket = serverSocket.accept();
				ServerSocketView view = new ServerSocketView(gioco,socket);
				
				gioco.notificaObservers("inserisci nome e colore");
				
				Giocatore nuovoGiocatore = new Giocatore(null, null);
				NumGiocatori++;
			}
		/*	ServerSocketView view = new ServerSocketView(socket, this.gioco);
			this.gioco.registerObserver(view);
			view.registerObserver(this.controller);
			executor.submit(view);*/
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}

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
	
	//private Gioco gioco;
	//private Controller controller;
	
	public void startServer() throws ClassNotFoundException, IOException, JDOMException{
		Gioco localGioco= new Gioco();
		Controller localController= new Controller(localGioco);
		try{
			startSocket(localGioco, localController);
			//nuovo RMI server
		}
		catch(SocketTimeoutException e)
		{
			startServer();
		}
	}
	
	
	private void startSocket(Gioco gioco, Controller controller) throws IOException, ClassNotFoundException, JDOMException  {
		
		List<Giocatore> giocatori= new ArrayList<>();
		
		ExecutorService executor = Executors.newCachedThreadPool();

		ServerSocket serverSocket = new ServerSocket(PORT);
		
		System.out.println("SERVER SOCKET READY ON PORT: " + PORT);
		int NumGiocatori=0;
		try{
			while(true){
				if(NumGiocatori>2)
					serverSocket.setSoTimeout(20000);
				Socket socket = serverSocket.accept();
				ObjectInputStream socketin=new ObjectInputStream(socket.getInputStream());
				Login loginGiocatore=(Login) socketin.readObject();
				Giocatore nuovoGiocatore = new Giocatore(loginGiocatore.getNome(), loginGiocatore.getColore());
				giocatori.add(nuovoGiocatore);
				ServerSocketView view = new ServerSocketView(gioco,socket);
				view.registerObserver(controller);
				NumGiocatori++;
				executor.submit(view);
			}
		}
		catch(SocketTimeoutException e){
			gioco.inizializzaPartita(giocatori);
			throw e;
		}
		
		/*	ServerSocketView view = new ServerSocketView(socket, this.gioco);
			this.gioco.registerObserver(view);
			view.registerObserver(this.controller);
			*/
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}

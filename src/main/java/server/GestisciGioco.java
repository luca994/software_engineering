package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.jdom2.JDOMException;

import server.controller.Controller;
import server.model.Giocatore;
import server.model.Gioco;
import server.view.ServerSocketView;

public class GestisciGioco implements Runnable{

	private final Server server;
	private List<Socket> giocatoriAttesa;
	private List<Controller> controllersGiochi;
	private AtomicLong timer;
	
	/**
	 * builds an object GestisciGioco
	 * @throws IOException if there is an error while the server is waiting the connection
	 */
	public GestisciGioco() throws IOException{
		this.server=new Server();
		giocatoriAttesa = Collections.synchronizedList(new ArrayList<>());
		controllersGiochi=new ArrayList<>();
		timer = new AtomicLong();
	}
	
	/**
	 * builds the games with a list of players who connect to the server.
	 * When the timer expires, the method builds an other game.
	 * @throws IOException if there is an error while the server is waiting the connection
	 * @throws ClassNotFoundException if the class of the input object of the socket cannot be found
	 */
	public void creaGiochi() throws ClassNotFoundException, IOException {
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.submit(this);
		List<Giocatore> giocatori = new ArrayList<>();
		while(true){
			int numGiocatori=0;
			Gioco gioco=new Gioco();
			Controller controller = new Controller(gioco);
			timer.set(System.currentTimeMillis());
			while(numGiocatori<2 || (numGiocatori>=2 && (System.currentTimeMillis()-timer.get())<20000 )){
				if(!giocatoriAttesa.isEmpty()){
					Socket socket = giocatoriAttesa.get(0);
					giocatoriAttesa.remove(0);
					ObjectInputStream streamIn=new ObjectInputStream(socket.getInputStream());
					String nome=(String) streamIn.readObject();
					Giocatore giocatore = new Giocatore(nome);
					giocatori.add(giocatore);
					ObjectOutputStream streamOut = new ObjectOutputStream(socket.getOutputStream());
					streamOut.writeObject(giocatore);
					streamOut.flush();
					ServerSocketView serverView = new ServerSocketView(gioco, socket, giocatore);
					executor.submit(serverView);
					numGiocatori++;
				}
			}
			System.out.println("Gioco creato");
			controllersGiochi.add(controller);
			gioco.inizializzaPartita();
			gioco.notificaObservers(gioco);
			giocatori.clear();
		}
	}

	/**
	 * runs a socket server waiting for a connection, if someone connects to this server
	 * the method adds the socket in the list giocatoriAttesa
	 */
	@Override
	public void run() {
		try {
			Socket socket;
			while(true){
				socket=server.startSocket();
				timer.set(System.currentTimeMillis());
				giocatoriAttesa.add(socket);
				System.out.println("Aggiunto Giocatore");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}

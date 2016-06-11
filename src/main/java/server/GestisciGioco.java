package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import client.ConnessioneRMIInterface;
import eccezione.NomeGiaScelto;
import server.controller.Controller;
import server.model.Giocatore;
import server.model.Gioco;
import server.view.ServerRMIView;
import server.view.ServerRMIViewInterface;
import server.view.ServerSocketView;

public class GestisciGioco implements Runnable {

	private final SocketServer server;
	private List<Socket> giocatoriAttesa;
	private List<Controller> controllersGiochi;
	private AtomicLong timer;
	private ExecutorService executor;
	private Gioco gioco;
	private Controller controller;
	private List<Giocatore> giocatori;
	private static final int PORT = 1099;
	private Registry registry;
	private static final String NAME = "consiglioDeiQuattroRegistro";
	private String mappa;

	/**
	 * builds an object GestisciGioco
	 * 
	 * @throws IOException
	 *             if there is an error while the server is waiting the
	 *             connection
	 */
	public GestisciGioco() throws IOException {
		this.server = new SocketServer();
		giocatoriAttesa = Collections.synchronizedList(new ArrayList<>());
		controllersGiochi = new ArrayList<>();
		timer = new AtomicLong();
		executor = Executors.newCachedThreadPool();
		giocatori = Collections.synchronizedList(new ArrayList<>());
	}

	/**
	 * builds the games with a list of players who connect to the server. When
	 * the timer expires, the method builds an other game.
	 * 
	 * @throws IOException
	 *             if there is an error while the server is waiting the
	 *             connection
	 */
	public void creaGiochi() throws IOException {
		executor.submit(this);
		while (true) {
			gioco = new Gioco();
			controller = new Controller(gioco);
			timer.set(System.currentTimeMillis());
			while (giocatori.size() < 2 || (giocatori.size() >= 2 && (System.currentTimeMillis() - timer.get()) < 2000)) {
				if (!giocatoriAttesa.isEmpty()) {
					aggiungiGiocatoreSocket();
				}
			}
			System.out.println("Gioco creato");
			controllersGiochi.add(controller);
			gioco.setGiocatori(giocatori);
			giocatori = Collections.synchronizedList(new ArrayList<>());
			gioco.inizializzaPartita(mappa);
			executor.submit(gioco);
		}
	}

	/**
	 * takes the first player from the list of sockets giocatoriAttesa, then add
	 * it to the list of players of the game
	 * 
	 * @throws IOException
	 *             if there is an error while the server is waiting the
	 *             connection
	 * @throws ClassNotFoundException
	 *             if the class of the input object of the socket cannot be
	 *             found
	 */
	public void aggiungiGiocatoreSocket() throws IOException {
		try {
			Socket socket = giocatoriAttesa.remove(0);
			ObjectInputStream streamIn = new ObjectInputStream(socket.getInputStream());
			String nome = (String) streamIn.readObject();
			streamIn = new ObjectInputStream(socket.getInputStream());
			String mappaTemp = (String) streamIn.readObject();
			if(giocatori.isEmpty()){
				this.mappa=mappaTemp;
			}
			if(!controllaNome(nome)){
				ObjectOutputStream streamOut = new ObjectOutputStream(socket.getOutputStream());
				streamOut.writeObject(new NomeGiaScelto("Il nome è già stato scelto"));
				streamOut.flush();
				throw new NomeGiaScelto();
			}
			Giocatore giocatore = new Giocatore(nome);
			ObjectOutputStream streamOut = new ObjectOutputStream(socket.getOutputStream());
			streamOut.writeObject(giocatore);
			streamOut.flush();
			ServerSocketView serverView = new ServerSocketView(gioco, socket, giocatore);
			serverView.registerObserver(controller);
			executor.submit(serverView);
			giocatori.add(giocatore);
		} catch (ClassNotFoundException | NomeGiaScelto e) {
			System.out.println("Oggetto ricevuto non valido o nome già utilizzato, giocatore non aggiunto");
		}
	}
	
	/**
	 * checks if the name of the player is already taken
	 * @param nome the name you want to control
	 * @return true if the name isn't taken yet, otherwise false 
	 */
	public boolean controllaNome(String nome){
		for(Giocatore g:giocatori){
			if(g.getNome().equals(nome))
				return false;
		}
		return true;
	}

	/**
	 * add a player connected through RMI to the game
	 * 
	 * @param giocatore
	 *            the player connected
	 * @param client
	 *            the client of the player
	 * @return return a new ServerRMIView
	 */
	public ServerRMIViewInterface aggiungiGiocatoreRMI(Giocatore giocatore, String mappa,ConnessioneRMIInterface client) {
		if(!controllaNome(giocatore.getNome()))
			return null;
		if(giocatori.isEmpty())
			this.mappa=mappa;
		giocatori.add(giocatore);
		ServerRMIViewInterface viewRMI = new ServerRMIView(gioco, giocatore, client, PORT);
		((ServerRMIView) viewRMI).registerObserver(controller);
		try {
			client.passaOggetto(giocatore);
			timer.set(System.currentTimeMillis());
			System.out.println("Aggiunto giocatore RMI");
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("giocatore RMI non aggiunto");
		}
		return viewRMI;
	}

	/**
	 * runs a socket server waiting for a connection, if someone connects to
	 * this server the method adds the socket in the list giocatoriAttesa
	 */
	@Override
	public void run() {
		try {
			while (true) {
				Socket socket = server.startSocket();
				timer.set(System.currentTimeMillis());
				giocatoriAttesa.add(socket);
				System.out.println("Aggiunto giocatore socket");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * starts the RMI server and create a registry with a ServerRMIInterface
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public void startRMI() throws RemoteException, AlreadyBoundException {
		registry = LocateRegistry.createRegistry(PORT);
		ServerRMIInterface serverRMI = new ServerRMI(this);
		ServerRMIInterface serverRMIRemote = (ServerRMIInterface) UnicastRemoteObject.exportObject(serverRMI, PORT);
		registry.bind(NAME, serverRMIRemote);
		System.out.println("RMI registry on port: "+PORT);
	}
}

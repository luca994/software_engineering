package server;

import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import client.ConnessioneRMIInterface;
import server.config.Configurazione;
import server.controller.Controller;
import server.eccezioni.NomeGiaScelto;
import server.model.Giocatore;
import server.model.Gioco;
import server.view.ServerRMIView;
import server.view.ServerRMIViewInterface;
import server.view.ServerSocketView;

/**
 *
 * the class GestorePartite is responsible for managing the various game that
 * can be created simultaneously . It adds and removes players from the game or
 * via rmi socket and handles start the timer when there are two players and
 * start the game at the right time. It can be instanziated only one time
 * (singleton).
 */
public class GestorePartite implements Runnable {

	private static final Logger LOG = Logger.getLogger(GestorePartite.class.getName());
	private static GestorePartite gestorePartite = null;
	private List<Socket> giocatoriAttesa;
	private List<Controller> controllersGiochi;
	private AtomicLong timer;
	private ExecutorService executor;
	private Gioco gioco;
	private Controller controller;
	private List<Giocatore> giocatori;
	private Registry registry;
	private String mappa;

	/**
	 * builds an object GestisciGioco
	 * 
	 * @throws IOException
	 *             if there is an error while the server is waiting the
	 *             connection
	 */
	private GestorePartite() {
		giocatoriAttesa = Collections.synchronizedList(new ArrayList<>());
		controllersGiochi = new ArrayList<>();
		timer = new AtomicLong();
		executor = Executors.newCachedThreadPool();
		giocatori = Collections.synchronizedList(new ArrayList<>());
	}

	/**
	 * the method to call to have the reference to the only existent instance of
	 * GestorePartite
	 * 
	 * @return
	 */
	public static synchronized GestorePartite getGestorePartite() {
		if (gestorePartite == null)
			gestorePartite = new GestorePartite();
		return gestorePartite;
	}

	/**
	 * builds the games with a list of players who connect to the server. When
	 * the timer expires, starts the current game and builds other game.
	 * 
	 * @throws IOException
	 *             if there is an error while the server is waiting the
	 *             connection
	 */
	public void creaGiochi() {
		executor.submit(this);
		while (true) {
			gioco = new Gioco();
			controller = new Controller(gioco);
			timer.set(System.currentTimeMillis());
			while (giocatori.size() < 2 || (giocatori.size() >= 2
					&& (System.currentTimeMillis() - timer.get()) < Configurazione.TEMPO_ATTESA_AVVIO_PARTITA)) {
				if (!giocatoriAttesa.isEmpty()) {
					timer.set(System.currentTimeMillis());
					aggiungiGiocatoreSocket();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					LOG.log(Level.SEVERE, "Gestore partite interrotto?!");
				}
			}
			LOG.log(Level.INFO, "NUOVA PARTITA CREATA");
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
	public void aggiungiGiocatoreSocket() {
		try {
			ServerSocketView serverSocketView = new ServerSocketView(gioco, giocatoriAttesa.remove(0));
			String nomeGiocatore = serverSocketView.riceviString();
			String mappaTemp = serverSocketView.riceviString();
			if (giocatori.isEmpty()) {
				this.mappa = mappaTemp;
			}
			if (!controllaNome(nomeGiocatore)) {
				serverSocketView.inviaOggetto(new NomeGiaScelto("Il nome è già stato scelto"));
				throw new NomeGiaScelto();
			}
			Giocatore giocatore = new Giocatore(nomeGiocatore);
			serverSocketView.inviaOggetto(giocatore);
			serverSocketView.setGiocatore(giocatore);
			serverSocketView.registerObserver(controller);
			executor.submit(serverSocketView);
			giocatori.add(giocatore);
			serverSocketView.inviaOggetto("Giocatore aggiunto alla partita");
		} catch (NomeGiaScelto e) {
			LOG.log(Level.INFO, "Nome già utilizzato, giocatore non aggiunto");
		} catch (IOException e1) {
			LOG.log(Level.SEVERE, "CONNESSIONE COL CLIENT NON STABILITA", e1);
		}

	}

	/**
	 * checks if the name of the player is already taken
	 * 
	 * @param nome
	 *            the name you want to control
	 * @return true if the name isn't taken yet, otherwise false
	 */
	public boolean controllaNome(String nome) {
		if (nome.equals("dummy"))
			return false;
		for (Giocatore g : giocatori) {
			if (g.getNome().equals(nome))
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
	public ServerRMIViewInterface aggiungiGiocatoreRMI(Giocatore giocatore, String mappa,
			ConnessioneRMIInterface client) {
		if (!controllaNome(giocatore.getNome()))
			return null;
		if (giocatori.isEmpty())
			this.mappa = mappa;
		giocatori.add(giocatore);
		ServerRMIViewInterface viewRMI = new ServerRMIView(gioco, giocatore, client, Configurazione.RMI_PORT);
		((ServerRMIView) viewRMI).registerObserver(controller);
		try {
			client.passaOggetto(giocatore);
			client.passaOggetto("Giocatore aggiunto alla partita");
			timer.set(System.currentTimeMillis());
			LOG.log(Level.INFO, "NUOVO GIOCATORE CONNESSO CON RMI");
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "ERRORE NELL'AGGIUNGERE GIOCATORE RMI", e);
		}
		return viewRMI;
	}

	/**
	 * starts the RMI server and create a registry with a ServerRMIInterface
	 * 
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public void startRMI() throws RemoteException, AlreadyBoundException {
		registry = LocateRegistry.createRegistry(Configurazione.RMI_PORT);
		ServerRMIInterface serverRMI = new ServerRMI(this);
		ServerRMIInterface serverRMIRemote = (ServerRMIInterface) UnicastRemoteObject.exportObject(serverRMI,
				Configurazione.RMI_PORT);
		registry.bind(Configurazione.RMI_REGISTRY_NAME, serverRMIRemote);
		LOG.log(Level.INFO, "RMI registry on port: " + Configurazione.RMI_PORT);
	}

	/**
	 * starts the socket server
	 */
	public void startSocket() {
		try {
			SocketServer socketServer = new SocketServer();
			socketServer.startSocket();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "ERRORE NELL AVVIO DEL SOCKET SERVER", e);
			LOG.log(Level.INFO, "SOCKET SERVER NON CONNESSO", e);
		}
	}

	/**
	 * runs a socket server waiting for a connection, if someone connects to
	 * this server the method adds the socket in the list giocatoriAttesa
	 */
	@Override
	public void run() {
		try {
			LOG.log(Level.INFO, "Tempo durata turni: " + Configurazione.getMaxTimeForTurn() / 1000 + " Secondi");
			startRMI();
			startSocket();
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the giocatoriAttesa
	 */
	public synchronized List<Socket> getGiocatoriAttesa() {
		return giocatoriAttesa;
	}
}

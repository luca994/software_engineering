/**
 * 
 */
package server.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.bonus.Bonus;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.componenti.Citta;
import server.model.componenti.OggettoVendibile;



public class ServerSocketView extends ServerView implements Runnable {


	private static final Logger LOG = Logger.getLogger(ServerSocketView.class.getName());
	
	private Socket socket;
	
	private final ObjectOutputStream socketOut;

	private final ObjectInputStream socketIn;
	
	private AzioneFactory azioneFactory;
	
	private Bonus bonusDaCompletare;

	private Semaphore semBonus;

	/**
	 * builds a server socket view
	 * 
	 * @param gioco
	 *            the game to which the view is connected
	 * @param socket
	 *            the socket to which the player client is connected
	 * @param nuovoGiocatore
	 *            the player connected to this view
	 * @throws IOException 
	 */
	public ServerSocketView(Gioco gioco, Socket socket) throws IOException{
		gioco.registerObserver(this);
		socketOut = new ObjectOutputStream(socket.getOutputStream());
		socketIn = new ObjectInputStream(socket.getInputStream());
		this.socket = socket;
		azioneFactory = new AzioneFactory(gioco);
		semBonus = new Semaphore(0);
	}

	/**
	 * asks an input to the player
	 * 
	 * @param oggetto
	 *            the object you want to send to the player
	 */
	public synchronized void inviaOggetto(Object oggetto) {
		try {
			socketOut.writeObject(oggetto);
			socketOut.flush();
			socketOut.reset();
		} catch (IOException e) {
			LOG.log(Level.FINEST, e.toString(), e);
			disconnetti();
		}
	}
	
	public synchronized void disconnetti() {
		if (socketOut != null) {
			try {
				socketOut.writeObject("Disconnetti");
				socketOut.flush();
				socketOut.reset();
			} catch (IOException e) {
				LOG.log(Level.FINER, e.toString(), e);
			}
		}
		try {
			socketOut.close();
			socketIn.close();
			socket.close();
		} catch (IOException e) {
			LOG.log(Level.FINE, "Il socket è già chiuso: " + e.toString(), e);
		}
		socket = null;
		this.notificaObservers("Sospendi", getGiocatore());
	}
	
	public synchronized String riceviString(){
		try {
			return (String)socketIn.readObject();
		} catch (ClassNotFoundException e) {
			LOG.log(Level.FINE, "Errore nella lettura della stringa" , e);
		} catch (IOException e1) {
			LOG.log(Level.SEVERE, "ERRORE NELLA CONNESSIONE CON CLIENT");
			disconnetti();
		}
		return null;
	}


	/**
	 * reads the socket and does an action when receives an object
	 * 
	 * @param Temp
	 */
	@Override
	public void run() {
		while (socket!=null && !socket.isClosed() ) {
			try {
				Object object = socketIn.readObject();
				if (object instanceof BonusGettoneCitta) {
					List<Citta> tmp = new ArrayList<>(((BonusGettoneCitta) object).getCitta());
					if (!"passa".equals(tmp.get(0).getNome()))
						((BonusGettoneCitta) bonusDaCompletare).setCittaPerCompletamentoBonus(tmp.get(0));
					semBonus.release();
				}
				if (object instanceof BonusTesseraPermesso) {
					((BonusTesseraPermesso) bonusDaCompletare).setTessera(((BonusTesseraPermesso) object).getTessera());
					semBonus.release();
				}
				if (object instanceof BonusRiutilizzoCostruzione) {
					((BonusRiutilizzoCostruzione) bonusDaCompletare)
							.setTessera(((BonusRiutilizzoCostruzione) object).getTessera());
					semBonus.release();
				}
				if (object instanceof AzioneFactory) {
					azioneFactory.setTipoAzione(((AzioneFactory) object).getTipoAzione());
					if (azioneFactory.completaAzioneFactory(((AzioneFactory) object), getGiocatore())) {
						Azione azioneGiocatore = azioneFactory.createAzione();
						azioneFactory = new AzioneFactory(azioneFactory.getGioco());
						this.notificaObservers(azioneGiocatore, getGiocatore());
					} else {
						inviaOggetto("Parametri dell'azione errati, la view è stata modificata");
					}
				}

				if (object instanceof OggettoVendibile) {
					this.notificaObservers(cercaOggettoVendibile(((OggettoVendibile) object)), getGiocatore());
				}
				if (object instanceof String) {
					this.notificaObservers(object, getGiocatore());
				}
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Il giocatore " + getGiocatore().getNome() + " si è disconnesso",e);
				disconnetti();
			} catch (ClassNotFoundException e1) {
				LOG.log(Level.SEVERE, "OGGETTO SCONOSCIUTO RICEVUTO",e1);
				disconnetti();
			}
		}
	}

	/**
	 * sends an object to the client
	 */
	@Override
	public void update(Object cambiamento) {
		inviaOggetto(cambiamento);
	}

	/**
	 * * if cambiamento is a bonus, the method sends it to the client (only if
	 * this is the view of the player attributo), waits for an attribute, then
	 * sends the attribute and the bonus to the controller. if cambiamento is
	 * another type of object the method sends the object to the client only if
	 * this is the view of the player attributo
	 */

	@Override
	public void update(Object cambiamento, Giocatore attributo) {

		if (cambiamento instanceof Bonus && getGiocatore().equals(attributo)) {
			bonusDaCompletare = (Bonus) cambiamento;
			inviaOggetto(cambiamento);
			try {
				semBonus.acquire();
				this.notificaObservers((Bonus) cambiamento, this.getGiocatore());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (getGiocatore().equals(attributo)) {
			inviaOggetto(cambiamento);
		}	}

	@Override
	public void update(Bonus cambiamento, List<String> input) {
		throw new IllegalArgumentException();
	}
}

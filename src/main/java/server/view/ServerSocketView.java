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

import server.model.Assistente;
import server.model.CartaPolitica;
import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.OggettoVendibile;
import server.model.TesseraCostruzione;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.bonus.Bonus;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;

/**
 * @author Massimiliano Ventura
 *
 */
public class ServerSocketView extends ServerView implements Runnable {

	private Socket socket;
	private Giocatore giocatore;
	private List<String> input;
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
	 */
	public ServerSocketView(Gioco gioco, Socket socket, Giocatore nuovoGiocatore) {
		gioco.registerObserver(this);
		this.giocatore = nuovoGiocatore;
		this.socket = socket;
		azioneFactory = new AzioneFactory(gioco);
		semBonus = new Semaphore(0);
		input = new ArrayList<>(2);
	}

	/**
	 * asks an input to the player
	 * 
	 * @param oggetto
	 *            the object you want to send to the player
	 */
	public synchronized void inviaOggetto(Object oggetto) {
		try {
			ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketOut.writeObject(oggetto);
			socketOut.flush();
		} catch (IOException e) {
			System.err.println("socket del giocatore " + giocatore.getNome()
					+ " non connesso o errore nella creazione dello stream");
		}
	}

	/**
	 * reads the socket and does an action when receives an object
	 * 
	 * @param Temp
	 */
	@Override
	public void run() {
		while (true) {
			try {
				ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
				Object object = socketIn.readObject();
				if (object instanceof BonusGettoneCitta) {
					List<Citta> tmp = new ArrayList<>(((BonusGettoneCitta) object).getCitta());
					if(!tmp.get(0).getNome().equals("passa"))
						((BonusGettoneCitta) bonusDaCompletare).getCitta().add(tmp.get(0));
					semBonus.release();
				}
				if (object instanceof BonusTesseraPermesso){
					((BonusTesseraPermesso) bonusDaCompletare).setTessera(((BonusTesseraPermesso) object).getTessera());
					semBonus.release();
				}
				if(object instanceof BonusRiutilizzoCostruzione){
					((BonusRiutilizzoCostruzione) bonusDaCompletare).setTessera(((BonusRiutilizzoCostruzione) object).getTessera());
					semBonus.release();
				}
				if (object instanceof AzioneFactory) {
					azioneFactory.setTipoAzione(((AzioneFactory) object).getTipoAzione());
					if (azioneFactory.completaAzioneFactory(((AzioneFactory) object), giocatore)) {
						Azione azioneGiocatore = azioneFactory.createAzione();
						azioneFactory = new AzioneFactory(azioneFactory.getGioco());
						this.notificaObservers(azioneGiocatore, giocatore);
					} else {
						inviaOggetto("Parametri dell'azione errati, la view è stata modificata");
					}
				}

				if (object instanceof OggettoVendibile){
					this.notificaObservers(cercaOggettoVendibile(((OggettoVendibile) object)), giocatore);
				}
				if (object instanceof String){
					this.notificaObservers(object, giocatore);
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public OggettoVendibile cercaOggettoVendibile(OggettoVendibile oggettoVendibileDaCercare) {
		if (oggettoVendibileDaCercare.getGiocatore() == null && oggettoVendibileDaCercare.getMercato() == null
				&& oggettoVendibileDaCercare.getPrezzo() > 0) {
			if (oggettoVendibileDaCercare instanceof CartaPolitica) {
				giocatore.cercaCarta((CartaPolitica) oggettoVendibileDaCercare)
						.setPrezzo(oggettoVendibileDaCercare.getPrezzo());
				return giocatore.cercaCarta((CartaPolitica) oggettoVendibileDaCercare);
			}
			if (oggettoVendibileDaCercare instanceof TesseraCostruzione) {
				giocatore.cercaTesseraCostruzione((TesseraCostruzione) oggettoVendibileDaCercare)
						.setPrezzo(oggettoVendibileDaCercare.getPrezzo());
				return giocatore.cercaTesseraCostruzione((TesseraCostruzione) oggettoVendibileDaCercare);
			}
			if (oggettoVendibileDaCercare instanceof Assistente && !giocatore.getAssistenti().isEmpty()) {
				giocatore.getAssistenti().get(0).setPrezzo(oggettoVendibileDaCercare.getPrezzo());
				return giocatore.getAssistenti().get(0);
			}
		}
		if (oggettoVendibileDaCercare.getGiocatore() != null && oggettoVendibileDaCercare.getMercato() != null
				&& oggettoVendibileDaCercare.getPrezzo() > 0)
			return oggettoVendibileDaCercare;
		return null;
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

		if (cambiamento instanceof Bonus && this.giocatore.equals(attributo)) {
			bonusDaCompletare = (Bonus) cambiamento;
			inviaOggetto(cambiamento);
			try {
				semBonus.acquire();
				this.notificaObservers((Bonus) cambiamento, input);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (this.giocatore.equals(attributo)) {
			inviaOggetto(cambiamento);
		}
	}

	@Override
	public void update(Bonus cambiamento, List<String> input) {
		throw new IllegalArgumentException();
	}
}

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
import server.model.Consigliere;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.OggettoVendibile;
import server.model.Regione;
import server.model.TesseraCostruzione;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.bonus.Bonus;
import server.observer.Observable;
import server.observer.Observer;

/**
 * @author Massimiliano Ventura
 *
 */
public class ServerSocketView extends Observable<Object, Bonus> implements Observer<Object, Bonus>, Runnable {

	private Socket socket;
	private Giocatore giocatore;
	private String[] input;
	private AzioneFactory azioneFactory;

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
				if (object instanceof Bonus) {
					input = (String[]) socketIn.readObject();
					semBonus.release();
				}
				if (object instanceof AzioneFactory) {
					azioneFactory.setTipoAzione(((AzioneFactory) object).getTipoAzione());
					if (completaAzioneFactory((AzioneFactory) object)) {
						Azione azioneGiocatore = azioneFactory.createAzione();
						azioneFactory = new AzioneFactory(azioneFactory.getGioco());
						this.notificaObservers(azioneGiocatore, giocatore);
					} else {
						inviaOggetto("Parametri dell'azione errati, la view è stata modificata");
					}
				}
				if (object instanceof OggettoVendibile)
					this.notificaObservers(cercaOggettoVendibile(((OggettoVendibile) object)),
							giocatore);
				else
					inviaOggetto("Parametri dell'azione errati, la view è stata modificata");

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException(e.getMessage());
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
	 * sets all the parameters of azioneFactory based on the azioneFactory sent
	 * by the client
	 * 
	 * @param azioneFactoryCompleta
	 *            the azioneFactory sent by the view
	 */
	public boolean completaAzioneFactory(AzioneFactory azioneFactoryCompleta) {
		if (azioneFactoryCompleta.getCartePolitica() != null) {
			List<CartaPolitica> carteAzione = new ArrayList<>();
			for (CartaPolitica c : azioneFactoryCompleta.getCartePolitica()) {
				if (giocatore.cercaCarta(c) != null) {
					carteAzione.add(giocatore.cercaCarta(c));
				} else {
					return false;
				}
			}
			azioneFactory.setCartePolitica(carteAzione);
		}

		if (azioneFactoryCompleta.getCitta() != null) {
			Citta cittaAzione = azioneFactory.getGioco().getTabellone()
					.cercaCitta(azioneFactoryCompleta.getCitta().getNome());
			if (cittaAzione == null)
				return false;
			azioneFactory.setCitta(cittaAzione);
		}

		if (azioneFactoryCompleta.getConsigliere() != null) {
			Consigliere consigliereAzione = azioneFactory.getGioco().getTabellone()
					.getConsigliereDaColore(azioneFactoryCompleta.getConsigliere().getColore());
			if (consigliereAzione == null)
				return false;

			azioneFactory.setConsigliere(consigliereAzione);
		}

		if (azioneFactoryCompleta.getConsiglio() != null) {
			Regione regioneAzione = azioneFactory.getGioco().getTabellone()
					.getRegioneDaNome(azioneFactoryCompleta.getConsiglio().getRegione().getNome());
			if (regioneAzione == null)
				return false;

			azioneFactory.setConsiglio(regioneAzione.getConsiglio());
		}

		if (azioneFactoryCompleta.getRegione() != null) {
			Regione regioneAzione = azioneFactory.getGioco().getTabellone()
					.getRegioneDaNome(azioneFactoryCompleta.getRegione().getNome());
			if (regioneAzione == null)
				return false;

			azioneFactory.setRegione(regioneAzione);
		}
		if (azioneFactoryCompleta.getTesseraCostruzione() != null) {
			if (giocatore.cercaTesseraCostruzione(azioneFactoryCompleta.getTesseraCostruzione()) != null)
				azioneFactory.setTesseraCostruzione(
						giocatore.cercaTesseraCostruzione(azioneFactoryCompleta.getTesseraCostruzione()));
			else
				return false;
		}
		return true;
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
	public void update(Bonus cambiamento, String[] input) {
		throw new IllegalArgumentException();
	}
}

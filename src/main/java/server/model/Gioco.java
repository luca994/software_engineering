package server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import server.model.stato.gioco.Attesa;
import server.model.stato.gioco.Esecuzione;
import server.model.stato.gioco.StatoGioco;
import server.model.stato.gioco.Terminato;
import server.observer.Observable;

/**
 * @author Luca
 *
 */
public class Gioco extends Observable implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3738439371299756375L;
	
	private static final int MIN_NUM_GIOCATORI = 2;
	private static final int NUM_EMPORI_MASSIMO = 10;

	private List<Giocatore> giocatori;
	private Tabellone tabellone;
	private StatoGioco statoGioco;

	/**
	 * Constructor of Gioco. instantiate an object of type Gioco with an
	 * arrayList of giocatori, initially empty, with the StatoGioco set to
	 * Attesa, and with tabellone set to null.
	 */
	public Gioco() {

		giocatori = new ArrayList<>();
		this.tabellone = null;
		this.statoGioco = new Attesa(this);
	}

	/**
	 * initializes the game environment Before call this method you have to add
	 * Giocatori to Gioco, and the game must not already be in Esecuzione it
	 * create the tabellone, initializes the attributes of the Giocatori to
	 * participate in a game. If there are only two Giocatori , this method
	 * performs the extra initialization
	 * 
	 * @throws IllegalArgumentException
	 *             if the list of Giocatori is less than MIN_NUM_GIOCATORI
	 * @throws IllegalStateException
	 *             if the game is already in execution
	 */
	public void inizializzaPartita() {

		if (this.giocatori.size() < MIN_NUM_GIOCATORI)
			throw new IllegalArgumentException("Numero di giocatori troppo basso per iniziare la partita");
		if (statoGioco instanceof Esecuzione)
			throw new IllegalStateException("La partita non deve essere in esecuzione per essere inizializzato");

		String nomeMappaScelta = "src/main/resources/mappacollegamenti0.xml"; /*
																				 * Ottenuta
																				 * dal
																				 * controller
																				 */

		this.tabellone = new Tabellone(nomeMappaScelta, this);

		inizializzaGiocatori();

		/* Setup aggiuntivo per 2 giocatori */
		if (giocatori.size() == 2)
			inizializzazioneGiocatoreDummy();

		this.notificaObservers(tabellone);

	}

	/**
	 * initializes the parameters of the players and prepare them to match this
	 * method is called by inizializzaPartita()
	 */
	private void inizializzaGiocatori() {

		/* ottengo elenco nome giocatori */
		int numGiocatore = 1;
		for (Giocatore gio : giocatori) {

			gio.setEmporiRimasti(NUM_EMPORI_MASSIMO);

			for (int j = 0; j < numGiocatore; j++) {
				gio.getAssistenti().add(new Assistente());
			}

			this.tabellone.getPercorsoNobilta().getCaselle().get(0).getGiocatori().add(gio);
			this.tabellone.getPercorsoVittoria().getCaselle().get(0).getGiocatori().add(gio);
			this.tabellone.getPercorsoRicchezza().getCaselle().get(9 + numGiocatore).getGiocatori().add(gio);

			for (int i = 0; i < 6; i++) {
				gio.getCartePolitica().add(new CartePoliticaFactory().creaCartaPolitica());
			}
			numGiocatore++;
		}
	}

	/**
	 * initializes the additional player who needs to be created once the game
	 * has only two players. This method is called by inizializzaPartita()
	 */
	private void inizializzazioneGiocatoreDummy() {

		Giocatore dummy = new Giocatore("dummy");

		for (Regione regi : tabellone.getRegioni()) {
			for (Citta cit : regi.getTessereCoperte().get(0).getCitta()) {
				cit.getEmpori().add(dummy);
			}
			Collections.shuffle(regi.getTessereCoperte());
		}
	}

	/**
	 * Run the entire game. It should be called when the game has already been
	 * initialized and is therefore still in Attesa state. After the method has
	 * been executed the game will be terminated.
	 * 
	 * @throws IllegalStateException
	 *             if the game is not in Attesa state.
	 */
	public void eseguiPartita() {
		if (!(statoGioco instanceof Attesa))
			throw new IllegalStateException("Stai eseguendo una partita che è già in esecuzione!");
		while (!(statoGioco instanceof Terminato)) {
			statoGioco.prossimoStato();
			statoGioco.eseguiFase();
		}
	}

	/**
	 * runs the method eseguiPartita in another thread
	 */
	@Override
	public void run() {
		eseguiPartita();
	}

	/**
	 * @return the tabellone
	 */
	public Tabellone getTabellone() {
		return tabellone;
	}

	/**
	 * @return the giocatori
	 */
	public List<Giocatore> getGiocatori() {
		return giocatori;
	}

	/**
	 * @return the stato
	 */
	public StatoGioco getStato() {
		return statoGioco;
	}

	/**
	 * set the list of players
	 * 
	 * @param giocatori
	 *            the list of players to set
	 */
	public void setGiocatori(List<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}

	/**
	 * @param stato
	 *            the stato to set
	 */
	public void setStato(StatoGioco stato) {
		this.statoGioco = stato;
	}
}

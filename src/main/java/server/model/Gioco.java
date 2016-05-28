package server.model;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdom2.JDOMException;

import server.model.stato.gioco.Attesa;
import server.model.stato.gioco.Esecuzione;
import server.model.stato.gioco.StatoGioco;
import server.model.stato.gioco.Terminato;
import server.observer.Observable;

/**
 * @author Luca
 *
 */
public class Gioco extends Observable {

	private static final int MIN_NUM_GIOCATORI = 2;
	private static final int NUM_EMPORI_MASSIMO = 10;

	private List<Giocatore> giocatori;
	private Tabellone tabellone;
	private StatoGioco statoGioco;

	public Gioco() {

		giocatori = new ArrayList<>();
		this.tabellone = null;
		this.statoGioco = new Attesa(this);
	}

	/**
	 * adds a Giocatore to the list of giocatori for this Gioco
	 * 
	 * @param nome
	 *            the name of the Giocatore
	 * @param colore
	 *            the color of the Giocatore
	 */
	public void aggiungiGiocatore(String nome, Color colore) {

		giocatori.add(new Giocatore(nome, colore));
	}

	/**
	 * initializes the game environment
	 * 
	 * @param giocatori
	 *            the list of giocatori who plays the game
	 * @throws JDOMException
	 * @throws IOException
	 */
	public void inizializzaPartita() throws JDOMException, IOException {

		if (giocatori.size() < MIN_NUM_GIOCATORI)
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

		new Mercato(tabellone.getPercorsoRicchezza());

	}

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
				gio.getCartePolitica().add(new CartaPolitica());
			}
			numGiocatore++;
		}
	}

	/**
	 * initializes the additional player who needs to be created once the game
	 * has only two players
	 */
	private void inizializzazioneGiocatoreDummy() {

		Giocatore dummy = new Giocatore(null, Color.DARK_GRAY);

		/* Per il giocatore aggiuntivo è stato scelto il grigio scuro */

		for (Regione regi : tabellone.getRegioni()) {
			for (Citta cit : regi.getTessereCoperte().get(0).getCittà()) {
				cit.getEmpori().add(dummy);
			}
			Collections.shuffle(regi.getTessereCoperte());
		}
	}

	/**
	 * run the entire game. It should be called when the game has already been
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
	 * @param stato
	 *            the stato to set
	 */
	public void setStato(StatoGioco stato) {
		this.statoGioco = stato;
	}

}

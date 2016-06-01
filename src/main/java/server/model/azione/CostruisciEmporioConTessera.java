package server.model.azione;

import java.util.ArrayList;
import java.util.List;

import eccezione.EmporioGiaCostruito;
import eccezione.FuoriDalLimiteDelPercorso;
import eccezione.NumeroAiutantiIncorretto;
import server.model.Citta;
import server.model.Giocatore;
import server.model.TesseraCostruzione;

/**
 * Main action that allows you to build an emporium. Choose one of the cities on
 * a tile building permit face up that you own, build the store and cover the
 * tile.
 *
 */
public class CostruisciEmporioConTessera extends AzionePrincipale {

	private Citta citta;
	private TesseraCostruzione tessera;

	/**
	 * @param citta
	 * @param tessera
	 */
	public CostruisciEmporioConTessera(Citta citta, TesseraCostruzione tessera) {
		super(null);
		this.citta = citta;
		this.tessera = tessera;
	}

	/**
	 * Performs the action
	 * 
	 * @throws EmporioGiaCostruito
	 *             if there is already a player's emporium in the destination
	 *             city.
	 * @throws NumeroAiutantiIncorretto
	 *             if the player builds an emporium in a city where there are
	 *             already other emporiums of other player and has no helpers
	 *             needed to build
	 * 
	 * @throws NullPointerException
	 *             if the player is null
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) throws EmporioGiaCostruito, NumeroAiutantiIncorretto {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");

		if (citta.getEmpori().contains(giocatore))
			throw new EmporioGiaCostruito("Hai già un Emporio in questa città");
		if (citta.getEmpori().size() > giocatore.getAssistenti().size())
			throw new NumeroAiutantiIncorretto(
					"Il giocatore non ha abbastanza aiutanti per costruire l'emporio in quella posizione");

		citta.getEmpori().add(giocatore);
		giocatore.spostaTesseraValidaInTesseraUsata(tessera);
		giocatore.decrementaEmporiRimasti();

		for (int i = 0; i < citta.getEmpori().size(); i++)
			giocatore.getAssistenti().remove(0);

		// Se il giocatore ha finito gli empori guadagna 3 punti vittoria
		if (giocatore.getEmporiRimasti() == 0) {
			try {
				getGioco().getTabellone().getPercorsoVittoria().muoviGiocatore(giocatore, 3);
			} catch (FuoriDalLimiteDelPercorso e) {
				throw new IllegalArgumentException(e);
			}
			giocatore.getStatoGiocatore().tuttiGliEmporiCostruiti();
		}

		// Il giocatore ottiene i bonus di questa e delle città collegate

		List<Citta> cittaConBonusDaOttenere = new ArrayList<>();
		cittaConBonusDaOttenere.add(citta);
		citta.cittaVicinaConEmporio(giocatore, cittaConBonusDaOttenere);

		/*
		 * la lista cittàConBonusDaOttenere che viene creata è un lista di città
		 * in cui viene messa inizialmente la città corrente, poi viene chiamato
		 * il metodo cittàVicinaConEmporio che riempe la lista con tutte le
		 * città adiacenti che hanno un emporio del giocatore.
		 */

		for (Citta citt : cittaConBonusDaOttenere)
			citt.eseguiBonus(giocatore);

		/*
		 * controllo se ho gli empori in tutte le città di un colore o di una
		 * regione e prendo la tessera bonus se mi spetta (IL controllo viene
		 * fatto direttamente dal metodo del tabellone prendiTesseraBonus)
		 */
		getGioco().getTabellone().prendiTesseraBonus(giocatore, citta);

		giocatore.getStatoGiocatore().azioneEseguita(this);
	}

	/**
	 * @return the città
	 */
	public Citta getCittà() {
		return citta;
	}

	/**
	 * @param citta
	 *            the città to set
	 */
	public void setCittà(Citta citta) {
		this.citta = citta;
	}

	/**
	 * @return the tessera
	 */
	public TesseraCostruzione getTessera() {
		return tessera;
	}

	/**
	 * @param tessera
	 *            the tessera to set
	 */
	public void setTessera(TesseraCostruzione tessera) {
		this.tessera = tessera;
	}

}

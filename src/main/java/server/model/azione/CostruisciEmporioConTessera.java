package server.model.azione;

import java.util.ArrayList;
import java.util.List;

import server.eccezioni.CittaNonCorretta;
import server.eccezioni.EmporioGiaCostruito;
import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.eccezioni.NumeroAiutantiIncorretto;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Citta;
import server.model.componenti.TesseraCostruzione;

/**
 * Main action that allows you to build an emporium. Choose one of the cities on
 * a tile building permit face up that you own, build the store and cover the
 * tile.
 *
 */
public class CostruisciEmporioConTessera extends AzionePrincipale {

	private final Citta citta;
	private final TesseraCostruzione tessera;

	/**
	 * @param citta
	 * @param tessera
	 */
	public CostruisciEmporioConTessera(Gioco gioco, Citta citta, TesseraCostruzione tessera) {
		super(gioco);
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
	 * @throws CittaNonCorretta
	 *             if the city selected in which you want to build an emporium
	 *             is not in TesseraCostruzione
	 * 
	 * @throws NullPointerException
	 *             if the player is null
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore)
			throws EmporioGiaCostruito, NumeroAiutantiIncorretto, CittaNonCorretta {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		if (!tessera.verifyCitta(citta))
			throw new CittaNonCorretta("La citta selezionata non è presente nella tessera");
		if (citta.getEmpori().contains(giocatore))
			throw new EmporioGiaCostruito("Hai già un Emporio in questa città");
		if (citta.getEmpori().size() > giocatore.getAssistenti().size())
			throw new NumeroAiutantiIncorretto(
					"Il giocatore non ha abbastanza aiutanti per costruire l'emporio in quella posizione");
		for (int i = 0; i < citta.getEmpori().size(); i++)
			giocatore.getAssistenti().remove(0);

		citta.getEmpori().add(giocatore);
		giocatore.spostaTesseraValidaInTesseraUsata(tessera);
		giocatore.decrementaEmporiRimasti();

		/*
		 * Se il giocatore ha finito gli empori guadagna 3 punti vittoria,
		 * l'eccezione FuoriDalLimiteDelPercorso diventa unchecked perchè non
		 * deve essere lanciata visto che il giocatore si sposta di un numero di
		 * passi positivo
		 */
		if (giocatore.getEmporiRimasti() == 0) {
			try {
				getGioco().getTabellone().getPercorsoVittoria().muoviGiocatore(giocatore, 3);
			} catch (FuoriDalLimiteDelPercorso e) {
				throw new IllegalArgumentException(e);
			}
			giocatore.getStatoGiocatore().tuttiGliEmporiCostruiti();
			getGioco().notificaObservers("Il giocatore "+giocatore.getNome()+" ha costruito tutti gli empori!");
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

		for (Citta citt : cittaConBonusDaOttenere){
			if(citt.getBonus()!=null)
				citt.eseguiBonus(giocatore);
		}
		/*
		 * controllo se ho gli empori in tutte le città di un colore o di una
		 * regione e prendo la tessera bonus se mi spetta (IL controllo viene
		 * fatto direttamente dal metodo del tabellone prendiTesseraBonus)
		 */
		getGioco().getTabellone().prendiTesseraBonus(giocatore, citta);

		giocatore.getStatoGiocatore().azioneEseguita(this);
	}

}

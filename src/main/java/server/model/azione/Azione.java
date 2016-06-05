package server.model.azione;

import eccezione.CartePoliticaIncorrette;
import eccezione.EmporioGiaCostruito;
import eccezione.FuoriDalLimiteDelPercorso;
import eccezione.NumeroAiutantiIncorretto;
import server.model.Giocatore;
import server.model.Gioco;

/**
 * Abstact class for the actions
 *
 */
public abstract class Azione {

	private transient Gioco gioco;

	public Azione(Gioco gioco) {
		this.gioco = gioco;
	}

	/**
	 * Performs the action
	 * 
	 * @param giocatore
	 *            player who performs the action
	 * @throws FuoriDalLimiteDelPercorso
	 * @throws CartePoliticaIncorrette 
	 * @throws NumeroAiutantiIncorretto 
	 * @throws EmporioGiaCostruito 
	 */
	public abstract void eseguiAzione(Giocatore giocatore) throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito;

	/**
	 * @return the gioco
	 */
	public Gioco getGioco() {
		return gioco;
	}
}

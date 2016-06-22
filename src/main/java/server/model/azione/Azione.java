package server.model.azione;

import eccezione.CartePoliticaIncorrette;
import eccezione.CittaNonCorretta;
import eccezione.EccezioneConsiglioDeiQuattro;
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

	private Gioco gioco;

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
	 * @throws CittaNonCorretta 
	 */
	public abstract void eseguiAzione(Giocatore giocatore) throws EccezioneConsiglioDeiQuattro;

	/**
	 * @return the gioco
	 */
	public Gioco getGioco() {
		return gioco;
	}
}

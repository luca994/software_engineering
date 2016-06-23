package server.model.azione;

import server.eccezione.CartePoliticaIncorrette;
import server.eccezione.CittaNonCorretta;
import server.eccezione.EccezioneConsiglioDeiQuattro;
import server.eccezione.EmporioGiaCostruito;
import server.eccezione.FuoriDalLimiteDelPercorso;
import server.eccezione.NumeroAiutantiIncorretto;
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

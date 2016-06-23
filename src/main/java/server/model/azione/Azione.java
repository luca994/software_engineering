package server.model.azione;

import server.eccezioni.CartePoliticaIncorrette;
import server.eccezioni.CittaNonCorretta;
import server.eccezioni.EccezioneConsiglioDeiQuattro;
import server.eccezioni.EmporioGiaCostruito;
import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.eccezioni.NumeroAiutantiIncorretto;
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

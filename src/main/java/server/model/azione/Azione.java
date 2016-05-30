package server.model.azione;

import java.io.IOException;

import server.model.Giocatore;
import server.model.Gioco;

/**
 * @author Luca
 *
 */
public abstract class Azione {

	private Gioco gioco;

	public Azione(Gioco gioco) {
		this.gioco = gioco;
	}

	/**
	 * This method must be called by the controller before executing an action .
	 * It used to check if the input entered is correct
	 * 
	 * @param giocatore is the player who performs the action
	 * @return true if is possible to make the actions, else return false
	 * 
	 */
	public abstract boolean verificaInput(Giocatore giocatore);

	/**
	 * performs the action
	 * @param giocatore player who performs the action
	 * @throws IOException
	 */
	public abstract void eseguiAzione(Giocatore giocatore) throws IOException;

	/**
	 * @return the gioco
	 */
	public Gioco getGioco() {
		return gioco;
	}
}

package server.model.stato.gioco;

import java.io.Serializable;

import server.model.Gioco;
import server.model.stato.Stato;

/**
 *	The abstract class that represents the generic state of a game
 */
public abstract class StatoGioco implements Stato, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4060927229756321983L;
	private Gioco gioco;
	
	/**
	 * Generic constructor for a game state
	 */
	public StatoGioco(Gioco gioco){
		this.gioco=gioco;
	}
	
	/**It executes and manages the current stage of the game*/
	public abstract void eseguiFase();
	
	/** Sets the game state to the next state*/
	public abstract void prossimoStato();

	/**
	 * @return the gioco
	 */
	public Gioco getGioco() {
		return gioco;
	}
	
}

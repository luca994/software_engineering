package server.model.stato.gioco;

import server.model.Gioco;

/**
 * The abstract class that represents the generic state of the game market round
 *
 */
public abstract class FaseTurnoMercato extends Esecuzione {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1198417673249888090L;

	/**
	 * Constructor for the state of FaseTurnoMercato
	 */
	public FaseTurnoMercato(Gioco gioco) {
		super(gioco);
	}
}

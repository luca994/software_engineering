package server.model.stato.giocatore;

import server.model.Giocatore;

/**
 * The abstract class that represents the generic player market round
 *
 */
public abstract class TurnoMercato extends StatoGiocatore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7211606457736446827L;

	/**
	 * constructor for market round state
	 */
	public TurnoMercato(Giocatore giocatore) {
		super(giocatore);
	}
}

package server.model.stato.giocatore;

import server.model.Giocatore;

public abstract class TurnoMercato extends StatoGiocatore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7211606457736446827L;

	public TurnoMercato(Giocatore giocatore) {
		super(giocatore);
	}
}

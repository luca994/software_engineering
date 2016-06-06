package server.model.stato.gioco;

import server.model.Gioco;

public abstract class FaseTurnoMercato extends Esecuzione {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1198417673249888090L;

	public FaseTurnoMercato(Gioco gioco) {
		super(gioco);
	}
}

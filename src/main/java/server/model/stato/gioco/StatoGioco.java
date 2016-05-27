package server.model.stato.gioco;

import server.model.Gioco;
import server.model.stato.Stato;

public abstract class StatoGioco implements Stato {

	private final Gioco gioco;
	
	public StatoGioco(Gioco gioco){
		this.gioco=gioco;
	}
	
	public abstract void eseguiFase();
	
	public abstract void prossimoStato();

	/**
	 * @return the gioco
	 */
	public Gioco getGioco() {
		return gioco;
	}
	
}

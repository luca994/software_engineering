package server.model.stato.gioco;

import server.model.Gioco;
import server.model.stato.Stato;

public abstract class StatoGioco implements Stato {

	protected Gioco gioco;
	
	public StatoGioco(Gioco gioco){
		this.gioco=gioco;
	}
	
	public abstract void eseguiFase();
	
	public abstract void prossimoStato();
}

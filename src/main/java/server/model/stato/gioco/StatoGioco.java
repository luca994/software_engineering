package server.model.stato.gioco;

import java.io.Serializable;

import server.model.Gioco;
import server.model.stato.Stato;

public abstract class StatoGioco implements Stato, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4060927229756321983L;
	private Gioco gioco;
	
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

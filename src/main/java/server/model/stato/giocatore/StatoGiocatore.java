package server.model.stato.giocatore;

import server.model.Giocatore;
import server.model.stato.Stato;

public abstract class StatoGiocatore implements Stato {
	
	protected final Giocatore giocatore;
	
	public StatoGiocatore(Giocatore giocatore){
		this.giocatore=giocatore;
	}
	
	public abstract void prossimoStato(); 
}

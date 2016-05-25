package server.model.stato;

import server.model.Giocatore;

public abstract class StatoGiocatore implements Stato {
	
	public abstract void prossimoStato(Giocatore giocatore); 
}

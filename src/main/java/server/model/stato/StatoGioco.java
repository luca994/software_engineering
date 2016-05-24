package server.model.stato;

import server.model.Gioco;

public abstract class StatoGioco implements Stato {

	public abstract void prossimoStato(Gioco gioco);
}

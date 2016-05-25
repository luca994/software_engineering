package server.model.stato;

import server.model.Gioco;

public abstract class Esecuzione extends StatoGioco {
	
	public abstract void prossimoStato(Gioco gioco);

}

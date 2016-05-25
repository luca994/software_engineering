package server.model.stato;

import server.model.Giocatore;
import server.model.Mercato;

public class Attesa extends StatoGiocatore {

	@Override
	public void prossimoStato(Giocatore giocatore) {
		giocatore.setStatoGiocatore(new TurnoNormale(giocatore));
	}
	
	
	
	/*
	public void prossimoStato(Giocatore giocatore,Mercato mercato){
		giocatore.setStatoGiocatore(new TurnoMercato(giocatore));
	}
	*/
}

package server.model.stato;

import java.util.List;

import server.model.Giocatore;
import server.model.Gioco;

public class Esecuzione extends StatoGioco {

	private List<Giocatore> giocatori;
	private Gioco gioco;
	
	public Esecuzione(Gioco gioco){
		giocatori=gioco.getGiocatori();
	}	
	
	public void gioca(){
		for(Giocatore giocat : giocatori){
			giocat.getStatoGiocatore().prossimoStato(giocat);
			while(giocat.getStatoGiocatore() instanceof TurnoNormale);
		}
		this.prossimoStato(gioco);
	}
	
	
	public void prossimoStato(Gioco gioco){
	}

}

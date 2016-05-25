package server.model.stato;

import java.util.List;

import server.model.Giocatore;
import server.model.Gioco;

public class FaseMercatoAggiuntaOggetti extends Esecuzione {

	private List<Giocatore> giocatori;
	private Gioco gioco;
	
	
	public FaseMercatoAggiuntaOggetti(Gioco gioco) {
		this.gioco=gioco;
		this.giocatori=gioco.getGiocatori();
	}
	
	
	

	@Override
	public void prossimoStato(Gioco gioco) {
		// TODO Auto-generated method stub
		
	}

}

package server.model.stato;

import java.util.List;

import server.model.Giocatore;
import server.model.Gioco;

public class FaseNormale extends Esecuzione {

	private List<Giocatore> giocatori;
	private Gioco gioco;
	
	public FaseNormale(Gioco gioco) {
		this.gioco=gioco;
		this.giocatori=gioco.getGiocatori();
	}
	
	public void avviaTurni(){
		for(Giocatore giocat : giocatori){
			giocat.getStatoGiocatore().prossimoStato(giocat);
			while(giocat.getStatoGiocatore() instanceof TurnoNormale);
		}
		this.prossimoStato(gioco);
	}

	@Override
	public void prossimoStato(Gioco gioco) {
		gioco.setStato(new FaseMercatoAggiuntaOggetti(gioco));
	}

}

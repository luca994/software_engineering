package server.model.stato.gioco;


import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.giocatore.TurnoNormale;

public class FaseTurnoSemplice extends Esecuzione {
	
	public FaseTurnoSemplice(Gioco gioco) {
		super(gioco);
	}
	
	public void eseguiFase(){
		for(Giocatore giocat : giocatori){
			giocat.getStatoGiocatore().prossimoStato();
			while(giocat.getStatoGiocatore() instanceof TurnoNormale);
		}
	}

	@Override
	public void prossimoStato() {
		gioco.setStato(new FaseTurnoMercatoAggiuntaOggetti(gioco));
	}

}

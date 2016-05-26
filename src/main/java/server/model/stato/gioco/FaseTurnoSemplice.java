package server.model.stato.gioco;


import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.giocatore.TurniConclusi;
import server.model.stato.giocatore.TurnoNormale;

public class FaseTurnoSemplice extends Esecuzione {
	
	private boolean ultimoTurno=false;
	
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
		if(ultimoTurno)
			gioco.setStato(new Terminato(gioco));
		else
		gioco.setStato(new FaseTurnoMercatoAggiuntaOggetti(gioco));
	}

	/**
	 * @param ultimoTurno the ultimoTurno to set
	 */
	public void setUltimoTurno(boolean ultimoTurno) {
		this.ultimoTurno = ultimoTurno;
	}

	
}

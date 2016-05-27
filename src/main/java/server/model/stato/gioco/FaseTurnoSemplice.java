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
	
	/**
	 * executes this phase of the game.
	 * if a player has build the last emporium, it starts the last turn for each other players.
	 */
	public void eseguiFase(){
		boolean exit=false;
		for(Giocatore giocat : giocatori){
			if(giocat.getStatoGiocatore() instanceof TurniConclusi){
				exit=true;
				break;
				}
			giocat.getStatoGiocatore().prossimoStato();
			while(giocat.getStatoGiocatore() instanceof TurnoNormale);
			if(ultimoTurno)
				giocat.setStatoGiocatore(new TurniConclusi(giocat));
			if(giocat.getStatoGiocatore() instanceof TurniConclusi){
				ultimoTurno=true;
		}}
		if(ultimoTurno && !exit)
			eseguiFase();
	}

	
	
	@Override
	public void prossimoStato() {
		if(ultimoTurno)
			gioco.setStato(new Terminato(gioco));
		else
		gioco.setStato(new FaseTurnoMercatoAggiuntaOggetti(gioco));
	}
	
}

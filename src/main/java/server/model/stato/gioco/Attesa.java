package server.model.stato.gioco;

import server.model.Gioco;

public class Attesa extends StatoGioco {
	
	public Attesa(Gioco gioco){
		super(gioco);
	}
	
	/**
	 * when the game is on stand-by, nothing is done
	 */
	@Override
	public void eseguiFase() {
	}
	
	@Override
	public void prossimoStato(){
		getGioco().setStato(new FaseTurnoSemplice(getGioco()));
	}
}
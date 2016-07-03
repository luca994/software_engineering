package server.model.stato.gioco;

import server.model.Gioco;

/**
 * the class that represents the standby state of the game
 *
 */
public class Attesa extends StatoGioco {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -789094881939145865L;

	/**
	 * Constructor for standby state
	 * @param gioco 
	 */
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
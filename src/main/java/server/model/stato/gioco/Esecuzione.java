/**
 * 
 */
package server.model.stato.gioco;

import java.util.List;

import server.config.Configurazione;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.giocatore.Sospeso;

/**
 * The abstract class that represents the generic execution state of the game
 */
public abstract class Esecuzione extends StatoGioco implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4925505216711534227L;
	private final List<Giocatore> giocatori;
	private Giocatore giocatoreCorrente;
	 /**
	  * Costructor for state execution
	  * @param gioco
	  */
	public Esecuzione(Gioco gioco) {
		super(gioco);
		this.giocatori=gioco.getGiocatori();
	}

	/**
	 * @param giocatoreCorrente the giocatoreCorrente to set
	 */
	public void setGiocatoreCorrente(Giocatore giocatoreCorrente) {
		this.giocatoreCorrente = giocatoreCorrente;
	}

	/**
	 * @return the giocatori
	 */
	public List<Giocatore> getGiocatori() {
		return giocatori;
	}
	
	/**
	 * checks if the player takes too much time for doing the action.
	 * When the time expires, the method puts the player in suspended state.
	 */
	@Override
	public void run() {
		Giocatore giocatoreControllato = giocatoreCorrente;
		long timer = System.currentTimeMillis();
		while(giocatoreCorrente!=null && giocatoreCorrente.equals(giocatoreControllato) && this.getGioco().getStato() instanceof Esecuzione){
			if(System.currentTimeMillis()-timer>Configurazione.getMaxTimeForTurn()){
				giocatoreCorrente.setStatoGiocatore(new Sospeso(giocatoreControllato));
				getGioco().notificaObservers("Sei stato sopseso", giocatoreControllato);
				getGioco().notificaObservers("Il giocatore "+giocatoreControllato.getNome()+" è stato sospeso");
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

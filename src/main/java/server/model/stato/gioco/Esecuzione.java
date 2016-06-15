/**
 * 
 */
package server.model.stato.gioco;

import java.util.List;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.giocatore.Sospeso;

/**
 * @author Luca
 *
 */
public abstract class Esecuzione extends StatoGioco implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4925505216711534227L;
	private final List<Giocatore> giocatori;
	private Giocatore giocatoreCorrente;
	
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
		while(giocatoreCorrente!=null && giocatoreCorrente.equals(giocatoreControllato)){
			if(System.currentTimeMillis()-timer>600000){
				giocatoreCorrente.setStatoGiocatore(new Sospeso(giocatoreControllato));
				getGioco().notificaObservers("Sei stato sopseso", giocatoreControllato);
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

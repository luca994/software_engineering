/**
 * 
 */
package server.model.stato.gioco;

import java.util.List;

import server.model.Giocatore;
import server.model.Gioco;

/**
 * @author Luca
 *
 */
public abstract class Esecuzione extends StatoGioco {

	private final List<Giocatore> giocatori;
	
	public Esecuzione(Gioco gioco) {
		super(gioco);
		this.giocatori=gioco.getGiocatori();
	}

	/**
	 * @return the giocatori
	 */
	public List<Giocatore> getGiocatori() {
		return giocatori;
	}

}

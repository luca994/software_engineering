package percorso;


import java.util.HashSet;
import java.util.Set;

import model.Giocatore;

/**
 *This class represent the generic box
 *in every route(Percorso)
 * @author Massimiliano Ventura
 */
public abstract class Casella {
	
	private Set<Giocatore> giocatori;
	
	public Casella(){
		this.giocatori= new HashSet<Giocatore>();
	}
	
	public void setGiocatori(Set<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}

	/**
	 * @return the list of giocatori in the current box(casella)
	 */
	public Set<Giocatore> getGiocatori() {
		return this.giocatori;
	}
}

package server.model.percorso;


import java.util.HashSet;
import java.util.Set;

import server.model.Giocatore;

/**
 *This class represent the generic box
 *in every route(Percorso)
 * @author Massimiliano Ventura
 */
public abstract class Casella {
	
	private Set<Giocatore> giocatori;

	/**
	 * builds a box
	 */
	public Casella(){
		this.giocatori=new HashSet<>();
	}
	
	/**
	 * set the set of players
	 * @param giocatori the set of players to set
	 */
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

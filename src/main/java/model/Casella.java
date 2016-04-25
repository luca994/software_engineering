package model;


import java.util.HashSet;
import java.util.Set;

/**
 * @author Massimiliano Ventura
 *This class represent the generic box
 *in every route(Percorso)
 */
public abstract class Casella {
	public Casella(){
		this.giocatori= new HashSet<Giocatore>();
	}
	
	private Set<Giocatore> giocatori;

	
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

package model;

import java.util.SortedSet;

/**
 * @author Luca
 *
 */
public class Gioco {

	private SortedSet<Giocatore> giocatori;
	private Tabellone tabellone;
	private boolean vittoria;
	private int id;
	private Mercato mercato;
	
	
	/**
	 * @return the tabellone
	 */
	public Tabellone getTabellone() {
		return tabellone;
	}


	public void gioco(){}
}

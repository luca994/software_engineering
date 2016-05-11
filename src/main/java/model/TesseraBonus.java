package model;

import java.util.Set;

import bonus.Bonus;

/**
 * 
 * @author Riccardo
 *
 */
public class TesseraBonus extends OggettoConBonus {

	private Regione regione;
	private String colore;

	/**
	 * build a bonus tile
	 * @param regione the region of the bonus tile (it can be null)
	 * @param colore the color of the bonus tile (it can be null)
	 * @param bonus the set of bonus of the bonus tile
	 */
	public TesseraBonus(Regione regione, String colore, Set<Bonus> bonus){
		super(bonus);
		this.regione=regione;
		this.colore=colore;
	}

	public Regione getRegione() {
		return regione;
	}

	public String getColore() {
		return colore;
	}
	
	
}

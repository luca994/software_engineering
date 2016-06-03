package server.model.tesserebonus;

import java.awt.Color;
import java.util.Set;

import server.model.bonus.Bonus;

public class TesseraBonusCitta extends TesseraBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1935703575003252450L;
	
	private Color colore;
	
	/**
	 * build the bonus region tile
	 * @param bonus the set of bonus of the tile
	 * @param colore the color of the cities associated to the tile
	 */
	public TesseraBonusCitta(Set<Bonus> bonus, Color colore) {
		super(bonus);
		this.colore=colore;
	}

	/**
	 * 
	 * @return the color of the tile
	 */
	public Color getColore() {
		return colore;
	}
	
	

}

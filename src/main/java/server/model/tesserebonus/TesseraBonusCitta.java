package server.model.tesserebonus;

import java.util.Set;

import server.model.bonus.Bonus;

public class TesseraBonusCitta extends TesseraBonus {

	private String colore;
	
	/**
	 * build the bonus region tile
	 * @param bonus the set of bonus of the tile
	 * @param colore the color of the cities associated to the tile
	 */
	public TesseraBonusCitta(Set<Bonus> bonus, String colore) {
		super(bonus);
		this.colore=colore;
	}

	/**
	 * 
	 * @return the color of the tile
	 */
	public String getColore() {
		return colore;
	}
	
	

}

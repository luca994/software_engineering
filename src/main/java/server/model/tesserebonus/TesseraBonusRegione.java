package server.model.tesserebonus;

import java.util.Set;

import server.model.bonus.Bonus;
import server.model.componenti.Regione;

public class TesseraBonusRegione extends TesseraBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -201930626017881614L;
	
	private Regione regione;
	
	/**
	 * build the bonus region tile
	 * @param bonus the set of bonus of the tile
	 * @param regione the region where there is the tile
	 */
	public TesseraBonusRegione(Set<Bonus> bonus, Regione regione) {
		super(bonus);
		this.regione=regione;
	}

	/**
	 * 
	 * @return the region in which there is the tile
	 */
	public Regione getRegione() {
		return regione;
	}	
}

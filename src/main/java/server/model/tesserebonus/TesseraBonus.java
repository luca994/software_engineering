package server.model.tesserebonus;

import java.io.Serializable;
import java.util.Set;

import server.model.bonus.Bonus;
import server.model.componenti.OggettoConBonus;

/**
 * The abstract class that represents the bonus tiles
 */
public abstract class TesseraBonus extends OggettoConBonus implements Serializable {

	private static final long serialVersionUID = 5687695826330602953L;

	/**
	 * constructor for a bonus tile
	 * @param bonus
	 */
	public TesseraBonus(Set<Bonus> bonus) {
		super(bonus);
	}

}

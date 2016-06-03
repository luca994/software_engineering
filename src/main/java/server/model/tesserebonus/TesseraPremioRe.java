package server.model.tesserebonus;

import java.util.Set;

import server.model.bonus.Bonus;

public class TesseraPremioRe extends TesseraBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1121320870656126238L;

	/**
	 * build the king tile
	 * @param bonus the set of bonus of the tile
	 */
	public TesseraPremioRe(Set<Bonus> bonus) {
		super(bonus);
	}

}

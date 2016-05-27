package server.model.tesserebonus;

import java.util.Set;

import server.model.OggettoConBonus;
import server.model.bonus.Bonus;

/**
 * 
 * @author Riccardo
 *
 */
public abstract class TesseraBonus extends OggettoConBonus {

	public TesseraBonus(Set<Bonus> bonus) {
		super(bonus);
	}
	
}

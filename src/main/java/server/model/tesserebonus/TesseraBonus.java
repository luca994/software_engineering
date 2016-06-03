package server.model.tesserebonus;

import java.io.Serializable;
import java.util.Set;

import server.model.OggettoConBonus;
import server.model.bonus.Bonus;

/**
 * 
 * @author Riccardo
 *
 */
public abstract class TesseraBonus extends OggettoConBonus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5687695826330602953L;

	public TesseraBonus(Set<Bonus> bonus) {
		super(bonus);
	}
	
}

/**
 * 
 */
package server.model.percorso;

import java.util.Set;

import server.model.bonus.Bonus;

/**
 * This is the class that represent the box with bonus; it's used in the routes
 * that have bonuses.
 */
public class CasellaConBonus extends Casella {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3111208064884016769L;

	private Set<Bonus> bonus;

	/**
	 * constructor for casellaConBonus
	 * 
	 * @param bonus
	 *            set of bonus located in this casellaConBonus
	 * @throws NullPointerException
	 *             if the set of bonus is null
	 */
	public CasellaConBonus(Set<Bonus> bonus) {
		if (bonus == null)
			throw new NullPointerException("il bonus non può essere nullo");
		this.bonus = bonus;
	}

	/**
	 * @return
	 */
	public Set<Bonus> getBonus() {
		return bonus;
	}
}

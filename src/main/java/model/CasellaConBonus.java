/**
 * 
 */
package model;

import java.util.Set;

/**
 * @author Massimiliano Ventura
 *This is the class that represent the box
 *with bonus; it's used in the routes
 *that have bonuses.
 */
public class CasellaConBonus extends Casella {
	/**
	 * giocatori: current set of players standing on this specific box(casella)
	 * bouns: set of bonuses assigned to this box, it may be empty, with one or two bonuses
	 */
	private Set<Bonus> bonus;
	public CasellaConBonus (Set<Bonus> bonus){
		this.bonus=bonus;
	}
	/**
	 * @return
	 */
	public Set<Bonus> getBonus() {
		// TODO Auto-generated method stub
		return bonus;
	}
}

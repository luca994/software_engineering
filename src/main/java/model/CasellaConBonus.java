/**
 * 
 */
package model;

import java.util.Set;
import java.util.SortedSet;

/**
 * @author Massimiliano Ventura
 *This is the class that represent the box
 *without any bonus; it's used in the routes
 *that do not have any bonuses.
 */
public class CasellaConBonus extends Casella {
	/**
	 * giocatori: current set of players standing on this specific box(casella)
	 * bouns: set of bonuses assigned to this box, it may be empty, with one or two bonuses
	 */
	private SortedSet<Giocatore> giocatori;
	private Set<Bonus> bonus;
}

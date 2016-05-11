/**
 * 
 */
package bonus;

import model.Giocatore;

/**
 * @author Massimiliano Ventura
 *This class represent the generic bonus
 */
public interface Bonus {
	/**
	 * azioneBonus: abstract method,it will be implemented in the specific subclasses.
	 */
	public abstract void azioneBonus(Giocatore giocatore);
}

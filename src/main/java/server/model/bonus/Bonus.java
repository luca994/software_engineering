/**
 * 
 */
package server.model.bonus;

import java.io.Serializable;

import server.model.Giocatore;

/**
 * This class represent the generic bonus
 */
public interface Bonus extends Serializable {
	/**
	 * abstract method,it will be implemented in the specific subclasses.
	 */
	public abstract void azioneBonus(Giocatore giocatore);

	public abstract boolean isUguale(Bonus bonusDaConfrontare);
}

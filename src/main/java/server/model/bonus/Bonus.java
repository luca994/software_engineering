/**
 * 
 */
package server.model.bonus;

import java.io.IOException;

import server.model.Giocatore;

/**
 * @author Massimiliano Ventura
 *This class represent the generic bonus
 */
public interface Bonus {
	/**
	 * azioneBonus: abstract method,it will be implemented in the specific subclasses.
	 * @throws IOException 
	 * 
	 */
	public abstract void azioneBonus(Giocatore giocatore) throws IOException;
}

package server.model.componenti;

import java.io.Serializable;
import java.util.Set;

import server.model.Giocatore;
import server.model.bonus.Bonus;

/**
 * the abstract class that represents a generic object with bonus
 */
public abstract class OggettoConBonus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6874157692673843025L;

	private Set<Bonus> bonus;

	/**
	 * @param bonus
	 */
	public OggettoConBonus(Set<Bonus> bonus) {
		this.bonus = bonus;
	}

	/**
	 * assign the bonus to the player
	 * 
	 * @param giocatore
	 *            the player who recive the bonus
	 */
	public void eseguiBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		for (Bonus b : bonus) {
			b.azioneBonus(giocatore);
		}
	}

	/**
	 * @return the bonus
	 */
	public Set<Bonus> getBonus() {
		return bonus;
	}

	/**
	 * set the set of bonus
	 * 
	 * @param bonus
	 *            the set bonus to set
	 */
	public void setBonus(Set<Bonus> bonus) {
		this.bonus = bonus;
	}

}

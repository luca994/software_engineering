package server.model;

import java.io.Serializable;
import java.util.Set;

import server.model.bonus.Bonus;

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

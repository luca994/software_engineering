package model;

import java.util.Set;

import model.bonus.Bonus;

public abstract class OggettoConBonus {

	private Set<Bonus> bonus;
	
	
	/**
	 * @param bonus
	 */
	public OggettoConBonus(Set<Bonus> bonus) {
		this.bonus = bonus;
	}

	public void eseguiBonus (Giocatore giocatore){
		for(Bonus b : bonus){
			b.azioneBonus(giocatore);
		}
	}

	/**
	 * @return the bonus
	 */
	public Set<Bonus> getBonus() {
		return bonus;
	}
}

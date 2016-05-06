package model;

import java.util.Set;

/**
 * 
 * @author Riccardo
 *
 */
public class TesseraBonus extends OggettoConBonus {

	private Set<Bonus> bonus;

	/**
	 * @return the bonus
	 */
	public Set<Bonus> getBonus() {
		return bonus;
	}

	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(Set<Bonus> bonus) {
		this.bonus = bonus;
	}	
	
	public void eseguiBonus (Giocatore giocatore){
		for(Bonus b : bonus){
			b.azioneBonus(giocatore);
		}}
}

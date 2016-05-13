package model;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.bonus.Bonus;

public abstract class OggettoConBonus {

	private static final Logger log= Logger.getLogger( OggettoConBonus.class.getName() );
	private Set<Bonus> bonus;
	
	
	/**
	 * @param bonus
	 */
	public OggettoConBonus(Set<Bonus> bonus) {
		this.bonus = bonus;
	}

	public void eseguiBonus (Giocatore giocatore){
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			for(Bonus b : bonus){
				b.azioneBonus(giocatore);
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}

	/**
	 * @return the bonus
	 */
	public Set<Bonus> getBonus() {
		return bonus;
	}
}

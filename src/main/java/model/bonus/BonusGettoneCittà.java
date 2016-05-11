package model.bonus;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Città;
import model.Giocatore;



/**
 * This class asks the player which city bonuses wants to obtain with this particular bonus.
 * @author Massimiliano Ventura
 *
 */
public class BonusGettoneCittà implements Bonus {
	private static final Logger log= Logger.getLogger( BonusGettoneCittà.class.getName() );
	/* (non-Javadoc)
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	
	private int numeroCittà;
	private Set<Città> città;
	
	public BonusGettoneCittà(int numeroCittà)
	{
		this.numeroCittà=numeroCittà;
	}
	
	/**
	 * @return the numeroCittà
	 */
	public int getNumeroCittà() {
		return numeroCittà;
	}

	/**
	 * @return the città
	 */
	public Set<Città> getCittà() {
		return città;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {
		//metodo del controller che prende i bonus della città dal giocatore 
		//le città devono essere diverse  
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			for(Città  cit:this.città)
			{
				if(cit.presenzaEmporio(giocatore))
					cit.eseguiBonus(giocatore);
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}

	/* (non-Javadoc)
	 * @see bonus.Bonus#azioneBonus(model.Giocatore)
	 */

}

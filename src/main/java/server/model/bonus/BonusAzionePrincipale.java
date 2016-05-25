/**
 * 
 */
package server.model.bonus;

import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Giocatore;

/**
 * @author Massimiliano Ventura
 *This bonus enables the Player to make another main move(Azione Principale)
 */
public class BonusAzionePrincipale implements Bonus {
	private static final Logger log= Logger.getLogger( BonusAzionePrincipale.class.getName() );
	/* (non-Javadoc)
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	@Override
	public void azioneBonus(Giocatore giocatore) {
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");	
		//	giocatore.setAzionePrincipale(false);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}
}

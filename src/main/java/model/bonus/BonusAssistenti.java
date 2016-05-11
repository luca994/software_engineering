/**
 * 
 */
package model.bonus;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.Assistente;
import model.Giocatore;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusAssistenti implements Bonus {
	private static final Logger log= Logger.getLogger( BonusAssistenti.class.getName() );
	private int numeroAssistenti;
	
	
	public BonusAssistenti(int numeroAssistenti){
		this.numeroAssistenti=numeroAssistenti;
	}
	
	/* (non-Javadoc)
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	@Override
	public void azioneBonus(Giocatore giocatore) {
		try{
			if(giocatore==null)
		
			throw new NullPointerException("Il giocatore non può essere nullo");
		for(int i=0;i<numeroAssistenti;i++)
		giocatore.getAssistenti().add(new Assistente());
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}
	
}

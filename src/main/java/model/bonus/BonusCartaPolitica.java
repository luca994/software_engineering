/**
 * 
 */
package model.bonus;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.CartaPolitica;
import model.Giocatore;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusCartaPolitica implements Bonus {
	private static final Logger log= Logger.getLogger( BonusCartaPolitica.class.getName() );
	private int numeroCarte;
	
	public BonusCartaPolitica(int numeroCarte)
	{
		this.numeroCarte=numeroCarte;
	}
	
	@Override
	public void azioneBonus(Giocatore giocatore) 
	{
		try{
			if(giocatore==null)
				throw new NullPointerException();
			for(int i=0;i<this.numeroCarte;i++)
				giocatore.getCartePolitica().add(new CartaPolitica());
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}

}

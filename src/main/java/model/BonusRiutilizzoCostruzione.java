/**
 * 
 */
package model;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *To make the method azioneBonus work the class need a method(tesseraDalGiocatore)that returns
 *a tile(tesseraCostruzione), chosen from the player from his set of used card
 *the moment that azioneBonus is called.
 * @author Massimiliano Ventura
 *
 */
public class BonusRiutilizzoCostruzione implements Bonus {
	private static final Logger log= Logger.getLogger( BonusRiutilizzoCostruzione.class.getName() );
	private TesseraCostruzione tessera;
	
	/**
	 * @return the tessera
	 */
	public TesseraCostruzione getTessera() {
		return tessera;
	}
	
	/**
	 * @param tessera the tessera to set
	 */
	public void setTessera(TesseraCostruzione tessera) {
		this.tessera = tessera;
	}
	
	@Override
	public void azioneBonus(Giocatore giocatore) {
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");		
			//this.tessera=tesseraDalGiocatore(giocatore);//metodo del controller che chiama la view
			Iterator<Bonus> itbonus=tessera.getBonus().iterator();
			while(itbonus.hasNext())
			{
				itbonus.next().azioneBonus(giocatore);
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}

}

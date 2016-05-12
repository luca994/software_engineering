/**
 * 
 */
package model.bonus;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.Giocatore;
import model.TesseraCostruzione;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusTesseraPermesso implements Bonus {
	private static final Logger log= Logger.getLogger( BonusTesseraPermesso.class.getName() );
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
		//this.tessera=tesseraDalGiocatore(giocatore);//metodo del controller che chiama la view	
		giocatore.getTessereValide().add(tessera);
		tessera.getRegioneDiAppartenenza().nuovaTessera(tessera);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}

}

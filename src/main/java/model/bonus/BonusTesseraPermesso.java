/**
 * 
 */
package model.bonus;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.Giocatore;
import model.Gioco;
import model.TesseraCostruzione;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusTesseraPermesso implements Bonus {
	private static final Logger log= Logger.getLogger( BonusTesseraPermesso.class.getName() );
	private TesseraCostruzione tessera;
	private Gioco gioco;
	
	public BonusTesseraPermesso(Gioco gioco){
		this.gioco=gioco;
	}
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
			gioco.notificaObservers(this);
			giocatore.getTessereValide().add(tessera);
			tessera.getRegioneDiAppartenenza().nuovaTessera(tessera);
			for(Bonus b:tessera.getBonus()){
				b.azioneBonus(giocatore);
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}

}

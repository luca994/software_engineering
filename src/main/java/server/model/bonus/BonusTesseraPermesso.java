/**
 * 
 */
package server.model.bonus;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.TesseraCostruzione;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusTesseraPermesso implements Bonus {

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
	public void azioneBonus(Giocatore giocatore){
			gioco.notificaObservers(this);
			giocatore.getTessereValide().add(tessera);
			tessera.getRegioneDiAppartenenza().nuovaTessera(tessera);
			for(Bonus b:tessera.getBonus()){
				b.azioneBonus(giocatore);
			}
		
	}

}

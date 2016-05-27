/**
 * 
 */
package server.model.bonus;

import java.io.IOException;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.TesseraCostruzione;

/**
 *To make the method azioneBonus work the class need a method(tesseraDalGiocatore)that returns
 *a tile(tesseraCostruzione), chosen from the player from his set of used card
 *the moment that azioneBonus is called.
 * @author Massimiliano Ventura
 *
 */
public class BonusRiutilizzoCostruzione implements Bonus {

	private TesseraCostruzione tessera;
	private Gioco gioco;
	private Giocatore giocatore;
	
	public BonusRiutilizzoCostruzione(Gioco gioco){
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
	
	/**
	 * 
	 * @return the player
	 */
	public Giocatore getGiocatore() {
		return giocatore;
	}

	@Override
	public void azioneBonus(Giocatore giocatore){
		
			this.giocatore=giocatore;
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");		
			gioco.notificaObservers(this);
			if(tessera!=null){
				for(Bonus b: tessera.getBonus()){
					b.azioneBonus(giocatore);
				}
			}
	}

}

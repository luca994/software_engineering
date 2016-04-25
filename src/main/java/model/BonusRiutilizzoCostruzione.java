/**
 * 
 */
package model;

import java.util.Iterator;

/**
 * @author Massimiliano Ventura
 *To make the method azioneBonus work you have to set the tile that have the bonus you 
 *want to reuse with this bonus. Then you can call the method passing the player that 
 *owns the previously set tile.
 */
public class BonusRiutilizzoCostruzione extends Bonus {

	private TesseraCostruzione tessera;
	/**
	 * @return the tessera
	 */
	public OggettoConBonus getTessera() {
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
		Iterator<Bonus> itbonus=tessera.getElencoBonus().iterator();
		while(itbonus.hasNext())
		{
			itbonus.next().azioneBonus(giocatore);
		}
		
	}

}

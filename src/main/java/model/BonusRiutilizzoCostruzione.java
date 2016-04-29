/**
 * 
 */
package model;

import java.util.Iterator;

/**
 * @author Massimiliano Ventura
 *To make the method azioneBonus work the class need a method(tesseraDalGiocatore)that returns
 *a tile(tesseraCostruzione), chosen from the player from his set of used card
 *the moment that azioneBonus is called.
 */
public class BonusRiutilizzoCostruzione implements Bonus {

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
		//this.tessera=tesseraDalGiocatore(giocatore);//metodo del controller che chiama la view
		Iterator<Bonus> itbonus=tessera.getElencoBonus().iterator();
		while(itbonus.hasNext())
		{
			itbonus.next().azioneBonus(giocatore);
		}
		
	}

}

/**
 * 
 */
package model;

import java.util.Iterator;
import java.util.Set;


/**
 * @author Massimiliano Ventura
 *
 */
public class BonusGettoneCittà extends Bonus {

	/* (non-Javadoc)
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	private Città città;
	@Override
	public void azioneBonus(Giocatore giocatore) {
		this.città=bonusCittàDalGiocatore(giocatore);//metodo del controller che prende i bonus della città dal giocatore 
		città.eseguiAzioneBonus(giocatore);
	}

}

/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *This bonus enables the Player to make another main move(Azione Principale)
 */
public class BonusAzionePrincipale implements Bonus {

	/* (non-Javadoc)
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	@Override
	public void azioneBonus(Giocatore giocatore) {
		giocatore.setAzionePrincipale(true);
	}

}

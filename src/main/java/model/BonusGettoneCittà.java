package model;


/**
 * This class asks the player which city bonuses wants to obtain with this particular bonus.
 * @author Massimiliano Ventura
 *
 */
public class BonusGettoneCittà implements Bonus {

	/* (non-Javadoc)
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	private Città città;
	@Override
	public void azioneBonus(Giocatore giocatore) {
		//this.città=bonusCittàDalGiocatore(giocatore);//metodo del controller che prende i bonus della città dal giocatore 
		città.eseguiAzioneBonus(giocatore);
	}

}

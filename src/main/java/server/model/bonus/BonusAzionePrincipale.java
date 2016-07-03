/**
 * 
 */
package server.model.bonus;

import server.model.Giocatore;

/**
 * the class that represents the main action bonus
 */
public class BonusAzionePrincipale implements Bonus {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6309046785344410844L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	
	@Override
	public void azioneBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		giocatore.getStatoGiocatore().azionePrincipaleAggiuntiva();


	}

	@Override
	public String toString() {
		return "BonusAzionePrincipale";
	}

	@Override
	public boolean isUguale(Bonus bonusDaConfrontare) {
		return bonusDaConfrontare instanceof BonusAzionePrincipale;
	}
}

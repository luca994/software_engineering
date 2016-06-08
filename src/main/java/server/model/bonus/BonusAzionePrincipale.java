/**
 * 
 */
package server.model.bonus;

import server.model.Giocatore;

/**
 * @author Massimiliano Ventura This bonus enables the Player to make another
 *         main move(Azione Principale)
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
	public boolean isUguale(Bonus bonusDaConfrontare) {
		return bonusDaConfrontare instanceof BonusAzionePrincipale;
	}
}

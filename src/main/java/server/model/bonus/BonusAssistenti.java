/**
 * 
 */
package server.model.bonus;

import server.model.Assistente;
import server.model.Giocatore;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusAssistenti implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4956022062307571028L;
	private int numeroAssistenti;

	public BonusAssistenti(int numeroAssistenti) {
		this.numeroAssistenti = numeroAssistenti;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	@Override
	public void azioneBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");

		for (int i = 0; i < numeroAssistenti; i++)
			giocatore.getAssistenti().add(new Assistente());
	}

}

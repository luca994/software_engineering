/**
 * 
 */
package server.model.bonus;

import server.model.Giocatore;
import server.model.componenti.Assistente;

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
		if (numeroAssistenti < 0)
			throw new IllegalStateException("Il numero di assistenti non può essere negativo");
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BonusAssistenti [numeroAssistenti=" + numeroAssistenti + "]";
	}
	
	@Override
	public boolean isUguale(Bonus bonusDaConfrontare) {
		return bonusDaConfrontare instanceof BonusAssistenti
				&& ((BonusAssistenti) bonusDaConfrontare).getNumeroAssistenti() == numeroAssistenti;
	}

	/**
	 * @return the numeroAssistenti
	 */
	public int getNumeroAssistenti() {
		return numeroAssistenti;
	}
}

package server.model.bonus;

import server.model.Giocatore;
import server.model.percorso.Percorso;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusMoneta implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -752223726614249534L;
	private final int steps;
	private final Percorso percorsoRicchezza;

	public BonusMoneta(Percorso percorsoRicchezza, int steps) {
		this.steps = steps;
		this.percorsoRicchezza = percorsoRicchezza;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		percorsoRicchezza.muoviGiocatore(giocatore, steps);
	}

}

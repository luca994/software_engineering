package server.model.bonus;

import server.model.Giocatore;
import server.model.percorso.Percorso;

/**
 * This specific bonus moves the player-token a previously set number of steps
 * (from file) along the victory route
 * 
 * @author Massimiliano Ventura
 */
public class BonusPercorsoNobilta implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4367120543526592204L;
	private final int steps;
	private final Percorso percorsoNobilta;

	public BonusPercorsoNobilta(Percorso percorsoNobilta, int steps) {
		this.percorsoNobilta = percorsoNobilta;
		this.steps = steps;
	}

	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * @return the percorsoNobiltà
	 */
	public Percorso getPercorsoNobilta() {
		return percorsoNobilta;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		percorsoNobilta.muoviGiocatore(giocatore, steps);
	}

}

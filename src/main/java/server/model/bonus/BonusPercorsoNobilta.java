package server.model.bonus;

import server.eccezioni.FuoriDalLimiteDelPercorso;
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
		if (steps < 0 || percorsoNobilta == null)
			throw new IllegalStateException("Il percorso non può essere nullo e i passi non possono essere negativi");
		this.percorsoNobilta = percorsoNobilta;
		this.steps = steps;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BonusPercorsoNobilta [steps=" + steps + "]";
	}
	
	@Override
	public void azioneBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		try {
			percorsoNobilta.muoviGiocatore(giocatore, steps);
		} catch (FuoriDalLimiteDelPercorso e) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public boolean isUguale(Bonus bonusDaConfrontare) {
		return bonusDaConfrontare instanceof BonusPercorsoNobilta
				&& ((BonusPercorsoNobilta) bonusDaConfrontare).getSteps() == steps;
	}

	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}

}

package model;

/**
 * @author Massimiliano Ventura
 *This specific bonus moves the player-token a
 *previously set of steps (from file) 
 *along the victory route
 */
public class BonusPercorsoNobiltà extends Bonus {
	private int steps;
	private Percorso percorsoNobiltà;
	@Override
	public void azioneBonus(Giocatore giocatore) {
		percorsoNobiltà.muoviGiocatoreAvanti(giocatore, steps);
	}
	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}
	/**
	 * @param steps the steps to set
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}
	/**
	 * @return the percorsoNobiltà
	 */
	public Percorso getPercorsoNobiltà() {
		return percorsoNobiltà;
	}
	/**
	 * @param percorsoNobiltà the percorsoNobiltà to set
	 */
	public void setPercorsoNobiltà(Percorso percorsoNobiltà) {
		this.percorsoNobiltà = percorsoNobiltà;
	}
	
}

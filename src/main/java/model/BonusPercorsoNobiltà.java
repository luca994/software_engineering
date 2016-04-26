package model;

/**
 * @author Massimiliano Ventura
 *This specific bonus moves the player-token a
 *previously set of steps (from file) 
 *along the victory route
 */
public class BonusPercorsoNobiltà extends Bonus {
	private final int steps;
	private final Percorso percorsoNobiltà;
	public BonusPercorsoNobiltà(Percorso percorsoNobiltà, int steps)
	{
		this.percorsoNobiltà=percorsoNobiltà;
		this.steps=steps;
	}
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
	 * @return the percorsoNobiltà
	 */
	public Percorso getPercorsoNobiltà() {
		return percorsoNobiltà;
	}
	
	
}

/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusPuntoVittoria extends Bonus {
	private final Percorso percorsoVittoria;
	private final int steps;
	public BonusPuntoVittoria(Percorso percorsoVittoria, int steps) {
		this.percorsoVittoria = percorsoVittoria;
		this.steps=steps;
	}
	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}
	/**
	 * @return the percorsoVittoria
	 */
	public Percorso getPercorsoVittoria() {
		return percorsoVittoria;
	}
	/**
	 * @param steps the steps to set
	 */
	@Override
	public void azioneBonus(Giocatore giocatore) 
	{
		percorsoVittoria.muoviGiocatoreAvanti(giocatore, steps);

	}
}

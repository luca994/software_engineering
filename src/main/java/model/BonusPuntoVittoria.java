/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusPuntoVittoria extends Bonus {

	private Percorso percorsoVittoria;
	private int steps;

	/**
	 * @return the percorsoVittoria
	 */
	public Percorso getPercorsoVittoria() {
		return percorsoVittoria;
	}

	/**
	 * @param percorsoVittoria the percorsoVittoria to set
	 */
	public void setPercorsoVittoria(Percorso percorsoVittoria) {
		this.percorsoVittoria = percorsoVittoria;
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
	@Override
	public void azioneBonus(Giocatore giocatore) {
		percorsoVittoria.muoviGiocatoreAvanti(giocatore, steps);

	}

}

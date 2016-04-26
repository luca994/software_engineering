/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusMoneta extends Bonus {
	private final int step;
	private final Percorso percorsoVittoria;
	public BonusMoneta(Percorso percorsoVittoria, int step)
	{
		this.step=step;
		this.percorsoVittoria=percorsoVittoria;
	}
	@Override
	public void azioneBonus(Giocatore giocatore) 
	{
		percorsoVittoria.muoviGiocatore(giocatore, step);
	}
}

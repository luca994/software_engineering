package bonus;

import model.Giocatore;
import percorso.Percorso;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusMoneta implements Bonus {
	
	private final int steps;
	private final Percorso percorsoRicchezza;
	
	public BonusMoneta(Percorso percorsoRicchezza, int steps)
	{
		this.steps=steps;
		this.percorsoRicchezza=percorsoRicchezza;
	}
	
	@Override
	public void azioneBonus(Giocatore giocatore) 
	{
		percorsoRicchezza.muoviGiocatore(giocatore, steps);
	}
}

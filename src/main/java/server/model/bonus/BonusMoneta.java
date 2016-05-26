package server.model.bonus;

import java.io.IOException;

import server.model.Giocatore;
import server.model.percorso.Percorso;

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
	public void azioneBonus(Giocatore giocatore) throws IOException
	{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			percorsoRicchezza.muoviGiocatore(giocatore, steps);
		}
	
	
}

package server.model.bonus;

import java.io.IOException;

import server.model.Giocatore;
import server.model.percorso.Percorso;

/**
 *This specific bonus moves the player-token a
 *previously set number of steps (from file) along the victory route
 * @author Massimiliano Ventura
 */
public class BonusPercorsoNobiltà implements Bonus {

	
	private final int steps;
	private final Percorso percorsoNobiltà;
	
	public BonusPercorsoNobiltà(Percorso percorsoNobiltà, int steps)
	{
		this.percorsoNobiltà=percorsoNobiltà;
		this.steps=steps;
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
	
	@Override
	public void azioneBonus(Giocatore giocatore) throws IOException {
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			percorsoNobiltà.muoviGiocatoreAvanti(giocatore, steps);
		}
		
	}

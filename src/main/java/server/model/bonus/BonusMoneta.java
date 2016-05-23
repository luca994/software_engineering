package server.model.bonus;

import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Giocatore;
import server.model.percorso.Percorso;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusMoneta implements Bonus {
	private static final Logger log= Logger.getLogger( BonusMoneta.class.getName() );
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
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			percorsoRicchezza.muoviGiocatore(giocatore, steps);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}
}

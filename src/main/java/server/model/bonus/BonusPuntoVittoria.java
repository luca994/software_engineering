package server.model.bonus;

import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Giocatore;
import server.model.percorso.Percorso;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusPuntoVittoria implements Bonus {

	private static final Logger log= Logger.getLogger( BonusPuntoVittoria.class.getName() );
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
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere null");
			percorsoVittoria.muoviGiocatoreAvanti(giocatore, steps);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}
}

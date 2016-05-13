/**
 * 
 */
package model.azione;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.Città;
import model.Giocatore;
import model.Re;
import model.percorso.Percorso;

/**
 * @author Massimiliano Ventura
 *
 */
public class MuoviRe implements Azione {

	private static final Logger log= Logger.getLogger( MuoviRe.class.getName() );
	private Città destinazione;
	private Re re;
	private Percorso percorsoRicchezza;
	/**
	 * @param re
	 * @param percorsoRicchezza
	 */
	public MuoviRe(Re re, Percorso percorsoRicchezza) {
		super();
		this.re = re;
		this.percorsoRicchezza = percorsoRicchezza;
	}
	
	@Override
	public void eseguiAzione(Giocatore giocatore) {
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");		
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}	
	}

}

/**
 * 
 */
package server.model.azione;

import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Giocatore;

/**
 * @author Massimiliano Ventura
 *
 */
public class AzioneOpzionaleNulla implements Azione {
	
	private static final Logger log= Logger.getLogger( AzioneOpzionaleNulla.class.getName() );
	
	
	@Override
	public void eseguiAzione(Giocatore giocatore) {
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			//giocatore.setAzioneOpzionale(true);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}

}

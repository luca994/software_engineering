/**
 * 
 */
package azione;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.Giocatore;

/**
 * @author Massimiliano Ventura
 *
 */
public class AzionePrincipaleNulla implements Azione {
	private static final Logger log= Logger.getLogger( AzionePrincipaleNulla.class.getName() );
	/* (non-Javadoc)
	 * @see model.Azione#eseguiAzione(model.Giocatore)
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) {
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nulla");
			giocatore.setAzionePrincipale(true);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}

}

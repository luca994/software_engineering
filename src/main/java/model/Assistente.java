package model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Luca
 *
 */
public class Assistente extends OggettoVendibile {
	private static final Logger log= Logger.getLogger( Assistente.class.getName() );
	@Override
	public void transazione(Giocatore giocatore) {
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			getPercorsoRicchezza().muoviGiocatore(giocatore, -getPrezzo());
			getPercorsoRicchezza().muoviGiocatore(getGiocatore(), getPrezzo());
			giocatore.getAssistenti().add(this);
			getGiocatore().getAssistenti().remove(this);
			getMercato().getOggettiInVendita().remove(this);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}
	
	

}

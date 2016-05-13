package model.azione;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.Giocatore;
import model.Regione;

/**
 * @author Luca
 *
 */
public class CambioTessereCostruzione implements Azione {
	
	private static final Logger log= Logger.getLogger( CambioTessereCostruzione.class.getName() );
	private Regione regione;
	
	
	
	/**
	 * @param regione
	 */
	public CambioTessereCostruzione(Regione regione) {
		this.regione = regione;
	}


	/**
	 * Get two new Tessere Costruzione in the visble list of obtaiable Tessere Costruzione
	 * @throws IllegalStateException if giocatore hasn't enough Aiutanti 
	 */
	@Override
	public void eseguiAzione (Giocatore giocatore){
		try{
			if (giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			if(giocatore.getAssistenti().isEmpty())
				throw new IllegalStateException("Il giocatore non possiede abbastanza aiutanti par eseguire l'azione");
			regione.getTessereCoperte().addAll(regione.getTessereCostruzione());
			regione.getTessereCostruzione().clear();
			regione.getTessereCostruzione().add(regione.getTessereCoperte().get(0));
			regione.getTessereCoperte().remove(0);
			regione.getTessereCostruzione().add(regione.getTessereCoperte().get(0));
			regione.getTessereCoperte().remove(0);
			giocatore.getAssistenti().remove(0);
			giocatore.setAzioneOpzionale(true);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}
}

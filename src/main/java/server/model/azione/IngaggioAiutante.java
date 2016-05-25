package server.model.azione;

import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Assistente;
import server.model.Giocatore;
import server.model.percorso.Percorso;

/**
 * @author Luca
 *
 */
public class IngaggioAiutante implements Azione {

	private static final Logger log= Logger.getLogger( IngaggioAiutante.class.getName() );
	
	private final static int COSTO_AIUTANTE=3;
	private Percorso percorsoRicchezza;
	
	/**
	 * @param percorsoRicchezza
	 */
	public IngaggioAiutante(Percorso percorsoRicchezza) {
		super();
		this.percorsoRicchezza = percorsoRicchezza;
	}

	
	/**
	 * @author Riccardo
	 * the player add an assistant to his assistants's list and come back of
	 * COSTO_AIUTANTE in the richness route
	 * @param giocatore the player who wants to buy an assistant
	 */
	public void eseguiAzione (Giocatore giocatore){
		try{
			if (giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			percorsoRicchezza.muoviGiocatore(giocatore, -COSTO_AIUTANTE);
			giocatore.getAssistenti().add(new Assistente());
	//		giocatore.setAzioneOpzionale(true);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}	
	}
}

package server.model.azione;

import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Giocatore;

/**
 * @author Luca
 *
 */
public class AzionePrincipaleAggiuntiva extends Azione {
	
	public AzionePrincipaleAggiuntiva() {
		super(null);
	}


	private static final Logger log= Logger.getLogger( AzionePrincipaleAggiuntiva.class.getName() );
	
	
	@Override
	/**
	 * The player can execute a new Azione Principale paying 3 Aiutanti
	 */
	public void eseguiAzione (Giocatore giocatore){
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			if(giocatore.getAssistenti().size()>=3){
			for(int i=0;i<3;i++)
				giocatore.getAssistenti().remove(0);	
		//	giocatore.setAzionePrincipale(false);
		//	giocatore.setAzioneOpzionale(true);
			}
			else
				throw new IllegalStateException("Non hai abbastanza aiutanti");
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}


	@Override
	public boolean verificaInput(Giocatore giocatore) {
		// TODO Auto-generated method stub
		return false;
	}
}

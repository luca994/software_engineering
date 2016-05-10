package model;

/**
 * @author Luca
 *
 */
public class AzionePrincipaleAggiuntiva implements Azione {
	@Override
	/**
	 * The player can execute a new Azione Principale paying 3 Aiutanti
	 */
	public void eseguiAzione (Giocatore giocatore){
		try{
			if(giocatore.getAssistenti().size()>=3){
			for(int i=0;i<3;i++)
				giocatore.getAssistenti().remove(0);	
			giocatore.setAzionePrincipale(false);
			giocatore.setAzioneOpzionale(true);}
			else
				throw new IllegalStateException("Non hai abbastanza aiutanti");
		}
		catch(IllegalStateException e)
		{
			System.err.println(e.getLocalizedMessage());
		}
	}
}

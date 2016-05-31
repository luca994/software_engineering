package server.model.azione;

import server.model.Giocatore;

/**
 * @author Luca
 *
 */
public class AzionePrincipaleAggiuntiva extends AzioneRapida {
	
	public AzionePrincipaleAggiuntiva() {
		super(null);
	}

	
	
	@Override
	/**
	 * The player can execute a new Azione Principale paying 3 Aiutanti
	 */
	public void eseguiAzione (Giocatore giocatore){
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			if(giocatore.getAssistenti().size()<3)
				throw new IllegalArgumentException("Il giocatore non ha abbastanza assistenti");
			for(int i=0;i<3;i++)
			giocatore.getAssistenti().remove(0);	
			giocatore.getStatoGiocatore().azionePrincipaleAggiuntiva();
			giocatore.getStatoGiocatore().azioneEseguita(this);
	}


	@Override
	public boolean verificaInput(Giocatore giocatore) {
		return (giocatore.getAssistenti().size()>=3);
	}
}

package server.model.azione;

import server.config.Configurazione;
import server.eccezione.NumeroAiutantiIncorretto;
import server.model.Giocatore;

/**
 * Rapid action that allows you to run a new main action by paying with
 * assistants.
 *
 */
public class AzionePrincipaleAggiuntiva extends AzioneRapida {

	public AzionePrincipaleAggiuntiva() {
		super(null);
	}

	/**
	 * Performs the action
	 * 
	 * @throws NullPointerException
	 *             if the player is null
	 * @throws NumeroAiutantiIncorretto
	 *             if the number of Assistenti that player owns is insufficient to execute the
	 *             action. (<3)
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) throws NumeroAiutantiIncorretto {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		if (giocatore.getAssistenti().size() < Configurazione.COSTO_AZIONE_PRINCIPALE_AGGIUNTIVA)
			throw new NumeroAiutantiIncorretto("Il giocatore non ha abbastanza assistenti");
		for (int counter = 0; counter < Configurazione.COSTO_AZIONE_PRINCIPALE_AGGIUNTIVA; counter++)
			giocatore.getAssistenti().remove(0);
		giocatore.getStatoGiocatore().azionePrincipaleAggiuntiva();
		giocatore.getStatoGiocatore().azioneEseguita(this);
	}
}

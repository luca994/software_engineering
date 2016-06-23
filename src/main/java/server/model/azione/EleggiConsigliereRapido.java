package server.model.azione;

import server.config.Configurazione;
import server.eccezioni.NumeroAiutantiIncorretto;
import server.model.Giocatore;
import server.model.componenti.Consigliere;
import server.model.componenti.Consiglio;

/**
 * Rapid action that allows you to elect a councilor for an assistant .
 *
 */
public class EleggiConsigliereRapido extends AzioneRapida {

	private Consigliere consigliere;
	private Consiglio consiglio;

	/**
	 * @param consigliere
	 * @param consiglio
	 */
	public EleggiConsigliereRapido(Consigliere consigliere, Consiglio consiglio) {
		super(null);
		this.consigliere = consigliere;
		this.consiglio = consiglio;
	}

	/**
	 * Performs the action
	 * 
	 * @throws NumeroAiutantiIncorretto
	 *             if the number of Assistenti that player owns is insufficient
	 *             to execute the action.
	 * @throws NullPointerException
	 *             if the player is null
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) throws NumeroAiutantiIncorretto {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		if (giocatore.getAssistenti().size() < Configurazione.COSTO_ELEGGI_CONSIGLIERE_RAPIDO)
			throw new NumeroAiutantiIncorretto("Il giocatore non possiede abbastanza aiutanti per eseguire l'azione");

		consiglio.addConsigliere(consigliere);
		consiglio.removeConsigliere();
		for(int counter=0;counter<Configurazione.COSTO_ELEGGI_CONSIGLIERE_RAPIDO;counter++)
			giocatore.getAssistenti().remove(0);
		giocatore.getStatoGiocatore().azioneEseguita(this);

	}
}

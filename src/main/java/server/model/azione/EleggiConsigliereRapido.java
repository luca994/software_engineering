package server.model.azione;

import eccezione.NumeroAiutantiIncorretto;
import server.model.Giocatore;
import server.model.componenti.Consigliere;
import server.model.componenti.Consiglio;

/**
 * Rapid action that allows you to elect a councilor for an assistant .
 *
 */
public class EleggiConsigliereRapido extends AzioneRapida {

	private static final int NUM_AIUTANTI_COSTO_ELEGGI_CONSIGLIERE_RAPIDO = 1;

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
		if (giocatore.getAssistenti().size() < NUM_AIUTANTI_COSTO_ELEGGI_CONSIGLIERE_RAPIDO)
			throw new NumeroAiutantiIncorretto("Il giocatore non possiede abbastanza aiutanti per eseguire l'azione");

		consiglio.addConsigliere(consigliere);
		consiglio.removeConsigliere();
		giocatore.getAssistenti().remove(0);
		giocatore.getStatoGiocatore().azioneEseguita(this);

	}
}

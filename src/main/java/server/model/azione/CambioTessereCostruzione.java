package server.model.azione;

import eccezione.NumeroAiutantiIncorretto;
import server.model.Giocatore;
import server.model.Regione;

/**
 * Rapid action that allows you to take the two TesserePermesso face up in a
 * region, return them to the bottom of the corresponding deck and draws two new
 * ones from the top of the deck.
 *
 */
public class CambioTessereCostruzione extends AzioneRapida {

	private Regione regione;

	/**
	 * @param regione
	 */
	public CambioTessereCostruzione(Regione regione) {
		super(null);
		this.regione = regione;
	}

	/**
	 * Performs the action
	 * 
	 * @throws NumeroAiutantiIncorretto
	 *             if the number of Assistenti that player owns is insufficient
	 *             to execute the action.
	 * 
	 * @throws NullPointerException
	 *             if the player is null
	 * 
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) throws NumeroAiutantiIncorretto {

		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		if (giocatore.getAssistenti().isEmpty())
			throw new NumeroAiutantiIncorretto("Il giocatore non possiede abbastanza aiutanti per eseguire l'azione");
		regione.getTessereCoperte().addAll(regione.getTessereCostruzione());
		regione.getTessereCostruzione().clear();
		regione.getTessereCostruzione().add(regione.getTessereCoperte().get(0));
		regione.getTessereCoperte().remove(0);
		regione.getTessereCostruzione().add(regione.getTessereCoperte().get(0));
		regione.getTessereCoperte().remove(0);
		giocatore.getAssistenti().remove(0);
		giocatore.getStatoGiocatore().azioneEseguita(this);

	}

}

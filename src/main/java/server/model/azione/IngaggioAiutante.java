package server.model.azione;

import eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Assistente;
import server.model.Giocatore;
import server.model.Gioco;

/**
 * Rapid action that allows you to hire an assistant paying 3 coins
 *
 */
public class IngaggioAiutante extends AzioneRapida {

	private final static int COSTO_AIUTANTE = 3;

	public IngaggioAiutante(Gioco gioco) {
		super(gioco);
	}

	/**
	 * Performs the action
	 * 
	 * 
	 * @throws FuoriDalLimiteDelPercorso
	 *             if the player doesn't have enough money to executes the
	 *             action.
	 */
	public void eseguiAzione(Giocatore giocatore) throws FuoriDalLimiteDelPercorso {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, -COSTO_AIUTANTE);
		giocatore.getAssistenti().add(new Assistente());
		giocatore.getStatoGiocatore().azioneEseguita(this);
	}

}

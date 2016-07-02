package server.model.azione;

import server.config.Configurazione;
import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Assistente;

/**
 * Rapid action that allows you to hire an assistant paying 3 coins
 *
 */
public class IngaggioAiutante extends AzioneRapida {

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
	@Override
	public void eseguiAzione(Giocatore giocatore) throws FuoriDalLimiteDelPercorso {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
				-Configurazione.COSTO_INGAGGIA_AIUTANTE);
		giocatore.getAssistenti().add(new Assistente());
		giocatore.getStatoGiocatore().azioneEseguita(this);
	}

}

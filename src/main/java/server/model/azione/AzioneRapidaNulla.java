/**
 * 
 */
package server.model.azione;

import server.model.Giocatore;
import server.model.Gioco;

/**
 * @author Luca
 *
 */
public class AzioneRapidaNulla extends AzioneRapida {

	public AzioneRapidaNulla(Gioco gioco) {
		super(gioco);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.model.azione.Azione#eseguiAzione(server.model.Giocatore)
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) {
		giocatore.getStatoGiocatore().azioneEseguita(this);
	}

}

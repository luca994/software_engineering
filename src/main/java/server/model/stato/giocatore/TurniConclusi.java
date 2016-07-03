package server.model.stato.giocatore;

import server.model.Giocatore;
import server.model.azione.Azione;
import server.model.componenti.OggettoVendibile;

/**
 * The class that represents the status of a player who has finished his game
 */
public class TurniConclusi extends StatoGiocatore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7294097463183734775L;

	/**
	 * Constructor for state TurniConclusi
	 */
	public TurniConclusi(Giocatore giocatore) {
		super(giocatore);
	}

	/**
	 * this method can not be called in the state of rounds concluded
	 */
	@Override
	public void azioneEseguita(Azione azione) {
		throw new IllegalStateException();
	}

	/**
	 * this method can not be called in the state of rounds concluded
	 */
	@Override
	public void azionePrincipaleAggiuntiva() {
		throw new IllegalStateException();
	}

	/**
	 * this method can not be called in the state of rounds concluded
	 */
	@Override
	public void azioneRapidaAggiuntiva() {
		throw new IllegalStateException();
	}

	/**
	 * the next state is not implemented
	 */
	@Override
	public void prossimoStato() {
	}

	/**
	 * this method can not be called in the state of rounds concluded
	 */
	@Override
	public void tuttiGliEmporiCostruiti() {
		throw new IllegalStateException();
	}

	/**
	 * this method can not be called in the state of rounds concluded
	 */
	@Override
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere) {
		throw new IllegalStateException();
	}

	/**
	 * this method can not be called in the state of rounds concluded
	 */
	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) {
		throw new IllegalStateException();
	}

}

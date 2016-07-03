package server.model.stato.giocatore;

import server.model.Giocatore;
import server.model.azione.Azione;
import server.model.componenti.OggettoVendibile;

/**
 * The class that represents the status of the player when it is not his turn
 */
public class AttesaTurno extends StatoGiocatore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3974356968762307853L;

	/**
	 * Constructor for state AttesaTurno
	 * @param giocatore the player that is waiting for his turn
	 */
	public AttesaTurno(Giocatore giocatore) {
		super(giocatore);
	}

	/**
	 * sets the player to the next state
	 */
	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new TurnoNormale(giocatore));
	}

	/**
	 * this method can not be called during the waiting state
	 */
	@Override
	public void azioneEseguita(Azione azione) {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the waiting state
	 */
	@Override
	public void azionePrincipaleAggiuntiva() {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the waiting state
	 */
	@Override
	public void azioneRapidaAggiuntiva() {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the waiting state
	 */
	@Override
	public void tuttiGliEmporiCostruiti() {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the waiting state
	 */
	@Override
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere) {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the waiting state
	 */
	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) {
		throw new UnsupportedOperationException();
	}
}

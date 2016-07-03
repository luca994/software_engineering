package server.model.stato.giocatore;

import server.model.Giocatore;
import server.model.azione.Azione;
import server.model.componenti.OggettoVendibile;

/**
 * The class that represents an outstanding player from the game
 */
public class Sospeso extends StatoGiocatore {

	private static final long serialVersionUID = -5090687060870237321L;

	/**
	 * The constructor for state sospeso
	 */
	public Sospeso(Giocatore giocatore) {
		super(giocatore);
	}

	/**
	 * this method can not be called during the suspended state
	 */
	@Override
	public void azioneEseguita(Azione azione) {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the suspended state
	 */
	@Override
	public void azionePrincipaleAggiuntiva() {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the suspended state
	 */
	@Override
	public void azioneRapidaAggiuntiva() {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the suspended state
	 */
	@Override
	public void tuttiGliEmporiCostruiti() {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the suspended state
	 */
	@Override
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere) {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the suspended state
	 */
	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) {
		throw new UnsupportedOperationException();
	}

	/**
	 * this method can not be called during the suspended state
	 */
	@Override
	public void prossimoStato() {
		throw new UnsupportedOperationException();
	}

}

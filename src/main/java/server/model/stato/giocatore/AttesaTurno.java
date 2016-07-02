package server.model.stato.giocatore;

import server.model.Giocatore;
import server.model.azione.Azione;
import server.model.componenti.OggettoVendibile;

public class AttesaTurno extends StatoGiocatore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3974356968762307853L;

	public AttesaTurno(Giocatore giocatore) {
		super(giocatore);
	}

	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new TurnoNormale(giocatore));
	}

	@Override
	public void azioneEseguita(Azione azione) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void azionePrincipaleAggiuntiva() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void azioneRapidaAggiuntiva() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void tuttiGliEmporiCostruiti() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) {
		throw new UnsupportedOperationException();
	}
}

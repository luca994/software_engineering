package server.model.stato.giocatore;

import server.model.Giocatore;
import server.model.OggettoVendibile;

public class AttesaTurno extends StatoGiocatore {

	public AttesaTurno(Giocatore giocatore) {
		super(giocatore);
	}

	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new TurnoNormale(giocatore));
	}

	@Override
	public void azionePrincipaleEseguita() {
		throw new IllegalStateException();
	}

	@Override
	public void azioneRapidaEseguita() {
		throw new IllegalStateException();
	}

	@Override
	public void azionePrincipaleAggiuntiva() {
		throw new IllegalStateException();
	}

	@Override
	public void azioneRapidaAggiuntiva() {
		throw new IllegalStateException();
	}

	@Override
	public void tuttiGliEmporiCostruiti() {
		throw new IllegalStateException();
	}

	@Override
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere, int prezzo) {
		throw new IllegalStateException();
	}

	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) {
		throw new IllegalStateException();
	}

}

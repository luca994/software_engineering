package server.model.stato.giocatore;

import server.model.Giocatore;
import server.model.OggettoVendibile;

public class TurniConclusi extends StatoGiocatore {

	public TurniConclusi(Giocatore giocatore) {
		super(giocatore);
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
	public void prossimoStato() {
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

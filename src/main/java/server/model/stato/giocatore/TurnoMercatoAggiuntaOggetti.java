package server.model.stato.giocatore;

import java.lang.instrument.IllegalClassFormatException;

import server.model.Giocatore;
import server.model.Mercato;
import server.model.OggettoVendibile;

public class TurnoMercatoAggiuntaOggetti extends TurnoMercato {

	private Mercato mercato;

	public TurnoMercatoAggiuntaOggetti(Giocatore giocatore, Mercato mercato) {
		super(giocatore);
		this.mercato = mercato;
	}

	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere, int prezzo) {
		oggettoDaAggiungere.setMercato(mercato);
		oggettoDaAggiungere.setPrezzo(prezzo);
		oggettoDaAggiungere.setGiocatore(giocatore);
		oggettoDaAggiungere.aggiungiOggetto(mercato);
	}

	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new AttesaTurno(giocatore));
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
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) throws IllegalClassFormatException {
		throw new IllegalStateException();
	}
}

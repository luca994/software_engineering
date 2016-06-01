package server.model.stato.giocatore;

import eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.Mercato;
import server.model.OggettoVendibile;
import server.model.azione.Azione;

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
	public void azioneEseguita(Azione azione) {
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
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) throws FuoriDalLimiteDelPercorso {
		throw new IllegalStateException();
	}
}

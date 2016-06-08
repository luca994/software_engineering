package server.model.stato.giocatore;

import eccezione.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.Mercato;
import server.model.OggettoVendibile;
import server.model.azione.Azione;

public class TurnoMercatoAggiuntaOggetti extends TurnoMercato {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8664434123655986775L;
	
	private Mercato mercato;

	public TurnoMercatoAggiuntaOggetti(Giocatore giocatore, Mercato mercato) {
		super(giocatore);
		this.mercato = mercato;
	}

	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere, int prezzo) {
		if(prezzo<=0)
			throw new IllegalArgumentException("Il prezzo deve essere un numero positivo");
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

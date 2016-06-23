package server.model.stato.giocatore;

import server.eccezione.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.azione.Azione;
import server.model.componenti.OggettoVendibile;

public class TurnoMercatoCompraVendita extends TurnoMercato {

	/**
	 * 
	 */
	private static final long serialVersionUID = 895962931696841870L;

	public TurnoMercatoCompraVendita(Giocatore giocatore) {
		super(giocatore);
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
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere) {
		throw new IllegalStateException("Il giocatore" + giocatore
				+ " ha eseguito un metodo per aggiungere un oggetto al mercato" + "durante la fase di compravendita");
	}

	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) throws FuoriDalLimiteDelPercorso {
		oggettoDaAcquistare.compra(giocatore);
	}

}

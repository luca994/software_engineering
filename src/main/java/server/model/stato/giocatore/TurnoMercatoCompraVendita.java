package server.model.stato.giocatore;

import java.lang.instrument.IllegalClassFormatException;

import server.model.Giocatore;
import server.model.OggettoVendibile;

public class TurnoMercatoCompraVendita extends TurnoMercato {

	private static final String ERRORE= "Errore: il giocatore ha eseguito un'azione normale durante il turno di mercato";
	
	public TurnoMercatoCompraVendita(Giocatore giocatore) {
		super(giocatore);
	}

	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new AttesaTurno(giocatore));
	}

	@Override
	public void azionePrincipaleEseguita() {
		throw new IllegalStateException(ERRORE);
	}

	@Override
	public void azioneRapidaEseguita() {
		throw new IllegalStateException(ERRORE);
	}

	@Override
	public void azionePrincipaleAggiuntiva() {
		throw new IllegalStateException(ERRORE);
		
	}

	@Override
	public void azioneRapidaAggiuntiva() {
		throw new IllegalStateException(ERRORE);	
	}

	@Override
	public void tuttiGliEmporiCostruiti() {
		throw new IllegalStateException(ERRORE);
	}

	@Override
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere, int prezzo)
			throws IllegalClassFormatException {
		throw new IllegalStateException("Il giocatore"+giocatore+" ha eseguito un metodo per aggiungere un oggetto al mercato"
				+ "durante la fase di compravendita");
	}

	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) throws IllegalClassFormatException {
	}

}

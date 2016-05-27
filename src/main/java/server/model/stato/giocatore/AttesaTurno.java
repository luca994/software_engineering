package server.model.stato.giocatore;


import java.lang.instrument.IllegalClassFormatException;

import server.model.Giocatore;
import server.model.OggettoVendibile;

public class AttesaTurno extends StatoGiocatore {

	public static final String ERRORE_1="Errore: il giocatore ha eseguito un'azionePrincipale quando non era il suo turno";
	public static final String ERRORE_2="Errore: il giocatore ha eseguito un'azioneRapida quando non era il suo turno";
	
	public AttesaTurno(Giocatore giocatore) {
		super(giocatore);
	}
	
	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new TurnoNormale(giocatore));
	}

	@Override
	public void azionePrincipaleEseguita() {
		throw new IllegalStateException(ERRORE_1);
	}

	@Override
	public void azioneRapidaEseguita() {
		throw new IllegalStateException(ERRORE_2);
	}

	@Override
	public void azionePrincipaleAggiuntiva() {
		throw new IllegalStateException(ERRORE_1);
	}

	@Override
	public void azioneRapidaAggiuntiva() {
		throw new IllegalStateException(ERRORE_2);
	}

	@Override
	public void tuttiGliEmporiCostruiti() {
		throw new IllegalStateException();
	}

	@Override
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere, int prezzo)
			throws IllegalClassFormatException {
		throw new IllegalStateException();
	}

	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) throws IllegalClassFormatException {
		throw new IllegalStateException();		
	}
	
}

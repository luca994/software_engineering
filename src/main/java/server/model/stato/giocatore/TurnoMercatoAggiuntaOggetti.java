package server.model.stato.giocatore;

import server.model.Giocatore;

public class TurnoMercatoAggiuntaOggetti extends TurnoMercato {
	
	private static final String ERRORE= "Errore: il giocatore ha eseguito un'azionePrincipale durante il turno di mercato";
	
	public TurnoMercatoAggiuntaOggetti(Giocatore giocatore) {
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

}

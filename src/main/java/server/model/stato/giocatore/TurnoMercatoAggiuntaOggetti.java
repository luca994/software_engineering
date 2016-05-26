package server.model.stato.giocatore;

import server.model.Giocatore;

public class TurnoMercatoAggiuntaOggetti extends TurnoMercato {
	
	public TurnoMercatoAggiuntaOggetti(Giocatore giocatore) {
		super(giocatore);
	}

	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new AttesaTurno(giocatore));
	}

}

package server.model.stato.giocatore;

import server.model.Giocatore;

public class AttesaTurno extends StatoGiocatore {

	public AttesaTurno(Giocatore giocatore) {
		super(giocatore);
	}
	
	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new TurnoNormale(giocatore));
	}
	
}

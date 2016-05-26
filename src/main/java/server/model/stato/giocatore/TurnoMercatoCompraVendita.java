package server.model.stato.giocatore;

import server.model.Giocatore;

public class TurnoMercatoCompraVendita extends TurnoMercato {

	public TurnoMercatoCompraVendita(Giocatore giocatore) {
		super(giocatore);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new AttesaTurno(giocatore));
	}

}

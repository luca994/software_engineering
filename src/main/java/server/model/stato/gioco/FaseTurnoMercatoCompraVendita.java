package server.model.stato.gioco;

import server.model.Gioco;

public class FaseTurnoMercatoCompraVendita extends FaseTurnoMercato {

	public FaseTurnoMercatoCompraVendita(Gioco gioco) {
		super(gioco);
	}

	@Override
	public void eseguiFase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prossimoStato() {
		gioco.setStato(new FaseTurnoSemplice(gioco));
	}

}

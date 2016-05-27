package server.model.stato.gioco;

import server.model.Gioco;
import server.model.Mercato;

public class FaseTurnoMercatoCompraVendita extends FaseTurnoMercato {

	private Mercato mercato;
	
	public FaseTurnoMercatoCompraVendita(Gioco gioco,Mercato mercato) {
		super(gioco);
		this.mercato=mercato;
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

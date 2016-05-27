package server.model.stato.gioco;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.Mercato;
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;

public class FaseTurnoMercatoAggiuntaOggetti extends FaseTurnoMercato {	
	
	private Mercato mercato;
	
	public FaseTurnoMercatoAggiuntaOggetti(Gioco gioco) {
		super(gioco);
		giocatori=gioco.getGiocatori();
		mercato = new Mercato(gioco.getTabellone().getPercorsoRicchezza()); 
	}
	
	@Override
	public void eseguiFase() {
		for(Giocatore giocat: giocatori){
			giocat.setStatoGiocatore(new TurnoMercatoAggiuntaOggetti(giocat,mercato));
			while(giocat.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti);
	}}

	@Override
	public void prossimoStato() {
		gioco.setStato(new FaseTurnoMercatoCompraVendita(gioco,mercato));
	}

}

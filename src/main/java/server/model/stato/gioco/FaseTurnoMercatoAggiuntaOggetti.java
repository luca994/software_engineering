package server.model.stato.gioco;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.Mercato;
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;

public class FaseTurnoMercatoAggiuntaOggetti extends FaseTurnoMercato {

	private Mercato mercato;

	public FaseTurnoMercatoAggiuntaOggetti(Gioco gioco) {
		super(gioco);
		mercato = new Mercato(gioco.getTabellone().getPercorsoRicchezza());
	}

	@Override
	public void eseguiFase() {
		for (Giocatore giocat : getGiocatori()) {
			giocat.setStatoGiocatore(new TurnoMercatoAggiuntaOggetti(giocat, mercato));
			while (giocat.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti);
		}
	}

	@Override
	public void prossimoStato() {
		getGioco().setStato(new FaseTurnoMercatoCompraVendita(getGioco(),mercato));
	}

}

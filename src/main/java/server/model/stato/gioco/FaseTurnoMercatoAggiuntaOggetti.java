package server.model.stato.gioco;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.Mercato;
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;

public class FaseTurnoMercatoAggiuntaOggetti extends FaseTurnoMercato {

	/**
	 * 
	 */
	private static final long serialVersionUID = -540726560802672676L;
	private Mercato mercato;

	public FaseTurnoMercatoAggiuntaOggetti(Gioco gioco) {
		super(gioco);
		mercato = new Mercato(gioco.getTabellone().getPercorsoRicchezza());
	}

	@Override
	public void eseguiFase() {
		for (Giocatore giocat : getGiocatori()) {
			giocat.setStatoGiocatore(new TurnoMercatoAggiuntaOggetti(giocat, mercato));
			getGioco().notificaObservers(getGioco().getTabellone());
			while (true) {
				synchronized (giocat.getStatoGiocatore()) {
					if (!(giocat.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti))
						break;
				}
			}
		}
	}

	@Override
	public void prossimoStato() {
		getGioco().setStato(new FaseTurnoMercatoCompraVendita(getGioco(), mercato));
	}

}

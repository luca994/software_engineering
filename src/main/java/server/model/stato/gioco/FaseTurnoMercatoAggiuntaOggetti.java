package server.model.stato.gioco;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Mercato;
import server.model.stato.giocatore.Sospeso;
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
			if(!(giocat.getStatoGiocatore() instanceof Sospeso)){
				giocat.setStatoGiocatore(new TurnoMercatoAggiuntaOggetti(giocat, mercato));
				getGioco().notificaObservers(getGioco().getTabellone());
				while (true) {
					synchronized (giocat.getStatoGiocatore()) {
						if (!(giocat.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti))
							break;
					}
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void prossimoStato() {
		getGioco().setStato(new FaseTurnoMercatoCompraVendita(getGioco(), mercato));
	}

}

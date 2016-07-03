package server.model.stato.gioco;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Mercato;
import server.model.stato.giocatore.Sospeso;
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;
/**
 * the class that represents the gaming market was on duty when you add items to the market
 *
 */
public class FaseTurnoMercatoAggiuntaOggetti extends FaseTurnoMercato{

	/**
	 * 
	 */
	private static final long serialVersionUID = -540726560802672676L;
	private Mercato mercato;

	/**
	 * Constructor class for faseTurnoMercatoAggiuntaOggetti
	 * @param gioco
	 */
	public FaseTurnoMercatoAggiuntaOggetti(Gioco gioco) {
		super(gioco);
		mercato = new Mercato(gioco.getTabellone().getPercorsoRicchezza());
	}

	/**
	 * He handles this kind of turn for each player
	 */
	@Override
	public void eseguiFase() {
		for (Giocatore giocat : getGiocatori()) {
			if(!(giocat.getStatoGiocatore() instanceof Sospeso)){
				//avvio il thread che controlla il tempo per l'azione
				setGiocatoreCorrente(giocat);
				Thread threadTempo = new Thread(this);
				threadTempo.start();
				
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
		setGiocatoreCorrente(null);
	}

	/**
	 * sets the game state to the next state
	 */
	@Override
	public void prossimoStato() {
		getGioco().setStato(new FaseTurnoMercatoCompraVendita(getGioco(), mercato));
	}

	/**
	 * @return the mercato
	 */
	public Mercato getMercato() {
		return mercato;
	}
}

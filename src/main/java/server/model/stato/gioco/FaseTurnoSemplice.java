package server.model.stato.gioco;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.giocatore.TurniConclusi;
import server.model.stato.giocatore.TurnoNormale;

public class FaseTurnoSemplice extends Esecuzione{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8160577673952636015L;
	private boolean ultimoTurno;

	public FaseTurnoSemplice(Gioco gioco) {
		super(gioco);
		ultimoTurno=false;
	}

	/**
	 * before executing the method , all players must be in the state of
	 * AttesaTurno Executes a regular turn for each players, and if a player has
	 * build the last emporium, it executes the last turn for each other
	 * players.
	 */
	public void eseguiFase() {
		boolean exit = false;
		for (Giocatore giocat : getGiocatori()) {
			if(giocat.isConnesso()){
				if (giocat.getStatoGiocatore() instanceof TurniConclusi) {
					exit = true;
					break;
				}
				giocat.getStatoGiocatore().prossimoStato();
				getGioco().notificaObservers(getGioco().getTabellone());
				
				while (true) {
					synchronized (giocat.getStatoGiocatore()) {
						if (!(giocat.getStatoGiocatore() instanceof TurnoNormale))
							break;
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (ultimoTurno)
					giocat.setStatoGiocatore(new TurniConclusi(giocat));
				if (giocat.getStatoGiocatore() instanceof TurniConclusi) {
					ultimoTurno = true;
				}
			}
		}
		if (ultimoTurno && !exit)
			eseguiFase();
	}

	@Override
	public void prossimoStato() {
		if (ultimoTurno)
			getGioco().setStato(new Terminato(getGioco()));
		else
			getGioco().setStato(new FaseTurnoMercatoAggiuntaOggetti(getGioco()));
	}
}

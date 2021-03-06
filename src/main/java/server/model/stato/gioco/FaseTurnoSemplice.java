package server.model.stato.gioco;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.giocatore.Sospeso;
import server.model.stato.giocatore.TurniConclusi;
import server.model.stato.giocatore.TurnoNormale;

/**
 * The class that represents the phase of the game where you play a simple round
 *
 */
public class FaseTurnoSemplice extends Esecuzione{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8160577673952636015L;
	private boolean ultimoTurno;

	public FaseTurnoSemplice(Gioco gioco) {
		super(gioco);
		ultimoTurno = false;
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
			
			//avvio il thread che controlla il tempo per l'azione
			if(!(giocat.getStatoGiocatore() instanceof Sospeso)){
				setGiocatoreCorrente(giocat);
				Thread threadTempo = new Thread(this);
				threadTempo.start();
			}
			
			if (!(giocat.getStatoGiocatore() instanceof Sospeso)) {
				if (giocat.getStatoGiocatore() instanceof TurniConclusi) {
					exit = true;
					break;
				}
				giocat.getStatoGiocatore().prossimoStato();
				if(ultimoTurno)
					getGioco().notificaObservers("Ultimo turno!",giocat);
			}
			getGioco().notificaObservers(getGioco().getTabellone());
			
			//prova bonus
			/*try {
				giocat.getTessereUsate().add(getGioco().getTabellone().getRegioni().get(2).getTessereCostruzione().get(1));
				getGioco().getTabellone().getPercorsoNobilta().muoviGiocatore(giocat, 13);
				getGioco().getTabellone().getRegioni().get(0).getTessereCostruzione().get(0).getBonus().clear();
				getGioco().getTabellone().getRegioni().get(0).getTessereCostruzione().get(0).getBonus().add(new BonusPercorsoNobilta(getGioco().getTabellone().getPercorsoNobilta(), 1));
				getGioco().notificaObservers(getGioco().getTabellone());
			} catch (FuoriDalLimiteDelPercorso e1) {
				e1.printStackTrace();
			}*/
			//
			
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
		setGiocatoreCorrente(null);
		if (ultimoTurno && !exit)
			eseguiFase();
	}

	/**
	 * sets the game state to the next state
	 */
	@Override
	public void prossimoStato() {
		if (ultimoTurno)
			getGioco().setStato(new Terminato(getGioco()));
		else
			getGioco().setStato(new FaseTurnoMercatoAggiuntaOggetti(getGioco()));
	}
}

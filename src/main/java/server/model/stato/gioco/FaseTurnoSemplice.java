package server.model.stato.gioco;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.bonus.Bonus;
import server.model.bonus.BonusMoneta;
import server.model.bonus.BonusPercorsoNobilta;
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
				
				//prova bonus (uso un bonus percorso nobiltà per far avere un bonusGettoneCitta)
				getGioco().getTabellone().cercaCitta("castrum").getBonus().clear();
				getGioco().getTabellone().cercaCitta("castrum").getBonus().add(new BonusMoneta(getGioco().getTabellone().getPercorsoRicchezza(), 4));
				getGioco().getTabellone().cercaCitta("castrum").getEmpori().add(giocat);
				getGioco().getTabellone().cercaCitta("arkon").getBonus().clear();
				getGioco().getTabellone().cercaCitta("arkon").getBonus().add(new BonusPercorsoNobilta(getGioco().getTabellone().getPercorsoNobilta(), 1));
				getGioco().getTabellone().cercaCitta("arkon").getEmpori().add(giocat);
				Bonus bonus = new BonusPercorsoNobilta(getGioco().getTabellone().getPercorsoNobilta(), 16);
				bonus.azioneBonus(giocat);
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

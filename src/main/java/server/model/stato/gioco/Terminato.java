package server.model.stato.gioco;

import java.util.ArrayList;
import java.util.List;

import server.eccezione.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.percorso.Casella;

public class Terminato extends StatoGioco {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2639717549945418496L;
	private static final int PUNTI_PRIMO_PERCORSO_NOBILTA = 5;
	private static final int PUNTI_SECONDO_PERCORSO_NOBILTA = 2;
	private static final int PUNTI_PIU_TESSERE_PERMESSO = 3;

	public Terminato(Gioco gioco) {
		super(gioco);
	}

	@Override
	public void eseguiFase() {
		List<Giocatore> possibiliVincitori;
		Giocatore vincitore = null;
		assegnaPuntiFinaliNobilta();
		assegnoPuntiGiocatoriConPiuPermessi();
		possibiliVincitori = calcoloVincitore();
		if (possibiliVincitori.isEmpty())
			throw new IllegalArgumentException();
		if (possibiliVincitori.size() == 1)
			vincitore = possibiliVincitori.get(0);
		if (possibiliVincitori.size() > 1)
			vincitore = ricalcoloVincitore(possibiliVincitori);
		if (vincitore == null)
			throw new NullPointerException();
	}

	/**
	 * Calculates the player/players who have the largest number of victory
	 * points
	 * 
	 * @return the winner/winners
	 */
	private List<Giocatore> calcoloVincitore() {
		List<Giocatore> vincitore = new ArrayList<>();
		List<Casella> caselleNobilta = getGioco().getTabellone().getPercorsoVittoria().caselleNonVuotePiuAvanti();
		vincitore.addAll(caselleNobilta.get(0).getGiocatori());
		return vincitore;

	}

	/**
	 * it is called when there is a draw between players . Recalculates the
	 * winner by counting the number of Politics cards and the number of
	 * assistants.
	 * 
	 * @param possibiliVincitori
	 *            the list of players who can win the game.
	 * @return the winner
	 */
	private Giocatore ricalcoloVincitore(List<Giocatore> possibiliVincitori) {
		Giocatore vincitore = null;
		int maxNumCarteEAiutanti = 0;
		for (Giocatore giocat : possibiliVincitori) {
			if (giocat.numeroAiutantiECartePolitica() > maxNumCarteEAiutanti) {
				maxNumCarteEAiutanti = giocat.numeroAiutantiECartePolitica();
				vincitore = giocat;
			}
		}
		return vincitore;
	}

	/**
	 * searches for the first and the second player in the path of nobility and
	 * assigns their points.
	 * 
	 */
	private void assegnaPuntiFinaliNobilta() {
		List<Casella> caselleNobilta = getGioco().getTabellone().getPercorsoNobilta().caselleNonVuotePiuAvanti();
		try {
			for (Giocatore giocat : caselleNobilta.get(0).getGiocatori())
				getGioco().getTabellone().getPercorsoVittoria().muoviGiocatore(giocat, PUNTI_PRIMO_PERCORSO_NOBILTA);

			if (caselleNobilta.get(0).getGiocatori().size() > 1)
				return;
			for (Giocatore giocat : caselleNobilta.get(1).getGiocatori())
				getGioco().getTabellone().getPercorsoVittoria().muoviGiocatore(giocat, PUNTI_SECONDO_PERCORSO_NOBILTA);
		} catch (FuoriDalLimiteDelPercorso e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Assigns points to players with more permessiCostruzione.
	 */
	private void assegnoPuntiGiocatoriConPiuPermessi() {
		List<Giocatore> giocatoriConPiuPermessi = new ArrayList<>();
		int maxNumTesserePermesso = 0;
		for (Giocatore giocat : getGioco().getGiocatori()) {
			if (giocat.numeroPermessiTotali() == maxNumTesserePermesso)
				giocatoriConPiuPermessi.add(giocat);
			if (giocat.numeroPermessiTotali() > maxNumTesserePermesso) {
				maxNumTesserePermesso = giocat.numeroPermessiTotali();
				giocatoriConPiuPermessi.clear();
				giocatoriConPiuPermessi.add(giocat);
			}
		}
		for (Giocatore giocat : giocatoriConPiuPermessi)
			try {
				getGioco().getTabellone().getPercorsoVittoria().muoviGiocatore(giocat, PUNTI_PIU_TESSERE_PERMESSO);
			} catch (FuoriDalLimiteDelPercorso e) {
				throw new IllegalArgumentException(e);
			}
	}

	@Override
	public void prossimoStato() {
	}
}

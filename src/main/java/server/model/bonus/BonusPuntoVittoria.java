package server.model.bonus;

import eccezione.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.percorso.Percorso;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusPuntoVittoria implements Bonus {

	private static final long serialVersionUID = 4725534973807481625L;
	private final Percorso percorsoVittoria;
	private final int steps;

	public BonusPuntoVittoria(Percorso percorsoVittoria, int steps) {
		if (steps < 0 || percorsoVittoria == null)
			throw new IllegalStateException("Il percorso non può essere nullo e i passi non possono essere negativi");
		this.percorsoVittoria = percorsoVittoria;
		this.steps = steps;
	}

	/**
	 * @param steps
	 *            the steps to set
	 */
	@Override
	public void azioneBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere null");
		try {
			percorsoVittoria.muoviGiocatore(giocatore, steps);
		} catch (FuoriDalLimiteDelPercorso e) {
			throw new IndexOutOfBoundsException();
		}
	}
}

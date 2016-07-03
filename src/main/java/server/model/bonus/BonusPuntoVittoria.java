package server.model.bonus;

import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.percorso.Percorso;

/**
 * 
 * the class that represents the victory point bonus
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BonusPuntoVittoria [Punti=" + steps + "]";
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

	@Override
	public boolean isUguale(Bonus bonusDaConfrontare) {
		return bonusDaConfrontare instanceof BonusPuntoVittoria
				&& ((BonusPuntoVittoria) bonusDaConfrontare).getSteps() == steps;
	}

	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}
}

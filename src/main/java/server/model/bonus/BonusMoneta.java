package server.model.bonus;

import server.model.Giocatore;
import server.model.percorso.Percorso;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusMoneta implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -752223726614249534L;
	private final int steps;
	private final Percorso percorsoRicchezza;

	public BonusMoneta(Percorso percorsoRicchezza, int steps) {
		if(steps<0||percorsoRicchezza==null)
			throw new IllegalStateException("Il percorso non può essere nullo e le monete non possono essere negative");
		this.steps = steps;
		this.percorsoRicchezza = percorsoRicchezza;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		try {
			percorsoRicchezza.muoviGiocatore(giocatore, steps);
		} catch (Exception e) {
			throw new IndexOutOfBoundsException();
		}
	}

}

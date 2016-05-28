/**
 * 
 */
package server.model.bonus;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.TesseraCostruzione;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusTesseraPermesso implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2996522240976825764L;
	private TesseraCostruzione tessera;
	private Gioco gioco;

	public BonusTesseraPermesso(Gioco gioco) {
		this.gioco = gioco;
	}

	/**
	 * @return the tessera
	 */
	public TesseraCostruzione getTessera() {
		return tessera;
	}

	/**
	 * @param tessera
	 *            the tessera to set
	 */
	public void setTessera(TesseraCostruzione tessera) {
		this.tessera = tessera;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {
		gioco.notificaObservers(this);
		giocatore.getTessereValide().add(tessera);
		tessera.getRegioneDiAppartenenza().nuovaTessera(tessera);
		for (Bonus b : tessera.getBonus()) {
			b.azioneBonus(giocatore);
		}

	}

}

/**
 * 
 */
package server.model.bonus;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.TesseraCostruzione;

/**
 * To make the method azioneBonus work the class need a
 * method(tesseraDalGiocatore)that returns a tile(tesseraCostruzione), chosen
 * from the player from his set of used card the moment that azioneBonus is
 * called.
 * 
 * @author Massimiliano Ventura
 *
 */
public class BonusRiutilizzoCostruzione implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1470256807943263026L;
	private TesseraCostruzione tessera;
	private Gioco gioco;
	private Giocatore giocatore;
	private boolean tesseraCostruzioneCorretta;

	public BonusRiutilizzoCostruzione(Gioco gioco) {
		this.gioco = gioco;
		tesseraCostruzioneCorretta = false;
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

	/**
	 * 
	 * @return the player
	 */
	public Giocatore getGiocatore() {
		return giocatore;
	}

	/**
	 * set the value of tesseraCostruzioneCorretta
	 * 
	 * @param tesseraCostruzioneCorretta
	 *            the value to set
	 */
	public void setTesseraCostruzioneCorretta(boolean tesseraCostruzioneCorretta) {
		this.tesseraCostruzioneCorretta = tesseraCostruzioneCorretta;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {

		this.giocatore = giocatore;
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		while (!tesseraCostruzioneCorretta) {
			gioco.notificaObservers(this);
		}
		if (tessera != null)
			for (Bonus b : tessera.getBonus())
				b.azioneBonus(giocatore);
	}

	@Override
	public boolean isUguale(Bonus bonusDaConfrontare) {
		return bonusDaConfrontare instanceof BonusRiutilizzoCostruzione
				&& ((BonusRiutilizzoCostruzione) bonusDaConfrontare).getTessera().isUguale(tessera);
	}

}

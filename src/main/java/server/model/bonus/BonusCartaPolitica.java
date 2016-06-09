/**
 * 
 */
package server.model.bonus;

import server.model.CartePoliticaFactory;
import server.model.Giocatore;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusCartaPolitica implements Bonus {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2539790215830619005L;
	private int numeroCarte;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BonusCartaPolitica [numeroCarte=" + numeroCarte + "]";
	}

	public BonusCartaPolitica(int numeroCarte) {
		if (numeroCarte < 0)
			throw new IllegalStateException("Il numero di carte non può essere negativo");
		this.numeroCarte = numeroCarte;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException();
		for (int i = 0; i < this.numeroCarte; i++)
			giocatore.getCartePolitica().add(new CartePoliticaFactory().creaCartaPolitica());
	}

	@Override
	public boolean isUguale(Bonus bonusDaConfrontare) {
		return bonusDaConfrontare instanceof BonusCartaPolitica
				&& ((BonusCartaPolitica) bonusDaConfrontare).getNumeroCarte() == numeroCarte;
	}

	/**
	 * @return the numeroCarte
	 */
	public int getNumeroCarte() {
		return numeroCarte;
	}

}

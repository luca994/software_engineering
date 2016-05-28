package server.model.bonus;

import java.util.HashSet;
import java.util.Set;

import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;

/**
 * This class asks the player which city bonuses wants to obtain with this
 * particular bonus.
 * 
 * @author Massimiliano Ventura
 *
 */
public class BonusGettoneCitta implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9062172546546184125L;
	private int numeroCitta;
	private Set<Citta> citta;
	private Gioco gioco;
	boolean cittaGiusta;

	public BonusGettoneCitta(int numeroCitta, Gioco gioco) {
		this.gioco = gioco;
		this.numeroCitta = numeroCitta;
		this.citta = new HashSet<>();
	}

	/**
	 * @return the numeroCittà
	 */
	public int getNumeroCitta() {
		return numeroCitta;
	}

	/**
	 * set the boolean
	 * 
	 * @param cittaGiusta
	 *            the parameter to set
	 */
	public void setCittaGiusta(boolean cittaGiusta) {
		this.cittaGiusta = cittaGiusta;
	}

	/**
	 * @return the città
	 */
	public Set<Citta> getCitta() {
		return citta;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		for (int i = 0; i < numeroCitta; i++) {
			do {
				cittaGiusta = true;
				gioco.notificaObservers(this);
				for (Citta c : citta) {
					for (Bonus b : c.getBonus()) {
						if (b instanceof BonusPercorsoNobilta) {
							cittaGiusta = false;
							citta.remove(c);
							gioco.notificaObservers("C'è un bonus percorso nobiltà");
						}
					}
					if (!c.getEmpori().contains(giocatore) && citta.contains(c)) {
						cittaGiusta = false;
						citta.remove(c);
						gioco.notificaObservers("Non hai un'emporio nella città");
					}
				}
			} while (!cittaGiusta);
		}

		for (Citta cit : this.citta) {
			cit.eseguiBonus(giocatore);
		}

	}
}

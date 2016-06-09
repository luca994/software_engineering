package server.model.bonus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BonusGettoneCitta [numeroCitta=" + numeroCitta + "]";
	}

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
				gioco.notificaObservers(this, giocatore);
				for (Citta c : citta) {
					for (Bonus b : c.getBonus()) {
						if (b instanceof BonusPercorsoNobilta) {
							cittaGiusta = false;
							citta.remove(c);
							gioco.notificaObservers("C'è un bonus percorso nobiltà", giocatore);
						}
					}
					if (!c.getEmpori().contains(giocatore) && citta.contains(c)) {
						cittaGiusta = false;
						citta.remove(c);
						gioco.notificaObservers("Non hai un'emporio nella città", giocatore);
					}
				}
			} while (!cittaGiusta);
		}

		for (Citta cit : this.citta) {
			cit.eseguiBonus(giocatore);
		}

	}

	@Override
	public boolean isUguale(Bonus bonusDaConfrontare) {
		if (bonusDaConfrontare instanceof BonusGettoneCitta
				&& ((BonusGettoneCitta) bonusDaConfrontare).getNumeroCitta() == numeroCitta) {
			List<Citta> copiaCitta = new ArrayList<>();
			copiaCitta.addAll(citta);
			for (Citta c1 : citta)
				for (Citta c : ((BonusGettoneCitta) bonusDaConfrontare).getCitta())
					if (c.isUguale(c1)){
						copiaCitta.remove(c1);
						break;
					}
			return copiaCitta.isEmpty();
		}
		return false;
	}

	/**
	 * @return the gioco
	 */
	public Gioco getGioco() {
		return gioco;
	}
}

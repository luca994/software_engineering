package server.model.bonus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Citta;

/**
 * the class that represents the coin city bonus
 */
public class BonusGettoneCitta implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9062172546546184125L;
	private int numeroCitta;
	private Set<Citta> citta;
	private Gioco gioco;
	private boolean cittaGiusta;
	private Citta cittaPerCompletamentoBonus;

	public BonusGettoneCitta(int numeroCitta, Gioco gioco) {
		if (numeroCitta < 0)
			throw new IllegalArgumentException("Il numero di città non può essere minore di 0");
		this.gioco = gioco;
		this.numeroCitta = numeroCitta;
		this.citta = new HashSet<>();
	}

	@Override
	public String toString() {
		return "BonusGettoneCitta [numeroCitta=" + numeroCitta + "]";
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

	/**
	 * @return the cittaPerCompletamentoBonus
	 */
	public Citta getCittaPerCompletamentoBonus() {
		return cittaPerCompletamentoBonus;
	}

	/**
	 * @param cittaPerCompletamentoBonus
	 *            the cittaPerCompletamentoBonus to set
	 */
	public void setCittaPerCompletamentoBonus(Citta cittaPerCompletamentoBonus) {
		this.cittaPerCompletamentoBonus = cittaPerCompletamentoBonus;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		for (int i = 0; i < numeroCitta; i++) {
			do {
				cittaGiusta = true;
				gioco.notificaObservers(gioco.getTabellone(), giocatore);
				gioco.notificaObservers(this, giocatore);
				List<Citta> cittaDaRimuovere = new ArrayList<>();
				for (Citta c : citta) {
					for (Bonus b : c.getBonus()) {
						if (b instanceof BonusPercorsoNobilta) {
							cittaGiusta = false;
							cittaDaRimuovere.add(c);
							gioco.notificaObservers("C'è un bonus percorso nobiltà", giocatore);
						}
					}
					if (!c.getEmpori().contains(giocatore) && citta.contains(c)) {
						cittaGiusta = false;
						cittaDaRimuovere.add(c);
						gioco.notificaObservers("Non hai un'emporio nella città", giocatore);
					}
				}
				citta.removeAll(cittaDaRimuovere);
				cittaPerCompletamentoBonus = null;
			} while (!cittaGiusta);
		}

		for (Citta cit : this.citta) {
			cit.eseguiBonus(giocatore);
		}
		citta = new HashSet<>();
	}

	@Override
	public boolean isUguale(Bonus bonusDaConfrontare) {
		if (bonusDaConfrontare instanceof BonusGettoneCitta
				&& ((BonusGettoneCitta) bonusDaConfrontare).getNumeroCitta() == numeroCitta) {
			List<Citta> copiaCitta = new ArrayList<>();
			copiaCitta.addAll(citta);
			for (Citta c1 : citta)
				for (Citta c : ((BonusGettoneCitta) bonusDaConfrontare).getCitta())
					if (c.isUguale(c1)) {
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

package server.model.componenti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.bonus.Bonus;

public class TesseraCostruzione extends OggettoVendibile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5137085350436101703L;

	private Set<Citta> citta;
	private Regione regioneDiAppartenenza;
	private Set<Bonus> bonus;
	private final String id;

	/**
	 * @param bonus
	 * @param citta
	 * @param regioneDiAppartenenza
	 */
	public TesseraCostruzione(Set<Bonus> bonus, Set<Citta> citta, Regione regioneDiAppartenenza, String id) {
		this.bonus = bonus;
		this.citta = citta;
		this.regioneDiAppartenenza = regioneDiAppartenenza;
		this.id=id;
	}

	public boolean verifyCitta(Citta cittaDaVerificare) {
		return this.citta.contains(cittaDaVerificare);
	}

	/**
	 * assigns all the bonuses in the set of bonus
	 * 
	 * @param giocatore
	 */
	public void eseguiBonus(Giocatore giocatore) {
		for (Bonus b : bonus) {
			b.azioneBonus(giocatore);
		}
	}

	/**
	 * @return the bonus
	 */
	public Set<Bonus> getBonus() {
		return bonus;
	}

	/**
	 * @return the città
	 */
	public Set<Citta> getCitta() {
		return citta;
	}

	/**
	 * @return the regioneDiAppartenenza
	 */
	public Regione getRegioneDiAppartenenza() {
		return regioneDiAppartenenza;
	}

	/**
	 * @param citta
	 *            the città to set
	 */
	public void setCitta(Set<Citta> citta) {
		this.citta = citta;
	}

	/**
	 * @param regioneDiAppartenenza
	 *            the regioneDiAppartenenza to set
	 */
	public void setRegioneDiAppartenenza(Regione regioneDiAppartenenza) {
		this.regioneDiAppartenenza = regioneDiAppartenenza;
	}

	@Override
	public void compra(Giocatore nuovoProprietario) throws FuoriDalLimiteDelPercorso {
		if (nuovoProprietario == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		transazioneDenaro(nuovoProprietario);
		nuovoProprietario.getTessereValide().add(this);
		getGiocatore().getTessereValide().remove(this);
		getMercato().getOggettiInVendita().remove(this);
		resettaAttributiOggettoVendibile();
	}

	@Override
	public String toString() {
		if (getPrezzo() == 0)
			return "TesseraCostruzione [città=" + citta + ", regioneDiAppartenenza=" + regioneDiAppartenenza
					+ ", bonus=" + bonus + "]";
		else
			return "TesseraCostruzione [città=" + citta + ", regioneDiAppartenenza=" + regioneDiAppartenenza
					+ ", bonus=" + bonus + "]" + " prezzo: " + getPrezzo() + " proprietario: "
					+ getGiocatore().getNome();
	}

	/**
	 * compares the region name , the names of the city and the bonuses and
	 * returns true if they are all the same
	 * 
	 * @param tesseraDaConfrontare
	 *            is the tessera to compare
	 * @return true if tesseraDaConforntare is similar to this.
	 */
	public boolean isUguale(TesseraCostruzione tesseraDaConfrontare) {
		if (!tesseraDaConfrontare.regioneDiAppartenenza.getNome().equals(regioneDiAppartenenza.getNome()))
			return false;
		List<Citta> copiaCitta = new ArrayList<>();
		copiaCitta.addAll(citta);
		for (Citta c1 : citta)
			for (Citta c : tesseraDaConfrontare.getCitta())
				if (c.isUguale(c1)) {
					copiaCitta.remove(c1);
					break;
				}
		if (!copiaCitta.isEmpty())
			return false;
		List<Bonus> copiaBonus = new ArrayList<>();
		copiaBonus.addAll(bonus);
		for (Bonus b1 : bonus)
			for (Bonus b : tesseraDaConfrontare.getBonus())
				if (b.isUguale(b1)) {
					copiaBonus.remove(b1);
					break;
				}
		return copiaBonus.isEmpty();
	}

	/**
	 * @param bonus
	 *            the bonus to set
	 */
	public void setBonus(Set<Bonus> bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
}

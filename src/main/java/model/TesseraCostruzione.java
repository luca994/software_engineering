package model;

import java.util.Set;

public class TesseraCostruzione extends OggettoConBonus {

	private Set<Città> città;
	private Regione regioneDiAppartenenza;
	
	/**
	 * @param bonus
	 * @param città
	 * @param regioneDiAppartenenza
	 */
	public TesseraCostruzione(Set<Bonus> bonus, Set<Città> città, Regione regioneDiAppartenenza) {
		super(bonus);
		this.città = città;
		this.regioneDiAppartenenza = regioneDiAppartenenza;
	}
	
	public boolean verifyCittà(Città cittàDaVerificare){
		return this.città.contains(cittàDaVerificare);
		}
	
	/**
	 * @return the città
	 */
	public Set<Città> getCittà() {
		return città;
	}
	
	/**
	 * @return the regioneDiAppartenenza
	 */
	public Regione getRegioneDiAppartenenza() {
		return regioneDiAppartenenza;
	}


	/**
	 * @param città the città to set
	 */
	public void setCittà(Set<Città> città) {
		this.città = città;
	}


	/**
	 * @param regioneDiAppartenenza the regioneDiAppartenenza to set
	 */
	public void setRegioneDiAppartenenza(Regione regioneDiAppartenenza) {
		this.regioneDiAppartenenza = regioneDiAppartenenza;
	}
}

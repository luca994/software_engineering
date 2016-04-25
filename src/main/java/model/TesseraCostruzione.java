package model;

import java.util.Set;

public class TesseraCostruzione extends OggettoConBonus {

	private Set<String> nomeCittà;
	private Set<Bonus> elencoBonus;
	/**
	 * @return the nomeCittà
	 */
	public Set<String> getNomeCittà() {
		return nomeCittà;
	}
	/**
	 * @param nomeCittà the nomeCittà to set
	 */
	public void setNomeCittà(Set<String> nomeCittà) {
		this.nomeCittà = nomeCittà;
	}
	/**
	 * @return the elencoBonus
	 */
	public Set<Bonus> getElencoBonus() {
		return this.elencoBonus;
	}
	/**
	 * @param elencoBonus the elencoBonus to set
	 */
	public void setElencoBonus(Set<Bonus> elencoBonus) {
		this.elencoBonus = elencoBonus;
	}
}

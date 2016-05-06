package model;

import java.util.Set;

public class TesseraCostruzione extends OggettoConBonus {

	private Set<Città> città;
	private Set<Bonus> elencoBonus;
	
	
	
	/**
	 * @param città
	 * @param elencoBonus
	 */
	public TesseraCostruzione(Set<Città> città, Set<Bonus> elencoBonus) {
		this.città = città;
		this.elencoBonus = elencoBonus;
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
	
	public void eseguiBonus (Giocatore giocatore){
		for(Bonus b : elencoBonus){
			b.azioneBonus(giocatore);
		}
	}
	public boolean verifyCittà(Città cittàDaVerificare){

		return this.città.contains(cittàDaVerificare);
		}
}

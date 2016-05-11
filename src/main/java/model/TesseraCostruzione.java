package model;

import java.util.Set;

import model.bonus.Bonus;

public class TesseraCostruzione extends OggettoVendibile{

	private Set<Città> città;
	private Regione regioneDiAppartenenza;
	private Set<Bonus> bonus;
	
	/**
	 * @param bonus
	 * @param città
	 * @param regioneDiAppartenenza
	 */
	public TesseraCostruzione(Set<Bonus> bonus, Set<Città> città, Regione regioneDiAppartenenza) {
		this.bonus=bonus;
		this.città = città;
		this.regioneDiAppartenenza = regioneDiAppartenenza;
	}
	
	public boolean verifyCittà(Città cittàDaVerificare){
		return this.città.contains(cittàDaVerificare);
		}
	
	/**
	 * assigns all the bonuses in the set of bonus
	 * @param giocatore
	 */
	public void eseguiBonus (Giocatore giocatore){
		for(Bonus b : bonus){
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

	@Override
	public void transazione(Giocatore giocatore) {
		try{
			getPercorsoRicchezza().muoviGiocatore(giocatore, -getPrezzo());
			getPercorsoRicchezza().muoviGiocatore(getGiocatore(), getPrezzo());
			giocatore.getTessereValide().add(this);
			getGiocatore().getTessereValide().remove(this);
			getMercato().getOggettiInVendita().remove(this);
		}
		catch(IndexOutOfBoundsException e){
			System.err.println(e.getMessage());
		}
	}
}

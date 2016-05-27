package server.model;

import java.io.IOException;
import java.util.Set;

import server.model.bonus.Bonus;

public class TesseraCostruzione extends OggettoVendibile{

	private Set<Citta> citta;
	private Regione regioneDiAppartenenza;
	private Set<Bonus> bonus;
	
	/**
	 * @param bonus
	 * @param citta
	 * @param regioneDiAppartenenza
	 */
	public TesseraCostruzione(Set<Bonus> bonus, Set<Citta> citta, Regione regioneDiAppartenenza) {
		this.bonus=bonus;
		this.citta = citta;
		this.regioneDiAppartenenza = regioneDiAppartenenza;
	}
	
	public boolean verifyCittà(Citta cittàDaVerificare){
		return this.citta.contains(cittàDaVerificare);
		}
	
	/**
	 * assigns all the bonuses in the set of bonus
	 * @param giocatore
	 * @throws IOException  
	 */
	public void eseguiBonus (Giocatore giocatore) throws IOException{
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
	public Set<Citta> getCittà() {
		return citta;
	}
	
	/**
	 * @return the regioneDiAppartenenza
	 */
	public Regione getRegioneDiAppartenenza() {
		return regioneDiAppartenenza;
	}


	/**
	 * @param citta the città to set
	 */
	public void setCittà(Set<Citta> citta) {
		this.citta = citta;
	}


	/**
	 * @param regioneDiAppartenenza the regioneDiAppartenenza to set
	 */
	public void setRegioneDiAppartenenza(Regione regioneDiAppartenenza) {
		this.regioneDiAppartenenza = regioneDiAppartenenza;
	}

	@Override
	public void transazione(Giocatore giocatore){
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			getPercorsoRicchezza().muoviGiocatore(giocatore, -getPrezzo());
			getPercorsoRicchezza().muoviGiocatore(getGiocatore(), getPrezzo());
			giocatore.getTessereValide().add(this);
			getGiocatore().getTessereValide().remove(this);
			getMercato().getOggettiInVendita().remove(this);
	}

	@Override
	public String toString() {
		return "TesseraCostruzione [città=" + citta + ", regioneDiAppartenenza=" + regioneDiAppartenenza + ", bonus="
				+ bonus + "]";
	}
	
	
}

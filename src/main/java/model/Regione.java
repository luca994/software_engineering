package model;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Riccardo
 *
 */
public class Regione {

	private Set<Città> città;
	private Consiglio consiglio;
	private String nome;
	private List<OggettoConBonus> tessereCostruzione;
	private List<OggettoConBonus> tessereCoperte;
	
	public Regione(List<OggettoConBonus> tessereCoperte){
		this.tessereCoperte=tessereCoperte;
		Collections.shuffle(this.tessereCoperte);
		this.tessereCostruzione.add(this.tessereCoperte.get(0));
		this.tessereCostruzione.add(this.tessereCoperte.get(1));
		for(OggettoConBonus tessera: this.tessereCostruzione)
		{
			this.tessereCoperte.remove(tessera);
		}
			
	}
	/**
	 *Removes the parameter tessera from the list of obtainable Tessere Permesso Costruzione and moves the first
	 *element of the covered Tessere Permesso Costruzione in to the list of obtainable Tessere
	 * @param tessera
	 */
	public void nuovaTessera(OggettoConBonus tessera)
	{
		this.tessereCostruzione.remove(tessera);
		this.tessereCostruzione.add(this.tessereCoperte.get(0));
		this.tessereCoperte.remove(0);
	}
	/**
	 * @return the città
	 */
	public Set<Città> getCittà() {
		return città;
	}
	/**
	 * @return the consiglio
	 */
	public Consiglio getConsiglio() {
		return consiglio;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @return the tessereCostruzione
	 */
	public List<OggettoConBonus> getTessereCostruzione() {
		return tessereCostruzione;
	}
	/**
	 * @return the tessereCoperte
	 */
	public List<OggettoConBonus> getTessereCoperte() {
		return tessereCoperte;
	}
	
}

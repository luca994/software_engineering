package model;

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
	
}

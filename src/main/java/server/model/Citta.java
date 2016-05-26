package server.model;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Riccardo
 *
 */

public class Citta extends OggettoConBonus {

	private final String nome;
	private final Regione regione;
	private Color colore;
	private Set<Giocatore> empori;
	private Re re;
	private Set<Citta> cittaVicina;

	/**
	 * @param nome
	 * @param regione
	 */
	public Citta(String nome, Regione regione) {
		super(null);
		this.nome = nome;
		this.regione = regione;
		this.empori = new HashSet<>();
		this.cittaVicina = new HashSet<>();
	}

	/**
	 * creates a list of cities which have the player's emporium
	 * 
	 * @param giocatore
	 *            the owner of the emporium
	 * @return return a list of near cities which have the player's emporium
	 */
	/* cittàVicineConEmpori inizialmente vuota si riempe con la ricorsione */
	public List<Citta> cittaVicinaConEmporio(Giocatore giocatore, List<Citta> cittaVicineConEmpori) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		for (Citta c : cittaVicina) {
			if (c.getEmpori().contains(giocatore) && !cittaVicineConEmpori.contains(c)) {
				cittaVicineConEmpori.add(c);
				c.cittaVicinaConEmporio(giocatore, cittaVicineConEmpori);
			}
		}
		return cittaVicineConEmpori;
	}

	/**
	 * @return the empori
	 */
	public Set<Giocatore> getEmpori() {
		return empori;
	}

	/**
	 * @param re
	 *            the re to set
	 */
	public void setRe(Re re) {
		this.re = re;
	}

	/**
	 * @return the cittàVicina
	 */
	public Set<Citta> getCittàVicina() {
		return cittaVicina;
	}

	/**
	 * @return the regione
	 */
	public Regione getRegione() {
		return regione;
	}

	/**
	 * @return the colore
	 */
	public Color getColore() {
		return colore;
	}

	/**
	 * @param colore
	 *            the colore to set
	 */
	public void setColore(Color colore) {
		this.colore = colore;
	}

	/**
	 * @return the re
	 */
	public Re getRe() {
		return re;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
}

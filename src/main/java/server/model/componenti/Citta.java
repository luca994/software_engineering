package server.model.componenti;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import server.model.Giocatore;

/**
 * 
 * @author Riccardo
 *
 */

public class Citta extends OggettoConBonus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7048245012061517818L;

	private int numGettone;
	private final String nome;
	private final Regione regione;
	private Color colore;
	private Set<Giocatore> empori;
	private Re re;
	private Set<Citta> cittaVicina;

	/**
	 * Constructor for citta, initializes nome,regione,empori,cittaVicina.
	 * 
	 * @param nome
	 *            the nome for the inizialization
	 * @param regione
	 *            the regione for the inizialization
	 * 
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
	 * 
	 * @param cittaVicineConEmpori
	 *            an empty list.
	 * @return return a list of near cities which have the player's emporium
	 * @throws NullPointerException
	 *             if giocatore or cittaVicineConEmpori are null
	 */
	/* cittàVicineConEmpori inizialmente vuota si riempe con la ricorsione */
	public List<Citta> cittaVicinaConEmporio(Giocatore giocatore, List<Citta> cittaVicineConEmpori) {
		if (giocatore == null || cittaVicineConEmpori == null)
			throw new NullPointerException();
		for (Citta c : cittaVicina) {
			if (c.getEmpori().contains(giocatore) && !cittaVicineConEmpori.contains(c)) {
				cittaVicineConEmpori.add(c);
				c.cittaVicinaConEmporio(giocatore, cittaVicineConEmpori);
			}
		}
		return cittaVicineConEmpori;
	}

	public boolean isUguale(Citta cittaDaControllare) {
		return cittaDaControllare.getNome().equals(nome);
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
	public Set<Citta> getCittaVicina() {
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

	/**
	 * @return the numGettone
	 */
	public int getNumGettone() {
		return numGettone;
	}

	/**
	 * @param numGettone the numGettone to set
	 */
	public void setNumGettone(int numGettone) {
		this.numGettone = numGettone;
	}
}

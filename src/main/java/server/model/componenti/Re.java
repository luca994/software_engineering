package server.model.componenti;

import java.io.Serializable;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import server.model.Tabellone;

/**
 * 
 * @author Riccardo
 *
 */
public class Re implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6854976496303892844L;
	
	private Consiglio consiglio;
	private Citta citta;
	private Tabellone tabellone;

	/**
	 * build the king
	 * 
	 * @param colore
	 *            the color of the king
	 */
	public Re(Citta citta, Consiglio consiglio,Tabellone tabellone) {
		this.citta = citta;
		this.consiglio = consiglio;
		this.tabellone=tabellone;
	}

	/**
	 * counts the minimum number of steps that the king must do to reach the destination
	 * city
	 * 
	 * @param destinazione
	 *            is the end vertex
	 * @return number of steps that the king must do
	 */
	public int contaPassi(Citta destinazione) {
		if (destinazione == null)
			throw new NullPointerException("La città di destinazione non può essere nulla");
		DijkstraShortestPath<Citta, DefaultEdge> percorso = new DijkstraShortestPath<>(tabellone.generaGrafo(), citta,
				destinazione);
		return (int) percorso.getPathLength();
	}

	/**
	 * moves the king in the destination city
	 * 
	 * @param destinazione
	 *            is
	 * @return number of steps that the king must do
	 */
	public int muoviRe(Citta destinazione) {
		if (destinazione == null)
			throw new NullPointerException("La città di destinazione non può essere nulla");
		this.citta.setRe(null);
		this.citta = destinazione;
		this.citta.setRe(this);
		return contaPassi(destinazione);
	}

	/**
	 * @return the consiglio
	 */
	public Consiglio getConsiglio() {
		return consiglio;
	}

	/**
	 * @return the città
	 */
	public Citta getCitta() {
		return citta;
	}

	/**
	 * @param citta the citta to set
	 */
	public void setCitta(Citta citta) {
		this.citta = citta;
	}
	
}

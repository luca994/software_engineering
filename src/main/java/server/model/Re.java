package server.model;


import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Riccardo
 *
 */
public class Re {

	private Consiglio consiglio;
	private Citta citta;
	private Tabellone tabellone;
	
	/**
	 * build the king
	 * @param colore the color of the king
	 */
	public Re(Citta citta, Consiglio consiglio){
		this.citta=citta;
		this.consiglio=consiglio;
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
	public Citta getCittà() {
		return citta;
	}
	
	

	/**
	 * counts the number of steps that the king must do to reach the destination city
	 * @param destinazione is the end vertex
	 * @return number of steps that the king must do
	 */
	public int contaPassi(Citta destinazione){
		if(destinazione == null)
	    	throw new NullPointerException("La città di destinazione non può essere nulla");
		DijkstraShortestPath<Citta, DefaultEdge> percorso = new DijkstraShortestPath<>(tabellone.generaGrafo(), citta, destinazione);
		return (int) percorso.getPathLength(); 
	}
	
	/**
	 * moves the king in the destination city
	 * @param destinazione is 
	 * @return number of steps that the king must do
	 */
	public int muoviRe(Citta destinazione){
	    if(destinazione == null)
	    	throw new NullPointerException("La città di destinazione non può essere nulla");
		this.citta.setRe(null);
		this.citta=destinazione;
		this.citta.setRe(this);
		return contaPassi(destinazione);
	}
}

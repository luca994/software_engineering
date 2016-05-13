package model;


import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Riccardo
 *
 */
public class Re {

	private static final Logger log= Logger.getLogger( Re.class.getName() );
	private final String colore;
	private Consiglio consiglio;
	private Città città;
	private Tabellone tabellone;
	
	/**
	 * build the king
	 * @param colore the color of the king
	 */
	public Re(String colore, Città città, Consiglio consiglio){
		this.colore=colore;
		this.città=città;
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
	public Città getCittà() {
		return città;
	}
	
	

	/**
	 * counts the number of steps that the king must do to reach the destination city
	 * @param destinazione is the end vertex
	 * @return number of steps that the king must do
	 */
	public int contaPassi(Città destinazione){
		if(destinazione == null)
	    	throw new NullPointerException("La città di destinazione non può essere nulla");
		DijkstraShortestPath<Città, DefaultEdge> percorso = new DijkstraShortestPath<>(tabellone.generaGrafo(), città, destinazione);
		return (int) percorso.getPathLength(); 
	}
	
	/**
	 * moves the king in the destination city
	 * @param destinazione is 
	 * @return number of steps that the king must do
	 */
	public int muoviRe(Città destinazione){
	    if(destinazione == null)
	    	throw new NullPointerException("La città di destinazione non può essere nulla");
		this.città.setRe(null);
		this.città=destinazione;
		this.città.setRe(this);
		return contaPassi(destinazione);
	}
}

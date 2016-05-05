package model;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

import org.jdom2.JDOMException;

/**
 * @author Luca
 *
 */
public class Gioco {

	private SortedSet<Giocatore> giocatori;
	private Tabellone tabellone;
	private boolean vittoria;
	private int id;
	private Mercato mercato;
	
	
	/**
	 * @return the tabellone
	 */
	public Tabellone getTabellone() {
		return tabellone;
	}
	public Gioco (List<Giocatore> giocatori) throws JDOMException, IOException//Verrà una roba mooolto lunga
	{
		//Inizializzazione Ambiente di gioco
		String nomeMappaScelta="mappacollegamenti0.xml";// Ottenuta dal controller
		this.tabellone=new Tabellone(nomeMappaScelta);
		
	}//mettere i catch delle eccezioni della lettura xml

	public void gioco(){}
}

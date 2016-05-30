package server.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Riccardo
 *
 */
public class Consiglio {

	private static final int MIN_NUMERO_CONSIGLIERI_DA_SODDISFARE = 1;
	private Tabellone tabellone;
	private Queue<Consigliere> consiglieri;
	private Regione regione;

	/**
	 * constructor for Consiglio, it creates a new consiglio with 4 consiglieri taken from those available
	 * @param tabellone the tabellone from which the counciliers must be taken
	 * @throws NullPointerException if the tabellone in input is null
	 */
	public Consiglio(Tabellone tabellone) {
		if (tabellone==null)
			throw new NullPointerException();
		this.tabellone=tabellone;
		this.consiglieri = new LinkedList<>();
		for (int i = 0; i < 4; i++)// Prendo quattro consiglieri da quelli
									// disponibili e li metto nel consiglio
		{
			consiglieri.add(tabellone.getConsiglieriDisponibili().get(0));
			tabellone.getConsiglieriDisponibili().remove(0);
		}
	}

	/**
	 * creates a list of color that contain the colors of the
	 * councillors
	 * 
	 * @return a list with the color of the councillors
	 */
	public List<Color> acquisisciColoriConsiglio() {
		List<Color> colori = new ArrayList<>();
		for (Consigliere c : consiglieri)
			colori.add(c.getColore());
		return colori;
	}

	/**
	 * Retrieves and remove the head of this queue.
	 * @throws NoSuchElementException if this queue is empty.
	 */

	public void removeConsigliere() {
		tabellone.getConsiglieriDisponibili().add(consiglieri.element());
		consiglieri.remove();
	}

	/**
	 * Inserts the specified element into this queue if it is
	 * possible to do so immediately without violating capacity restrictions,
	 * returning true upon success and throwing an IllegalStateException if no
	 * space is currently available.
	 */
	public boolean addConsigliere(Consigliere consigliereDaAggiungere) {
		if (consigliereDaAggiungere == null)
			throw new NullPointerException("Il consigliere non può essere nullo");
		if (tabellone.getConsiglieriDisponibili().contains(consigliereDaAggiungere)) {
			tabellone.getConsiglieriDisponibili().remove(consigliereDaAggiungere);
			consiglieri.add(consigliereDaAggiungere);
		} else {
			return false;
		}
		return true;
	}
	
	public List<CartaColorata> soddisfaConsiglio(List<CartaPolitica> carte){
		if(carte==null)
			throw new NullPointerException("In input è stato passato un giocatore null");
		if(carte.size()<MIN_NUMERO_CONSIGLIERI_DA_SODDISFARE || carte.size()>consiglieri.size())
			throw new IllegalArgumentException("Il numero di carte selezionato non è appropriato");
		List<CartaColorata> carteUtilizzate = new ArrayList<>();
		List<Color> coloriConsiglio = acquisisciColoriConsiglio();
		for(CartaPolitica cp : carte)
			for(Color col: coloriConsiglio)
				if(((CartaColorata)cp).getColore().equals(col)){
					coloriConsiglio.remove(col);
					carteUtilizzate.add((CartaColorata)cp);
					break;
					}
		return carteUtilizzate;
	}
	
	
	

	/**
	 * @return the tabellone
	 */
	public Tabellone getTabellone() {
		return tabellone;
	}

	/**
	 * @param tabellone
	 *            the tabellone to set
	 */
	public void setTabellone(Tabellone tabellone) {
		this.tabellone = tabellone;
	}

	/**
	 * @return the consiglieri
	 */
	public Queue<Consigliere> getConsiglieri() {
		return consiglieri;
	}

	/**
	 * @param consiglieri
	 *            the consiglieri to set
	 */
	public void setConsiglieri(Queue<Consigliere> consiglieri) {
		this.consiglieri = consiglieri;
	}

	/**
	 * @return the regione
	 */
	public Regione getRegione() {
		return regione;
	}

	/**
	 * @param regione
	 *            the regione to set
	 */
	public void setRegione(Regione regione) {
		this.regione = regione;
	}

}

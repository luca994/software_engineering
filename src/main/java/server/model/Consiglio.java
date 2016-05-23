package server.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Riccardo
 *
 */
public class Consiglio {
	

	private static final Logger log= Logger.getLogger( Consiglio.class.getName() );
	private Tabellone tabellone;
	private Queue<Consigliere> consiglieri;
	private Regione regione;
	
	/**
	 * @param tabellone
	 */
	public Consiglio(Tabellone tabellone) {
	
		this.consiglieri=new LinkedList<>();	
		for(int i=0;i<4;i++)//Prendo quattro consiglieri da quelli disponibili e li metto nel consiglio
		{
			consiglieri.add(tabellone.getConsiglieriDisponibili().get(0));
			tabellone.getConsiglieriDisponibili().remove(0);			
		}
	}
	
/**
 * this method create a list of <String>colori that contain the color of the councillors
 * @return a list with the color of the councillors
 */
	public List<Color> acquisisciColoriConsiglio(){
		List<Color> colori= new ArrayList<>(); 
		for (Consigliere c : consiglieri)
			colori.add(c.getColore());
		return colori;
	}
	
	/**
	 * this metod Retrieves and remove the head of this queue,
	 * Throws NoSuchElementException if this queue is empty.
	 */
	
	public void removeConsigliere(){
		try{
		tabellone.addConsigliereDisponibile(consiglieri.element());
		consiglieri.remove();
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}
	
	/**
	 * this method Inserts the specified element into this queue if it is possible
	 * to do so immediately without violating capacity restrictions, returning true upon 
	 * success and throwing an IllegalStateException if no space is currently available.
	 */
	public boolean addConsigliere(Consigliere consigliereDaAggiungere){
		try
		{
			if(consigliereDaAggiungere==null)
				throw new NullPointerException("Il consigliere non può essere nullo");
			if(tabellone.ifConsigliereDisponibile(consigliereDaAggiungere))
			{
				tabellone.removeConsigliereDisponibile(consigliereDaAggiungere);
				consiglieri.add(consigliereDaAggiungere);
			}
			else
			{
				return false;
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
		return true;
	}

	/**
	 * @return the tabellone
	 */
	public Tabellone getTabellone() {
		return tabellone;
	}

	/**
	 * @param tabellone the tabellone to set
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
	 * @param consiglieri the consiglieri to set
	 */
	public void setConsiglieri(Queue<Consigliere> consiglieri) {
		this.consiglieri = consiglieri;
	}

	/**
	 * @return the log
	 */	
	public static Logger getLog() {
		return log;
	}

	/**
	 * @return the regione
	 */
	public Regione getRegione() {
		return regione;
	}

	/**
	 * @param regione the regione to set
	 */
	public void setRegione(Regione regione) {
		this.regione = regione;
	}

}

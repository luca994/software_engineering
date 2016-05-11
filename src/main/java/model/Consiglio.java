package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
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
	private Queue<Consigliere> consiglio;
	private int id;
	private Regione regione;
	
	public Consiglio(Tabellone tabellone)
	{
		this.consiglio=new LinkedList<>();
		for(int i=0;i<4;i++)//Prendo quattro consiglieri da quelli disponibili e li metto nel consiglio
		{
			consiglio.add(tabellone.getConsiglieriDisponibili().get(0));
			tabellone.getConsiglieriDisponibili().remove(0);
		}
	}
	
	
	public List<String> acquisisciColoriConsiglio(){
		List<String> colori= new ArrayList<>(); 
		for(Consigliere c : consiglio)
			colori.add(c.getColore());
		return colori;
	}
	
	/**
	 * Retrieves and remove the head of this queue,
	 * Throws NoSuchElementException if this queue is empty.
	 * @author Luca
	 */
	//Questo metodo devo chiamarlo dall'azione eleggiConsigliere ed eleggiConsigliereRapido 
	public void removeConsigliere(){
		try{
		tabellone.addConsigliereDisponibile(consiglio.element());
		consiglio.remove();
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}
	
	/**
	 * this method Inserts the specified element into this queue if it is possible
	 * to do so immediately without violating capacity restrictions, returning true upon 
	 * success and throwing an IllegalStateException if no space is currently available.
	 * @author Luca
	 */
	public boolean addConsigliere(Consigliere consigliereDaAggiungere){
		try
		{
			if(consigliereDaAggiungere==null)
				throw new NullPointerException("Il consigliere non può essere nullo");
			if(tabellone.ifConsigliereDisponibile(consigliereDaAggiungere))
			{
				tabellone.removeConsigliereDisponibile(consigliereDaAggiungere);
				consiglio.add(consigliereDaAggiungere);
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
	 * @param regione the regione to set
	 */
	public void setRegione(Regione regione) {
		this.regione = regione;
	}


	/**
	 * @return the regione
	 */
	public Regione getRegione() {
		return regione;
	}
}

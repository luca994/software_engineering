package model;

import java.util.Queue;

/**
 * @author Riccardo
 *
 */
public class Consiglio {
	
	private Tabellone tabellone;
	private Queue<Consigliere> consiglio;
	private int id;
	
	/**
	 * this method retrieves and remove the head of this queue,
	 * it throws an exception if this queue is empty.
	 * @author Luca
	 * Questo metodo devo chiamarlo dall'azione eleggiConsigliere ed eleggiConsigliereRapido
	 */
	public void removeConsigliere(){
		try{
		tabellone.addConsigliereDisponibile(consiglio.element());
		consiglio.remove();
		}
		catch (Exception NoSuchElementException){
			System.out.println("Errore:il consiglio"+ id +" è vuoto!");
		}
	}
	
	/**
	 * this method Inserts the specified element into this queue if it is possible
	 * to do so immediately without violating capacity restrictions, returning true upon 
	 * success and throwing an IllegalStateException if no space is currently available.
	 * 
	 * @author Luca
	 * 
	 * Questo metodo devo chiamarlo dall'azione eleggiConsigliere ed eleggiConsigliereRapido 
	 * 
	 */
	public boolean addConsigliere(Consigliere consigliereDaAggiungere){
		try{
			if(tabellone.ifConsigliereDisponibile(consigliereDaAggiungere)){
			tabellone.removeConsigliereDisponibile(consigliereDaAggiungere);
			consiglio.add(consigliereDaAggiungere);}
			else{
				System.out.println("Il consigliere selezionato non è disponibile"
						+ ", sceglierne un altro.");
				return false;
			}
			}
			catch (IllegalStateException e){
				System.out.println("Errore:il consiglio"+ id +" è pieno!/nConsigliere non eletto!");
			}
		return true;
	}
}

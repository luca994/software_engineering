package model;

import java.util.List;
import java.util.Set;

/**
 * @author Riccardo
 *
 */
public class Tabellone {

	private Set<Regione> regioni;
	private Set<OggettoConBonus> tessereBonus;
	private List<Consigliere> consiglieriDisponibili;
	private Set<Consiglio> consigli;
	private Percorso percorsoNobiltà;
	private Percorso percorsoRicchezza;
	private Percorso percorsoVittoria;
	
	/**
	 * 
	 * @param consigliereInTab
	 * @return Returns true if this list contains the specified element
	 */
	public boolean ifConsigliereDisponibile(Consigliere consigliereInTab){
			return consiglieriDisponibili.contains(consigliereInTab);
	}
	
	
	/**
	 * @author Luca
	 * This method appends the specified element to the end of this list
	 * @param consigliereDaAggiungere
	 * 
	 */
	public void addConsigliereDisponibile(Consigliere consigliereDaAggiungere){
		try{
			consiglieriDisponibili.add(consigliereDaAggiungere);
		}
		catch(UnsupportedOperationException e1){
			System.out.println("L'operazione aggiungi non è supportata dalla lista"
					+ "consiglieriDisponibili del Tabellone");
		}
		catch(ClassCastException e2){
			System.out.println("La classe del tipo specificato non è adatta al tipo della lista"
					+ "consiglieriDisponibili del Tabellone");
		}
		catch(NullPointerException e3){
			System.out.println("L'elemento specificato è null, non si può aggiungere alla"
					+ "lista consiglieriDisponibili del Tabellone" );
		}
		catch(IllegalArgumentException e4){
			System.out.println("Alcune proprietà dell'elemento impediscono di aggiungerlo"
					+ "correttamente alla lista consiglierDisponibili del Tabellone");
		}}
	
	/**
	 * This method removes the first occurrence of the specified 
	 * element from this list, if it is present
	 * @author Luca
	 * @param consigliereDaRimuovere
	 */
	public void removeConsigliereDisponibile(Consigliere consigliereDaRimuovere){
		try{
			consiglieriDisponibili.remove(consigliereDaRimuovere);
		}
		catch(ClassCastException e1){
			System.out.println("Il tipo specificato non è compatibile con la lista");
		}
		catch(NullPointerException e2){
			System.out.println("Il tipo specificato è Null ");
		}
		catch(UnsupportedOperationException e3){
			System.out.println("l'operazione di rimozione non è supportata dalla lista");
		}
	}


	/**
	 * @return the regioni
	 */
	public Set<Regione> getRegioni() {
		return regioni;
	}


	/**
	 * @return the tessereBonus
	 */
	public Set<OggettoConBonus> getTessereBonus() {
		return tessereBonus;
	}


	/**
	 * @return the consiglieriDisponibili
	 */
	public List<Consigliere> getConsiglieriDisponibili() {
		return consiglieriDisponibili;
	}


	/**
	 * @return the consigli
	 */
	public Set<Consiglio> getConsigli() {
		return consigli;
	}


	/**
	 * @return the percorsoNobiltà
	 */
	public Percorso getPercorsoNobiltà() {
		return percorsoNobiltà;
	}


	/**
	 * @return the percorsoRicchezza
	 */
	public Percorso getPercorsoRicchezza() {
		return percorsoRicchezza;
	}


	/**
	 * @return the percorsoVittoria
	 */
	public Percorso getPercorsoVittoria() {
		return percorsoVittoria;
	}
	
	
	
}

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
	
	/**
	 * @author Luca
	 * @param consigliereDaAggiungere
	 * Appends the specified element to the end of this list
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
		}
		}
	
}

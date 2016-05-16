/**
 * 
 */
package view;

import java.util.Scanner;

import model.Città;
import model.Giocatore;

/**
 * @author Massimiliano Ventura
 *
 */
public class View {
	public String acquisisciCittàBonus(){
		return ottieniStringa("Inserisci il nome della città in cui hai l'emporio di cui vuoi ottenere il bonus");		
	}
	/**
	 * @author Massimiliano Ventura
	 *
	 */
	public void messaggioErrore(String errore){
		System.out.println(errore);
	}
	public String ottieniStringa(String messaggioInformativo){
		System.out.println(messaggioInformativo);
		Scanner input = new Scanner(System.in); 
		return input.nextLine();

		
	}
	public String acquisisciNomeMappa (){
		String numero=ottieniStringa("Inserisci un numero da 0 a 7 per caricare la corrispettiva mappa e premi Enter");
		return new String("mappacollegamenti"+numero+".xml");
	}
	public void update(){
		
	}
}

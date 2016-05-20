/**
 * 
 */
package view;

import java.util.Scanner;

import client.Observable;
import client.Observer;
import model.Gioco;
import model.bonus.BonusGettoneCittà;

/**
 * @author Massimiliano Ventura
 *
 */
public class View extends Observable implements Observer {
	
	public View(Gioco gioco){
		gioco.registerObserver(this);
	}
	
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
		String messaggioInput=input.nextLine();
		return messaggioInput;

		
	}
	public String acquisisciNomeMappa (){
		String numero=ottieniStringa("Inserisci un numero da 0 a 7 per caricare la corrispettiva mappa e premi Enter");
		return new String("mappacollegamenti"+numero+".xml");
	}
	
	/*public void update(BonusGettoneCittà cambiamento){
		String nomeCittà = ottieniStringa("Inserisci il nome di una città dove hai un emporio"
				+ "e di cui vuoi ottenere il bonus");
		this.notificaObservers(cambiamento, nomeCittà);
	}*/

	@Override
	public void update(Object cambiamento) {
		if(cambiamento instanceof BonusGettoneCittà){
			String nomeCittà = ottieniStringa("Inserisci il nome di una città dove hai un emporio"
					+ " e di cui vuoi ottenere il bonus");
			this.notificaObservers(cambiamento, nomeCittà);
		}
		if(cambiamento instanceof String){
			System.out.println(cambiamento);
		}
	}

	@Override
	public void update(Object cambiamento, String input) {
		// TODO Auto-generated method stub
		
	}
}

/**
 * 
 */
package view;

import java.util.Scanner;

import client.Observable;
import client.Observer;
import model.Giocatore;
import model.Gioco;
import model.TesseraCostruzione;
import model.bonus.BonusGettoneCittà;
import model.bonus.BonusRiutilizzoCostruzione;
import model.bonus.BonusTesseraPermesso;

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
	public <Bonus> void update(Bonus cambiamento) {
		if(cambiamento instanceof BonusGettoneCittà){
			String[] nomeCittà = {ottieniStringa("Inserisci il nome di una città dove hai un emporio"
					+ " e di cui vuoi ottenere il bonus, se non hai un'emporio scrivi 'passa'")};
			this.notificaObservers(cambiamento, nomeCittà);
		}
		if(cambiamento instanceof String){
			System.out.println(cambiamento);
		}
		if(cambiamento instanceof BonusTesseraPermesso){
			String[] tessera = {ottieniStringa("Inserisci il numero della tessera permesso che vuoi ottenere")};
			try{
				this.notificaObservers(cambiamento, tessera);
			}
			catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
				update(cambiamento);
			}
		}
		if(cambiamento instanceof BonusRiutilizzoCostruzione){
			try{
				Giocatore gio=((BonusRiutilizzoCostruzione) cambiamento).getGiocatore();
				for(TesseraCostruzione t:gio.getTessereUsate()){
					System.out.println(t);
				}
				String numLista= ottieniStringa("inserisci 0 se la tessera è nella lista delle tessere valide, altrimenti 1. Scrivi 'passa' se non hai tessere");
				String numTessera = ottieniStringa("inserisci il numero della tessera da riciclare");
				String[] array = {numLista,numTessera};
				this.notificaObservers(cambiamento,array);
			}
			catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
				update(cambiamento);
			}
		}
	}

	@Override
	public void update(Object cambiamento, String[] input) {
		// TODO Auto-generated method stub
		
	}
}

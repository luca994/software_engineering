package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca
 *
 */
public class AcquistaPermesso implements Azione {

	private TesseraCostruzione tessera;
	private List<CartaPolitica> cartePolitica;
	private Consiglio consiglioDaSoddisfare;
	private Percorso percorsoRicchezza;
	private int counter=0;
	
	/**
	 * @param tessera the tessera to set
	 */
	public void setTessera(TesseraCostruzione tessera) {
		this.tessera = tessera;
	}

	/**
	 * @param consiglioDaSoddisfare the consiglioDaSoddisfare to set
	 */
	public void setConsiglioDaSoddisfare(Consiglio consiglioDaSoddisfare) {
		this.consiglioDaSoddisfare = consiglioDaSoddisfare;
	}

	/**
	 * @param cartePolitica the cartePolitica to set
	 */
	public void setCartePolitica(List<CartaPolitica> cartePolitica) {
		this.cartePolitica = cartePolitica;
	}

	
	/**
	 * 
	 */
	public void eseguiAzione (Giocatore giocatore){
		if(giocatore.containsAllCarte(cartePolitica)){
			boolean elementCounted = false;
			ArrayList<String> colori = consiglioDaSoddisfare.acquisisciColoriConsiglio();
			for(CartaPolitica car : cartePolitica){
				elementCounted = false;
				for(String col : colori){
					if(car.getColore().equals(col)==true && elementCounted==false)
						counter++;
						elementCounted=true;
				}			
			}
			for(CartaPolitica car : cartePolitica){
				if(car.getColore()=="jolly")
					counter++;}
		switch(counter){
			case 1: percorsoRicchezza.muoviGiocatore(giocatore, -10);
					break;
			case 2: percorsoRicchezza.muoviGiocatore(giocatore, -7);
					break;
			case 3: percorsoRicchezza.muoviGiocatore(giocatore, -4);
					break;
			case 4: 
					break;
			default: System.out.println("Errore nel conteggio dei consiglieri"
					+ "soddisfatti");
					break;}
			giocatore.setAzionePrincipale(true);
		}
		List<OggettoConBonus> tessereDaScegliere=consiglioDaSoddisfare.getRegione().getTessereCostruzione();
		if(tessereDaScegliere.contains(tessera)){
			giocatore.addTessereValide(tessera);
			tessera.eseguiBonus(giocatore);
			//Scopri dal mazzetto una nuova tessera costruzione
			giocatore.setAzionePrincipale(true);
		}
	}
}

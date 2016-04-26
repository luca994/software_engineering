package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca
 *
 */
public class AcquistaPermesso implements Azione {

	private OggettoConBonus tessera;
	private List<CartaPolitica> cartePolitica;
	private Consiglio consiglioDaSoddisfare;
	private Percorso percorsoRicchezza;
	private int counter=0;
	
	/**
	 * @param tessera the tessera to set
	 */
	public void setTessera(OggettoConBonus tessera) {
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
	 * this method verifies that the c
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
			case 1: percorsoRicchezza.muoviGiocatoreIndietro(giocatore, 10);
					break;
			case 2: percorsoRicchezza.muoviGiocatoreIndietro(giocatore, 7);
					break;
			case 3: percorsoRicchezza.muoviGiocatoreIndietro(giocatore, 4);
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
			//INCOMPLETO!!Manca da Applicare i bonus della tessera.
			//Perchè TesseraCostruzione ha un set di bonus? non dovrebbe essere list???
		}
	}
}

package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo
 *
 */
public class Mercato {

	private List<OggettoVendibile> oggettiInVendita;
	private Percorso percorsoRicchezza;
	/**
	 * build the object
	 */
	public Mercato(){
		oggettiInVendita=new ArrayList();
	}
	
	/**
	 * add an object to the list oggettiInVendita
	 * @param oggetto the object you want to sell
	 * @param prezzo the price of the object
	 * @param giocatore the player who sells the object
	 */
	public <T> void aggiungiOggetto(T oggetto,int prezzo,Giocatore giocatore){
		oggettiInVendita.add(new OggettoVendibile(oggetto,prezzo,giocatore));
	}
	
	/**
	 * it does the transaction between two player
	 * @param oggetto the object you want to buy
	 * @param giocatore the player who wants to buy the object
	 */
	public void transazione(OggettoVendibile oggetto,Giocatore giocatore){
		try{
			percorsoRicchezza.muoviGiocatore(giocatore, -oggetto.getPrezzo());
			if(oggetto.getOggetto() instanceof Assistente){
				giocatore.getAssistenti().add((Assistente) oggetto.getOggetto());
				oggetto.getGiocatore().getAssistenti().remove(oggetto);
			}
			if(oggetto.getOggetto() instanceof TesseraCostruzione){
				giocatore.getTessereValide().add((OggettoConBonus) oggetto.getOggetto());
				oggetto.getGiocatore().getTessereValide().remove(oggetto);
			}
			if(oggetto.getOggetto() instanceof CartaPolitica){
				giocatore.getCartePolitica().add((CartaPolitica) oggetto.getOggetto());
				oggetto.getGiocatore().getCartePolitica().remove(oggetto);
			}
			percorsoRicchezza.muoviGiocatore(oggetto.getGiocatore(), oggetto.getPrezzo());
			oggettiInVendita.remove(oggetto.getOggetto());
		}
		catch(IndexOutOfBoundsException e){
			System.err.println(e.getMessage());
		}
	}
}

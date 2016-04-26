package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Riccardo
 *
 */
public class Mercato {

	List<OggettoVendibile> oggettiInVendita;
	Gioco gioco;
	
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
		//controlla di che tipo è l' oggetto all' interno dell' oggetto vendibile e in base a questo
		//rimuove l' oggetto nella lista "giusta" del giocatore.
		if(oggetto.getOggetto() instanceof TesseraCostruzione){
			//rimuovo l'oggetto dalla lista del giocatore che lo ha venduto
			oggetto.getGiocatore().getTessereValide().remove(oggetto.getOggetto());
			//aggiungo l' oggetto al giocatore che lo compra
			giocatore.getTessereValide().add((OggettoConBonus) oggetto.getOggetto());
		}
		//uguali all' if sopra
		if(oggetto.getOggetto() instanceof CartaPolitica){
			oggetto.getGiocatore().getCartePolitica().remove(oggetto.getOggetto());
			giocatore.getCartePolitica().add((CartaPolitica) oggetto.getOggetto());
		}
		if(oggetto.getOggetto() instanceof TesseraCostruzione){
			oggetto.getGiocatore().getAssistenti().remove(oggetto.getOggetto());
			giocatore.getAssistenti().add((Assistente) oggetto.getOggetto());
		}
		
		gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, -oggetto.getPrezzo());
		gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(oggetto.getGiocatore(), oggetto.getPrezzo());
		
	}
}

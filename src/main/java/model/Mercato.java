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
			Iterator<OggettoConBonus> itrTessere = oggetto.getGiocatore().getTessereValide().iterator();
			//scorro la lista finchè non trovo l' oggetto e lo rimuovo
			while(itrTessere.hasNext()){
				if(itrTessere==oggetto.getOggetto()){
					itrTessere.remove();
					break;
				}
				itrTessere.next();
			}
			//aggiungo l' oggetto al giocatore che lo compra
			giocatore.getTessereValide().add((OggettoConBonus) oggetto.getOggetto());
		}
		//uguali al metodo sopra
		if(oggetto.getOggetto() instanceof CartaPolitica){
			Iterator<CartaPolitica> itrCarte = oggetto.getGiocatore().getCartePolitica().iterator();
			while(itrCarte.hasNext()){
				if(itrCarte==oggetto.getOggetto()){
					itrCarte.remove();
					break;
				}
				itrCarte.next();
			}
			giocatore.getCartePolitica().add((CartaPolitica) oggetto.getOggetto());
		}
		if(oggetto.getOggetto() instanceof TesseraCostruzione){
			Iterator<Assistente> itrAssistenti = oggetto.getGiocatore().getAssistenti().iterator();
			while(itrAssistenti.hasNext()){
				if(itrAssistenti==oggetto.getOggetto()){
					itrAssistenti.remove();
					break;
				}
				itrAssistenti.next();
			}
			giocatore.getAssistenti().add((Assistente) oggetto.getOggetto());
		}
		
		//manca la parte che scala i soldi
		
	}
}

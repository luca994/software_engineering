package server.model;

import java.io.IOException;

import server.model.percorso.Percorso;

/**
 * @author Riccardo
 *
 */
public abstract class OggettoVendibile {

	private int prezzo;
	private Giocatore giocatore;
	private Mercato mercato;

	/**
	 * add an object to the list of oggettiInVendita of the market
	 * @param mercato the market in which there is the list
	 */
	public void aggiungiOggetto(Mercato mercato){
		mercato.getOggettiInVendita().add(this);
	}

	/**
	 * does the transaction between two players. It will be implemented
	 * in the subclasses
	 * @param giocatore the player who wants to buy the object
	 * @throws IOException 
	 */
	public abstract void transazione(Giocatore giocatore) throws IOException;
	
	/**
	 * return the price
	 * @return 
	 */
	public int getPrezzo() {
		return prezzo;
	}
	/**
	 * return the owner of the object
	 * @return
	 */
	public Giocatore getGiocatore() {
		return giocatore;
	}

	/**
	 * 
	 * @return the money route of the game
	 */
	public Percorso getPercorsoRicchezza() {
		return getMercato().getPercorsoRicchezza();
	}

	/**
	 * 
	 * @return the market of the game
	 */
	public Mercato getMercato() {
		return mercato;
	}

	/**
	 * 
	 * @param prezzo the price of the object you want to sell to set
	 */
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	/**
	 * 
	 * @param giocatore the player who wants to sell the object
	 */
	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}

	/**
	 * 
	 * @param mercato the market of the game
	 */
	public void setMercato(Mercato mercato) {
		this.mercato = mercato;
	}
}

package model;

/**
 * @author Riccardo
 *
 * @param <T> the type of the object you want to sell
 */
public class OggettoVendibile <T> {

	private T oggetto;
	private int prezzo;
	private Giocatore giocatore;
	
	/**
	 * @param oggetto the object you want to sell
	 * @param prezzo the price of the object
	 * @param giocatore the player who sells
	 * 
	 * build the object
	 */
	public <T> OggettoVendibile(T oggetto, int prezzo, Giocatore giocatore){
		this.oggetto=oggetto;
		this.prezzo=prezzo;
		this.giocatore=giocatore;
	}
}

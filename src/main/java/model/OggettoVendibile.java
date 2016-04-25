package model;

/**
 * @author Riccardo
 *
 * @param <T> the type of the object you want to sell
 */
public class OggettoVendibile <T> {

	private final T oggetto;
	private final int prezzo;
	private final Giocatore giocatore;
	
	/**
	 * @param oggetto the object you want to sell
	 * @param prezzo the price of the object
	 * @param giocatore the player who sells
	 * 
	 * build the object
	 */
	public OggettoVendibile(T oggetto, int prezzo, Giocatore giocatore){
		this.oggetto=oggetto;
		this.prezzo=prezzo;
		this.giocatore=giocatore;
	}
	/**
	 * return the object
	 * @return
	 */
	public T getOggetto() {
		return oggetto;
	}
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
	
	
}

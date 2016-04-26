package model;

import java.util.List;

/**
 * @author Luca
 *
 */
public class AcquistaPermesso implements Azione {

	private OggettoConBonus tessera;
	private List<CartaPolitica> cartePolitica;
	private Consiglio consiglioDaSoddisfare;
	
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

	
	public void eseguiAzione (Giocatore giocatore){
		
	};
}

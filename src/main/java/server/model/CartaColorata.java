/**
 * 
 */
package server.model;

import java.awt.Color;

/**
 * @author Luca
 *
 */
public class CartaColorata extends CartaPolitica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7490822094047460290L;
	
	private final Color colore;

	/**
	 * 
	 */
	public CartaColorata(Color colore) {
		this.colore = colore;
	}

	/**
	 * @return the colore
	 */
	public Color getColore() {
		return colore;
	}

	@Override
	public boolean isUguale(CartaPolitica carta) {
		return carta instanceof CartaColorata && ((CartaColorata) carta).getColore().equals(colore);
		
	}

	@Override
	public String toString() {
		return "Carta Politica "+"colore: "+ParseColor.colorIntToString(colore.getRGB());
	}


	
}

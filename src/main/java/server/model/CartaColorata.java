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

}

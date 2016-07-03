/**
 * 
 */
package server.model.componenti;

import java.awt.Color;

import server.model.ParseColor;

/**
 * The class represents the colorful political cards
 */
public class CartaColorata extends CartaPolitica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7490822094047460290L;

	/** the color of the card */
	private final Color colore;

	/**
	 * Constructor for cartaColorata
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

	/**
	 * compares two cards and returns true if the cards have the same color
	 */
	@Override
	public boolean isUguale(CartaPolitica carta) {
		return carta instanceof CartaColorata && ((CartaColorata) carta).getColore().equals(colore);

	}

	@Override
	public String toString() {
		if (getPrezzo() == 0)
			return "Carta Politica " + "Colore: " + ParseColor.colorIntToString(colore.getRGB());
		else
			return "Carta Politica " + "colore: " + ParseColor.colorIntToString(colore.getRGB()) + " prezzo:"
					+ getPrezzo() + " proprietario: " + getGiocatore().getNome();
	}

}

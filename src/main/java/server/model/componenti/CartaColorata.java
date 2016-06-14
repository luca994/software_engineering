/**
 * 
 */
package server.model.componenti;

import java.awt.Color;

import server.model.ParseColor;

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
		if(getPrezzo()==0)
			return "Carta Politica "+"Colore: "+ParseColor.colorIntToString(colore.getRGB());
		else
			return "Carta Politica "+"colore: "+ParseColor.colorIntToString(colore.getRGB())+" prezzo:"+getPrezzo()+" proprietario: "+getGiocatore().getNome();
	}


	
}

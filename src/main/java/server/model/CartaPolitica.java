package server.model;

import java.awt.Color;

/**
 * @author Luca
 *
 */
public class CartaPolitica extends OggettoVendibile {

	private final Color colore;

	/**
	 * Constructor for cartaPolitica, that generate cartaPolitica of a random
	 * color
	 */
	public CartaPolitica() {

		int numero = (int) (Math.random() * 89);

		if (numero >= 0 && numero <= 12)
			this.colore = Color.BLACK;
		else if (numero >= 13 && numero <= 25)
			this.colore = Color.WHITE;
		else if (numero >= 26 && numero <= 38)
			this.colore = Color.ORANGE;
		else if (numero >= 39 && numero <= 51)
			this.colore = Color.PINK;
		else if (numero >= 52 && numero <= 64)
			this.colore = Color.CYAN; // sarebbe blu
		else if (numero >= 65 && numero <= 77)
			this.colore = Color.MAGENTA; // sarebbe viola
		else
			this.colore = Color.RED; // colore del JOLLY
	}

	@Override
	public void compra(Giocatore nuovoProprietario) {
		if (nuovoProprietario == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		transazioneDenaro(nuovoProprietario);
		nuovoProprietario.getCartePolitica().add(this);
		getGiocatore().getCartePolitica().remove(this);
		resettaAttributiOggettoVendibile(nuovoProprietario);
		getMercato().getOggettiInVendita().remove(this);
	}

	/**
	 * @return the colore
	 */
	public Color getColore() {
		return colore;
	}

}

package server.model.componenti;

import java.awt.Color;

/**
 * The factory for cartaPolitica
 */
public class CartaPoliticaFactory {

	/**
	 * creates a politic card randomly, can creates a jolly or a colored paper
	 * of a random color
	 * 
	 * @return a politic card
	 */
	public CartaPolitica creaCartaPolitica() {

		int numero = (int) (Math.random() * 89);

		if (numero >= 0 && numero <= 12)
			return new CartaColorata(Color.black);
		else if (numero >= 13 && numero <= 25)
			return new CartaColorata(Color.white);
		else if (numero >= 26 && numero <= 38)
			return new CartaColorata(Color.orange);
		else if (numero >= 39 && numero <= 51)
			return new CartaColorata(Color.pink);
		else if (numero >= 52 && numero <= 64)
			return new CartaColorata(Color.cyan);
		else if (numero >= 65 && numero <= 77)
			return new CartaColorata(Color.magenta);
		else
			return new Jolly();
	}

}

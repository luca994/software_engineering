/**
 * 
 */
package server.model;

/**
 * @author Luca
 *
 */
public class Jolly extends CartaPolitica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2655637161326198916L;

	@Override
	public boolean isUguale(CartaPolitica carta) {
		return carta instanceof Jolly;
	}

	@Override
	public String toString() {
		return "Carta Politica "+"Jolly";
	}
}

/**
 * 
 */
package server.model.componenti;

/**
 * the class that represents the jolly politic card
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
		if(getPrezzo()==0)
			return "Carta Politica "+"JOLLY";
		else
			return "Carta Politica "+"JOLLY"+" prezzo:"+getPrezzo()+" proprietario: "+getGiocatore().getNome();
	}
}

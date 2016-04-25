/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusCartaPolitica extends Bonus {
	/**
	 * This object is obtained via factory method, yet to be implemented;
	 */
	private CartaPolitica cartaPolitica=new CartaPolitica();
	
	@Override
	public void azioneBonus(Giocatore giocatore) 
	{
		giocatore.getCartePolitica().add(cartaPolitica);

	}

}

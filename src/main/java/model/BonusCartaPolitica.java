/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusCartaPolitica implements Bonus {
	
	private CartaPolitica cartaPolitica=new CartaPolitica();
	
	@Override
	public void azioneBonus(Giocatore giocatore) 
	{
		giocatore.getCartePolitica().add(cartaPolitica);

	}

}

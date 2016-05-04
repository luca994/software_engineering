/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusCartaPolitica implements Bonus {
	private int numeroCarte;
	public BonusCartaPolitica(int numeroCarte)
	{
		this.numeroCarte=numeroCarte;
	}
	
	@Override
	public void azioneBonus(Giocatore giocatore) 
	{
		
		for(int i=0;i<this.numeroCarte;i++)
		giocatore.getCartePolitica().add(new CartaPolitica());

	}

}

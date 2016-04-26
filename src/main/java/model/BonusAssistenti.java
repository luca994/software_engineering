/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusAssistenti extends Bonus {
	public BonusAssistenti(int numeroAssistenti){
		this.numeroAssistenti=numeroAssistenti;
	}
	private int numeroAssistenti;
	/* (non-Javadoc)
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	@Override
	public void azioneBonus(Giocatore giocatore) {
		giocatore.getAssistenti().add(new Assistente());
		
	}
	
}

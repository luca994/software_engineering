/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusAssistenti implements Bonus {
	public BonusAssistenti(int numeroAssistenti){
		this.numeroAssistenti=numeroAssistenti;
	}
	private int numeroAssistenti;
	/* (non-Javadoc)
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	@Override
	public void azioneBonus(Giocatore giocatore) {
		for(int i=0;i<numeroAssistenti;i++)
		giocatore.getAssistenti().add(new Assistente());
	}
	
}

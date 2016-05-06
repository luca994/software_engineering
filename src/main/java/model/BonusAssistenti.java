/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusAssistenti implements Bonus {

	private int numeroAssistenti;
	
	
	public BonusAssistenti(int numeroAssistenti){
		this.numeroAssistenti=numeroAssistenti;
	}
	
	/* (non-Javadoc)
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	@Override
	public void azioneBonus(Giocatore giocatore) {
		for(int i=0;i<numeroAssistenti;i++)
		giocatore.getAssistenti().add(new Assistente());
	}
	
}

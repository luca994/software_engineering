package model;

import java.util.Set;

/**
 * This class asks the player which city bonuses wants to obtain with this particular bonus.
 * @author Massimiliano Ventura
 *
 */
public class BonusGettoneCittà implements Bonus {

	/* (non-Javadoc)
	 * @see model.Bonus#azioneBonus(model.Giocatore)
	 */
	
	private int numeroCittà;
	private Set<Città> città;
	
	public BonusGettoneCittà(int numeroCittà)
	{
		this.numeroCittà=numeroCittà;
	}
	
	/**
	 * @return the numeroCittà
	 */
	public int getNumeroCittà() {
		return numeroCittà;
	}

	/**
	 * @return the città
	 */
	public Set<Città> getCittà() {
		return città;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {
		//this.città=bonusCittàDalGiocatore(giocatore, numeroCittà);
		//metodo del controller che prende i bonus della città dal giocatore 
		//le città devono essere diverse  
		/*Iterator<Città> itcittà=this.città.iterator();
		while(itcittà.hasNext())
			itcittà.next().eseguiAzioneBonus(giocatore);*/
		for(Città  cit:this.città)
		{
			if(cit.presenzaEmporio(giocatore))
				cit.eseguiBonus(giocatore);
		}
	}

}

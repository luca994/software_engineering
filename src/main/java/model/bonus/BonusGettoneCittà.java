package model.bonus;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.Controller;
//import model.Cambiamento;
import model.Città;
import model.Giocatore;
import model.Gioco;
import view.View;



/**
 * This class asks the player which city bonuses wants to obtain with this particular bonus.
 * @author Massimiliano Ventura
 *
 */
public class BonusGettoneCittà implements Bonus {
	private static final Logger log= Logger.getLogger( BonusGettoneCittà.class.getName() );
	
	//private Cambiamento cambiamento;
	private int numeroCittà;
	private Set<Città> città;
	private Gioco gioco;
	
	public BonusGettoneCittà(int numeroCittà, Gioco gioco)
	{
		//cambiamento = new Cambiamento();
		this.gioco=gioco;
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
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			Iterator<Città> itrcittà = città.iterator();
			Città temp = itrcittà.next(); 
			for(int i=0; i<numeroCittà;)
			{
				try{
					gioco.notificaObservers(this,null);
					if(temp.presenzaEmporio(giocatore)){
						temp=itrcittà.next();
						i++;
					}
					else{
						throw new IllegalStateException("Non hai empori in questa città");
					}
				}
				catch(IllegalStateException e){
				}
			}

			for(Città  cit:this.città){
				cit.eseguiBonus(giocatore);
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}
}

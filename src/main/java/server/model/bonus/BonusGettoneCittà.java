package server.model.bonus;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Città;
import server.model.Giocatore;
import server.model.Gioco;



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
	boolean cittàGiusta;
	
	public BonusGettoneCittà(int numeroCittà, Gioco gioco)
	{
		//cambiamento = new Cambiamento();
		this.gioco=gioco;
		this.numeroCittà=numeroCittà;
		this.città=new HashSet<Città>();
	}
	
	/**
	 * @return the numeroCittà
	 */
	public int getNumeroCittà() {
		return numeroCittà;
	}

	/**
	 * set the boolean
	 * @param cittàGiusta the parameter to set
	 */
	public void setCittàGiusta(boolean cittàGiusta) {
		this.cittàGiusta = cittàGiusta;
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
			for(int i=0; i<numeroCittà; i++)
			{
				do{
					cittàGiusta = true;
					gioco.notificaObservers(this);
					for(Città c:città){
						for(Bonus b:c.getBonus()){
							if(b instanceof BonusPercorsoNobiltà){
								cittàGiusta=false;
								città.remove(c);
								gioco.notificaObservers("C'è un bonus percorso nobiltà");
							}
						}
						if(!c.getEmpori().contains(giocatore)&&città.contains(c)){
							cittàGiusta=false;
							città.remove(c);
							gioco.notificaObservers("Non hai un'emporio nella città");
						}
					}
				}while(!cittàGiusta);
			}

			for(Città cit:this.città){
				cit.eseguiBonus(giocatore);
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}
}

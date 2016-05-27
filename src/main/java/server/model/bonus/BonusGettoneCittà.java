package server.model.bonus;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;



/**
 * This class asks the player which city bonuses wants to obtain with this particular bonus.
 * @author Massimiliano Ventura
 *
 */
public class BonusGettoneCittà implements Bonus {

	
	//private Cambiamento cambiamento;
	private int numeroCittà;
	private Set<Citta> citta;
	private Gioco gioco;
	boolean cittàGiusta;
	
	public BonusGettoneCittà(int numeroCittà, Gioco gioco)
	{
		//cambiamento = new Cambiamento();
		this.gioco=gioco;
		this.numeroCittà=numeroCittà;
		this.citta=new HashSet<Citta>();
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
	public Set<Citta> getCittà() {
		return citta;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) throws IOException{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			for(int i=0; i<numeroCittà; i++)
			{
				do{
					cittàGiusta = true;
					gioco.notificaObservers(this);
					for(Citta c:citta){
						for(Bonus b:c.getBonus()){
							if(b instanceof BonusPercorsoNobiltà){
								cittàGiusta=false;
								citta.remove(c);
								gioco.notificaObservers("C'è un bonus percorso nobiltà");
							}
						}
						if(!c.getEmpori().contains(giocatore)&&citta.contains(c)){
							cittàGiusta=false;
							citta.remove(c);
							gioco.notificaObservers("Non hai un'emporio nella città");
						}
					}
				}while(!cittàGiusta);
			}

			for(Citta cit:this.citta){
				cit.eseguiBonus(giocatore);
			}
		
	}
}

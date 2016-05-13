package model.azione;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.Consigliere;
import model.Consiglio;
import model.Giocatore;

/**
 * @author Luca
 *
 */
public class EleggiConsigliereRapido implements Azione {

	private static final Logger log= Logger.getLogger( EleggiConsigliereRapido.class.getName() );
	private Consigliere consigliere;
	private Consiglio consiglio;
	/**
	 * @param consigliere
	 * @param consiglio
	 */
	public EleggiConsigliereRapido(Consigliere consigliere, Consiglio consiglio) {
		super();
		this.consigliere = consigliere;
		this.consiglio = consiglio;
	}
	/**
	 *Elects a specified Consigliere, 
	 *@throws IllegalStateException if giocatore hasn't enough Aiutanti 
	 *@param giocatore
	 */
	@Override
	public void eseguiAzione (Giocatore giocatore)
	{
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			if(!giocatore.getAssistenti().isEmpty()){
				if(this.consiglio.addConsigliere(this.consigliere))
				{
					this.consiglio.removeConsigliere();
					giocatore.getAssistenti().remove(0);
					giocatore.setAzioneOpzionale(true);
				}
			}
		else
			throw new IllegalStateException("Il giocatore non possiede abbastanza aiutanti par eseguire l'azione");
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}	
	}
	/**
	 * @param consiglio the consiglio to set
	 */
	public void setConsiglio(Consiglio consiglio) {
		this.consiglio = consiglio;
	}
	/**
	 * @param consigliere the consigliere to set
	 */
	public void setConsigliere(Consigliere consigliere) {
		this.consigliere = consigliere;
	}
}

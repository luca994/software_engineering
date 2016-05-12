package model.azione;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.Consigliere;
import model.Consiglio;
import model.Giocatore;
import model.percorso.Percorso;

/**
 * @author Luca
 *
 */
public class EleggiConsigliere implements Azione {

	private static final Logger log= Logger.getLogger( EleggiConsigliere.class.getName() );
	private static final int MONETE_ELEZIONE_CONSIGLIERE = 4;
	private Consigliere consigliere;
	private Consiglio consiglio;
	private Percorso percorsoRicchezza;
	
		/**Constructor method
	 * @param consigliere
	 * @param consiglio
	 * @param percorsoRicchezza
	 */
	public EleggiConsigliere(Consigliere consigliere, Consiglio consiglio, Percorso percorsoRicchezza) {
		super();
		this.consigliere = consigliere;
		this.consiglio = consiglio;
		this.percorsoRicchezza = percorsoRicchezza;
	}


	/**
	 * @param consigliere the consigliere to set
	 */
	public void setConsigliere(Consigliere consigliere) {
		this.consigliere = consigliere;
	}


	/**
	 * @param consiglio the consiglio to set
	 */
	public void setConsiglio(Consiglio consiglio) {
		this.consiglio = consiglio;
	}

/**
 * Elects a new Consigliere from ConsiglieriDisponibili and if all went well , set the boolean AzionePrincipale to true
 */
	@Override
	public void eseguiAzione (Giocatore giocatore){
		try{	
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			if(this.consiglio.addConsigliere(this.consigliere)){
				this.consiglio.removeConsigliere();
				percorsoRicchezza.muoviGiocatore(giocatore, MONETE_ELEZIONE_CONSIGLIERE);
				giocatore.setAzionePrincipale(true);
				}
			}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}
}

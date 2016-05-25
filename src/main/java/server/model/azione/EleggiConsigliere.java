package server.model.azione;

import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Consigliere;
import server.model.Consiglio;
import server.model.Giocatore;
import server.model.percorso.Percorso;

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
	

/**
	 * @param consigliere
	 * @param consiglio
	 * @param percorsoRicchezza
	 */
	public EleggiConsigliere(Consigliere consigliere, Consiglio consiglio, Percorso percorsoRicchezza) {
		this.consigliere = consigliere;
		this.consiglio = consiglio;
		this.percorsoRicchezza = percorsoRicchezza;
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
			//	giocatore.setAzionePrincipale(true);
				}
			}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}


/**
 * @return the consigliere
 */
public Consigliere getConsigliere() {
	return consigliere;
}


/**
 * @param consigliere the consigliere to set
 */
public void setConsigliere(Consigliere consigliere) {
	this.consigliere = consigliere;
}


/**
 * @return the consiglio
 */
public Consiglio getConsiglio() {
	return consiglio;
}


/**
 * @param consiglio the consiglio to set
 */
public void setConsiglio(Consiglio consiglio) {
	this.consiglio = consiglio;
}


/**
 * @return the percorsoRicchezza
 */
public Percorso getPercorsoRicchezza() {
	return percorsoRicchezza;
}


/**
 * @param percorsoRicchezza the percorsoRicchezza to set
 */
public void setPercorsoRicchezza(Percorso percorsoRicchezza) {
	this.percorsoRicchezza = percorsoRicchezza;
}


/**
 * @return the log
 */
public static Logger getLog() {
	return log;
}}

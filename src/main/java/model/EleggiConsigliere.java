package model;

/**
 * @author Luca
 *
 */
public class EleggiConsigliere implements Azione {

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
 * This method performs the move and if all went well , set the boolean AzionePrincipale to true
 */
	@Override
	public void eseguiAzione (Giocatore giocatore){
		if(this.consiglio.addConsigliere(this.consigliere)){
		this.consiglio.removeConsigliere();
		percorsoRicchezza.muoviGiocatoreAvanti(giocatore, MONETE_ELEZIONE_CONSIGLIERE);
		giocatore.setAzionePrincipale(true);}
	}
}

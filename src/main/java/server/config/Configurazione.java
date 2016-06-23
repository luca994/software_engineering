/**
 * 
 */
package server.config;

/**
 * @author Luca
 *
 */
public class Configurazione {
	
	

	/** Game Settings */
	
	/** Actions setup */
		
	/** The number of money you gain when you elect a councillor */
	public static final int MONETE_ELEZIONE_CONSIGLIERE = 4;
	
	/** The number of money you have to pay for each city that the king crosses to go to the selected city*/
	public static final int PREZZO_STRADA_PERCORSA = 2;

	/** The number of money you have to pay to fulfill a council with one card played*/
	public static final int PREZZO_UNA_CARTA_GIOCATA = 10;
	
	/** The number of money you have to pay to fulfill a council with two cards played*/
	public static final int PREZZO_DUE_CARTE_GIOCATE = 7;

	/** The number of money you have to pay to fulfill a council with three cards played*/
	public static final int PREZZO_TRE_CARTE_GIOCATE = 4;

	/** The number of money you have to pay to fulfill a council with four cards played*/
	public static final int PREZZO_QUATTRO_CARTE_GIOCATE = 0;

	/** The minimum number of councillors that you have to fulfill to */
	public static final int MIN_NUM_CONSIGLIERI_DA_SODDISFARE = 1;
	
	/** The number of money you have to pay to do the rapid action IngaggiaConsigliere*/
	public static final int COSTO_INGAGGIA_AIUTANTE = 3;
	
	/** The number of assistants you have to pay to do the rapid action CambioTessereCostruzione*/
	public static final int COSTO_CAMBIA_TESSERE_COSTRUZIONE = 1;
	
	/** The number of assistants you have to pay to do the rapid action EleggiConsigliereRapido*/
	public static final int COSTO_ELEGGI_CONSIGLIERE_RAPIDO = 1;
	
	/** The number of assistants you have to pay to do the rapid action AzionePrincipaleAggiuntiva*/
	public static final int COSTO_AZIONE_PRINCIPALE_AGGIUNTIVA = 3;
	
	/** End of the game and final score settings*/
	
	/** The number of victory points awarded to the first player in the path nobility*/
	public static final int PUNTI_PRIMO_PERCORSO_NOBILTA = 5;
	/** The number of victory points awarded to the second player in the path nobility*/
	public static final int PUNTI_SECONDO_PERCORSO_NOBILTA = 2;
	/** The number of victory points awarded to the player with more tiles building permit */
	public static final int PUNTI_PIU_TESSERE_PERMESSO = 3;
	
	
	
	/** Private constructor 	*/
	private Configurazione() {
	}






}

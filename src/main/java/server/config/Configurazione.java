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
	
	/** The minimum number of a player to play a game */
	public static final int MIN_NUM_GIOCATORI = 2;
	/** The number of emporiums that you have to build to finish a game*/
	public static final int NUM_EMPORI_MASSIMO = 10;
	
	/** Actions setup */
		
	/** The number of money you gain when you elect a councillor */
	public static final int MONETE_ELEZIONE_CONSIGLIERE = 4;
	
	/** The number of money you have to pay for each city that the king crosses to go to the selected city*/
	public static final int PREZZO_STRADA_PERCORSA = 2;

	/** The number of money you have to pay to satisfy a council with one card played*/
	public static final int PREZZO_UNA_CARTA_GIOCATA = 10;
	
	/** The number of money you have to pay to satisfy a council with two cards played*/
	public static final int PREZZO_DUE_CARTE_GIOCATE = 7;

	/** The number of money you have to pay to satisfy a council with three cards played*/
	public static final int PREZZO_TRE_CARTE_GIOCATE = 4;

	/** The number of money you have to pay to satisfy a council with four cards played*/
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
	
	/** Server Settings */
	
	/** The RMI server port */
	public static final int RMI_PORT = 1099;

	/** The Socket server port */
	public static final int SOCKET_PORT = 29999;
	
	/** The name of the RMI registry */
	public static final String RMI_REGISTRY_NAME = "consiglioDeiQuattroRegistro";
	
	/** The time (in milliseconds) to wait before a game of two players starts */
	public static final int TEMPO_ATTESA_AVVIO_PARTITA = 5000;
	

	/** The time (in milliseconds) available to each player to take their turn */
	private static Integer durataTurnoMassima;
	
	/** Private constructor */
	private Configurazione() {
	}


	/**
	 * @return the maxTimeForTurn
	 */
	public static synchronized void setMaxTimeForTurn(int time) {
		if(durataTurnoMassima==null)
			durataTurnoMassima=time;
	}

	/**
	 * @return the maxTimeForTurn
	 */
	public static synchronized int getMaxTimeForTurn() {
		return durataTurnoMassima;
	}

}

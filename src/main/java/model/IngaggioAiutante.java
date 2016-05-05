package model;

/**
 * @author Luca
 *
 */
public class IngaggioAiutante implements Azione {

	private final static int COSTO_AIUTANTE=3;
	private Percorso percorsoRicchezza;
	
	/**
	 * @author Riccardo
	 * the player buy an assistant for 3 coins.
	 * @param giocatore the player who wants to buy an assistant
	 */
	public void eseguiAzione (Giocatore giocatore){
		try{
			percorsoRicchezza.muoviGiocatore(giocatore, -COSTO_AIUTANTE);
			giocatore.getAssistenti().add(new Assistente());
			giocatore.setAzioneOpzionale(true);
		}
		catch(IndexOutOfBoundsException e){
			System.err.println(e.getLocalizedMessage());
			//se viene catturata l' eccezione vuol dire che il giocatore
			//non ha abbastanza soldi, quindi il metodo termina senza 
			//apportare nessuna modifica.
		}
	}
}

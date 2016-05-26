package server.model.azione;

import java.io.IOException;

import server.model.Assistente;
import server.model.Giocatore;
import server.model.Gioco;

/**
 * @author Luca
 *
 */
public class IngaggioAiutante extends Azione {

	
	private final static int COSTO_AIUTANTE=3;
	
	
	/**
	 * @param percorsoRicchezza
	 */
	public IngaggioAiutante(Gioco gioco) {
		super(gioco);
	}

	
	/**
	 * @author Riccardo
	 * the player add an assistant to his assistants's list and come back of
	 * COSTO_AIUTANTE in the richness route
	 * @param giocatore the player who wants to buy an assistant
	 * @throws IOException 
	 */
	public void eseguiAzione (Giocatore giocatore) throws IOException{
			if (giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, -COSTO_AIUTANTE);
			giocatore.getAssistenti().add(new Assistente());
			giocatore.getStatoGiocatore().azioneRapidaEseguita();
	}


	@Override
	public boolean verificaInput(Giocatore giocatore) {
		return true;
	}
}

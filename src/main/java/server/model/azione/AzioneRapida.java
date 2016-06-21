package server.model.azione;

import server.model.Gioco;

/**
 * Abstract class used to classify the type of action.
 * 
 */
public abstract class AzioneRapida extends Azione {

	public AzioneRapida(Gioco gioco) {
		super(gioco);
	}

}

package server.model.azione;

import server.model.Gioco;

/**
 * Abstract class used to classify the type of action.
 *
 */
public abstract class AzionePrincipale extends Azione {

	public AzionePrincipale(Gioco gioco) {
		super(gioco);
	}

}

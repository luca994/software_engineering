package server.model.azione;

import server.model.Giocatore;

/**
 * @author Luca
 *
 */
@FunctionalInterface
public interface Azione {

		public void eseguiAzione(Giocatore giocatore) throws Exception;
}

package model.azione;

import model.Giocatore;

/**
 * @author Luca
 *
 */
@FunctionalInterface
public interface Azione {

		public void eseguiAzione(Giocatore giocatore);
}

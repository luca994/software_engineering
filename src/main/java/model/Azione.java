package model;

/**
 * @author Luca
 *
 */
public abstract class Azione {

		private Giocatore giocatore;
		

		/**
		 * @return the giocatore
		 */
		public Giocatore getGiocatore() {
			return giocatore;
		}


		public abstract void eseguiAzione();
}

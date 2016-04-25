package model;

/**
 * @author Luca
 *
 */
public class Emporio {

		private final String colore;
		private final Giocatore giocatore;
		
		/**
		 * build an emporium for a player
		 * @param colore the color choosen by the player
		 * @param giocatore the player
		 */
		public Emporio(String colore,Giocatore giocatore){
			this.colore=colore;
			this.giocatore=giocatore;
		}

		/**
		 * 
		 * @return return the color
		 */
		public String getColore() {
			return colore;
		}

		/**
		 * 
		 * @return return the player
		 */
		public Giocatore getGiocatore() {
			return giocatore;
		}
		
		
}

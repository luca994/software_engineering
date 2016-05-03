package model;

import java.util.List;

/**
 * @author Luca
 *
 */
public class Giocatore {

		private int id;
		private String nome;
		private List<Assistente> assistenti;
		private List<CartaPolitica> cartePolitica;
		private boolean azionePrincipale;
		private boolean azioneOpzionale;
		private String colore;
		private List<OggettoConBonus> tessereUsate;
		private List<OggettoConBonus> tessereValide;
		private Azione azione;
		private Gioco gioco;
		private Tabellone tabellone;
		private boolean connesso;
		
		
		public void moveTesseraValidaToTesseraUsata (OggettoConBonus tesseraToMove){
			/*si potrebbe inserire l'eccezione che viene lanciata
			 * se la tessera selezionata non è nella lista di tessere valide
			 */
			tessereValide.remove(tesseraToMove);  //gestire le eccezioni lanciate
			tessereUsate.add(tesseraToMove);  //anche qua gestire eccezioni
		}
		
		/**
		 * this method add tessereV to the list tessereValide
		 * @param tessereV
		 */
		public void addTessereValide(OggettoConBonus tessereV){
			tessereValide.add(tessereV);
		}
		
		/**
		 * this method add tessereU to the list tessereUsate
		 * @param tessereU
		 */
		public void addTessereUsate(OggettoConBonus tessereU){
			tessereUsate.add(tessereU);
		}
		/**
		 * this method verify if the list in imput is contained by the list of giocatore's card
		 * @param carteDaVerificare
		 * @return
		 */
		public boolean containsAllCarte(List<CartaPolitica> carteDaVerificare){
			return cartePolitica.containsAll(carteDaVerificare);
		}
		
		/**
		 * this method verify if the object in imput is contained by the list of giocatore's tessereUsate
		 * @param tesseraUsataDaVerificare
		 * @return
		 */
		public boolean containsTesseraUsata(OggettoConBonus tesseraUsataDaVerificare){
			return tessereUsate.contains(tesseraUsataDaVerificare);
		}
		/**
		 * this method verify if the object in imput is contained by the list of giocatore's tessereValide
		 * @param tesseraValidaDaVerificare
		 * @return
		 */
		public boolean containsTesseraValide(OggettoConBonus tesseraValidaDaVerificare){
			return tessereValide.contains(tesseraValidaDaVerificare);
		}
		
		/**
		 * @param azionePrincipale the azionePrincipale to set
		 */
		public void setAzionePrincipale(boolean azionePrincipale) {
			this.azionePrincipale = azionePrincipale;
		}
		/**
		 * @param azioneOpzionale the azioneOpzionale to set
		 */
		public void setAzioneOpzionale(boolean azioneOpzionale) {
			this.azioneOpzionale = azioneOpzionale;
		}
		public int getId() {
			return id;
		}
		public String getNome() {
			return nome;
		}
		public List<Assistente> getAssistenti() {
			return assistenti;
		}
		public List<CartaPolitica> getCartePolitica() {
			return cartePolitica;
		}
		public boolean isAzionePrincipale() {
			return azionePrincipale;
		}
		public boolean isAzioneOpzionale() {
			return azioneOpzionale;
		}
		public String getColore() {
			return colore;
		}
		public List<OggettoConBonus> getTessereUsate() {
			return tessereUsate;
		}
		public List<OggettoConBonus> getTessereValide() {
			return tessereValide;
		}
		public Azione getAzione() {
			return azione;
		}
		public Gioco getGioco() {
			return gioco;
		}
		public Tabellone getTabellone() {
			return tabellone;
		}
		public boolean isConnesso() {
			return connesso;
		}
		
		
}

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
		private List<TesseraCostruzione> tessereUsate;
		private List<TesseraCostruzione> tessereValide;
		private Gioco gioco;
		private Tabellone tabellone;
		private boolean connesso;
		private int emporiRimasti;
		
		public Giocatore(String nome, String colore){
			this.colore=colore;
			this.nome=nome;
		}
		public boolean getAzionePrincipale(){
			return this.azionePrincipale;
		}
		public boolean getAzioneOpzionale(){
			return this.azioneOpzionale;
		}
		/**
		 * @param emporiRimasti the emporiRimasti to set
		 */
		public void setEmporiRimasti(int emporiRimasti) {
			this.emporiRimasti = emporiRimasti;
		}

		/**
		 * @return the emporiRimasti
		 */
		public int getEmporiRimasti() {
			return emporiRimasti;
		}

		/**
		 * @param emporiRimasti the emporiRimasti to set
		 */
		public int decrementaEmporiRimasti() {
			emporiRimasti = emporiRimasti-1;
			return emporiRimasti;
		}

		public void moveTesseraValidaToTesseraUsata (TesseraCostruzione tesseraToMove){
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
		public void addTessereValide(TesseraCostruzione tessereV){
			tessereValide.add(tessereV);
		}
		
		/**
		 * this method add tessereU to the list tessereUsate
		 * @param tessereU
		 */
		public void addTessereUsate(TesseraCostruzione tessereU){
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
		public boolean containsTesseraUsata(TesseraCostruzione tesseraUsataDaVerificare){
			return tessereUsate.contains(tesseraUsataDaVerificare);
		}
		/**
		 * this method verify if the object in imput is contained by the list of giocatore's tessereValide
		 * @param tesseraValidaDaVerificare
		 * @return
		 */
		public boolean containsTesseraValide(TesseraCostruzione tesseraValidaDaVerificare){
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
		public List<TesseraCostruzione> getTessereUsate() {
			return tessereUsate;
		}
		public List<TesseraCostruzione> getTessereValide() {
			return tessereValide;
		}
		
		public Gioco getGioco() {
			return gioco;
		}
		/**
		 * @param gioco the gioco to set
		 */
		public void setGioco(Gioco gioco) {
			this.gioco = gioco;
		}

		public Tabellone getTabellone() {
			return tabellone;
		}
		public boolean isConnesso() {
			return connesso;
		}

		/**
		 * @param i
		 */
		public void setId(int id) {
			this.id=id;			
		}

		
		
		
}

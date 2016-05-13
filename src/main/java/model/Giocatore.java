package model;

import java.awt.Color;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Luca
 *
 */
/**
 * @author Luca
 *
 */
public class Giocatore {

		private static final Logger log= Logger.getLogger( Giocatore.class.getName() );
		private int id;
		private String nome;
		private List<Assistente> assistenti;
		private List<CartaPolitica> cartePolitica;
		private boolean azionePrincipale;
		private boolean azioneOpzionale;
		private Color colore;
		private List<TesseraCostruzione> tessereUsate;
		private List<TesseraCostruzione> tessereValide;
		private boolean connesso;
		private int emporiRimasti;
		
		/**Constructor for the class Giocatore
		 * @param nome
		 * @param colore
		 */
		public Giocatore(String nome, Color colore) {
			this.nome = nome;
			this.colore = colore;
		}

	
	
		/**
		 * @return decrement of one unit integer EmporiRimasti
		 */
		public void decrementaEmporiRimasti() {
			emporiRimasti --;
		}


		/**this method move TesseraCostruzione from list tessereValide to list tessereUsate
		 * @param tesseraToMove is the Tessera to move from tessereValide to tessereUsate
		 */
		public void moveTesseraValidaToTesseraUsata (TesseraCostruzione tesseraToMove){
			if(!tessereValide.contains(tesseraToMove)){
				throw new IllegalArgumentException("La tessera da spostare non è nelle tessereValide del giocatore");
			}
				tessereValide.remove(tesseraToMove);
				tessereUsate.add(tesseraToMove);
		}


		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}


		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}


		/**
		 * @return the nome
		 */
		public String getNome() {
			return nome;
		}


		/**
		 * @param nome the nome to set
		 */
		public void setNome(String nome) {
			this.nome = nome;
		}


		/**
		 * @return the assistenti
		 */
		public List<Assistente> getAssistenti() {
			return assistenti;
		}


		/**
		 * @param assistenti the assistenti to set
		 */
		public void setAssistenti(List<Assistente> assistenti) {
			this.assistenti = assistenti;
		}


		/**
		 * @return the cartePolitica
		 */
		public List<CartaPolitica> getCartePolitica() {
			return cartePolitica;
		}


		/**
		 * @param cartePolitica the cartePolitica to set
		 */
		public void setCartePolitica(List<CartaPolitica> cartePolitica) {
			this.cartePolitica = cartePolitica;
		}


		/**
		 * @return the azionePrincipale
		 */
		public boolean isAzionePrincipale() {
			return azionePrincipale;
		}


		/**
		 * @param azionePrincipale the azionePrincipale to set
		 */
		public void setAzionePrincipale(boolean azionePrincipale) {
			this.azionePrincipale = azionePrincipale;
		}


		/**
		 * @return the azioneOpzionale
		 */
		public boolean isAzioneOpzionale() {
			return azioneOpzionale;
		}


		/**
		 * @param azioneOpzionale the azioneOpzionale to set
		 */
		public void setAzioneOpzionale(boolean azioneOpzionale) {
			this.azioneOpzionale = azioneOpzionale;
		}


		/**
		 * @return the colore
		 */
		public Color getColore() {
			return colore;
		}


		/**
		 * @param colore the colore to set
		 */
		public void setColore(Color colore) {
			this.colore = colore;
		}


		/**
		 * @return the tessereUsate
		 */
		public List<TesseraCostruzione> getTessereUsate() {
			return tessereUsate;
		}


		/**
		 * @param tessereUsate the tessereUsate to set
		 */
		public void setTessereUsate(List<TesseraCostruzione> tessereUsate) {
			this.tessereUsate = tessereUsate;
		}


		/**
		 * @return the tessereValide
		 */
		public List<TesseraCostruzione> getTessereValide() {
			return tessereValide;
		}


		/**
		 * @param tessereValide the tessereValide to set
		 */
		public void setTessereValide(List<TesseraCostruzione> tessereValide) {
			this.tessereValide = tessereValide;
		}


		/**
		 * @return the connesso
		 */
		public boolean isConnesso() {
			return connesso;
		}


		/**
		 * @param connesso the connesso to set
		 */
		public void setConnesso(boolean connesso) {
			this.connesso = connesso;
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
		public void setEmporiRimasti(int emporiRimasti) {
			this.emporiRimasti = emporiRimasti;
		}


		/**
		 * @return the log
		 */
		public static Logger getLog() {
			return log;
		}
}
package server.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.StatoGiocatore;

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
		private Color colore;
		private List<TesseraCostruzione> tessereUsate;
		private List<TesseraCostruzione> tessereValide;
		private int emporiRimasti;
		private StatoGiocatore statoGiocatore;
		
		/**Constructor for the class Giocatore
		 * @param nome
		 * @param colore
		 */
		public Giocatore(String nome, Color colore) {
			this.nome = nome;
			this.colore = colore;
			this.assistenti=new ArrayList<>();
			this.cartePolitica=new ArrayList<>();
			this.tessereUsate=new ArrayList<>();
			this.tessereValide=new ArrayList<>();
			statoGiocatore=new AttesaTurno(this);
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
		 * @return the statoGiocatore
		 */
		public StatoGiocatore getStatoGiocatore() {
			return statoGiocatore;
		}



		/**
		 * @param statoGiocatore the statoGiocatore to set
		 */
		public void setStatoGiocatore(StatoGiocatore statoGiocatore) {
			this.statoGiocatore = statoGiocatore;
		}


		
}
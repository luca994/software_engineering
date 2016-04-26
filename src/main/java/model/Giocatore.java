package model;

import java.util.List;

/**
 * @author Luca
 *
 */
public class Giocatore {

		private int id;
		private String nome;
		private List<Emporio> emporiRimasti;
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
		public List<Emporio> getEmporiRimasti() {
			return emporiRimasti;
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

package model;

import java.util.List;

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
		
}

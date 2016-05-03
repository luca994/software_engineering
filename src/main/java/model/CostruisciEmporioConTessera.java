package model;

/**
 * @author Luca
 *
 */
public class CostruisciEmporioConTessera implements Azione {

	private Città città;
	private TesseraCostruzione tessera;
	
	public void eseguiAzione (Giocatore giocatore){
		if(giocatore.containsTesseraValide(tessera) && tessera.verifyCittà(città)){
			//azione da definire, chiedere a richi come funzionano i metodi di città
			giocatore.moveTesseraValidaToTesseraUsata(tessera);
			giocatore.setAzionePrincipale(true);
		}
	}
}

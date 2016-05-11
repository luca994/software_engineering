package model.azione;

import java.util.ArrayList;
import java.util.List;

import model.Città;
import model.Giocatore;
import model.Tabellone;
import model.TesseraCostruzione;

/**
 * @author Luca
 *
 */
public class CostruisciEmporioConTessera implements Azione {

	private Città città;
	private TesseraCostruzione tessera;
	private Tabellone tabellone;
	/**
	 * @param città
	 * @param tessera
	 */
	public CostruisciEmporioConTessera(Città città, TesseraCostruzione tessera, Tabellone tabellone) {
		super();
		this.città = città;
		this.tessera = tessera;
		this.tabellone=tabellone;
	}

	/**
	 * @param città the città to set
	 */
	public void setCittà(Città città) {
		this.città = città;
	}

	/**
	 * @param tessera the tessera to set
	 */
	public void setTessera(TesseraCostruzione tessera) {
		this.tessera = tessera;
	}
	
	/**
	 * Build a new emporio using a Tessera Costruzione owned by the player
	 * Throws IllegalStateException if Tessera Costruzione is not appropriated for this city or the player alredy has an emporio
	 */
	@Override
	public void eseguiAzione (Giocatore giocatore){
		try{
			if(giocatore.containsTesseraValide(tessera) && tessera.verifyCittà(città)&&città.presenzaEmporio(giocatore)){
				//azione da definire, chiedere a richi come funzionano i metodi di città
				giocatore.moveTesseraValidaToTesseraUsata(tessera);
				città.aggiungiEmporio(giocatore);
				giocatore.decrementaEmporiRimasti();
				//Se il giocatore ha finito gli empori guadagna 3 punti vittoria	
				if(giocatore.getEmporiRimasti()==0)
					tabellone.getPercorsoVittoria().muoviGiocatore(giocatore, 3);
				//Mi piglio i bonus di questa e delle città collegate
				List<Città> cittàConBonusDaOttenere=new ArrayList<Città>();
				cittàConBonusDaOttenere.add(città);
				città.cittàVicinaConEmporio(giocatore, cittàConBonusDaOttenere);
				for(Città citt: cittàConBonusDaOttenere)
					citt.eseguiBonus(giocatore);
				giocatore.setAzionePrincipale(true);
				//controllo se ho gli empori in tutte le città di un colore o di una regione e prendo la
				//tessera bonus se mi spetta
				tabellone.prendiTesseraBonus(giocatore, città);
			}
			else 
				throw new IllegalStateException("L'emporio è già presente oppure la tessera non è appropriata");
		}
		catch(IllegalStateException e){
			System.err.println(e.getLocalizedMessage());
			//Non viene eseguito nulla, il turno del giocatore continua
		}
	}

	
}

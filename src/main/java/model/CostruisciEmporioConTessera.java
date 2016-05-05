package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca
 *
 */
public class CostruisciEmporioConTessera implements Azione {

	private Città città;
	private TesseraCostruzione tessera;
	/**
	 * @param città
	 * @param tessera
	 */
	public CostruisciEmporioConTessera(Città città, TesseraCostruzione tessera) {
		super();
		this.città = città;
		this.tessera = tessera;
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
				//Mi piglio i bonus di questa e delle città collegate
				List<Città> cittàConBonusDaOttenere=new ArrayList<Città>();
				cittàConBonusDaOttenere.add(città);
				città.cittàVicinaConEmporio(giocatore, cittàConBonusDaOttenere);
				for(Città citt: cittàConBonusDaOttenere)
					citt.eseguiAzioneBonus(giocatore);
				giocatore.setAzionePrincipale(true);
			
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

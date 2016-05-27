package server.model;

import java.util.ArrayList;
import java.util.List;

import server.model.percorso.Percorso;

/**
 * @author Riccardo
 *
 */
public class Mercato {

	private List<OggettoVendibile> oggettiInVendita;
	private Percorso percorsoRicchezza;
	
	/**
	 * build the object
	 */
	public Mercato(Percorso percorsoRicchezza){
		oggettiInVendita=new ArrayList<>();
		this.percorsoRicchezza=percorsoRicchezza;
	}

	/**
	 * 
	 * @return the list of oggettiInVendita
	 */
	public List<OggettoVendibile> getOggettiInVendita() {
		return oggettiInVendita;
	}

	/**
	 * 
	 * @return the money route of the game
	 */
	public Percorso getPercorsoRicchezza() {
		return percorsoRicchezza;
	}
	
	
}

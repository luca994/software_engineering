/**
 * 
 */
package model.azione;

import model.Consigliere;
import model.Consiglio;
import model.percorso.Percorso;

/**
 * @author Luca
 *
 */
public class AzioneFactory {
	
	public Azione getAzione(String tipoAzione,Consigliere consigliere,Consiglio consiglio, Percorso percorso){
		if (tipoAzione == null){
			return null;
			}
		if (tipoAzione.equalsIgnoreCase("ELEGGI CONSIGLIERE")){
			EleggiConsigliere eleggiConsigliere = new EleggiConsigliere(consigliere, consiglio, percorso);
			return eleggiConsigliere;
		}
		return null;
	}
	
	
	
	
}

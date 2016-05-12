/**
 * 
 */
package model.azione;

import model.Consigliere;
import model.Consiglio;
import model.Giocatore;
import model.Regione;
import model.Tabellone;
import model.TesseraCostruzione;
import model.percorso.Percorso;

import java.util.List;

import model.CartaPolitica;
import model.Città;

/**
 * @author Luca
 *
 */
public class AzioneFactory {
	
	private Giocatore giocatore;
	private Consiglio consiglio;
	private Consigliere consigliere;
	private Percorso percorsoRicchezza;
	private TesseraCostruzione tesseraCostruzione;
	private List<CartaPolitica> cartePolitica;
	private Regione regione;
	private Città citta;
	private Tabellone tabellone;
	
	
	public Azione getAzione(String tipoAzione){
		if (tipoAzione == null){
			return null;
			}
		if (tipoAzione.equalsIgnoreCase("ELEGGI CONSIGLIERE")){
			EleggiConsigliere eleggiConsigliere = new EleggiConsigliere(consigliere, consiglio, percorsoRicchezza);
			return eleggiConsigliere;
		}
		if (tipoAzione.equalsIgnoreCase("ACQUISTA PERMESSO")){
			AcquistaPermesso acquistaPermesso = new AcquistaPermesso(tesseraCostruzione,cartePolitica,consiglio,percorsoRicchezza);
			return acquistaPermesso;
		}
		if (tipoAzione.equalsIgnoreCase("AZIONE OPZIONALE NULLA")){
			AzioneOpzionaleNulla azioneOpNulla = new AzioneOpzionaleNulla();
			return azioneOpNulla;
		}
		if (tipoAzione.equalsIgnoreCase("AZIONE PRINCIPALE AGGIUNTIVA")){
			AzionePrincipaleAggiuntiva azionePrincAgg = new AzionePrincipaleAggiuntiva();
			return azionePrincAgg;
		}
		if (tipoAzione.equalsIgnoreCase("AZIONE PRINCIPALE NULLA")){
			AzionePrincipaleNulla azionePrincNulla = new AzionePrincipaleNulla();
			return azionePrincNulla;
		}
		if (tipoAzione.equalsIgnoreCase("CAMBIO TESSERE COSTRUZIONE")){
			CambioTessereCostruzione cambioTessere = new CambioTessereCostruzione(regione);
			return cambioTessere;
		}
		if (tipoAzione.equalsIgnoreCase("COSTRUISCI EMPORIO CON RE")){
			CostruisciEmporioConRe costrEmpRe = new CostruisciEmporioConRe(tabellone, giocatore, cartePolitica);
			return costrEmpRe;
		}
		if (tipoAzione.equalsIgnoreCase("COSTRUISCI EMPORIO CON TESSERA")){
			CostruisciEmporioConTessera costrEmpTess = new CostruisciEmporioConTessera(citta, tesseraCostruzione, tabellone);
			return costrEmpTess;
		}
		if (tipoAzione.equalsIgnoreCase("ELEGGI CONSIGLIERE RAPIDO")){
			EleggiConsigliereRapido eleggiConsigliereRapido = new EleggiConsigliereRapido(consigliere, consiglio);
			return eleggiConsigliereRapido;
		}
		if (tipoAzione.equalsIgnoreCase("INGAGGIO AIUTANTE")){
			IngaggioAiutante ingaggiaAiutante = new IngaggioAiutante(percorsoRicchezza);
			return ingaggiaAiutante;
		}
		if (tipoAzione.equalsIgnoreCase("MUOVI RE")){
			MuoviRe muoviRe = new MuoviRe(tabellone.getRe(), percorsoRicchezza);
			return muoviRe;
		}
		return null;
	}
	
	
	
	
}

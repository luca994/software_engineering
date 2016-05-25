/**
 * 
 */
package server.model.azione;

import java.util.List;

import server.model.CartaPolitica;
import server.model.Città;
import server.model.Consigliere;
import server.model.Consiglio;
import server.model.Giocatore;
import server.model.Regione;
import server.model.Tabellone;
import server.model.TesseraCostruzione;
import server.model.percorso.Percorso;

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
	
	
	public Azione createAzione(String tipoAzione){
		if (tipoAzione == null){
			throw new IllegalStateException("l'azione non può essere nulla");
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
			CostruisciEmporioConRe costrEmpRe = new CostruisciEmporioConRe(tabellone, giocatore, cartePolitica,citta);
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
		throw new IllegalStateException("l'azione inserita non è corretta");
	}
	
	
	
	
}

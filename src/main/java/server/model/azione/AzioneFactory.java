/**
 * 
 */
package server.model.azione;

import java.util.List;

import server.model.CartaPolitica;
import server.model.Citta;
import server.model.Consigliere;
import server.model.Consiglio;
import server.model.Gioco;
import server.model.Regione;
import server.model.TesseraCostruzione;

/**
 * @author Luca
 *
 */
public class AzioneFactory {

	private Consiglio consiglio;
	private Consigliere consigliere;
	private TesseraCostruzione tesseraCostruzione;
	private List<CartaPolitica> cartePolitica;
	private Regione regione;
	private Citta citta;

	private Gioco gioco;

	public AzioneFactory(Gioco gioco) {
		this.gioco = gioco;
	}

	public Azione createAzione(String tipoAzione) {
		if (tipoAzione == null) {
			throw new IllegalStateException("l'azione non può essere nulla");
		}
		if ("ELEGGI CONSIGLIERE".equalsIgnoreCase(tipoAzione)) {
			return new EleggiConsigliere(gioco,consigliere, consiglio);
		}
		if ("ACQUISTA PERMESSO".equalsIgnoreCase(tipoAzione)) {
			return new AcquistaPermesso(gioco,tesseraCostruzione, cartePolitica, consiglio);
		}
		if ("AZIONE OPZIONALE NULLA".equalsIgnoreCase(tipoAzione)) {
			return new AzioneOpzionaleNulla();
		}
		if ("AZIONE PRINCIPALE AGGIUNTIVA".equalsIgnoreCase(tipoAzione)) {
			return new AzionePrincipaleAggiuntiva();
		}
		if ("AZIONE PRINCIPALE NULLA".equalsIgnoreCase(tipoAzione)) {
			return new AzionePrincipaleNulla();
		}
		if ("CAMBIO TESSERE COSTRUZIONE".equalsIgnoreCase(tipoAzione)) {
			return new CambioTessereCostruzione(regione);
		}
		if ("COSTRUISCI EMPORIO CON RE".equalsIgnoreCase(tipoAzione)) {
			return new CostruisciEmporioConRe(gioco, cartePolitica, citta);
		}
		if ("COSTRUISCI EMPORIO CON TESSERA".equalsIgnoreCase(tipoAzione)) {
			return new CostruisciEmporioConTessera(citta, tesseraCostruzione);
		}
		if ("ELEGGI CONSIGLIERE RAPIDO".equalsIgnoreCase(tipoAzione)) {
			return new EleggiConsigliereRapido(consigliere, consiglio);
		}
		if ("INGAGGIO AIUTANTE".equalsIgnoreCase(tipoAzione)) {
			return new IngaggioAiutante(gioco);
		}
		throw new IllegalStateException("l'azione inserita non è corretta");
	}

}

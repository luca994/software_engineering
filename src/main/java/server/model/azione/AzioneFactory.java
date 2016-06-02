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
 * Factory class for actions, is used to instantiate a new action. 
 *
 */
public class AzioneFactory {

	private Consiglio consiglio;
	private Consigliere consigliere;
	private TesseraCostruzione tesseraCostruzione;
	private List<CartaPolitica> cartePolitica;
	private Regione regione;
	private Citta citta;

	private final Gioco gioco;

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
		if ("AZIONE PRINCIPALE AGGIUNTIVA".equalsIgnoreCase(tipoAzione)) {
			return new AzionePrincipaleAggiuntiva();
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

	/**
	 * @param consiglio the consiglio to set
	 */
	public void setConsiglio(Consiglio consiglio) {
		this.consiglio = consiglio;
	}

	/**
	 * @param consigliere the consigliere to set
	 */
	public void setConsigliere(Consigliere consigliere) {
		this.consigliere = consigliere;
	}

	/**
	 * @param tesseraCostruzione the tesseraCostruzione to set
	 */
	public void setTesseraCostruzione(TesseraCostruzione tesseraCostruzione) {
		this.tesseraCostruzione = tesseraCostruzione;
	}

	/**
	 * @param cartePolitica the cartePolitica to set
	 */
	public void setCartePolitica(List<CartaPolitica> cartePolitica) {
		this.cartePolitica = cartePolitica;
	}

	/**
	 * @param regione the regione to set
	 */
	public void setRegione(Regione regione) {
		this.regione = regione;
	}

	/**
	 * @param citta the citta to set
	 */
	public void setCitta(Citta citta) {
		this.citta = citta;
	}

}

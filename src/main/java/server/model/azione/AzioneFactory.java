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

	private transient Gioco gioco;

	public AzioneFactory(Gioco gioco) {
		this.gioco = gioco;
	}

	public Azione createAzione(String tipoAzione) {
		if (tipoAzione == null) {
			throw new IllegalStateException("l'azione non può essere nulla");
		}
		if (Integer.parseInt(tipoAzione)==2) {
			return new EleggiConsigliere(gioco,consigliere, consiglio);
		}
		if (Integer.parseInt(tipoAzione)==0) {
			return new AcquistaPermesso(gioco,tesseraCostruzione, cartePolitica, consiglio);
		}
		if (Integer.parseInt(tipoAzione)==7) {
			return new AzionePrincipaleAggiuntiva();
		}
		if (Integer.parseInt(tipoAzione)==5) {
			return new CambioTessereCostruzione(regione);
		}
		if (Integer.parseInt(tipoAzione)==1) {
			return new CostruisciEmporioConRe(gioco, cartePolitica, citta);
		}
		if (Integer.parseInt(tipoAzione)==3) {
			return new CostruisciEmporioConTessera(citta, tesseraCostruzione);
		}
		if (Integer.parseInt(tipoAzione)==6) {
			return new EleggiConsigliereRapido(consigliere, consiglio);
		}
		if (Integer.parseInt(tipoAzione)==4) {
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

	/**
	 * 
	 * @return the council
	 */
	public Consiglio getConsiglio() {
		return consiglio;
	}

	/**
	 * 
	 * @return the councillor
	 */
	public Consigliere getConsigliere() {
		return consigliere;
	}

	/**
	 * 
	 * @return the buisness permit tile
	 */
	public TesseraCostruzione getTesseraCostruzione() {
		return tesseraCostruzione;
	}

	/**
	 * 
	 * @return return the list of politics card
	 */
	public List<CartaPolitica> getCartePolitica() {
		return cartePolitica;
	}

	/**
	 * 
	 * @return the region
	 */
	public Regione getRegione() {
		return regione;
	}

	/**
	 * 
	 * @return the city
	 */
	public Citta getCitta() {
		return citta;
	}

	/**
	 * 
	 * @return the game
	 */
	public Gioco getGioco() {
		return gioco;
	}

	
	
}

/**
 * 
 */
package server.model.azione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.CartaPolitica;
import server.model.componenti.Citta;
import server.model.componenti.Consigliere;
import server.model.componenti.Consiglio;
import server.model.componenti.Regione;
import server.model.componenti.TesseraCostruzione;

/**
 * Factory class for actions, is used to instantiate a new action. 
 *
 */
public class AzioneFactory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6546053698601351882L;
	
	private Consiglio consiglio;
	private Consigliere consigliere;
	private TesseraCostruzione tesseraCostruzione;
	private List<CartaPolitica> cartePolitica;
	private Regione regione;
	private Citta citta;
	private String tipoAzione;
	private boolean consiglioRe;

	private Gioco gioco;

	public AzioneFactory(Gioco gioco) {
		this.gioco = gioco;
		consiglioRe = false;
	}

	public Azione createAzione() {
		if (tipoAzione == null) {
			throw new IllegalStateException("l'azione non può essere nulla");
		}
		if (Integer.parseInt(tipoAzione)==2) {
			if(consigliere==null || consiglio==null)
				return null;
			return new EleggiConsigliere(gioco,consigliere, consiglio);
		}
		if (Integer.parseInt(tipoAzione)==0) {
			if(tesseraCostruzione==null || cartePolitica.isEmpty() || consiglio==null || consiglioRe)
				return null;
			return new AcquistaPermesso(gioco,tesseraCostruzione, cartePolitica, consiglio);
		}
		if (Integer.parseInt(tipoAzione)==7) {
			return new AzionePrincipaleAggiuntiva();
		}
		if (Integer.parseInt(tipoAzione)==5) {
			if(regione==null)
				return null;
			return new CambioTessereCostruzione(regione);
		}
		if (Integer.parseInt(tipoAzione)==1) {
			if(cartePolitica.isEmpty() || citta==null)
				return null;
			return new CostruisciEmporioConRe(gioco, cartePolitica, citta);
		}
		if (Integer.parseInt(tipoAzione)==3) {
			if(citta==null || tesseraCostruzione==null)
				return null;
			return new CostruisciEmporioConTessera(gioco,citta, tesseraCostruzione);
		}
		if (Integer.parseInt(tipoAzione)==6) {
			if(consigliere==null || consiglio==null)
				return null;
			return new EleggiConsigliereRapido(consigliere, consiglio);
		}
		if (Integer.parseInt(tipoAzione)==4) {
			return new IngaggioAiutante(gioco);
		}
		if (Integer.parseInt(tipoAzione)==8) {
			return new AzioneRapidaNulla(gioco);
		}
		throw new IllegalStateException("l'azione inserita non è corretta");
	}

	/**
	 * sets all the parameters of azioneFactory based on the azioneFactory sent
	 * by the client
	 * 
	 * @param azioneFactoryCompleta
	 *            the azioneFactory sent by the view
	 */
	public boolean completaAzioneFactory(AzioneFactory azioneFactoryCompleta, Giocatore giocatore) {
		if (azioneFactoryCompleta.getCartePolitica() != null) {
			List<CartaPolitica> carteAzione = new ArrayList<>();
			for (CartaPolitica c : azioneFactoryCompleta.getCartePolitica()) {
				if (giocatore.cercaCarta(c) != null) {
					carteAzione.add(giocatore.cercaCarta(c));
				} else {
					return false;
				}
			}
			this.setCartePolitica(carteAzione);
		}

		if (azioneFactoryCompleta.getCitta() != null) {
			Citta cittaAzione = this.getGioco().getTabellone()
					.cercaCitta(azioneFactoryCompleta.getCitta().getNome());
			if (cittaAzione == null)
				return false;
			this.setCitta(cittaAzione);
		}

		if (azioneFactoryCompleta.getConsigliere() != null) {
			Consigliere consigliereAzione = this.getGioco().getTabellone()
					.getConsigliereDaColore(azioneFactoryCompleta.getConsigliere().getColore());
			if (consigliereAzione == null)
				return false;

			this.setConsigliere(consigliereAzione);
		}

		if (azioneFactoryCompleta.getConsiglio() != null) {
			if(azioneFactoryCompleta.isConsiglioRe()){
				this.setConsiglio(gioco.getTabellone().getRe().getConsiglio());
			}
			else{
				Regione regioneAzione = this.getGioco().getTabellone()
						.getRegioneDaNome(azioneFactoryCompleta.getConsiglio().getRegione().getNome());
				if (regioneAzione == null)
					return false;

				this.setConsiglio(regioneAzione.getConsiglio());
			}
		}

		if (azioneFactoryCompleta.getRegione() != null) {
			Regione regioneAzione = this.getGioco().getTabellone()
					.getRegioneDaNome(azioneFactoryCompleta.getRegione().getNome());
			if (regioneAzione == null)
				return false;

			this.setRegione(regioneAzione);
		}
		if (azioneFactoryCompleta.getTesseraCostruzione() != null) {
			if ("3".equals(azioneFactoryCompleta.getTipoAzione())) {
				if (giocatore.cercaTesseraCostruzione(azioneFactoryCompleta.getTesseraCostruzione()) != null)
					this.setTesseraCostruzione(
							giocatore.cercaTesseraCostruzione(azioneFactoryCompleta.getTesseraCostruzione()));
				else
					return false;
			} else if ("0".equals(azioneFactoryCompleta.getTipoAzione())) {
				TesseraCostruzione tesseraTemp = this.getGioco().getTabellone().cercaTesseraCostruzioneInTabellone(azioneFactoryCompleta.getTesseraCostruzione());
				if (tesseraTemp != null)
					this.setTesseraCostruzione(tesseraTemp);
				else
					return false;
			}
		}
		return true;
	}
	
	/**
	 * @return the tipoAzione
	 */
	public String getTipoAzione() {
		return tipoAzione;
	}

	/**
	 * @return the consiglioRe
	 */
	public boolean isConsiglioRe() {
		return consiglioRe;
	}

	/**
	 * @param consiglioRe the consiglioRe to set
	 */
	public void setConsiglioRe(boolean consiglioRe) {
		this.consiglioRe = consiglioRe;
	}

	/**
	 * @param tipoAzione the tipoAzione to set
	 */
	public void setTipoAzione(String tipoAzione) {
		this.tipoAzione = tipoAzione;
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

package server.model;

import java.io.Serializable;

import eccezione.FuoriDalLimiteDelPercorso;

/**
 * @author Riccardo
 *
 */
public abstract class OggettoVendibile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6493300649762552925L;

	private Mercato mercato;
	private int prezzo = 0;
	private Giocatore proprietario;

	/**
	 * does the transaction between two players. It will be implemented in the
	 * subclasses
	 * 
	 * @param nuovoProprietario
	 *            the player who wants to buy the object
	 */
	public abstract void compra(Giocatore nuovoProprietario) throws FuoriDalLimiteDelPercorso;

	public boolean confrontaParametri(OggettoVendibile oggettoDaConfrontare) {
		return proprietario.getNome().equals(oggettoDaConfrontare.getGiocatore().getNome())
				&& prezzo == oggettoDaConfrontare.getPrezzo();
	}

	/**
	 * this method should be called by compra, to reset the parameters of the
	 * purchased item.
	 */
	public void resettaAttributiOggettoVendibile() {
		proprietario = null;
		prezzo = 0;
		mercato = null;
	}

	public void transazioneDenaro(Giocatore nuovoProprietario) throws FuoriDalLimiteDelPercorso {
		mercato.getPercorsoRicchezza().muoviGiocatore(nuovoProprietario, -getPrezzo());
		mercato.getPercorsoRicchezza().muoviGiocatore(getGiocatore(), getPrezzo());
	}

	/**
	 * add an object to the list of oggettiInVendita of the market
	 * 
	 * @param mercato
	 *            the market in which there is the list
	 */
	public void aggiungiOggetto(Mercato mercato) {
		mercato.getOggettiInVendita().add(this);
	}

	/**
	 * return the price
	 * 
	 * @return
	 */
	public int getPrezzo() {
		return prezzo;
	}

	/**
	 * return the owner of the object
	 * 
	 * @return
	 */
	public Giocatore getGiocatore() {
		return proprietario;
	}

	/**
	 * 
	 * @return the market of the game
	 */
	public Mercato getMercato() {
		return mercato;
	}

	/**
	 * 
	 * @param prezzo
	 *            the price of the object you want to sell to set
	 */
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	/**
	 * 
	 * @param giocatore
	 *            the player who wants to sell the object
	 */
	public void setGiocatore(Giocatore giocatore) {
		this.proprietario = giocatore;
	}

	/**
	 * 
	 * @param mercato
	 *            the market of the game
	 */
	public void setMercato(Mercato mercato) {
		this.mercato = mercato;
	}
}

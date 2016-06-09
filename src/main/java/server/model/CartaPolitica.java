package server.model;

import java.io.Serializable;

import eccezione.FuoriDalLimiteDelPercorso;

/**
 * @author Luca
 *
 */
public abstract class CartaPolitica extends OggettoVendibile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2278832953355123144L;

	@Override
	public void compra(Giocatore nuovoProprietario) throws FuoriDalLimiteDelPercorso {
		if (nuovoProprietario == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		transazioneDenaro(nuovoProprietario);
		nuovoProprietario.getCartePolitica().add(this);
		getGiocatore().getCartePolitica().remove(this);
		resettaAttributiOggettoVendibile();
		getMercato().getOggettiInVendita().remove(this);
	}
	/**
	 * checks if the card in input has the same parameter of this card
	 * @param carta the card to compare
	 * @return returns true if the card in input has the same parameters of this card, else return false
	 */
	public abstract boolean isUguale(CartaPolitica carta);
}

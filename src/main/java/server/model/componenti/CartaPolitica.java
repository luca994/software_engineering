package server.model.componenti;

import java.io.Serializable;

import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;

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
		getMercato().getOggettiInVendita().remove(this);
		resettaAttributiOggettoVendibile();
	}
	/**
	 * checks if the card in input has the same parameter of this card
	 * @param carta the card to compare
	 * @return returns true if the card in input has the same parameters of this card, else return false
	 */
	public abstract boolean isUguale(CartaPolitica carta);

}

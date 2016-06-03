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
		resettaAttributiOggettoVendibile(nuovoProprietario);
		getMercato().getOggettiInVendita().remove(this);
	}

}

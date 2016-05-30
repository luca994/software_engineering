package server.model;

/**
 * @author Luca
 *
 */
public abstract class CartaPolitica extends OggettoVendibile {

	@Override
	public void compra(Giocatore nuovoProprietario) {
		if (nuovoProprietario == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		transazioneDenaro(nuovoProprietario);
		nuovoProprietario.getCartePolitica().add(this);
		getGiocatore().getCartePolitica().remove(this);
		resettaAttributiOggettoVendibile(nuovoProprietario);
		getMercato().getOggettiInVendita().remove(this);
	}

}

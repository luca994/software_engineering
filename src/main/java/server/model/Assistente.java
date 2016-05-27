package server.model;

/**
 * @author Luca
 *
 */
public class Assistente extends OggettoVendibile {

	@Override
	public void compra(Giocatore nuovoProprietario) {
		if (nuovoProprietario == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		transazioneDenaro(nuovoProprietario);
		nuovoProprietario.getAssistenti().add(this);
		getGiocatore().getAssistenti().remove(this);
		resettaAttributiOggettoVendibile(nuovoProprietario);
		getMercato().getOggettiInVendita().remove(this);
	}

}

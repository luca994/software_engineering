package server.model;

import java.io.Serializable;

import eccezione.FuoriDalLimiteDelPercorso;

/**
 * @author Luca
 *
 */
public class Assistente extends OggettoVendibile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6011773694746511655L;

	@Override
	public void compra(Giocatore nuovoProprietario) throws FuoriDalLimiteDelPercorso {
		if (nuovoProprietario == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		transazioneDenaro(nuovoProprietario);
		nuovoProprietario.getAssistenti().add(this);
		getGiocatore().getAssistenti().remove(this);
		resettaAttributiOggettoVendibile();
		getMercato().getOggettiInVendita().remove(this);
	}

	public boolean isUguale(Assistente assistente) {
		if (assistente != null)
			return true;
		else
			throw new NullPointerException();
	}

	@Override
	public String toString() {
		if(getPrezzo()==0)
			return "Assistente";
		else
			return "Assistente"+" prezzo: "+getPrezzo()+" proprietario: "+getGiocatore().getNome();
	}

}

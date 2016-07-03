package server.model.componenti;

import java.io.Serializable;

import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;

/**
 * the class that represents the assistants
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
		getMercato().getOggettiInVendita().remove(this);
		resettaAttributiOggettoVendibile();
	}

	/**
	 * compares two assistants and returns true if they are equal
	 * @param assistente
	 * @return true if two assistents are equals
	 */
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

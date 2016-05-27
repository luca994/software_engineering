package server.model;

/**
 * @author Luca
 *
 */
public class Assistente extends OggettoVendibile {
	
	
	@Override
	public void transazione(Giocatore giocatore) {
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			getPercorsoRicchezza().muoviGiocatore(giocatore, -getPrezzo());
			getPercorsoRicchezza().muoviGiocatore(getGiocatore(), getPrezzo());
			giocatore.getAssistenti().add(this);
			getGiocatore().getAssistenti().remove(this);
			getMercato().getOggettiInVendita().remove(this);
	}
	
	

}

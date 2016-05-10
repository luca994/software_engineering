package model;

/**
 * @author Luca
 *
 */
public class Assistente extends OggettoVendibile {

	@Override
	public void transazione(Giocatore giocatore) {
		try{
			getPercorsoRicchezza().muoviGiocatore(giocatore, -getPrezzo());
			getPercorsoRicchezza().muoviGiocatore(getGiocatore(), getPrezzo());
			giocatore.getAssistenti().add(this);
			getGiocatore().getAssistenti().remove(this);
			getMercato().getOggettiInVendita().remove(this);
		}
		catch(IndexOutOfBoundsException e){
			System.err.println(e.getMessage());
		}
	}
	
	

}

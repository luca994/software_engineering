package model;

/**
 * @author Luca
 *
 */
public class CambioTessereCostruzione implements Azione {

	private Regione regione;
	
	/**
	 * Get two new Tessere Costruzione in the visble list of obtaiable Tessere Costruzione
	 * @throws IllegalStateException if giocatore hasn't enough Aiutanti 
	 */
	@Override
	public void eseguiAzione (Giocatore giocatore){
		try{
			if(giocatore.getAssistenti().isEmpty())
				throw new IllegalStateException("Il giocatore non possiede abbastanza aiutanti par eseguire l'azione");
			regione.getTessereCoperte().addAll(regione.getTessereCostruzione());
			regione.getTessereCostruzione().clear();
			regione.getTessereCostruzione().add(regione.getTessereCoperte().get(0));
			regione.getTessereCoperte().remove(0);
			regione.getTessereCostruzione().add(regione.getTessereCoperte().get(0));
			regione.getTessereCoperte().remove(0);
			giocatore.getAssistenti().remove(0);
			giocatore.setAzioneOpzionale(true);
		}
		catch(IllegalStateException e)
		{
			System.err.println(e.getLocalizedMessage());
			
		}
	}
}

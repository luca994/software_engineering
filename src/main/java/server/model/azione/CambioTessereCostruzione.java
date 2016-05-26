package server.model.azione;

import server.model.Giocatore;
import server.model.Regione;

/**
 * @author Luca
 *
 */
public class CambioTessereCostruzione extends Azione {
	
	private Regione regione;
	
	
	/**
	 * @param regione
	 */
	public CambioTessereCostruzione(Regione regione) {
		super(null);
		this.regione = regione;
	}


	/**
	 * Get two new Tessere Costruzione in the visble list of obtaiable Tessere Costruzione
	 * @throws IllegalStateException if giocatore hasn't enough Aiutanti 
	 */
	@Override
	public void eseguiAzione (Giocatore giocatore){
		
			if (giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			if(giocatore.getAssistenti().isEmpty())
				throw new IllegalStateException("Il giocatore non possiede abbastanza aiutanti per eseguire l'azione");
			regione.getTessereCoperte().addAll(regione.getTessereCostruzione());
			regione.getTessereCostruzione().clear();
			regione.getTessereCostruzione().add(regione.getTessereCoperte().get(0));
			regione.getTessereCoperte().remove(0);
			regione.getTessereCostruzione().add(regione.getTessereCoperte().get(0));
			regione.getTessereCoperte().remove(0);
			giocatore.getAssistenti().remove(0);
		//	giocatore.setAzioneOpzionale(true);
		
	}


	@Override
	public boolean verificaInput(Giocatore giocatore) {
		// TODO Auto-generated method stub
		return false;
	}
}

/**
 * 
 */
package server.model.azione;

import server.model.Giocatore;

/**
 * @author Massimiliano Ventura
 *
 */
public class AzioneOpzionaleNulla extends Azione {
	
	public AzioneOpzionaleNulla() {
		super(null);
	}


	@Override
	public void eseguiAzione(Giocatore giocatore) {
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			//giocatore.setAzioneOpzionale(true);	
	}


	@Override
	public boolean verificaInput(Giocatore giocatore) {
		// TODO Auto-generated method stub
		return false;
	}

}

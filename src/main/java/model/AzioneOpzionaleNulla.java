/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class AzioneOpzionaleNulla implements Azione {

	/* (non-Javadoc)
	 * @see model.Azione#eseguiAzione(model.Giocatore)
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) {
		giocatore.setAzioneOpzionale(true);

	}

}

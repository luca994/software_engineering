/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class AzionePrincipaleNulla implements Azione {

	/* (non-Javadoc)
	 * @see model.Azione#eseguiAzione(model.Giocatore)
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) {
		giocatore.setAzionePrincipale(true);
	}

}

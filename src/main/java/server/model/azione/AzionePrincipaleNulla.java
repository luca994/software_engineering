/**
 * 
 */
package server.model.azione;

import server.model.Giocatore;


/**
 * @author Massimiliano Ventura
 *
 */
public class AzionePrincipaleNulla extends Azione {
	
	public AzionePrincipaleNulla() {
		super(null);
	}

	
	@Override
	public void eseguiAzione(Giocatore giocatore) {
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nulla");
		giocatore.getStatoGiocatore().azionePrincipaleEseguita();
		
	}


	@Override
	public boolean verificaInput(Giocatore giocatore) {
		if(giocatore==null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		return true;
	}

}

package model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Luca
 *
 */
public class CartaPolitica extends OggettoVendibile {

	private static final Logger log= Logger.getLogger( CartaPolitica.class.getName() );
	private final String colore;
	
	public CartaPolitica(){
		
		int numero = (int)(Math.random()*89);
		
		if(numero>=0&&numero<=12)
			this.colore="NERO";
		else if(numero>=13&&numero<=25)
			this.colore="BIANCO";
		else if(numero>=26&&numero<=38)
			this.colore="ARANCIONE";
		else if(numero>=39&&numero<=51)
			this.colore="ROSA";
		else if(numero>=52&&numero<=64)
			this.colore="BLU";
		else if(numero>=65&&numero<=77)
			this.colore="VIOLA";
		else
			this.colore="JOLLY";
	}

	/**
	 * @return the colore
	 */
	public String getColore() {
		return colore;
	}

	@Override
	public void transazione(Giocatore giocatore) {
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			getPercorsoRicchezza().muoviGiocatore(giocatore, -getPrezzo());
			getPercorsoRicchezza().muoviGiocatore(getGiocatore(), getPrezzo());
			giocatore.getCartePolitica().add(this);
			getGiocatore().getCartePolitica().remove(this);
			getMercato().getOggettiInVendita().remove(this);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}
	
}

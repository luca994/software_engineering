package model;

/**
 * @author Luca
 *
 */
public class CartaPolitica {
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
	private final String colore;
	/**
	 * @return the colore
	 */
	public String getColore() {
		return colore;
	}
	
}

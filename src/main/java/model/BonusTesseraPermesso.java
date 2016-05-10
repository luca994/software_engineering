/**
 * 
 */
package model;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusTesseraPermesso implements Bonus {

	private TesseraCostruzione tessera;
	
	/**
	 * @return the tessera
	 */
	public TesseraCostruzione getTessera() {
		return tessera;
	}
	
	/**
	 * @param tessera the tessera to set
	 */
	public void setTessera(TesseraCostruzione tessera) {
		this.tessera = tessera;
	}
	
	@Override
	public void azioneBonus(Giocatore giocatore) {
		//this.tessera=tesseraDalGiocatore(giocatore);//metodo del controller che chiama la view	
		giocatore.addTessereValide(tessera);
		tessera.getRegioneDiAppartenenza().nuovaTessera(tessera);
	}

}

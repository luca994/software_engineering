/**
 * 
 */
package server.model.bonus;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.TesseraCostruzione;

/**
 * @author Massimiliano Ventura
 *
 */
public class BonusTesseraPermesso implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2996522240976825764L;
	private TesseraCostruzione tessera;
	private Gioco gioco;
	private boolean tesseraCorretta;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BonusTesseraPermesso";
	}

	public BonusTesseraPermesso(Gioco gioco) {
		this.gioco = gioco;
		tesseraCorretta=false;
	}

	/**
	 * @return the tessera
	 */
	public TesseraCostruzione getTessera() {
		return tessera;
	}

	/**
	 * @param tessera
	 *            the tessera to set
	 */
	public void setTessera(TesseraCostruzione tessera) {
		this.tessera = tessera;
	}
	
	/**
	 * set the boolean tesseraCorretta
	 * @param tesseraCorretta the value of tesseraCorretta to set
	 */
	public void setTesseraCorretta(boolean tesseraCorretta) {
		this.tesseraCorretta = tesseraCorretta;
	}
	
	/**
	 * @return the tesseraCorretta
	 */
	public boolean isTesseraCorretta() {
		return tesseraCorretta;
	}

	/**
	 * @return the gioco
	 */
	public Gioco getGioco() {
		return gioco;
	}

	@Override
	public void azioneBonus(Giocatore giocatore) {
		if(giocatore==null)
			throw new NullPointerException();
		gioco.notificaObservers(gioco.getTabellone(), giocatore);
		while(!tesseraCorretta){
			gioco.notificaObservers(this, giocatore);
		}
		if(tessera!=null){
			giocatore.getTessereValide().add(tessera);
			tessera.getRegioneDiAppartenenza().nuovaTessera(tessera);
			for (Bonus b : tessera.getBonus()) {
				b.azioneBonus(giocatore);
			}
		}
		tessera=null;
		tesseraCorretta=false;
	}

	@Override
	public boolean isUguale(Bonus bonusDaConfrontare) {
		return bonusDaConfrontare instanceof BonusTesseraPermesso
				&& ((BonusTesseraPermesso) bonusDaConfrontare).getTessera().isUguale(tessera);
	}
}

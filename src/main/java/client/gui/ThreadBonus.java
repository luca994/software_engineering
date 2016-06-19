/**
 * 
 */
package client.gui;

import javafx.scene.control.Label;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusTesseraPermesso;

/**
 * @author Massimiliano Ventura
 *
 */
public class ThreadBonus implements Runnable {


	private BonusGettoneCitta bonus;
	private Object oggetto;
	private ViewGUI viewGUI;
	private Label labelAzioneDaFare;
	/**
	 * @param viewGUI
	 * @param oggetto
	 */
	public ThreadBonus(ViewGUI viewGUI, Object oggetto, Label labelAzioneDaFare) {
		this.viewGUI=viewGUI;
		this.oggetto=oggetto;
		this.labelAzioneDaFare=labelAzioneDaFare;
	}

	public void setOggetto(BonusGettoneCitta oggetto){
		this.bonus=oggetto;
	}
	
	@Override
	public void run() {
		viewGUI.disabilitazioneBottoniAzione(true);
		
		if(oggetto instanceof BonusGettoneCitta){
			
			labelAzioneDaFare.setText("Clicca sulla città col bonus che ti interessa(col tuo emporio)");
			((BonusGettoneCitta) oggetto).getCitta().add(viewGUI.impostaCitta());
			
		}
		else if(oggetto instanceof BonusTesseraPermesso){
			labelAzioneDaFare.setText("Clicca sulla tessera permesso del tabellone che vuoi ottenere");
			((BonusTesseraPermesso) oggetto).setTessera(viewGUI.impostaTesseraCostruzioneAcquisto());
		}
		else {
			labelAzioneDaFare.setText("Clicca su una delle tue tessere permesso di cui vuoi ottenere i bonus");
			((BonusTesseraPermesso) oggetto).setTessera(viewGUI.impostaTesseraRiutilizzoCostruzione());
		}
		viewGUI.getConnessione().inviaOggetto(oggetto);
		viewGUI.disabilitazioneBottoniAzione(false);
	}
}



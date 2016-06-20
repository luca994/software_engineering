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
	}
	
	@Override
	public void run() {
		viewGUI.disabilitazioneBottoniAzione(true);
		
		if(oggetto instanceof BonusGettoneCitta){
			labelAzioneDaFare.setText("Clicca sulla città col bonus che ti interessa(col tuo emporio), poi clicca su conferma azione");	
			viewGUI.disabilitazioneBottoniCitta(false);
		}
		else if(oggetto instanceof BonusTesseraPermesso){
			labelAzioneDaFare.setText("Clicca sulla tessera permesso del tabellone che vuoi ottenere, poi clicca su conferma azione");
			viewGUI.disabilitazioneBottoniTessereCostruzione(false);
		}
		else {
			labelAzioneDaFare.setText("Clicca su una delle tue tessere permesso di cui vuoi ottenere i bonus, poi clicca su conferma azione");
			viewGUI.getTessereUsateListView().setDisable(false);
			viewGUI.getTessereValideListView().setDisable(false);
		}
	}
}



/**
 * 
 */
package client.view.gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.Connessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.model.componenti.OggettoVendibile;

/**
 * @author Massimiliano Ventura
 *
 */
public class ScreenPrezzoController implements Initializable {

	@FXML
	private Button confermaButton;
	@FXML
	private TextField priceTextField;
	
	private OggettoVendibile oggetto;
	
	private Connessione connessione;
	
	@FXML
	public void confermaButtonAction(ActionEvent event){
		Stage stage = (Stage) confermaButton.getScene().getWindow();
		try{
			oggetto.setPrezzo(Integer.parseInt(priceTextField.getText()));
			connessione.inviaOggetto(oggetto);
			stage.close();
		}catch(NumberFormatException e){
			stage.setTitle("Inserisci un numero");
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	/**
	 * @param oggetto the oggetto to set
	 */
	public void setOggetto(OggettoVendibile oggetto) {
		this.oggetto = oggetto;
	}

	/**
	 * @param connessione the connessione to set
	 */
	public void setConnessione(Connessione connessione) {
		this.connessione = connessione;
	}
}

/**
 * 
 */
package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author Massimiliano Ventura
 *
 */
public class MessaggioErroreController implements Initializable {

	String msg;

	@FXML // fx:id="confirmButton"
	private Button confirmButton; // Value injected by FXMLLoader
	@FXML
	private Label messaggioLabel;
	
	@FXML//è l'onAction dell'fxml
	private void handleConfirmButtonAction(ActionEvent event) {
		 Stage stage = (Stage) confirmButton.getScene().getWindow();
		 stage.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		messaggioLabel.setText(msg);
	}
}

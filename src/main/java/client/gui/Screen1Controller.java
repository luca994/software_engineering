/**
 * 
 */
package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * @author Massimiliano Ventura
 *
 */
public class Screen1Controller implements Initializable {

	ObservableList<String> sceltaConnessione = FXCollections.observableArrayList("Socket", "RMI");

	@FXML // fx:id="confirmButton"
	private Button confirmButton; // Value injected by FXMLLoader
	@FXML
	private TextField portaTextField;
	@FXML
	private TextField ipTextField;
	@FXML
	private TextField nomeUtenteTextField;
	@FXML
	private ChoiceBox<String> tipoConnessioneChoiceBox;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tipoConnessioneChoiceBox.setItems(sceltaConnessione);
		tipoConnessioneChoiceBox.setValue("Socket");
		ipTextField.setText("127.0.0.1");
		portaTextField.setText("29999");

		confirmButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("I dati inseriti sono:" + "\n" + "nome: " + nomeUtenteTextField.getText() + "\n"
						+ "ip: " + ipTextField.getText() + "\n" + "tipo connessione: "
						+ tipoConnessioneChoiceBox.getValue() + "\n"+"porta: "+portaTextField.getText());
			}
		});

	}

}

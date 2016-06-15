/**
 * 
 */
package client.gui;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.zip.DataFormatException;

import client.Connessione;
import client.ConnessioneFactory;
import client.View;
import eccezione.NomeGiaScelto;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Massimiliano Ventura
 *
 */
public class Screen1Controller implements Initializable {

	ObservableList<String> sceltaConnessione = FXCollections.observableArrayList("Socket", "RMI");
	ObservableList<String> sceltaMappa = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8");

	private ViewGUI view;

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
	@FXML
	private ChoiceBox<String> sceltaMappaChoiceBox;

	@FXML//è l'onAction dell'fxml
	private void handleConfirmButtonAction(ActionEvent event) {
		if (controlloCompletamentoCampi())
			if (impostaConnessione()) {
				this.view.startClient();
				Stage stage = (Stage) confirmButton.getScene().getWindow();
				stage.close();
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tipoConnessioneChoiceBox.setItems(sceltaConnessione);
		sceltaMappaChoiceBox.setItems(sceltaMappa);
		tipoConnessioneChoiceBox.setValue("Socket");
		sceltaMappaChoiceBox.setValue("8");
		ipTextField.setText("127.0.0.1");
		portaTextField.setText("29999");

	}

	private boolean impostaConnessione() {
		this.view = new ViewGUI();
		int scelta = 1;
		if ("Socket".equals(tipoConnessioneChoiceBox.getValue()))
			scelta = 0;
		ConnessioneFactory connessioneFactory = new ConnessioneFactory(view);
		try {
			view.setConnessione(connessioneFactory.createConnessione(scelta, ipTextField.getText(),
					Integer.parseInt(portaTextField.getText()), nomeUtenteTextField.getText(),
					sceltaMappaChoiceBox.getValue()));

			stampaErrore("vaffanculo\n lol");
			return true;
		} catch (NomeGiaScelto e) {
			stampaErrore("Nome già scelto");
			return false;
		} catch (DataFormatException e) {
			stampaErrore(e.getMessage());
			return false;
		} catch (UnknownHostException e) {
			stampaErrore("Indirizzo ip non corretto o non raggiungibile");
			return false;
		} catch (IOException e) {
			stampaErrore("C'è un problema nella connessione");
			return false;
		} catch (NotBoundException e) {
			stampaErrore("il nome del registro non è corretto");
			return false;
		}
	}

	private boolean controlloCompletamentoCampi() {
		if (nomeUtenteTextField.getText().equals("") || ipTextField.getText().equals("")
				|| portaTextField.getText().equals("")) {
			stampaErrore("Completa tutti i campi");
			return false;
		}
		return true;
	}

	public void stampaErrore(String msg) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScreenMessaggioErrore.fxml"));
		try {
			Stage stage = new Stage();
			stage.setTitle("Errore");
			stage.setScene(new Scene((AnchorPane) loader.load()));
			MessaggioErroreController controller = loader.<MessaggioErroreController> getController();
			controller.setMsg(msg);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

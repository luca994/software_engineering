/**
 * 
 */
package client.gui;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;

import client.ConnessioneFactory;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import server.eccezioni.NomeGiaScelto;

/**
 * @author Massimiliano Ventura
 *
 */
public class Screen1Controller implements Initializable {

	ObservableList<String> sceltaConnessione = FXCollections.observableArrayList("Socket", "RMI");
	ObservableList<String> sceltaMappa = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8");

	private ViewGUI view;
	private Parent rootGUI;

	

	@FXML
	private AnchorPane backgroundPane;
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

	private void azioneConfermaDati() {
		if (controlloCompletamentoCampi())
			if (impostaConnessione()) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						Stage stageGUI = new Stage();
						Scene scene = new Scene(rootGUI);

						scene.getStylesheets().add(getClass().getResource("/GUIfiles/stileGUI.css").toExternalForm());

						stageGUI.setScene(scene);
						stageGUI.setTitle("Council Of Four");
						stageGUI.show();
					}
				});
				Stage stageScreen1 = (Stage) confirmButton.getScene().getWindow();
				stageScreen1.close();
			}
	}

	@FXML // è l'onAction del bottone nell'fxml
	private void handleConfirmButtonAction() {
		azioneConfermaDati();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Setup sfondo
		BackgroundImage myBI = new BackgroundImage(
				new Image(getClass().getClassLoader().getResource("immaginiGUI/Screen1background.jpg").toString(), 500,
						377, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				BackgroundSize.DEFAULT);

		backgroundPane.setBackground(new Background(myBI));

		tipoConnessioneChoiceBox.setItems(sceltaConnessione);
		sceltaMappaChoiceBox.setItems(sceltaMappa);
		tipoConnessioneChoiceBox.setValue("Socket");
		sceltaMappaChoiceBox.setValue("8");
		ipTextField.setText("127.0.0.1");
		portaTextField.setText("29999");
		handlePortaHost();

	}

	private void handlePortaHost() {
		tipoConnessioneChoiceBox.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					if ("rmi".equalsIgnoreCase(newValue)) {
						portaTextField.setText("1099");
					} else if ("socket".equalsIgnoreCase(newValue)) {
						portaTextField.setText("29999");
					}
				});
	}

	private boolean impostaConnessione() {
		int scelta = 1;
		if ("Socket".equals(tipoConnessioneChoiceBox.getValue()))
			scelta = 0;
		ConnessioneFactory connessioneFactory = new ConnessioneFactory(view);
		try {

			view.setConnessione(connessioneFactory.createConnessione(scelta, ipTextField.getText(),
					Integer.parseInt(portaTextField.getText()), nomeUtenteTextField.getText(),
					sceltaMappaChoiceBox.getValue()));

			return true;
		} catch (NomeGiaScelto e) {
			view.stampaMessaggio("Errore", "Nome già scelto");
			return false;
		} catch (DataFormatException e) {
			view.stampaMessaggio("Errore", e.getMessage());
			return false;
		} catch (UnknownHostException e) {
			view.stampaMessaggio("Errore", "Indirizzo ip non corretto o non raggiungibile");
			return false;
		} catch (IOException e) {
			view.stampaMessaggio("Errore", "C'è un problema nella connessione");
			return false;
		} catch (NotBoundException e) {
			view.stampaMessaggio("Errore", "il nome del registro non è corretto");
			return false;
		}
	}

	private boolean controlloCompletamentoCampi() {
		if ("".equals(nomeUtenteTextField.getText()) || "".equals(ipTextField.getText())
				|| "".equals(portaTextField.getText())) {
			view.stampaMessaggio("Errore", "Completa tutti i campi");
			return false;
		}
		return true;
	}

	/**
	 * @param view
	 */
	public void setView(ViewGUI view) {
		this.view = view;
	}
	
	/**
	 * @param rootGUI
	 *            the rootGUI to set
	 */
	public void setRootGUI(Parent rootGUI) {
		this.rootGUI = rootGUI;
	}

}

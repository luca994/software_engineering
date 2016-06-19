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
import eccezione.NomeGiaScelto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;


/**
 * @author Massimiliano Ventura
 *
 */
public class Screen1Controller implements Initializable {

	ObservableList<String> sceltaConnessione = FXCollections.observableArrayList("Socket", "RMI");
	ObservableList<String> sceltaMappa = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8");

	private ViewGUI view;
	private Parent rootGUI;
	/**
	 * @param rootGUI the rootGUI to set
	 */
	public void setRootGUI(Parent rootGUI) {
		this.rootGUI = rootGUI;
	}

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
	
	private void azioneConfermaDati(){
		if (controlloCompletamentoCampi())
			if (impostaConnessione()) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						Stage stageGUI = new Stage();
                        Scene scene = new Scene(rootGUI);
                        stageGUI.setScene(scene);
                        stageGUI.setTitle("Gioco:vediamo se va");
                        stageGUI.show();
					}
                 });
				Stage stageScreen1 = (Stage) confirmButton.getScene().getWindow();
				stageScreen1.close();
			}
	}

	@FXML // è l'onAction del bottone nell'fxml
	private void handleConfirmButtonAction(ActionEvent event) {
		azioneConfermaDati();
	}
	@FXML
	public void buttonPressed(KeyEvent e)
	{
	    if(e.getCode()==KeyCode.ENTER)
	    {
	    	azioneConfermaDati();
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
		//Setup sfondo
		BackgroundImage myBI = new BackgroundImage(
				new Image(getClass().getClassLoader().getResource("immaginiGUI/Screen1background.jpg").toString(), 500,
						377, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				BackgroundSize.DEFAULT);

		backgroundPane.setBackground(new Background(myBI));

		/*
		 * BackgroundFill myBF = new BackgroundFill(Color.BLUEVIOLET, new
		 * CornerRadii(1), new Insets(0.0,0.0,0.0,0.0));// or null for the
		 * padding //then you set to your node or container or layout
		 * backgroundPane.setBackground(new Background(myBF));
		 */
		tipoConnessioneChoiceBox.setItems(sceltaConnessione);
		sceltaMappaChoiceBox.setItems(sceltaMappa);
		tipoConnessioneChoiceBox.setValue("Socket");
		sceltaMappaChoiceBox.setValue("8");
		ipTextField.setText("127.0.0.1");
		portaTextField.setText("29999");

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
			stampaMessaggio("Errore", "Nome già scelto");
			return false;
		} catch (DataFormatException e) {
			stampaMessaggio("Errore", e.getMessage());
			return false;
		} catch (UnknownHostException e) {
			stampaMessaggio("Errore", "Indirizzo ip non corretto o non raggiungibile");
			return false;
		} catch (IOException e) {
			stampaMessaggio("Errore", "C'è un problema nella connessione");
			return false;
		} catch (NotBoundException e) {
			stampaMessaggio("Errore", "il nome del registro non è corretto");
			return false;
		}
	}

	private boolean controlloCompletamentoCampi() {
		if (nomeUtenteTextField.getText().equals("") || ipTextField.getText().equals("")
				|| portaTextField.getText().equals("")) {
			stampaMessaggio("Errore", "Completa tutti i campi");
			return false;
		}
		return true;
	}

	public void stampaMessaggio(String nomeFinestra, String msg) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScreenMessaggioErrore.fxml"));
		try {
			Stage stage = new Stage();
			stage.setTitle(nomeFinestra);
			stage.setScene(new Scene((AnchorPane) loader.load()));
			MessaggioErroreController controller = loader.<MessaggioErroreController> getController();
			controller.setMsg(msg);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param view2
	 */
	public void setView(ViewGUI view) {
		this.view=view;		
	}
	
}

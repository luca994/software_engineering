/**
 * 
 */
package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

		confirmButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				controlloCompletamentoCampi();
					
			}

			
		});

	}
	private void controlloCompletamentoCampi() {
		if(nomeUtenteTextField.getText().equals("")||ipTextField.getText().equals("")||portaTextField.getText().equals("")){
	        try {
	        	FXMLLoader loader= new FXMLLoader(getClass().getResource("ScreenMessaggioErrore.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Errore");
	            stage.setScene(new Scene((AnchorPane)loader.load()));
	            MessaggioErroreController controller = loader.<MessaggioErroreController>getController();
	            controller.setMsg("Completa tutti i campi");
	            
	            stage.show();
	
	            //hide this current window (if this is want you want
	            //((Node)(event.getSource())).getScene().getWindow().hide();
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}

}

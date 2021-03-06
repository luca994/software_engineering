package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class ScreenPartitaTerminataController implements Initializable {
	
	@FXML
	private Label labelText;
	@FXML
	private ImageView imageViewBackground;
	@FXML
	private Label credits;
	
	public void setText(String testo){
		labelText.setText(testo);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageViewBackground.setImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/Screen1background.jpg").toString(), 500, 500, false, false));
		Font font = Font.loadFont(getClass().getClassLoader().getResource("font.ttf").toString(), 56);
		labelText.setFont(font);
		credits.setText("Consiglio\ndei\nQuattro\n\nProgetto di Ingegneria\ndel Software\nCG 8\nAA 2015-2016\nRiccardo Remigio\nLuca Santini\nMassimiliano Giovanni Ventura");
	}
}

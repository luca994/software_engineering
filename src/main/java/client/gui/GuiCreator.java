package client.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GuiCreator extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(GuiCreator.class.getResource("Screen1.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("CdQ: Configurazione");
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(GuiCreator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

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
			ViewGUI view=new ViewGUI();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen1.fxml"));
			primaryStage.setTitle("CdQ: Configurazione");
			primaryStage.setScene(new Scene((AnchorPane) loader.load()));
			Screen1Controller controller = loader.<Screen1Controller> getController();
			controller.setView(view);
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(GuiCreator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

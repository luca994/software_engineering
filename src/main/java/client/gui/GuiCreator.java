package client.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GuiCreator extends Application {

	@Override
	public void start(Stage screen1Stage) {
		try {
			FXMLLoader loaderGUI = new FXMLLoader(getClass().getResource("/GUIfiles/ScreenGUI.fxml"));
		    Parent rootGUI = loaderGUI.load();
			ViewGUI view=loaderGUI.getController();
			view.startClient();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIfiles/Screen1.fxml"));
			screen1Stage.setTitle("CdQ: Configurazione");
			screen1Stage.setScene(new Scene((AnchorPane) loader.load()));
			Screen1Controller controller = loader.<Screen1Controller> getController();
			controller.setView(view);
			controller.setRootGUI(rootGUI);
			screen1Stage.show();
		} catch (Exception ex) {
			Logger.getLogger(GuiCreator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

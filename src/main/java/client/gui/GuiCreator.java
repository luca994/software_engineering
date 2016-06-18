package client.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiCreator extends Application {

	@Override
	public void start(Stage screen1Stage) {
		try {
			ViewGUI view=new ViewGUI();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen1.fxml"));
			screen1Stage.setTitle("CdQ: Configurazione");
			screen1Stage.setScene(new Scene((AnchorPane) loader.load()));
			Screen1Controller controller = loader.<Screen1Controller> getController();
			controller.setView(view);
			screen1Stage.show();
			screen1Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		        	  if(controller.isBuonFine())
		        	  {
		        		  try{
		        				FXMLLoader loader = new FXMLLoader(getClass().getResource("ScreenGUI.fxml"));
		        				Parent root = loader.load();
		        				Stage stage = new Stage();
		        				stage.setTitle("Gioco vediamo se va");
		        				stage.setScene(new Scene(root));
		        				stage.show();
		        			} catch (IOException e) {
		        				e.printStackTrace();
		        			}
		        	  }		        	  }
		          });
		} catch (Exception ex) {
			Logger.getLogger(GuiCreator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

package client.view;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

import client.cli.ViewCLI;
import client.gui.GuiCreator;
import javafx.application.Application;

/**
 * 
 * the factory of the views
 *
 */
public class ViewFactory {

	private static final int VIEW_CLI = 1;
	private static final int VIEW_GUI = 2;

	private Set<String> listaView;

	/**
	 * builds the object ViewFactory
	 */
	public ViewFactory() {
		listaView = new HashSet<>();
		listaView.add("CommandLineInterface-CLI");
		listaView.add("GraphicUserInterface-GUI");
	}

	/**
	 * 
	 * @return return listaView
	 */
	public Set<String> getListaView() {
		return listaView;
	}

	/**
	 * creates the view of the player according to the input parameter
	 * 
	 * @param sceltaView
	 *            0 to create CLI_VIEW
	 * @throws InvalidParameterException
	 *             if the input isn't correct
	 */
	public View createView(int sceltaView) throws InvalidParameterException {
		switch (sceltaView) {

		case VIEW_CLI:
			return new ViewCLI();
		case VIEW_GUI:{
			Application.launch(GuiCreator.class, (java.lang.String[])null);
			System.exit(0);
		}
		default:
			throw new InvalidParameterException("La view selezionata non è valida!");

		}
	}
}

package client;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

import client.CLI.ViewCLI;

public class ViewFactory {

	private static final int VIEW_CLI = 0;
	
	private Set<String> listaView;
	
	public ViewFactory(){
		listaView = new HashSet<>();
		listaView.add("VIEW_CLI");
	}
	
	public Set<String> getListaView(){
		return listaView;
	}

	
	/**
	 * creates the view of the player according to the input parameter
	 * @param sceltaView 0 to create CLI_VIEW
	 */
	public View createView (int sceltaView){
		switch (sceltaView){
		
		case VIEW_CLI : return new ViewCLI();
		
		default:	throw new InvalidParameterException("La view selezionata non è valida!");
		
		}
		
	}
}

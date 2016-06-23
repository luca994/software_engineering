package client;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;

import client.cli.InputOutput;
import client.view.View;
import client.view.ViewFactory;

/**
 * this class handles the choice of view and the choice of the type of
 * connection for the current user
 * 
 * @author Luca
 */
public class GestoreClient {

	private View view;

	/**
	 * constructor for GestoreClient
	 */
	public GestoreClient() {
		scegliView();
	}

	/**
	 * this method asks the user who wants to view display. the user must enter
	 * the entire corresponding to his choice . It is then created and returned
	 * to the view chosen .
	 * 
	 * @return the view selected
	 * @throws InputMismatchException
	 *             if the input value is not an integer, the method terminates
	 *             with an InputMismatchException
	 */
	public void scegliView() {
		try {
			int i=1;
			ViewFactory viewFactory = new ViewFactory();
			InputOutput.stampa("Scegli la view che vuoi utilizzare: ");
			for(String s:viewFactory.getListaView()){
				InputOutput.stampa(i+") "+s);
				i++;
			}
			view = viewFactory.createView(InputOutput.leggiIntero(false));
		} catch (InvalidParameterException e) {
			InputOutput.stampa(e.getMessage());
			scegliView();
		} catch (InputMismatchException e) {
			InputOutput.stampa("Devi inserire un numero");
			scegliView();
		}
	}

	/**
	 * 
	 * @return the view
	 */
	public View getView() {
		return view;
	}

}

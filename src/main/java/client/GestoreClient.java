package client;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

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
			ViewFactory viewFactory = new ViewFactory();
			Scanner scanner = new Scanner(System.in);

			System.out.println("Scegli la view che vuoi utilizzare: " + viewFactory.getListaView());
			view = viewFactory.createView(scanner.nextInt());
		} catch (InvalidParameterException e) {
			System.out.println(e.getMessage());
			scegliView();
		} catch (InputMismatchException e) {
			System.out.println("Devi inserire un numero");
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

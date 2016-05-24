package client;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * this class handles the choice of view and the choice of the type of connection for the current user
 * @author Luca
 */
public class GestoreClient {
	
	private final View view;
	
	/**
	 * constructor for GestoreClient
	 */
	public GestoreClient(){
		view = scegliView();
	}
	
	/**
	 * this method asks the user who wants to view display.
	 * the user must enter the entire corresponding to his choice . 
	 * It is then created and returned to the view chosen .
	 * @return the view selected
	 * @throws InputMismatchException if the input value is not an integer, 
	 * 		   the method terminates with an InputMismatchException
	 */
	public View scegliView(){
		
		View viewTemp=null;
		ViewFactory viewFactory = new ViewFactory();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Scegli la view che vuoi utilizzare: "+ viewFactory.getListaView());	
		viewTemp = viewFactory.createView(scanner.nextInt());
		scanner.close();
		return viewTemp;
	}
	
	
}

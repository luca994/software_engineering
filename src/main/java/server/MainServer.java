package server;

import java.util.InputMismatchException;
import java.util.Scanner;

import server.config.Configurazione;

/**
 * the class  MainServer, manages the server
 */
public class MainServer {

	/** private constructor for class main server, to hide the public constructor */
	private MainServer() {
	}

	public static void main(String[] args) {

		Scanner sIn = new Scanner(System.in);
		System.out.println("Inserisci il tempo (in secondi) di durata massima di un turno (dopo il quale il giocatore viene sospeso): ");
		try{
		Configurazione.setMaxTimeForTurn(sIn.nextInt()*1000);
		}
		catch(InputMismatchException e){
			main(null);
		}
		sIn.close();
		GestorePartite.getGestorePartite().creaGiochi();
	}

}

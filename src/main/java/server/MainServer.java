package server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;

public class MainServer {

	public static void main(String[] args){
		try {
			GestisciGioco gestisciGioco = new GestisciGioco();
			gestisciGioco.startRMI();
			gestisciGioco.creaGiochi();
		} catch (IOException | AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}

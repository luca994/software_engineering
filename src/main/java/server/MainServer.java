package server;

import java.io.IOException;

public class MainServer {

	public static void main(String[] args){
		try {
			GestisciGioco gestisciGioco = new GestisciGioco();
			gestisciGioco.creaGiochi();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

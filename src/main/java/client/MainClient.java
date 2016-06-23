package client;

import client.cli.InputOutput;
import client.view.View;

public class MainClient {

	/**
	 * private constructor for MainClient, it can not be instantiated.
	 */
	private MainClient() {}

	
	public static void main(String[] args) {
		GestoreClient gestoreClient = new GestoreClient();
		if (gestoreClient.getView() != null) {
			View view = gestoreClient.getView();
			view.startClient();
		} else {
			InputOutput.stampa("Errore nella creazione della view");
		}
	}
}

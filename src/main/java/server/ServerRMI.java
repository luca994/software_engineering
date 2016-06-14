package server;

import client.ConnessioneRMIInterface;
import server.model.Giocatore;
import server.view.ServerRMIViewInterface;

public class ServerRMI implements ServerRMIInterface {

	private GestorePartite gestorePartite;
	
	public ServerRMI(GestorePartite gestorePartite) {
		this.gestorePartite = gestorePartite;
	}
	
	/**
	 * adds a player to the game
	 */
	@Override
	public ServerRMIViewInterface register(String nome, String mappa, ConnessioneRMIInterface client) {
		return gestorePartite.aggiungiGiocatoreRMI(new Giocatore(nome), mappa, client);
	}
}

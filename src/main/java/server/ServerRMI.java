package server;

import client.ConnessioneRMIInterface;
import server.model.Giocatore;
import server.view.ServerRMIViewInterface;

public class ServerRMI implements ServerRMIInterface {

	private GestisciGioco gestisciGioco;
	
	public ServerRMI(GestisciGioco gestisciGioco) {
		this.gestisciGioco = gestisciGioco;
	}
	
	@Override
	public ServerRMIViewInterface register(String nome, ConnessioneRMIInterface client) {
		return gestisciGioco.aggiungiGiocatoreRMI(new Giocatore(nome), client);
	}
}

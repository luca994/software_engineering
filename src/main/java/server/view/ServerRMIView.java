package server.view;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import client.ConnessioneRMIInterface;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.bonus.Bonus;

public class ServerRMIView extends ServerView implements ServerRMIViewInterface{

	private Giocatore giocatore;
	private ConnessioneRMIInterface client;
	
	public ServerRMIView(Gioco gioco, Giocatore giocatore, ConnessioneRMIInterface client, int port) {
		try{
			UnicastRemoteObject.exportObject(this, port);
			this.giocatore=giocatore;
			this.client=client;
			gioco.registerObserver(this);
		}catch(RemoteException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Object cambiamento) {

	}

	@Override
	public void update(Bonus cambiamento, List<String> input) {

	}

	@Override
	public void update(Object cambiamento, Giocatore attributo) {
		/*if (cambiamento instanceof Bonus && this.giocatore.equals(attributo)) {
			String parametro = client.impostaBonus((Bonus) cambiamento);
			try {
				this.notificaObservers((Bonus) cambiamento, parametro);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (this.giocatore.equals(attributo)) {
			inviaOggetto(cambiamento);
		}*/
	}

}

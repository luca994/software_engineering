package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import server.model.Giocatore;
import server.model.bonus.Bonus;

public interface ConnessioneRMIInterface extends Remote {

	public void impostaGiocatore(Giocatore giocatore) throws RemoteException;
	public void impostaBonus(Bonus bonus) throws RemoteException;
}

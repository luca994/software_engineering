package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ConnessioneRMIInterface;
import server.view.ServerRMIViewInterface;

public interface ServerRMIInterface extends Remote {
	public ServerRMIViewInterface register(String nome, String mappa, ConnessioneRMIInterface client) throws RemoteException;
}

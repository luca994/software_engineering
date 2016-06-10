package client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import server.ServerRMIInterface;
import server.model.Giocatore;
import server.model.bonus.Bonus;
import server.view.ServerRMIViewInterface;

public class ConnessioneRMI extends UnicastRemoteObject implements Connessione, ConnessioneRMIInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5928912941132877546L;
	private View view;
	private static final String NAME = "consiglioDeiQuattroRegistro";
	private ServerRMIViewInterface serverView;

	protected ConnessioneRMI(View view, String host, int port, String nome) throws RemoteException, NotBoundException {
		this.view=view;
		Registry registry = LocateRegistry.getRegistry(host, port);
		ServerRMIInterface serverRMI = (ServerRMIInterface) registry.lookup(NAME);
		serverView = serverRMI.register(nome, this);
	}

	@Override
	public void inviaOggetto(Object oggetto) throws IOException {
	}

	@Override
	public void impostaGiocatore(Giocatore giocatore){
		view.riceviOggetto(giocatore);
	}

	@Override
	public void impostaBonus(Bonus bonus) throws RemoteException {
		view.riceviOggetto(bonus);
	}
}

package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnessioneRMIInterface extends Remote {

	public void passaOggetto(Object oggetto) throws RemoteException;
}

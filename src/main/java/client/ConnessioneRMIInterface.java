package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * this interface represents the RMI connection of the client.
 *
 */
@FunctionalInterface
public interface ConnessioneRMIInterface extends Remote {

	/**
	 * sends an object to the view of the player, it is called by a remote server
	 * @param oggetto the object that you wnat to pass to the player's view
	 * @throws RemoteException if there is an error during the execution of the remote method
	 */
	public void passaOggetto(Object oggetto) throws RemoteException;
}

package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.gui.MessaggioChat;
import eccezione.NomeGiaScelto;
import server.ServerRMIInterface;
import server.model.azione.AzioneFactory;
import server.model.bonus.Bonus;
import server.model.componenti.OggettoVendibile;
import server.view.ServerRMIViewInterface;

public class ConnessioneRMI extends UnicastRemoteObject implements Connessione, ConnessioneRMIInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5928912941132877546L;
	private static final Logger LOG = Logger.getLogger(ConnessioneRMI.class.getName());
	private View view;
	private static final String NAME = "consiglioDeiQuattroRegistro";
	private ServerRMIViewInterface serverView;

	/**
	 * builds the RMI connection
	 * 
	 * @param view
	 *            the view to which the connection is related
	 * @param host
	 *            the host of the server
	 * @param port
	 *            the port of the server
	 * @param nome
	 *            the name of the player
	 * @throws RemoteException
	 *             if there is a problem in the connection with the registry
	 * @throws NotBoundException
	 *             if the name of the reference in the RMI registry is not
	 *             currently bound
	 * @throws NomeGiaScelto
	 *             if the name of the player is already taken
	 */
	public ConnessioneRMI(View view, String host, int port, String nome, String mappa)
			throws RemoteException, NotBoundException, NomeGiaScelto {
		this.view = view;
		Registry registry = LocateRegistry.getRegistry(host, port);
		ServerRMIInterface serverRMI = (ServerRMIInterface) registry.lookup(NAME);
		serverView = serverRMI.register(nome, mappa, this);
		if (serverView == null) {
			passaOggetto("il nome è già stato preso");
			throw new NomeGiaScelto("Il nome è già stato scelto");
		}
	}

	/**
	 * sends an object to the server @throws
	 */
	@Override
	public void inviaOggetto(Object oggetto) {
		try {
			if (oggetto instanceof Bonus) {
				serverView.impostaParametriBonus((Bonus) oggetto);
			}
			if (oggetto instanceof AzioneFactory) {
				serverView.riceviAzione((AzioneFactory) oggetto);
			}
			if (oggetto instanceof OggettoVendibile) {
				serverView.riceviOggettoVendibile((OggettoVendibile) oggetto);
			}
			if (oggetto instanceof String) {
				serverView.riceviStringa((String) oggetto);
			}
			if(oggetto instanceof MessaggioChat){
				serverView.riceviMessaggioChat((MessaggioChat) oggetto);
			}
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "Server Disconnesso");
		}
	}

	/**
	 * sends an object to the view
	 */
	@Override
	public void passaOggetto(Object oggetto) throws RemoteException {
		view.riceviOggetto(oggetto);
	}

	/**
	 * 
	 * @return the serverView
	 */
	public ServerRMIViewInterface getServerView() {
		return serverView;
	}
}

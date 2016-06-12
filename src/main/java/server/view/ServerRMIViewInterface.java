package server.view;

import java.rmi.Remote;
import java.rmi.RemoteException;

import server.model.OggettoVendibile;
import server.model.azione.AzioneFactory;
import server.model.bonus.Bonus;

public interface ServerRMIViewInterface extends Remote {

	public void impostaParametriBonus(Bonus bonus) throws RemoteException;

	public void riceviAzione(AzioneFactory azioneFactory) throws RemoteException;

	public void riceviOggettoVendibile(OggettoVendibile oggettoVendibile) throws RemoteException;

	public void riceviStringa(String messaggio) throws RemoteException;
}

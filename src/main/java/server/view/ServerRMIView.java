package server.view;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import client.ConnessioneRMIInterface;
import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.bonus.Bonus;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;

public class ServerRMIView extends ServerView implements ServerRMIViewInterface{

	private Giocatore giocatore;
	private ConnessioneRMIInterface client;
	private AzioneFactory azioneFactory;
	private Bonus bonusDaCompletare;
	private Semaphore semBonus;
	
	public ServerRMIView(Gioco gioco, Giocatore giocatore, ConnessioneRMIInterface client, int port) {
		try{
			UnicastRemoteObject.exportObject(this, port);
			this.giocatore=giocatore;
			this.client=client;
			gioco.registerObserver(this);
			azioneFactory = new AzioneFactory(gioco);
			semBonus = new Semaphore(0);
		}catch(RemoteException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Object cambiamento) {
		try {
			client.passaOggetto(cambiamento);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Bonus cambiamento, List<String> input) {

	}

	@Override
	public void update(Object cambiamento, Giocatore attributo) {
		try{
			if(cambiamento instanceof Bonus && this.giocatore.equals(attributo)){
				bonusDaCompletare = (Bonus) cambiamento;
				client.passaOggetto((Bonus) cambiamento);
				this.notificaObservers(cambiamento, attributo);
				semBonus.acquire();
			}
			else if(this.giocatore.equals(attributo)){
				client.passaOggetto(cambiamento);
			}
		}catch(RemoteException | InterruptedException e){
			e.printStackTrace();
		}
	}

	@Override
	public void impostaParametriBonus(Bonus bonus) throws RemoteException {
		if (bonus instanceof BonusGettoneCitta) {
			List<Citta> tmp = new ArrayList<>(((BonusGettoneCitta) bonus).getCitta());
			if(!tmp.get(0).getNome().equals("passa"))
				((BonusGettoneCitta) bonusDaCompletare).getCitta().add(tmp.get(0));
			semBonus.release();
		}
		if (bonus instanceof BonusTesseraPermesso){
			((BonusTesseraPermesso) bonusDaCompletare).setTessera(((BonusTesseraPermesso) bonus).getTessera());
			semBonus.release();
		}
		if(bonus instanceof BonusRiutilizzoCostruzione){
			((BonusRiutilizzoCostruzione) bonusDaCompletare).setTessera(((BonusRiutilizzoCostruzione) bonus).getTessera());
			semBonus.release();
		}
	}

	@Override
	public void riceviAzione(AzioneFactory azioneFactory) throws RemoteException {
		this.azioneFactory.setTipoAzione(azioneFactory.getTipoAzione());
		if(this.azioneFactory.completaAzioneFactory(azioneFactory, this.azioneFactory, giocatore)){
			Azione azione = this.azioneFactory.createAzione();
			this.azioneFactory = new AzioneFactory(azioneFactory.getGioco());
			this.notificaObservers(azione, giocatore);
		}
		else{
			client.passaOggetto("Parametri dell'azione errati");
		}
	}
}

package server.view;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import client.ConnessioneRMIInterface;
import client.view.MessaggioChat;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.bonus.Bonus;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.componenti.Citta;
import server.model.componenti.OggettoVendibile;

public class ServerRMIView extends ServerView implements ServerRMIViewInterface, Runnable {

	private ConnessioneRMIInterface client;
	private AzioneFactory azioneFactory;
	private Bonus bonusDaCompletare;
	private Semaphore semBonus;

	public ServerRMIView(Gioco gioco, Giocatore giocatore, ConnessioneRMIInterface client, int port) {
		try {
			UnicastRemoteObject.exportObject(this, port);
			setGiocatore(giocatore);
			this.client = client;
			gioco.registerObserver(this);
			azioneFactory = new AzioneFactory(gioco);
			semBonus = new Semaphore(0);
		} catch (RemoteException e) {
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
		try {
			if (cambiamento instanceof Bonus && getGiocatore().equals(attributo)) {
				bonusDaCompletare = (Bonus) cambiamento;
				client.passaOggetto((Bonus) cambiamento);
				this.notificaObservers(cambiamento, attributo);
				semBonus.acquire();
			} else if (getGiocatore().equals(attributo)) {
				client.passaOggetto(cambiamento);
			}
		} catch (RemoteException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void impostaParametriBonus(Bonus bonus) throws RemoteException {
		if (bonus instanceof BonusGettoneCitta) {
			List<Citta> tmp = new ArrayList<>(((BonusGettoneCitta) bonus).getCitta());
			if (!"passa".equals(tmp.get(0).getNome()))
				((BonusGettoneCitta) bonusDaCompletare).setCittaPerCompletamentoBonus(tmp.get(0));
			semBonus.release();
		}
		if (bonus instanceof BonusTesseraPermesso) {
			((BonusTesseraPermesso) bonusDaCompletare).setTessera(((BonusTesseraPermesso) bonus).getTessera());
			semBonus.release();
		}
		if (bonus instanceof BonusRiutilizzoCostruzione) {
			((BonusRiutilizzoCostruzione) bonusDaCompletare)
					.setTessera(((BonusRiutilizzoCostruzione) bonus).getTessera());
			semBonus.release();
		}
	}

	@Override
	public void riceviAzione(AzioneFactory azioneFactory) throws RemoteException {
		this.azioneFactory.setTipoAzione(azioneFactory.getTipoAzione());
		if (this.azioneFactory.completaAzioneFactory(azioneFactory, getGiocatore())) {
			Thread threadAzione = new Thread(this);
			threadAzione.start();
		} else {
			client.passaOggetto("Parametri dell'azione errati");
		}
	}

	@Override
	public void riceviOggettoVendibile(OggettoVendibile oggettoVendibile) throws RemoteException {
		this.notificaObservers(cercaOggettoVendibile(oggettoVendibile), getGiocatore());
	}

	@Override
	public void riceviStringa(String messaggio) throws RemoteException {
		this.notificaObservers(messaggio, getGiocatore());
	}

	/**
	 * runs the actions sent by the client
	 */
	@Override
	public void run() {
		try {
			Azione azione = this.azioneFactory.createAzione();
			if (azione != null) {
				this.azioneFactory = new AzioneFactory(this.azioneFactory.getGioco());
				this.notificaObservers(azione, getGiocatore());
			} else {
				client.passaOggetto("Parametri errati");
				this.azioneFactory = new AzioneFactory(azioneFactory.getGioco());
				client.passaOggetto(this.azioneFactory.getGioco().getTabellone());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.view.ServerRMIViewInterface#riceviMessaggioChat(client.gui.
	 * MessaggioChat)
	 */
	@Override
	public void riceviMessaggioChat(MessaggioChat messaggioChat) throws RemoteException {
		this.notificaObservers(messaggioChat);
	}

}

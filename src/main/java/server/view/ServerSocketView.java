/**
 * 
 */
package server.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server.model.CartaPolitica;
import server.model.Citta;
import server.model.Consigliere;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.Regione;
import server.model.Tabellone;
import server.model.TesseraCostruzione;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.bonus.Bonus;
import server.observer.Observable;
import server.observer.Observer;

/**
 * @author Massimiliano Ventura
 *
 */
public class ServerSocketView extends Observable implements Observer, Runnable{
	
	private Socket socket;
	private Giocatore giocatore;
	private String[] input;
	private AzioneFactory azioneFactory;
	private boolean inputBonus;
	
	/**
	 * builds a server socket view
	 * @param gioco the game to which the view is connected
	 * @param socket the socket to which the player client is connected
	 * @param nuovoGiocatore the player connected to this view
	 */
	public ServerSocketView(Gioco gioco,Socket socket, Giocatore nuovoGiocatore){
		gioco.registerObserver(this);
		this.giocatore=nuovoGiocatore;
		this.socket=socket;
		inputBonus=false;
	}
	
	/**
	 * asks an input to the player
	 * @param oggetto the object you want to send to the player
	 */
	public void inviaOggetto(Object oggetto){
		try {
			ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketOut.writeObject(oggetto);
			socketOut.flush();
		} catch (IOException e) {
			throw new IllegalArgumentException("socket non connesso o errore nella creazione dello stream");
		}
	}
	
	/**
	 * reads the socket and does an action when receives an object
	 */
	@Override
	public void run(){
		while(true)
		{
			try {
				ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
				Object object = socketIn.readObject();
				if(object instanceof Bonus){
					input = (String[])socketIn.readObject();
					inputBonus=true;
				}
				if(object instanceof AzioneFactory){
					String tipoAzione = (String) socketIn.readObject();
					completaAzioneFactory((AzioneFactory) object);
					Azione azioneGiocatore = azioneFactory.createAzione(tipoAzione);
					azioneFactory = new AzioneFactory(azioneFactory.getGioco());
					this.notificaObservers(azioneGiocatore);
				}
			}catch (ClassNotFoundException | IOException e){
				throw new IllegalArgumentException();
			}
		}
	}
	
	/**
	 * sets all the parameters of azioneFactory based on the azioneFactory sent by the client
	 * @param azioneFactoryCompleta the azioneFactory sent by the view
	 */
	public void completaAzioneFactory(AzioneFactory azioneFactoryCompleta){
		if(azioneFactoryCompleta.getCartePolitica()!=null){
			List<CartaPolitica> carteAzione = new ArrayList<>();
			for(CartaPolitica carta: giocatore.getCartePolitica()){
				if(azioneFactoryCompleta.getCartePolitica().contains(carta))
					carteAzione.add(carta);
				else{
					//bisogna notificare l'errore
				}
			}
			azioneFactory.setCartePolitica(carteAzione);
		}
		if(azioneFactoryCompleta.getCitta()!=null){
			Citta cittaAzione = azioneFactory.getGioco().getTabellone().cercaCitta(azioneFactoryCompleta.getCitta().getNome());
			azioneFactory.setCitta(cittaAzione);
		}
		if(azioneFactoryCompleta.getConsigliere()!=null){
			Consigliere consigliereAzione = azioneFactory.getGioco().getTabellone().getConsigliereDaColore(azioneFactoryCompleta.getConsigliere().getColore());
			azioneFactory.setConsigliere(consigliereAzione);
		}
		if(azioneFactoryCompleta.getConsiglio()!=null){
			Regione regioneAzione = azioneFactory.getGioco().getTabellone().getRegioneDaNome(azioneFactoryCompleta.getRegione().getNome());
			azioneFactory.setConsiglio(regioneAzione.getConsiglio());
		}
		if(azioneFactoryCompleta.getRegione()!=null){
			Regione regioneAzione = azioneFactory.getGioco().getTabellone().getRegioneDaNome(azioneFactoryCompleta.getRegione().getNome());
			azioneFactory.setRegione(regioneAzione);
		}
		if(azioneFactoryCompleta.getTesseraCostruzione()!=null){
			TesseraCostruzione tesseraAzione = azioneFactoryCompleta.getTesseraCostruzione();
			if(giocatore.getTessereValide().contains(tesseraAzione)){
				azioneFactory.setTesseraCostruzione(tesseraAzione);
			}
			else{
				//errore da gestire
			}
		}
	}
	
	/**
	 * sends a string to the client
	 * @param cambiamento the message to send
	 */
	/*public void update(String cambiamento){
		try{
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketOut.writeObject(cambiamento);
			socketOut.flush();
		}
		catch(IOException e){
			//da gestire
		}
	}*/
	
	/**
	 * sends a bonus to the client, waits for an attribute, 
	 * then sends the attribute and the bonus to the controller
	 */
	@Override
	public void update(Object cambiamento){
		if(cambiamento instanceof Bonus){
			inviaOggetto(cambiamento);
			while(!inputBonus);
			this.notificaObservers(cambiamento, input);
		}
		if(cambiamento instanceof Gioco){
			azioneFactory = new AzioneFactory((Gioco) cambiamento);
		}
		if(cambiamento instanceof Tabellone || cambiamento instanceof String){
			inviaOggetto(cambiamento);
		}
	}
	
	/**
	 * sends the game board to the client
	 * @param tabellone the game board
	 */
	/*public void update(Tabellone tabellone){
		try{
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketOut.writeObject(tabellone);
			socketOut.flush();
		}
		catch(IOException e){
			throw new IllegalStateException("problemi nel socket");
		}
	}*/
	
	/**
	 * set the game in the action factory of this view
	 * @param gioco the game to set
	 */
	/*public void update(Gioco gioco){
		azioneFactory = new AzioneFactory(gioco);
	}*/
	
	@Override
	public void update(Object cambiamento, String[] input) {
	}
}

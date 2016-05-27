/**
 * 
 */
package server.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.TesseraCostruzione;
import server.model.azione.Azione;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.observer.Observable;
import server.observer.Observer;

/**
 * @author Massimiliano Ventura
 *
 */
public class ServerSocketView extends Observable implements Observer, Runnable{
	
	private Socket socket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	private Giocatore giocatore;
	
	public ServerSocketView(Gioco gioco,Socket socket, Giocatore nuovoGiocatore){
		gioco.registerObserver(this);
		this.giocatore=nuovoGiocatore;
		this.socket=socket;
		try {
			this.socketIn=new ObjectInputStream(socket.getInputStream());
			this.socketOut=new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}
	
	public String ottieniStringa(String messaggioInformativo){
		try {
			socketOut.writeObject(messaggioInformativo);
			socketOut.flush();
			return (String)socketIn.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new IllegalArgumentException();
		}
		
	}
	@Override
	public void run(){
		while(true)
		{
				Object object;
				try {
					object = socketIn.readObject();
				} catch (ClassNotFoundException | IOException e) {
					throw new IllegalArgumentException();
				}
				if(object instanceof Azione)
					this.notificaObservers(object);
		
	}}
	
	@Override
	public <Bonus> void update(Bonus cambiamento){
		if(cambiamento instanceof BonusGettoneCitta){
			String[] nomeCitta = {ottieniStringa("Inserisci il nome di una città dove hai un emporio"
					+ " e di cui vuoi ottenere il bonus, se non hai un'emporio scrivi 'passa'")};
			this.notificaObservers(cambiamento, nomeCitta);
		}
		if(cambiamento instanceof BonusTesseraPermesso){
			String[] tessera = {ottieniStringa("Inserisci il numero della tessera permesso che vuoi ottenere")};
				this.notificaObservers(cambiamento, tessera);
		}
		if(cambiamento instanceof BonusRiutilizzoCostruzione){
				Giocatore gio=((BonusRiutilizzoCostruzione) cambiamento).getGiocatore();
				for(TesseraCostruzione t:gio.getTessereUsate()){
					try {
						socketOut.writeObject(t.toString());
					} catch (IOException e) {
						throw new IllegalArgumentException();
					}	
				String numLista= ottieniStringa("inserisci 0 se la tessera è nella lista delle tessere valide, altrimenti 1. Scrivi 'passa' se non hai tessere");
				String numTessera = ottieniStringa("inserisci il numero della tessera da riciclare");
				String[] array = {numLista,numTessera};
				this.notificaObservers(cambiamento,array);
		}
	}}
	
	@Override
	public void update(Object cambiamento, String[] input) {
	}
}

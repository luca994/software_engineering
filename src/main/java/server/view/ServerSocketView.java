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
import server.model.bonus.BonusGettoneCittà;
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
	
	public ServerSocketView(Gioco gioco,Socket socket, Giocatore nuovoGiocatore) throws IOException{
		gioco.registerObserver(this);
		this.giocatore=nuovoGiocatore;
		this.socket=socket;
		this.socketIn=new ObjectInputStream(socket.getInputStream());
		this.socketOut=new ObjectOutputStream(socket.getOutputStream());
	}
	
	public String ottieniStringa(String messaggioInformativo){
		try {
			socketOut.writeObject(messaggioInformativo);
			socketOut.flush();
			String messaggioInput=(String) 
			socketIn.readObject();
			return messaggioInput;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void run(){
		while(true)
		{
			try {
				Object object = socketIn.readObject();
				if(object instanceof Azione){
					this.notificaObservers(object);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public <Bonus> void update(Bonus cambiamento) throws IOException{
		if(cambiamento instanceof BonusGettoneCittà){
			String[] nomeCittà = {ottieniStringa("Inserisci il nome di una città dove hai un emporio"
					+ " e di cui vuoi ottenere il bonus, se non hai un'emporio scrivi 'passa'")};
			this.notificaObservers(cambiamento, nomeCittà);
		}
		if(cambiamento instanceof BonusTesseraPermesso){
			String[] tessera = {ottieniStringa("Inserisci il numero della tessera permesso che vuoi ottenere")};
			try{
				this.notificaObservers(cambiamento, tessera);
			}
			catch(IllegalArgumentException e){
				socketOut.writeObject(e.getMessage());
				socketOut.flush();
				update(cambiamento);
			}
		}
		if(cambiamento instanceof BonusRiutilizzoCostruzione){
			try{
				Giocatore gio=((BonusRiutilizzoCostruzione) cambiamento).getGiocatore();
				for(TesseraCostruzione t:gio.getTessereUsate()){
					socketOut.writeObject(t.toString());	
				}
				String numLista= ottieniStringa("inserisci 0 se la tessera è nella lista delle tessere valide, altrimenti 1. Scrivi 'passa' se non hai tessere");
				String numTessera = ottieniStringa("inserisci il numero della tessera da riciclare");
				String[] array = {numLista,numTessera};
				this.notificaObservers(cambiamento,array);
			}
			catch(IllegalArgumentException e){
				socketOut.writeObject(e.getMessage());
				socketOut.flush();
				update(cambiamento);
			}
		}
	}
	
	@Override
	public void update(Object cambiamento, String[] input) {
	
	}
}

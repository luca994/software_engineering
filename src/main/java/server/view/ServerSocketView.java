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
import server.model.bonus.Bonus;
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
		try {
			this.socketIn=new ObjectInputStream(socket.getInputStream());
			this.socketOut=new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * asks an input to the player
	 * @param messaggioInformativo the message you want to show to the player
	 * @return the input of the player
	 */
	public String[] ottieniStringa(Object oggetto){
		try {
			socketOut.writeObject(oggetto);
			socketOut.flush();
			return (String[])socketIn.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new IllegalArgumentException();
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
				Object object = socketIn.readObject();
				if(object instanceof Bonus){
					
				}
			}catch (ClassNotFoundException | IOException e){
				throw new IllegalArgumentException();
			}
		}
	}
	
	/**
	 * sends a string to the client
	 * @param cambiamento the message to send
	 */
	public void update(String cambiamento){
		try{
			socketOut.writeObject(cambiamento);
			socketOut.flush();
		}
		catch(IOException e){
			//da gestire
		}
	}
	
	/**
	 * sends a bonus to the client, waits for an attribute, 
	 * then sends the attribute and the bonus to the controller
	 */
	@Override
	public <Bonus> void update(Bonus cambiamento){
		String[] attributo = ottieniStringa(cambiamento);
		this.notificaObservers(cambiamento, attributo);
	}
	
	@Override
	public void update(Object cambiamento, String[] input) {
	}
}

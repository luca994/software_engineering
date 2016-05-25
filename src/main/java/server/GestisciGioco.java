package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdom2.JDOMException;

import client.Login;
import server.controller.Controller;
import server.model.Giocatore;
import server.model.Gioco;
import server.view.ServerSocketView;

public class GestisciGioco implements Runnable{

	private final Server server;
	private List<Socket> giocatoriAttesa;
	private List<Controller> controllersGiochi;
	private long timer;
	
	public GestisciGioco() throws IOException{
		this.server=new Server();
		giocatoriAttesa = Collections.synchronizedList(new ArrayList<>());
		controllersGiochi=new ArrayList<>();
	}
	
	public void creaGiochi() throws IOException, ClassNotFoundException, JDOMException{
		Thread t = new Thread(new GestisciGioco());
		t.start();
		List<Giocatore> giocatori = new ArrayList<>();
		Login login;
		while(true){
			int numGiocatori=0;
			Gioco gioco=new Gioco();
			Controller controller = new Controller(gioco);
			timer=System.currentTimeMillis();
			while(numGiocatori<2 || (numGiocatori>2 && (System.currentTimeMillis()-timer)<20000 )){
				if(!giocatori.isEmpty()){
					Socket socket = giocatoriAttesa.get(0);
					giocatoriAttesa.remove(0);
					ObjectInputStream streamIn=new ObjectInputStream(socket.getInputStream());
					login=(Login) streamIn.readObject();
					Giocatore giocatore = new Giocatore(login.getNome(), login.getColore());
					giocatori.add(giocatore);
					ServerSocketView serverView = new ServerSocketView(gioco, socket, giocatore);
					numGiocatori++;
				}
			}
			controllersGiochi.add(controller);
			gioco.inizializzaPartita(giocatori);
			giocatori.clear();
		}
	}

	@Override
	public void run() {
		try {
			Socket socket;
			while(true){
				socket=server.startSocket();
				timer=System.currentTimeMillis();
				giocatoriAttesa.add(socket);
			}
		} catch (ClassNotFoundException | IOException | JDOMException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}

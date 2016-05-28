package client.CLI;

import java.awt.Color;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.DataFormatException;

import client.ConnessioneFactory;
import client.Login;
import client.View;
import server.model.ParseColor;
import server.model.azione.AzioneFactory;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;

public class ViewCLI extends View implements Runnable{

	private AzioneFactory azione;
	private Login login;
	
	/**
	 * builds a ViewCLI object
	 */
	public ViewCLI(){
		azione = new AzioneFactory(null);
	}
	
	/**
	 * ask a connection type, the host and the port, then creates a connection with the server
	 */
	public void impostaConnessione(){
		ConnessioneFactory connessioneFactory = new ConnessioneFactory(this);
		Scanner scanner = new Scanner(System.in);
		try{
			System.out.println("Inserisci il tipo di connessione"+"\n"+"0) Socket"+"\n"+"1) RMI");
			int scelta = Integer.parseInt(scanner.nextLine());
			System.out.println("Inserisci l'indirizzo dell'host");
			String host = scanner.nextLine();
			System.out.println("Inserisci il numero della porta");
			int port = Integer.parseInt(scanner.nextLine());
			this.setConnessione(connessioneFactory.createConnessione(scelta, host, port));
		}
		catch(DataFormatException e){
			System.out.println(e.getMessage());
			impostaConnessione();
		}
		catch(UnknownHostException e){
			System.out.println("Indirizzo ip non corretto o non raggiungibile");
			impostaConnessione();
		}
		catch(IOException e){
			System.out.println("C'è un problema nella connessione");
			impostaConnessione();
		}
	}
	
	/**
	 * asks the name and the color that the player wants, then starts impostaConnessione
	 */
	public void inizializzazione(){
		try{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Inserisci il nome:");
			String nome = scanner.nextLine();
			System.out.println("Inserisci il colore:"+"\n"+"black"+"\n"+"blue"+"\n"+"cyan"+"\n"+"darkGray"+"\n"+"gray"+"\n"+"green"+"\n"+"lightGray"+"\n"+"magenta"+"\n"+"orange"+"\n"+"pink"+"\n"+"red"+"\n"+"white"+"\n"+"yellow");
			Color colore = ParseColor.colorStringToColor(scanner.nextLine());
			login = new Login(nome, colore);
			impostaConnessione();
			getConnessione().inviaOggetto(login);
		}
		catch(NoSuchFieldException e){
			System.out.println("il colore inserito non è corretto");
			inizializzazione();
		}
		catch(IOException e){
			//da gestire
		}
	}

	/**
	 * starts the client
	 */
	public void startClient(){
		inizializzazione();
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.submit(this.getConnessione());
		executor.submit(this);

	}
	
	/**
	 * takes input string from the user
	 */
	@Override
	public void run() {
		Scanner input = new Scanner(System.in);
		while(true){
			try{	
				String inputString = input.nextLine();
				this.getConnessione().inviaOggetto(inputString);
			}
			catch(IOException e){
				//da gestire
			}
		}
	}
	
	/**
	 * is called by the connection which gives an object
	 * @param oggetto the object passed by the connection
	 */
	public void riceviOggetto(Object oggetto){
		try{	
			if(oggetto instanceof String)
				System.out.println(oggetto);
			if(oggetto instanceof BonusGettoneCitta){
				String[] nomeCitta = {ottieniStringa("Inserisci il nome di una città dove hai un emporio"
													+ " e di cui vuoi ottenere il bonus, se non hai un'emporio scrivi 'passa'")};
				this.getConnessione().inviaOggetto(nomeCitta);
			}
			if(oggetto instanceof BonusTesseraPermesso){
				String[] tessera = {ottieniStringa("Inserisci il numero della tessera permesso che vuoi ottenere")};
				this.getConnessione().inviaOggetto(tessera);
			}
			if(oggetto instanceof BonusRiutilizzoCostruzione){
				String numLista= ottieniStringa("inserisci 0 se la tessera è nella lista delle tessere valide, altrimenti 1. Scrivi 'passa' se non hai tessere");
				String numTessera = ottieniStringa("inserisci il numero della tessera da riciclare");
				String[] array = {numLista,numTessera};
				this.getConnessione().inviaOggetto(array);
			}
		}catch(IOException e){
			//da gestire
		}
	}
	
	/**
	 * asks an input to the player
	 * @param messaggioInformativo the message you want to show to the player
	 * @return the input of the player
	 */
	public String ottieniStringa(String messaggio){
		Scanner scanner = new Scanner(System.in);
		System.out.println(messaggio);
		String ritorno = scanner.nextLine();
		return ritorno;
	}
}

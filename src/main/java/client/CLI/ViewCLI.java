package client.CLI;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.DataFormatException;

import client.ConnessioneFactory;
import client.View;
import server.model.ParseColor;
import server.model.Tabellone;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.azione.EleggiConsigliere;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.stato.giocatore.StatoGiocatore;
import server.model.stato.giocatore.TurnoNormale;

public class ViewCLI extends View implements Runnable{

	private AzioneFactory azioneFactory;
	private StatoGiocatore statoAttuale;
	private String inputString;
	private Tabellone tabellone;
	private boolean inserimentoBonus;
	
	/**
	 * builds a ViewCLI object
	 */
	public ViewCLI(){
		azioneFactory = new AzioneFactory(null);
		inserimentoBonus = false;
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
			impostaConnessione();
			getConnessione().inviaOggetto(nome);
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
			if(statoAttuale instanceof TurnoNormale){
				if(inserimentoBonus){
					inputString = input.nextLine();
					inserimentoBonus=false;
					notify();
				}
				else{
					System.out.println("Inserisci un azione:"+"\n"+"-Azione principale:"+"\n"+"0)Acquista permesso"+"\n"
										+"1)Costruisci emporio con re"+"\n"+"2)Eleggi consigliere"+"\n"
										+"3)Costruisci emporio con tessera costruzione"+"\n"+"-Azioni rapide:"+"\n"
										+"4)Ingaggia aiutante"+"\n"+"5)Cambia tessere costruzione in una regione"+"\n"
										+"6)Eleggi consigliere rapido"+"\n"+"7)Azione principale aggiutniva");
					String scelta = input.nextLine();
					Azione azione = azioneFactory.createAzione(scelta);
					inserimentoParametriAzione(azione);
				}
			}
		}
	}
	
	public void inserimentoParametriAzione(Azione azione){
		Scanner scanner = new Scanner(System.in);
		if(azione instanceof EleggiConsigliere){
			System.out.println("Inserisci il nome della regione che ha il consiglio che vuoi soddisfare o 're' se vuoi soddisfare il consiglio del re");
			String scelta = scanner.nextLine();
			if(scelta.equalsIgnoreCase("re")){
				((EleggiConsigliere) azione).setConsiglio(tabellone.getRe().getConsiglio());
			}
			else{
				if(tabellone.getRegioneDaNome(scelta)!=null)
					((EleggiConsigliere) azione).setConsiglio(tabellone.getRegioneDaNome(scelta).getConsiglio());
				else{
					System.out.println("La regione inserita non esiste");
					inserimentoParametriAzione(azione);
				}
			}
			System.out.println("Inserisci il colore del consigliere da eleggere: magenta, black, white, orange, cyan, pink");
			String colore = scanner.nextLine();
			try{
				if(tabellone.getConsigliereDaColore(ParseColor.colorStringToColor(colore))!=null){
					((EleggiConsigliere) azione).setConsigliere(tabellone.getConsigliereDaColore(ParseColor.colorStringToColor(colore)));
				}
				else{
					System.out.println("Non c'è un consigliere disponibile di quel colore");
					inserimentoParametriAzione(azione);
				}
			}
			catch(NoSuchFieldException e){
				System.out.println("Il colore inserito non è corretto");
				inserimentoParametriAzione(azione);
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
			if(oggetto instanceof StatoGiocatore)
				statoAttuale = (StatoGiocatore) oggetto;
			if(oggetto instanceof Tabellone)
				tabellone = (Tabellone) oggetto;
			if(oggetto instanceof BonusGettoneCitta){
				System.out.println("Inserisci il nome di una città dove hai un emporio"
													+ " e di cui vuoi ottenere il bonus, se non hai un'emporio scrivi 'passa'");
				wait();
				String[] prov = {inputString};
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(prov);
			}
			if(oggetto instanceof BonusTesseraPermesso){
				System.out.println("Inserisci il numero della tessera permesso che vuoi ottenere");
				wait();
				String[] prov = {inputString};
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(prov);
			}
			if(oggetto instanceof BonusRiutilizzoCostruzione){
				System.out.println("inserisci 0 se la tessera è nella lista delle tessere valide, altrimenti 1. Scrivi 'passa' se non hai tessere");
				wait();
				String prov = inputString;
				System.out.println("inserisci il numero della tessera da riciclare");
				wait();
				String[] array = {prov, inputString};
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(array);
			}
		}catch(IOException | InterruptedException e){
			//da gestire
		}
	}
}

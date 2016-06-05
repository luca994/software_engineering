package client.CLI;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.DataFormatException;

import client.ConnessioneFactory;
import client.View;
import server.model.CartaColorata;
import server.model.CartaPolitica;
import server.model.Giocatore;
import server.model.Jolly;
import server.model.ParseColor;
import server.model.Regione;
import server.model.Tabellone;
import server.model.TesseraCostruzione;
import server.model.azione.AcquistaPermesso;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.azione.CambioTessereCostruzione;
import server.model.azione.CostruisciEmporioConRe;
import server.model.azione.CostruisciEmporioConTessera;
import server.model.azione.EleggiConsigliere;
import server.model.azione.EleggiConsigliereRapido;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.StatoGiocatore;
import server.model.stato.giocatore.TurnoNormale;

public class ViewCLI extends View implements Runnable{

	private StatoGiocatore statoAttuale;
	private String inputString;
	private Tabellone tabellone;
	private AtomicBoolean inserimentoBonus;
	private AtomicBoolean inserimentoAzione;
	private Giocatore giocatore;
	
	/**
	 * builds a ViewCLI object
	 */
	public ViewCLI(){
		inserimentoBonus = new AtomicBoolean(false);
		inserimentoAzione = new AtomicBoolean(true);
		statoAttuale = new AttesaTurno(giocatore);
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
			int port = scanner.nextInt();
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
		catch(InputMismatchException e){
			System.out.println("La porta deve essere un numero");
			impostaConnessione();
		}
	}
	
	/**
	 * asks the name that the player wants, then starts impostaConnessione
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
	@Override
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
				if(inserimentoBonus.get()){
					inputString = input.nextLine();
					inserimentoBonus.set(false);
				}
				else if(inserimentoAzione.get()){
					try{
						System.out.println("Inserisci un azione:"+"\n"+"-Azione principale:"+"\n"+"0)Acquista permesso"+"\n"
										+"1)Costruisci emporio con re"+"\n"+"2)Eleggi consigliere"+"\n"
										+"3)Costruisci emporio con tessera costruzione"+"\n"+"-Azioni rapide:"+"\n"
										+"4)Ingaggia aiutante"+"\n"+"5)Cambia tessere costruzione in una regione"+"\n"
										+"6)Eleggi consigliere rapido"+"\n"+"7)Azione principale aggiutniva");
						String scelta = input.nextLine();
						AzioneFactory azioneFactory = new AzioneFactory(null);
						inserimentoParametriAzione(azioneFactory, azioneFactory.createAzione(scelta));
						this.getConnessione().inviaOggetto(azioneFactory);
						this.getConnessione().inviaOggetto(scelta);
						inserimentoAzione.set(false);
					}
					catch(IOException e){
						//da gestire
					}
				}
			}
		}
	}
	
	/**
	 * asks the input of the parameter to the user for create an action
	 * @param azioneFactory the action factory used by the cli to create the action
	 * @param azione the action you want to do
	 */
	private void inserimentoParametriAzione(AzioneFactory azioneFactory, Azione azione){
		if(azione instanceof EleggiConsigliere){
			inserimentoConsiglio(azioneFactory);
			inserimentoConsigliere(azioneFactory);
		}
		if(azione instanceof AcquistaPermesso){
			inserimentoConsiglio(azioneFactory);
			inserimentoCartePolitica(azioneFactory);
			inserimentoTesseraCostruzioneDaAcquistare(azioneFactory);
		}
		if(azione instanceof CambioTessereCostruzione){
			inserimentoRegione(azioneFactory);
		}
		if(azione instanceof CostruisciEmporioConRe){
			inserimentoCartePolitica(azioneFactory);
			inserimentoCitta(azioneFactory);
		}
		if(azione instanceof CostruisciEmporioConTessera){
			inserimentoTesseraCostruzioneDaUtilizzare(azioneFactory);
			inserimentoCitta(azioneFactory);
		}
		if(azione instanceof EleggiConsigliereRapido){
			inserimentoConsiglio(azioneFactory);
			inserimentoConsigliere(azioneFactory);
		}
	}
	
	/**
	 * asks the business permit tile that the user want to use to build, then add it to the action factory
	 * @param azioneFactory the action factory used by the cli to create the action
	 */
	private void inserimentoTesseraCostruzioneDaUtilizzare(AzioneFactory azioneFactory){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci la tessera costruzione che vuoi utilizzare (da 0 a "+giocatore.getTessereValide().size()+"):");
		int numTessera = scanner.nextInt();
		if(0<=numTessera && numTessera<giocatore.getTessereValide().size()){
			azioneFactory.setTesseraCostruzione(giocatore.getTessereValide().get(numTessera));
		}
		else{
			System.out.println("Numero tessera non corretto");
			inserimentoTesseraCostruzioneDaUtilizzare(azioneFactory);
		}
	}
	
	/**
	 * asks the city in which the user wants to move the king or build an emporium, then add it to the action factory
	 * @param azioneFactory the action factory used by the cli to create the action
	 */
	private void inserimentoCitta(AzioneFactory azioneFactory){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci la città di destinazione o dove vuoi costruire un emporio:");
		String cittaDestinazione = scanner.nextLine();
		if(tabellone.cercaCitta(cittaDestinazione)!=null){
			azioneFactory.setCitta(tabellone.cercaCitta(cittaDestinazione));
		}
		else{
			System.out.println("La città inserita non esiste");
			inserimentoCitta(azioneFactory);
		}
	}
	
	/**
	 * asks the region that the user wants to use, then add it to the action factory
	 * @param azioneFactory the action factory used by the cli to create the action
	 */
	private void inserimentoRegione(AzioneFactory azioneFactory){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci il nome della regione in cui vuoi cambiare le tessere:");
		String nomeRegione = scanner.nextLine();
		if(tabellone.getRegioneDaNome(nomeRegione)!=null){
			azioneFactory.setRegione(tabellone.getRegioneDaNome(nomeRegione));
		}
		else{
			System.out.println("Nome regione non corretto");
			inserimentoRegione(azioneFactory);
		}
	}
	
	/**
	 * asks the business permit tile that the user wants to buy, then add it to the action factory
	 * @param azioneFactory the action factory used by the cli to create the action
	 */
	private void inserimentoTesseraCostruzioneDaAcquistare(AzioneFactory azioneFactory){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci la tessera costruzione da acquistare (da 0 a 5)");
		int numTessera = scanner.nextInt();
		if(numTessera<6 && numTessera>=0){
			List<TesseraCostruzione> listaTessere = new ArrayList<TesseraCostruzione>();
			for(Regione r: tabellone.getRegioni()){
				listaTessere.addAll(r.getTessereCostruzione());
			}
			azioneFactory.setTesseraCostruzione(listaTessere.get(numTessera));
		}
		else{
			System.out.println("Numero tessera non corretto");
			inserimentoTesseraCostruzioneDaAcquistare(azioneFactory);
		}
	}
	
	/**
	 * asks the political cards that the user wants to use, then add them to the action factory
	 * @param azioneFactory the action factory used by the cli to create the action
	 */
	private void inserimentoCartePolitica(AzioneFactory azioneFactory){
		Scanner scanner = new Scanner(System.in);
		List<CartaPolitica> carteDaUsare = new ArrayList<>();
		System.out.println("Inserici il colore delle carte politica che vuoi utilizzare: black, white, orange, magenta, pink, cyan o 'jolly'");
		for(int i=0;i<4;i++){
			System.out.println("Inserisci la carta "+i+1+":");
			String colore = scanner.nextLine();
			if(colore.equalsIgnoreCase("jolly")){
				carteDaUsare.add(new Jolly());
			}
			else{
				try{
					carteDaUsare.add(new CartaColorata(ParseColor.colorStringToColor(colore)));
				}
				catch(NoSuchFieldException e){
					System.out.println("Il colore Inserito non è corretto");
					i--;
				}
			}
		}
		azioneFactory.setCartePolitica(carteDaUsare);
	}
	
	/**
	 * asks the color of the councillor that the user wants to elect, then add it to the action factory
	 * @param azioneFactory the action factory used by the cli to create the action
	 */
	private void inserimentoConsigliere(AzioneFactory azioneFactory){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci il colore del consigliere da eleggere: magenta, black, white, orange, cyan, pink");
		String colore = scanner.nextLine();
		try{
			if(tabellone.getConsigliereDaColore(ParseColor.colorStringToColor(colore))!=null){
				azioneFactory.setConsigliere(tabellone.getConsigliereDaColore(ParseColor.colorStringToColor(colore)));
			}
			else{
				System.out.println("Non c'è un consigliere disponibile di quel colore");
				inserimentoConsigliere(azioneFactory);
			}
		}
		catch(NoSuchFieldException e){
			System.out.println("Il colore inserito non è corretto");
			inserimentoConsigliere(azioneFactory);
		}
	}
	
	/**
	 * asks the council that the user wants to use, then add it to the action factory
	 * @param azioneFactory the action factory used by the cli to create the action
	 */
	private void inserimentoConsiglio(AzioneFactory azioneFactory){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci il nome della regione che ha il consiglio che vuoi utilizzare");
		String scelta = scanner.nextLine();
		if(scelta.equalsIgnoreCase("re")){
			azioneFactory.setConsiglio(tabellone.getRe().getConsiglio());
		}
		else{
			if(tabellone.getRegioneDaNome(scelta)!=null)
				azioneFactory.setConsiglio(tabellone.getRegioneDaNome(scelta).getConsiglio());
			else{
				System.out.println("La regione inserita non esiste");
				inserimentoConsiglio(azioneFactory);
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
			if(oggetto instanceof Giocatore)
				this.giocatore=(Giocatore) oggetto;
			if(oggetto instanceof Tabellone){
				tabellone = (Tabellone) oggetto;
				aggiornaGiocatore();
				aggiornaStato();
				inserimentoAzione.set(true);
			}
			if(oggetto instanceof Exception){
				System.out.println(((Exception) oggetto).getMessage());
			}
			if(oggetto instanceof BonusGettoneCitta){
				System.out.println("Inserisci il nome di una città dove hai un emporio"
													+ " e di cui vuoi ottenere il bonus, se non hai un'emporio scrivi 'passa'");
				inserimentoBonus.set(true);
				while(inserimentoBonus.get());
				String[] prov = {inputString};
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(prov);
			}
			if(oggetto instanceof BonusTesseraPermesso){
				System.out.println("Inserisci il numero della tessera permesso che vuoi ottenere");
				inserimentoBonus.set(true);
				while(inserimentoBonus.get());
				String[] prov = {inputString};
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(prov);
			}
			if(oggetto instanceof BonusRiutilizzoCostruzione){
				System.out.println("inserisci 0 se la tessera è nella lista delle tessere valide, altrimenti 1. Scrivi 'passa' se non hai tessere");
				inserimentoBonus.set(true);
				while(inserimentoBonus.get());
				String prov = inputString;
				System.out.println("inserisci il numero della tessera da riciclare");
				inserimentoBonus.set(true);
				while(inserimentoBonus.get());
				String[] array = {prov, inputString};
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(array);
			}
		}catch(IOException e){
			//da gestire
		}
	}
	
	/**
	 * takes the player from the game and puts its in the attribute giocatore
	 */
	public void aggiornaGiocatore(){
		for(Giocatore g: tabellone.getGioco().getGiocatori()){
			if(g.getNome().equals(giocatore.getNome())){
				giocatore=g;
			}
		}
	}
	
	/**
	 * takes the state of the player from the game
	 */
	public void aggiornaStato(){
		for(Giocatore g: tabellone.getGioco().getGiocatori()){
			if(this.giocatore.equals(g))
				this.statoAttuale=g.getStatoGiocatore();
		}
	}
}

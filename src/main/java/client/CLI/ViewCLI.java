package client.CLI;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.DataFormatException;

import client.ConnessioneFactory;
import client.View;
import server.model.CartaColorata;
import server.model.CartaPolitica;
import server.model.Citta;
import server.model.Consigliere;
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
import server.model.bonus.Bonus;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.percorso.Casella;
import server.model.percorso.CasellaConBonus;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.StatoGiocatore;
import server.model.stato.giocatore.TurnoNormale;

public class ViewCLI extends View implements Runnable {

	private StatoGiocatore statoAttuale;
	private String inputString;
	private Tabellone tabelloneClient;
	private AtomicBoolean inserimentoBonus;
	private AtomicBoolean inserimentoAzione;
	private Giocatore giocatore;
	private ExecutorService executor;
	private Semaphore semaforo;
	private int primoGiro;
	
	/**
	 * builds a ViewCLI object
	 */
	public ViewCLI() {
		semaforo = new Semaphore(0);
		inserimentoBonus = new AtomicBoolean(false);
		inserimentoAzione = new AtomicBoolean(true);
		statoAttuale = new AttesaTurno(giocatore);
		executor = Executors.newCachedThreadPool();
		primoGiro=0;
	}

	/**
	 * ask a connection type, the host and the port, then creates a connection
	 * with the server
	 */
	public void impostaConnessione() {
		ConnessioneFactory connessioneFactory = new ConnessioneFactory(this);
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Inserisci il tipo di connessione" + "\n" + "0) Socket" + "\n" + "1) RMI");
			int scelta = 0;//Integer.parseInt(scanner.nextLine());
			System.out.println("Inserisci l'indirizzo dell'host");
			String host = scanner.nextLine();
			if(host.equals(""))
				host = new String("127.0.0.1");
			System.out.println("Inserisci il numero della porta");
			int port = 29999; //scanner.nextInt();
			this.setConnessione(connessioneFactory.createConnessione(scelta, host, port));
		} catch (DataFormatException e) {
			System.out.println(e.getMessage());
			impostaConnessione();
		} catch (UnknownHostException e) {
			System.out.println("Indirizzo ip non corretto o non raggiungibile");
			impostaConnessione();
		} catch (IOException e) {
			System.out.println("C'è un problema nella connessione");
			impostaConnessione();
		} catch (InputMismatchException e) {
			System.out.println("La porta deve essere un numero");
			impostaConnessione();
		}
	}

	/**
	 * asks the name that the player wants, then starts impostaConnessione
	 */
	public void inizializzazione() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Inserisci il nome:");
			String nome = scanner.nextLine();
			impostaConnessione();
			executor.submit(getConnessione());
			getConnessione().inviaOggetto(nome);
		}
		catch(IOException e){
			throw new IllegalStateException();
		}
	}

	/**
	 * starts the client
	 */
	@Override
	public void startClient() {
		inizializzazione();
		executor.submit(this);
	}

	/**
	 * takes input string from the user
	 */
	@Override
	public void run() {
		Scanner input = new Scanner(System.in);
		while (true) {
			try {
				semaforo.acquire();
				primoGiro++;
				
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (statoAttuale instanceof TurnoNormale) {
				primoGiro=2;
				if (inserimentoBonus.get()) {
					inputString = input.nextLine();
					inserimentoBonus.set(false);
				}
				else if(inserimentoAzione.get()){
					try{
						System.out.println("Inserisci un azione:"+"\n"+"-Azione principale:"+"\n"+"0)Acquista permesso"+"\n"
										+"1)Costruisci emporio con re"+"\n"+"2)Eleggi consigliere"+"\n"
										+"3)Costruisci emporio con tessera costruzione"+"\n"+"-Azioni rapide:"+"\n"
										+"4)Ingaggia aiutante"+"\n"+"5)Cambia tessere costruzione in una regione"+"\n"
										+"6)Eleggi consigliere rapido"+"\n"
										+"7)Azione principale aggiuntiva"+"\n"
										+"8)Scegli cosa stampare dello stato attuale del gioco");
						String scelta = input.nextLine();
						if(Integer.parseInt(scelta)<7){
							AzioneFactory azioneFactory = new AzioneFactory(null);
							azioneFactory.setTipoAzione(scelta);
							if(inserimentoParametriAzione(azioneFactory, azioneFactory.createAzione())){
								this.getConnessione().inviaOggetto(azioneFactory);}
							else{
								if(semaforo.availablePermits()==0)
									semaforo.release();
							}
						}
						else if(Integer.parseInt(scelta)==8)
							stampeTabellone(input);
						else
						{
							System.out.println("Input non valido");
							if(semaforo.availablePermits()==0)
								semaforo.release();
						}
					}
					catch(IOException e){
						throw new IllegalStateException(e.getMessage());
					}
				}
			}
			else if(!(statoAttuale instanceof TurnoNormale)&&primoGiro<2)
				primoGiro=2;
			else{
				stampeTabellone(input);
				primoGiro=2;
			}
		}
	}

	/**
	 * asks the input of the parameter to the user for create an action
	 * 
	 * @param azioneFactory
	 *            the action factory used by the cli to create the action
	 * @param azione
	 *            the action you want to do
	 */
	private boolean inserimentoParametriAzione(AzioneFactory azioneFactory, Azione azione) {
		if (azione instanceof EleggiConsigliere) {
			if(!inserimentoConsiglio(azioneFactory)) 
				return false;
			if(!inserimentoConsigliere(azioneFactory)) 
				return false;
		}
		else if (azione instanceof AcquistaPermesso) {
			if(!inserimentoConsiglio(azioneFactory)) 
				return false;
			if(!inserimentoCartePolitica(azioneFactory)) 
				return false;
			if(!inserimentoTesseraCostruzioneDaAcquistare(azioneFactory))
				return false;
		}
		else if (azione instanceof CambioTessereCostruzione) {
			if(!inserimentoRegione(azioneFactory)) 
				return false;
		}
		else if (azione instanceof CostruisciEmporioConRe) {
			if(!inserimentoCartePolitica(azioneFactory)) 
				return false;
			if(!inserimentoCitta(azioneFactory)) 
				return false;
		}
		else if (azione instanceof CostruisciEmporioConTessera) {
			if(!inserimentoTesseraCostruzioneDaUtilizzare(azioneFactory)) 
				return false;
			if(!inserimentoCitta(azioneFactory)) 
				return false;
		}
		else if (azione instanceof EleggiConsigliereRapido) {
			if(!inserimentoConsiglio(azioneFactory)) 
				return false;
			if(!inserimentoConsigliere(azioneFactory))
				return false;
		}
		return true;
	}
	
	private  void stampeTabellone(Scanner input){
		synchronized(this.tabelloneClient){
		System.out.println("Scegli cosa visualizzare:\n"+
							"1) Stato generale del tabellone\n"+
							"2) Stato dei giocatori nei vari percorsi\n"+
							"3) Stato della specifica città\n"+
							"4) Stato dei consigli e carte disponibili\n"+
							"5) Elenco dei consiglieri disponibili ad essere eletti\n"+
							"6) Nomi degli altri giocatori\n"+
							"7) Stato dello specifico giocatore\n"+
							"8) Proprie Carte Politica\n"+
							"9) Proprie Tessere-Permesso Valide\n"+
							"10) Proprie Tessere-Permesso Usate\n"+
							"11) Dettaglio del Percorso Nobiltà (con vari bonus)\n"+
							"12) Aggiorna tabellone"
							);
		int scelta =Integer.parseInt(input.nextLine());
		switch(scelta){
			case 1:
				stampaGeneraleTabellone();
				break;
			case 2:
				stampaGeneralePercorsi();
				break;
			case 3:
				stampaSpecificaCittà(input);
				break;
			case 4:
				stampaStatoConsigli();
				break;
			case 5:
				stampaConsiglieriDisponibili();
				break;
			case 6:
				stampaNomiAvversari();
				break;
			case 7:
				stampaAvversarioDettagliato(input);
				break;
			case 8:
				stampaProprieCartePolitica();
				break;
			case 9:
				stampaProprieTesserePermessoValide();
				break;
			case 10:
				stampaProprieTesserePermessoUsate();
				break;
			case 11:
				stampaPercorsoNobiltaDettagliato();
				break;
			default:
				System.out.println("Aggiornamento...");
						
			}
			
				
		}
		if(semaforo.availablePermits()==0)
			semaforo.release();
	}

	/**
	 * Print the Caselle with relatives bonus of Percorso Nobiltà
	 */
	private void stampaPercorsoNobiltaDettagliato() {
		for(Casella caseNobi: tabelloneClient.getPercorsoNobilta().getCaselle())
			if(caseNobi instanceof CasellaConBonus){
				System.out.print("Casella Numero: "+tabelloneClient.getPercorsoNobilta().getCaselle().indexOf(caseNobi)+"\t");
				for(Bonus bonNobi: ((CasellaConBonus) caseNobi).getBonus())
					System.out.print(bonNobi.toString()+", ");
				System.out.print("\n");
			}
		
	}

	/**
	 * Print the used TesserePermessoDiCostruzione of the player in detail
	 */
	private void stampaProprieTesserePermessoUsate() {
		for(Giocatore gioPol: tabelloneClient.getGioco().getGiocatori())
			if(gioPol.getNome().equals(giocatore.getNome()))
				for(TesseraCostruzione tesseraC:gioPol.getTessereUsate())
					stampaTesseraPermesso(tesseraC);
		
	}

	/**
	 * Print the unsused TesserePermessoDiCostruzione of the player in detail
	 */
	private void stampaProprieTesserePermessoValide() {
		for(Giocatore gioPol: tabelloneClient.getGioco().getGiocatori())
			if(gioPol.getNome().equals(giocatore.getNome()))
				for(TesseraCostruzione tesseraC:gioPol.getTessereValide())
					stampaTesseraPermesso(tesseraC);
		
	}

	/**
	 * Print the CartePolitica currently owned by the player
	 */
	private void stampaProprieCartePolitica() {
		System.out.println("Carte Politica possedute:");
		for(Giocatore gioPol: tabelloneClient.getGioco().getGiocatori())
			if(gioPol.getNome().equals(giocatore.getNome()))
				for(CartaPolitica cPol:gioPol.getCartePolitica())
					if(cPol instanceof CartaColorata)
						System.out.println(((CartaColorata) cPol).getColore().toString());
					else
						System.out.println("Jolly");
		
	}

	/**
	 * Print the state of the player in input
	 * @param input
	 */
	private void stampaAvversarioDettagliato(Scanner input) {
		System.out.println("Inserisci il nome dell'avversario di cui vuoi conoscere i dettagli");
		String nomeAvv=input.nextLine();
		for(Giocatore avvDettaglio:tabelloneClient.getGioco().getGiocatori())
			if(avvDettaglio.getNome().equals(nomeAvv)){
				System.out.println("Punti Vittoria: "+tabelloneClient.getPercorsoVittoria().posizioneAttualeGiocatore(avvDettaglio));
				System.out.println("Punti Nobiltà: "+tabelloneClient.getPercorsoNobilta().posizioneAttualeGiocatore(avvDettaglio));
				System.out.println("Punti Ricchezza: "+tabelloneClient.getPercorsoRicchezza().posizioneAttualeGiocatore(avvDettaglio));
				System.out.println("Colore: "+avvDettaglio.getColore().toString());
				System.out.println("Numero di Assistenti: "+avvDettaglio.getAssistenti().size());
				System.out.println("Numero empori rimasti da costruire per terminare: "+avvDettaglio.getEmporiRimasti());
				System.out.print("Empori costruiti in: ");
				for(Regione regioneE: tabelloneClient.getRegioni())
					for(Citta cittaE: regioneE.getCitta())
						if(cittaE.getEmpori().contains(avvDettaglio))
							System.out.print(cittaE.getNome()+", ");
				System.out.print("\n");
			}
		
	}

	/**
	 * Print the name of all the players
	 */
	private void stampaNomiAvversari() {
		System.out.println("Nomi avversari:");
		for(Giocatore avversario: tabelloneClient.getGioco().getGiocatori())
			System.out.println("- "+avversario.getNome());
		
	}

	/**
	 * Print the available Consiglieri for the election in a Consiglio
	 */
	private void stampaConsiglieriDisponibili() {
		System.out.println("Consiglieri disponibili:");
		for(Consigliere consD: tabelloneClient.getConsiglieriDisponibili())
			System.out.println("- "+consD.getColore().toString());
		
	}

	/**
	 * Print the state of all Consigli and relative Regione
	 */
	private void stampaStatoConsigli() {
		for(Regione regi: tabelloneClient.getRegioni()){
			System.out.println("Regione: "+regi.getNome()+"\n"+"Stato Consiglio:");
			for(Consigliere con:regi.getConsiglio().getConsiglieri())
				System.out.println("- "+con.getColore().toString());
			System.out.println("Tessere disponibi all'acquisto:");
			for(TesseraCostruzione tess: regi.getTessereCostruzione())
				stampaTesseraPermesso(tess);					
		}
		System.out.println("Consiglio del Re: vale per "+tabelloneClient.getRe().getCitta().getNome());
		for(Consigliere reCon:tabelloneClient.getRe().getConsiglio().getConsiglieri())
			System.out.println("- "+reCon.getColore().toString());
		
	}

	/**
	 * Print on console the state of the city asked to input
	 * @param input
	 */
	private void stampaSpecificaCittà(Scanner input) {
		System.out.println("Inserisci il nome della città di cui vuoi conoscere lo stato");
		String nomeCitta=input.nextLine();
		Citta objCitta=tabelloneClient.cercaCitta(nomeCitta);
		if(objCitta==null)
			System.out.println("La città cercata non esiste");
		else{
			System.out.println("Regione: "+objCitta.getRegione().getNome());
			System.out.println("Colore: "+objCitta.getColore().toString());
			if(!objCitta.getEmpori().isEmpty()){
				for(Giocatore gio:objCitta.getEmpori())
					System.out.println("- "+gio.getNome());
			}
		}
		
	}

	/**
	 * Print the position on every Percorso of every Giocatore
	 */
	private void stampaGeneralePercorsi() {
		//Stampo stato percorso vittoria: i giocatori e la loro posizione
		System.out.println("Percorso Vittoria:");
		for(Casella casella:tabelloneClient.getPercorsoVittoria().getCaselle())
			if(!casella.getGiocatori().isEmpty())
				for(Giocatore gioca: casella.getGiocatori())
					System.out.println(" "+gioca.getNome() +": "+tabelloneClient.getPercorsoVittoria().posizioneAttualeGiocatore(gioca));
		//Nobiltà
		System.out.println("Percorso Nobiltà");
		for(Casella casella:tabelloneClient.getPercorsoNobilta().getCaselle())
			if(!casella.getGiocatori().isEmpty())
				for(Giocatore gioca: casella.getGiocatori())
					System.out.println(" "+gioca.getNome() +": "+tabelloneClient.getPercorsoNobilta().posizioneAttualeGiocatore(gioca));
		//Ricchezza
		System.out.println("Percorso Ricchezza:");
		for(Casella casella:tabelloneClient.getPercorsoRicchezza().getCaselle())
			if(!casella.getGiocatori().isEmpty())
				for(Giocatore gioca: casella.getGiocatori())
					System.out.println(" "+gioca.getNome() +": "+tabelloneClient.getPercorsoRicchezza().posizioneAttualeGiocatore(gioca));
		
	}

	/**
	 * prints the general state of tabellone on the console
	 */
	private void stampaGeneraleTabellone() {
		for(Regione regione:tabelloneClient.getRegioni()){
			System.out.println("Regione: "+regione.getNome()+"\n"+"Città:");
			for(Citta citta:regione.getCitta()){
				System.out.print(citta.getNome()+", collegata con: ");
				for(Citta collegamento:citta.getCittaVicina())
					System.out.print(collegamento.getNome()+" ");
				System.out.print("\n");
			}
		
	}
		
	}
	/**
	 * Print the details of tessera
	 * @param tessera
	 */
	private void stampaTesseraPermesso(TesseraCostruzione tessera){
		System.out.print("Città tessera: ");
		for(Citta cit:tessera.getCitta())
			System.out.print(cit.getNome()+", ");
		System.out.print("\n");
		System.out.print("Bonus tessera: ");
		for(Bonus bon: tessera.getBonus())
			System.out.print(bon.toString()+", ");
		System.out.print("\n");
	}
	/**
	 * asks the business permit tile that the user want to use to build, then
	 * add it to the action factory
	 * 
	 * @param azioneFactory
	 *            the action factory used by the cli to create the action
	 */
	private boolean inserimentoTesseraCostruzioneDaUtilizzare(AzioneFactory azioneFactory) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci la tessera costruzione che vuoi utilizzare (da 0 a "
				+ giocatore.getTessereValide().size() + ") oppure inserisci 'annulla' per annullare");
		String numTessera = scanner.nextLine();
		if(numTessera.equalsIgnoreCase("annulla")){
			return false;
		}
		try{
			azioneFactory.setTesseraCostruzione(giocatore.getTessereValide().get(Integer.parseInt(numTessera)));
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Numero tessera non corretto");
			//inserimentoTesseraCostruzioneDaUtilizzare(azioneFactory);
			return false;
		}
		catch(NumberFormatException e){
			System.out.println("L'input deve essere un numero o 'annulla'");
			return false;
		}
		return true;
	}

	/**
	 * asks the city in which the user wants to move the king or build an
	 * emporium, then add it to the action factory
	 * 
	 * @param azioneFactory
	 *            the action factory used by the cli to create the action
	 */
	private boolean inserimentoCitta(AzioneFactory azioneFactory) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci la città di destinazione o dove vuoi costruire un emporio oppure inserisci 'annulla' per annullare");
		String cittaDestinazione = scanner.nextLine();
		if(cittaDestinazione.equalsIgnoreCase("annulla"))
			return false;
		if (tabelloneClient.cercaCitta(cittaDestinazione) != null) {
			azioneFactory.setCitta(tabelloneClient.cercaCitta(cittaDestinazione));
		} else {
			System.out.println("La città inserita non esiste");
			//inserimentoCitta(azioneFactory);
			return false;
		}
		return true;
	}

	/**
	 * asks the region that the user wants to use, then add it to the action
	 * factory
	 * 
	 * @param azioneFactory
	 *            the action factory used by the cli to create the action
	 */
	private boolean inserimentoRegione(AzioneFactory azioneFactory) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci il nome della regione in cui vuoi cambiare le tessere oppure 'annulla' per annullare");
		String nomeRegione = scanner.nextLine();
		if(nomeRegione.equalsIgnoreCase("annulla"))
			return false;
		if (tabelloneClient.getRegioneDaNome(nomeRegione) != null) {
			azioneFactory.setRegione(tabelloneClient.getRegioneDaNome(nomeRegione));
		} else {
			System.out.println("Nome regione non corretto");
			//inserimentoRegione(azioneFactory);
			return false;
		}
		return true;
	}

	/**
	 * asks the business permit tile that the user wants to buy, then add it to
	 * the action factory
	 * 
	 * @param azioneFactory
	 *            the action factory used by the cli to create the action
	 */
	private boolean inserimentoTesseraCostruzioneDaAcquistare(AzioneFactory azioneFactory) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci la tessera costruzione da acquistare (da 0 a 5) oppure 'annulla' per annullare");
		String numTessera = scanner.nextLine();
		try{
			if (Integer.parseInt(numTessera) < 6 && Integer.parseInt(numTessera) >= 0) {
				List<TesseraCostruzione> listaTessere = new ArrayList<TesseraCostruzione>();
				for (Regione r : tabelloneClient.getRegioni()) {
					listaTessere.addAll(r.getTessereCostruzione());
				}
				azioneFactory.setTesseraCostruzione(listaTessere.get(Integer.parseInt(numTessera)));
			} else {
				System.out.println("Numero tessera non corretto");
				//inserimentoTesseraCostruzioneDaAcquistare(azioneFactory);
				return false;
			}
		}
		catch(NumberFormatException e){
			System.out.println("L'input deve essere un numero o annulla");
			return false;
		}
		return true;
	}

	/**
	 * asks the political cards that the user wants to use, then add them to the
	 * action factory
	 * 
	 * @param azioneFactory
	 *            the action factory used by the cli to create the action
	 */
	private boolean inserimentoCartePolitica(AzioneFactory azioneFactory) {
		Scanner scanner = new Scanner(System.in);
		List<CartaPolitica> carteDaUsare = new ArrayList<>();
		System.out.println(
				"Inserici il colore delle carte politica che vuoi utilizzare: black, white, orange, magenta, pink, cyan o 'jolly'."
						+ "Inserisci 'fine' se non vuoi utilizzare altre carte o 'annulla' per annullare");
		for (int i = 0; i < 4; i++) {
			System.out.println("Inserisci la carta " + ((int)(i + 1)) + ":");
			String colore = scanner.nextLine();
			if(colore.equalsIgnoreCase("annulla"))
				return false;
			if (colore.equalsIgnoreCase("jolly")){
				carteDaUsare.add(new Jolly());
			} else if (colore.equals("fine")) {
				break;
			} else {
				try {
					carteDaUsare.add(new CartaColorata(ParseColor.colorStringToColor(colore)));
				} catch (NoSuchFieldException e) {
					System.out.println("Il colore Inserito non è corretto");
					i--;
				}
			}
		}
		List<CartaPolitica> carteGiocatore = new ArrayList<>(giocatore.getCartePolitica());
		boolean noCarte = false;
		for(CartaPolitica c: carteDaUsare){
			noCarte=true;
			for(CartaPolitica cg: carteGiocatore){
				if(c.isUguale(cg)){
					carteGiocatore.remove(cg);
					noCarte=false;
					break;
				}
			}
			if(noCarte==true){
				System.out.println("Non hai una carta di quel colore");
				return false;
			}
		}
		azioneFactory.setCartePolitica(carteDaUsare);
		return true;
	}

	/**
	 * asks the color of the councillor that the user wants to elect, then add
	 * it to the action factory
	 * 
	 * @param azioneFactory
	 *            the action factory used by the cli to create the action
	 */
	private boolean inserimentoConsigliere(AzioneFactory azioneFactory) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci il colore del consigliere da eleggere: magenta, black, white, orange, cyan, pink oppure 'annulla' per annullare");
		String colore = scanner.nextLine();
		if(colore.equalsIgnoreCase("annulla"))
			return false;
		try {
			if (tabelloneClient.getConsigliereDaColore(ParseColor.colorStringToColor(colore)) != null) {
				azioneFactory.setConsigliere(tabelloneClient.getConsigliereDaColore(ParseColor.colorStringToColor(colore)));
			} else {
				System.out.println("Non c'è un consigliere disponibile di quel colore");
				//inserimentoConsigliere(azioneFactory);
				return false;
			}
		} catch (NoSuchFieldException e) {
			System.out.println("Il colore inserito non è corretto");
			//inserimentoConsigliere(azioneFactory);
			return false;
		}
		return true;
	}

	/**
	 * asks the council that the user wants to use, then add it to the action
	 * factory
	 * 
	 * @param azioneFactory
	 *            the action factory used by the cli to create the action
	 */
	private boolean inserimentoConsiglio(AzioneFactory azioneFactory) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci il nome della regione che ha il consiglio che vuoi utilizzare oppure 'annulla' per annullare");
		String scelta = scanner.nextLine();
		if(scelta.equalsIgnoreCase("annulla"))
			return false;
		if (scelta.equalsIgnoreCase("re")) {
			azioneFactory.setConsiglio(tabelloneClient.getRe().getConsiglio());
		} else {
			if (tabelloneClient.getRegioneDaNome(scelta) != null)
				azioneFactory.setConsiglio(tabelloneClient.getRegioneDaNome(scelta).getConsiglio());
			else {
				System.out.println("La regione inserita non esiste");
				//inserimentoConsiglio(azioneFactory);
				return false;
			}
		}
		return true;
	}

	/**
	 * is called by the connection which gives an object
	 * 
	 * @param oggetto
	 *            the object passed by the connection
	 */
	public void riceviOggetto(Object oggetto) {
		try {
			if (oggetto instanceof String)
				System.out.println(oggetto);
			if (oggetto instanceof Giocatore)
				this.giocatore = (Giocatore) oggetto;
			if (oggetto instanceof Tabellone) {
				tabelloneClient = (Tabellone) oggetto;
				aggiornaGiocatore();
				aggiornaStato();
				if(semaforo.availablePermits()==0){
					semaforo.release();
				}
				inserimentoAzione.set(true);
			}
			if (oggetto instanceof Exception) {
				System.out.println(((Exception) oggetto).getMessage());
			}
			if (oggetto instanceof BonusGettoneCitta) {
				System.out.println("Inserisci il nome di una città dove hai un emporio"
						+ " e di cui vuoi ottenere il bonus, se non hai un'emporio scrivi 'passa'");
				inserimentoBonus.set(true);
				while (inserimentoBonus.get());
				String[] prov = { inputString };
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(prov);
			}
			if (oggetto instanceof BonusTesseraPermesso) {
				System.out.println("Inserisci il numero della tessera permesso che vuoi ottenere");
				inserimentoBonus.set(true);
				while (inserimentoBonus.get())
					;
				String[] prov = { inputString };
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(prov);
			}
			if (oggetto instanceof BonusRiutilizzoCostruzione) {
				System.out.println(
						"inserisci 0 se la tessera è nella lista delle tessere valide, altrimenti 1. Scrivi 'passa' se non hai tessere");
				inserimentoBonus.set(true);
				while (inserimentoBonus.get())
					;
				String prov = inputString;
				System.out.println("inserisci il numero della tessera da riciclare");
				inserimentoBonus.set(true);
				while (inserimentoBonus.get())
					;
				String[] array = { prov, inputString };
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(array);
			}
		}catch(IOException e){
			throw new IllegalStateException();
		}
	}

	/**
	 * takes the player from the game and puts it in the attribute giocatore
	 */
	public void aggiornaGiocatore() {
		for (Giocatore g : tabelloneClient.getGioco().getGiocatori()) {
			if (g.getNome().equals(giocatore.getNome())) {
				giocatore = g;
			}
		}
	}

	/**
	 * takes the state of the player from the game
	 */
	public void aggiornaStato() {
		for (Giocatore g : tabelloneClient.getGioco().getGiocatori()) {
			if (this.giocatore.getNome().equals(g.getNome()))
				this.statoAttuale = g.getStatoGiocatore();
		}
	}
}

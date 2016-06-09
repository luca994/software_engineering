package client.CLI;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;
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
		primoGiro = 0;
	}

	/**
	 * ask a connection type, the host and the port, then creates a connection
	 * with the server
	 */
	public void impostaConnessione() {
		ConnessioneFactory connessioneFactory = new ConnessioneFactory(this);
		try {
			InputOutput.stampa("Inserisci il tipo di connessione" + "\n" + "0) Socket" + "\n" + "1) RMI");
			int scelta = 0;// Integer.parseInt(scanner.nextLine());
			InputOutput.stampa("Inserisci l'indirizzo dell'host");
			String host = InputOutput.leggiStringa();
			if (host.equals(""))
				host = new String("127.0.0.1");
			InputOutput.stampa("Inserisci il numero della porta");
			int port = 29999; // scanner.nextInt();
			this.setConnessione(connessioneFactory.createConnessione(scelta, host, port));
		} catch (DataFormatException e) {
			InputOutput.stampa(e.getMessage());
			impostaConnessione();
		} catch (UnknownHostException e) {
			InputOutput.stampa("Indirizzo ip non corretto o non raggiungibile");
			impostaConnessione();
		} catch (IOException e) {
			InputOutput.stampa("C'è un problema nella connessione");
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

			InputOutput.stampa("Inserisci il nome:");
			String nome = InputOutput.leggiStringa();
			impostaConnessione();
			executor.submit(getConnessione());
			getConnessione().inviaOggetto(nome);
		} catch (IOException e) {
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
		while (true) {
			try {
				semaforo.acquire();
				primoGiro++;

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (statoAttuale instanceof TurnoNormale) {
				primoGiro = 2;
				if (inserimentoBonus.get()) {
					inputString = InputOutput.leggiStringa();
					inserimentoBonus.set(false);
				} else if (inserimentoAzione.get()) {
					try {
						InputOutput.stampa("Inserisci un azione:\n");
						InputOutput.stampa("- Azione principale:\n");
						InputOutput.stampa("0) Acquista permesso");
						InputOutput.stampa("1) Costruisci emporio con re");
						InputOutput.stampa("2) Eleggi consigliere");
						InputOutput.stampa("3) Costruisci emporio con tessera costruzione");
						InputOutput.stampa("- Azioni rapide:\n");
						InputOutput.stampa("4) Ingaggia aiutante");
						InputOutput.stampa("5) Cambia tessere costruzione in una regione");
						InputOutput.stampa("6)Eleggi consigliere rapido");
						InputOutput.stampa("7) Azione principale aggiuntiva");
						InputOutput.stampa("- Informazioni:\n");
						InputOutput.stampa("8) Scegli cosa stampare dello stato attuale del gioco\n");
						String scelta = InputOutput.leggiStringa();
						if (Integer.parseInt(scelta) < 7) {
							AzioneFactory azioneFactory = new AzioneFactory(null);
							azioneFactory.setTipoAzione(scelta);
							if (inserimentoParametriAzione(azioneFactory, azioneFactory.createAzione())) {
								this.getConnessione().inviaOggetto(azioneFactory);
							} else {
								if (semaforo.availablePermits() == 0)
									semaforo.release();
							}
						} else if (Integer.parseInt(scelta) == 8)
							stampeTabellone();
						else {
							InputOutput.stampa("Input non valido");
							if (semaforo.availablePermits() == 0)
								semaforo.release();
						}
					} catch (IOException e) {
						throw new IllegalStateException(e.getMessage());
					}
				}
			} else if (!(statoAttuale instanceof TurnoNormale) && primoGiro < 2)
				primoGiro = 2;
			else {
				stampeTabellone();
				primoGiro = 2;
			}
			if (statoAttuale instanceof TurnoMercatoAggiuntaOggetti) {
				InputOutput.stampa("Mercato:");
				InputOutput.stampa("0) Metti in vendita un oggetto");
				InputOutput.stampa("1) Passa il turno");
				String scelta = InputOutput.leggiStringa();
				switch (Integer.parseInt(scelta)) {
				case 0:
					stampaOggettiVendibili();
					break;
				case 1:
					try {
						getConnessione().inviaOggetto("-");
					} catch (IOException e) {
						throw new IllegalStateException(e.getMessage());
					}
					break;
				default:

				}
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
			if (!inserimentoConsiglio(azioneFactory))
				return false;
			if (!inserimentoConsigliere(azioneFactory))
				return false;
		} else if (azione instanceof AcquistaPermesso) {
			if (!inserimentoConsiglio(azioneFactory))
				return false;
			if (!inserimentoCartePolitica(azioneFactory))
				return false;
			if (!inserimentoTesseraCostruzioneDaAcquistare(azioneFactory))
				return false;
		} else if (azione instanceof CambioTessereCostruzione) {
			if (!inserimentoRegione(azioneFactory))
				return false;
		} else if (azione instanceof CostruisciEmporioConRe) {
			if (!inserimentoCartePolitica(azioneFactory))
				return false;
			if (!inserimentoCitta(azioneFactory))
				return false;
		} else if (azione instanceof CostruisciEmporioConTessera) {
			if (!inserimentoTesseraCostruzioneDaUtilizzare(azioneFactory))
				return false;
			if (!inserimentoCitta(azioneFactory))
				return false;
		} else if (azione instanceof EleggiConsigliereRapido) {
			if (!inserimentoConsiglio(azioneFactory))
				return false;
			if (!inserimentoConsigliere(azioneFactory))
				return false;
		}
		return true;
	}

	private void stampaOggettiVendibili() {
	}

	private void stampeTabellone() {
		synchronized (this.tabelloneClient) {
			InputOutput.stampa("Scegli cosa visualizzare:");
			InputOutput.stampa("1) Stato generale del tabellone");
			InputOutput.stampa("2) Stato dei giocatori nei vari percorsi");
			InputOutput.stampa("3) Stato della specifica città");
			InputOutput.stampa("4) Stato dei consigli e carte disponibili");
			InputOutput.stampa("5) Elenco dei consiglieri disponibili ad essere eletti");
			InputOutput.stampa("6) Nomi degli altri giocatori");
			InputOutput.stampa("7) Stato dello specifico giocatore");
			InputOutput.stampa("8) Proprie Carte Politica");
			InputOutput.stampa("9) Proprie Tessere-Permesso Valide");
			InputOutput.stampa("10) Proprie Tessere-Permesso Usate");
			InputOutput.stampa("11) Dettaglio del Percorso Nobiltà (con vari bonus)");
			InputOutput.stampa("12) Aggiorna tabellone");
			int scelta = Integer.parseInt(InputOutput.leggiStringa());
			switch (scelta) {
			case 1:
				stampaGeneraleTabellone();
				break;
			case 2:
				stampaGeneralePercorsi();
				break;
			case 3:
				stampaSpecificaCitta();
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
				stampaAvversarioDettagliato();
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
				InputOutput.stampa("Aggiornamento...");

			}

		}
		if (semaforo.availablePermits() == 0)
			semaforo.release();
	}

	/**
	 * Print the Caselle with relatives bonus of Percorso Nobiltà
	 */
	private void stampaPercorsoNobiltaDettagliato() {
		for (Casella caseNobi : tabelloneClient.getPercorsoNobilta().getCaselle())
			if (caseNobi instanceof CasellaConBonus) {
				InputOutput.stampa("Casella Numero: "
						+ tabelloneClient.getPercorsoNobilta().getCaselle().indexOf(caseNobi) + "\t");
				for (Bonus bonNobi : ((CasellaConBonus) caseNobi).getBonus())
					InputOutput.stampa(bonNobi.toString() + ", ");
				InputOutput.stampa("\n");
			}

	}

	/**
	 * Print the used TesserePermessoDiCostruzione of the player in detail
	 */
	private void stampaProprieTesserePermessoUsate() {
		for (Giocatore gioPol : tabelloneClient.getGioco().getGiocatori())
			if (gioPol.getNome().equals(giocatore.getNome()))
				for (TesseraCostruzione tesseraC : gioPol.getTessereUsate())
					stampaTesseraPermesso(tesseraC);

	}

	/**
	 * Print the unsused TesserePermessoDiCostruzione of the player in detail
	 */
	private void stampaProprieTesserePermessoValide() {
		for (Giocatore gioPol : tabelloneClient.getGioco().getGiocatori())
			if (gioPol.getNome().equals(giocatore.getNome()))
				for (TesseraCostruzione tesseraC : gioPol.getTessereValide())
					stampaTesseraPermesso(tesseraC);

	}

	/**
	 * Print the CartePolitica currently owned by the player
	 */
	private void stampaProprieCartePolitica() {
		InputOutput.stampa("Carte Politica possedute:");
		for (Giocatore gioPol : tabelloneClient.getGioco().getGiocatori())
			if (gioPol.getNome().equals(giocatore.getNome()))
				for (CartaPolitica cPol : gioPol.getCartePolitica())
					if (cPol instanceof CartaColorata)
						InputOutput.stampa(((CartaColorata) cPol).getColore().toString());
					else
						InputOutput.stampa("Jolly");

	}

	/**
	 * Print the state of the player in input
	 * 
	 * @param input
	 */
	private void stampaAvversarioDettagliato() {
		InputOutput.stampa("Inserisci il nome dell'avversario di cui vuoi conoscere i dettagli");
		String nomeAvv = InputOutput.leggiStringa();
		for (Giocatore avvDettaglio : tabelloneClient.getGioco().getGiocatori())
			if (avvDettaglio.getNome().equals(nomeAvv)) {
				InputOutput.stampa("Punti Vittoria: "
						+ tabelloneClient.getPercorsoVittoria().posizioneAttualeGiocatore(avvDettaglio));
				InputOutput.stampa("Punti Nobiltà: "
						+ tabelloneClient.getPercorsoNobilta().posizioneAttualeGiocatore(avvDettaglio));
				InputOutput.stampa("Punti Ricchezza: "
						+ tabelloneClient.getPercorsoRicchezza().posizioneAttualeGiocatore(avvDettaglio));
				InputOutput.stampa("Colore: " + avvDettaglio.getColore().toString());
				InputOutput.stampa("Numero di Assistenti: " + avvDettaglio.getAssistenti().size());
				InputOutput
						.stampa("Numero empori rimasti da costruire per terminare: " + avvDettaglio.getEmporiRimasti());
				InputOutput.stampa("Empori costruiti in: ");
				for (Regione regioneE : tabelloneClient.getRegioni())
					for (Citta cittaE : regioneE.getCitta())
						if (cittaE.getEmpori().contains(avvDettaglio))
							InputOutput.stampa(cittaE.getNome() + ", ");
				InputOutput.stampa("\n");
			}

	}

	/**
	 * Print the name of all the players
	 */
	private void stampaNomiAvversari() {
		InputOutput.stampa("Nomi avversari:");
		for (Giocatore avversario : tabelloneClient.getGioco().getGiocatori())
			InputOutput.stampa("- " + avversario.getNome());

	}

	/**
	 * Print the available Consiglieri for the election in a Consiglio
	 */
	private void stampaConsiglieriDisponibili() {
		InputOutput.stampa("Consiglieri disponibili:");
		for (Consigliere consD : tabelloneClient.getConsiglieriDisponibili())
			InputOutput.stampa("- " + consD.getColore().toString());

	}

	/**
	 * Print the state of all Consigli and relative Regione
	 */
	private void stampaStatoConsigli() {
		for (Regione regi : tabelloneClient.getRegioni()) {
			InputOutput.stampa("Regione: " + regi.getNome() + "\n" + "Stato Consiglio:");
			for (Consigliere con : regi.getConsiglio().getConsiglieri())
				InputOutput.stampa("- " + con.getColore().toString());
			InputOutput.stampa("Tessere disponibi all'acquisto:");
			for (TesseraCostruzione tess : regi.getTessereCostruzione())
				stampaTesseraPermesso(tess);
		}
		InputOutput.stampa("Consiglio del Re: vale per " + tabelloneClient.getRe().getCitta().getNome());
		for (Consigliere reCon : tabelloneClient.getRe().getConsiglio().getConsiglieri())
			InputOutput.stampa("- " + reCon.getColore().toString());

	}

	/**
	 * Print on console the state of the city asked to input
	 * 
	 * @param input
	 */
	private void stampaSpecificaCitta() {
		InputOutput.stampa("Inserisci il nome della città di cui vuoi conoscere lo stato");
		String nomeCitta = InputOutput.leggiStringa();
		Citta objCitta = tabelloneClient.cercaCitta(nomeCitta);
		if (objCitta == null)
			InputOutput.stampa("La città cercata non esiste");
		else {
			InputOutput.stampa("Regione: " + objCitta.getRegione().getNome());
			InputOutput.stampa("Colore: " + objCitta.getColore().toString());
			if (!objCitta.getEmpori().isEmpty()) {
				for (Giocatore gio : objCitta.getEmpori())
					InputOutput.stampa("- " + gio.getNome());
			}
		}

	}

	/**
	 * Print the position on every Percorso of every Giocatore
	 */
	private void stampaGeneralePercorsi() {
		// Stampo stato percorso vittoria: i giocatori e la loro posizione
		InputOutput.stampa("Percorso Vittoria:");
		for (Casella casella : tabelloneClient.getPercorsoVittoria().getCaselle())
			if (!casella.getGiocatori().isEmpty())
				for (Giocatore gioca : casella.getGiocatori())
					InputOutput.stampa(" " + gioca.getNome() + ": "
							+ tabelloneClient.getPercorsoVittoria().posizioneAttualeGiocatore(gioca));
		// Nobiltà
		InputOutput.stampa("Percorso Nobiltà");
		for (Casella casella : tabelloneClient.getPercorsoNobilta().getCaselle())
			if (!casella.getGiocatori().isEmpty())
				for (Giocatore gioca : casella.getGiocatori())
					InputOutput.stampa(" " + gioca.getNome() + ": "
							+ tabelloneClient.getPercorsoNobilta().posizioneAttualeGiocatore(gioca));
		// Ricchezza
		InputOutput.stampa("Percorso Ricchezza:");
		for (Casella casella : tabelloneClient.getPercorsoRicchezza().getCaselle())
			if (!casella.getGiocatori().isEmpty())
				for (Giocatore gioca : casella.getGiocatori())
					InputOutput.stampa(" " + gioca.getNome() + ": "
							+ tabelloneClient.getPercorsoRicchezza().posizioneAttualeGiocatore(gioca));

	}

	/**
	 * prints the general state of tabellone on the console
	 */
	private void stampaGeneraleTabellone() {
		for (Regione regione : tabelloneClient.getRegioni()) {
			InputOutput.stampa("Regione: " + regione.getNome() + "\n" + "Città:");
			for (Citta citta : regione.getCitta()) {
				InputOutput.stampa(citta.getNome() + ", collegata con: ");
				for (Citta collegamento : citta.getCittaVicina())
					InputOutput.stampa(collegamento.getNome() + " ");
				InputOutput.stampa("\n");
			}

		}

	}

	/**
	 * Print the details of tessera
	 * 
	 * @param tessera
	 */
	private void stampaTesseraPermesso(TesseraCostruzione tessera) {
		InputOutput.stampa("Città tessera: ");
		for (Citta cit : tessera.getCitta())
			InputOutput.stampa(cit.getNome() + ", ");
		InputOutput.stampa("\n");
		InputOutput.stampa("Bonus tessera: ");
		for (Bonus bon : tessera.getBonus())
			InputOutput.stampa(bon.toString() + ", ");
		InputOutput.stampa("\n");
	}

	/**
	 * asks the business permit tile that the user want to use to build, then
	 * add it to the action factory
	 * 
	 * @param azioneFactory
	 *            the action factory used by the cli to create the action
	 */
	private boolean inserimentoTesseraCostruzioneDaUtilizzare(AzioneFactory azioneFactory) {
		InputOutput.stampa("Inserisci la tessera costruzione che vuoi utilizzare (da 0 a "
				+ giocatore.getTessereValide().size() + ") oppure inserisci 'annulla' per annullare");
		String numTessera = InputOutput.leggiStringa();
		if ("annulla".equalsIgnoreCase(numTessera)) {
			return false;
		}
		try {
			azioneFactory.setTesseraCostruzione(giocatore.getTessereValide().get(Integer.parseInt(numTessera)));
		} catch (IndexOutOfBoundsException e) {
			InputOutput.stampa("Numero tessera non corretto");
			// inserimentoTesseraCostruzioneDaUtilizzare(azioneFactory);
			return false;
		} catch (NumberFormatException e) {
			InputOutput.stampa("L'input deve essere un numero o 'annulla'");
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
		InputOutput.stampa(
				"Inserisci la città di destinazione o dove vuoi costruire un emporio oppure inserisci 'annulla' per annullare");
		String cittaDestinazione = InputOutput.leggiStringa();
		if (cittaDestinazione.equalsIgnoreCase("annulla"))
			return false;
		if (tabelloneClient.cercaCitta(cittaDestinazione) != null) {
			azioneFactory.setCitta(tabelloneClient.cercaCitta(cittaDestinazione));
		} else {
			InputOutput.stampa("La città inserita non esiste");
			// inserimentoCitta(azioneFactory);
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
		InputOutput.stampa(
				"Inserisci il nome della regione in cui vuoi cambiare le tessere oppure 'annulla' per annullare");
		String nomeRegione = InputOutput.leggiStringa();
		if (nomeRegione.equalsIgnoreCase("annulla"))
			return false;
		if (tabelloneClient.getRegioneDaNome(nomeRegione) != null) {
			azioneFactory.setRegione(tabelloneClient.getRegioneDaNome(nomeRegione));
		} else {
			InputOutput.stampa("Nome regione non corretto");
			// inserimentoRegione(azioneFactory);
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
		InputOutput.stampa("Inserisci la tessera costruzione da acquistare (da 0 a 5) oppure 'annulla' per annullare");
		String numTessera = InputOutput.leggiStringa();
		try {
			if (Integer.parseInt(numTessera) < 6 && Integer.parseInt(numTessera) >= 0) {
				List<TesseraCostruzione> listaTessere = new ArrayList<TesseraCostruzione>();
				for (Regione r : tabelloneClient.getRegioni()) {
					listaTessere.addAll(r.getTessereCostruzione());
				}
				azioneFactory.setTesseraCostruzione(listaTessere.get(Integer.parseInt(numTessera)));
			} else {
				InputOutput.stampa("Numero tessera non corretto");
				// inserimentoTesseraCostruzioneDaAcquistare(azioneFactory);
				return false;
			}
		} catch (NumberFormatException e) {
			InputOutput.stampa("L'input deve essere un numero o annulla");
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
		List<CartaPolitica> carteDaUsare = new ArrayList<>();
		InputOutput
				.stampa("Inserici il colore delle carte politica che vuoi utilizzare: black, white, orange, magenta, pink, cyan o 'jolly'."
						+ "Inserisci 'fine' se non vuoi utilizzare altre carte o 'annulla' per annullare");
		for (int i = 0; i < 4; i++) {
			InputOutput.stampa("Inserisci la carta " + ((int) (i + 1)) + ":");
			String colore = InputOutput.leggiStringa();
			if (colore.equalsIgnoreCase("annulla"))
				return false;
			if (colore.equalsIgnoreCase("jolly")) {
				carteDaUsare.add(new Jolly());
			} else if (colore.equals("fine")) {
				break;
			} else {
				try {
					carteDaUsare.add(new CartaColorata(ParseColor.colorStringToColor(colore)));
				} catch (NoSuchFieldException e) {
					InputOutput.stampa("Il colore Inserito non è corretto");
					i--;
				}
			}
		}
		List<CartaPolitica> carteGiocatore = new ArrayList<>(giocatore.getCartePolitica());
		boolean noCarte = false;
		for (CartaPolitica c : carteDaUsare) {
			noCarte = true;
			for (CartaPolitica cg : carteGiocatore) {
				if (c.isUguale(cg)) {
					carteGiocatore.remove(cg);
					noCarte = false;
					break;
				}
			}
			if (noCarte) {
				InputOutput.stampa("Non hai una carta di quel colore");
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
		InputOutput.stampa(
				"Inserisci il colore del consigliere da eleggere: magenta, black, white, orange, cyan, pink oppure 'annulla' per annullare");
		String colore = InputOutput.leggiStringa();
		if ("annulla".equalsIgnoreCase(colore))
			return false;
		try {
			if (tabelloneClient.getConsigliereDaColore(ParseColor.colorStringToColor(colore)) != null) {
				azioneFactory
						.setConsigliere(tabelloneClient.getConsigliereDaColore(ParseColor.colorStringToColor(colore)));
			} else {
				InputOutput.stampa("Non c'è un consigliere disponibile di quel colore");
				// inserimentoConsigliere(azioneFactory);
				return false;
			}
		} catch (NoSuchFieldException e) {
			InputOutput.stampa("Il colore inserito non è corretto");
			// inserimentoConsigliere(azioneFactory);
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
		InputOutput.stampa(
				"Inserisci il nome della regione che ha il consiglio che vuoi utilizzare oppure 'annulla' per annullare");
		String scelta = InputOutput.leggiStringa();
		if (scelta.equalsIgnoreCase("annulla"))
			return false;
		if (scelta.equalsIgnoreCase("re")) {
			azioneFactory.setConsiglio(tabelloneClient.getRe().getConsiglio());
		} else {
			if (tabelloneClient.getRegioneDaNome(scelta) != null)
				azioneFactory.setConsiglio(tabelloneClient.getRegioneDaNome(scelta).getConsiglio());
			else {
				InputOutput.stampa("La regione inserita non esiste");
				// inserimentoConsiglio(azioneFactory);
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
				InputOutput.stampa((String) oggetto);
			if (oggetto instanceof Giocatore)
				this.giocatore = (Giocatore) oggetto;
			if (oggetto instanceof Tabellone) {
				tabelloneClient = (Tabellone) oggetto;
				aggiornaGiocatore();
				aggiornaStato();
				if (semaforo.availablePermits() == 0) {
					semaforo.release();
				}
				inserimentoAzione.set(true);
			}
			if (oggetto instanceof Exception) {
				InputOutput.stampa(((Exception) oggetto).getMessage());
			}
			if (oggetto instanceof BonusGettoneCitta) {
				InputOutput.stampa("Inserisci il nome di una città dove hai un emporio"
						+ " e di cui vuoi ottenere il bonus, se non hai un'emporio scrivi 'passa'");
				inserimentoBonus.set(true);
				while (inserimentoBonus.get())
					;
				String[] prov = { inputString };
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(prov);
			}
			if (oggetto instanceof BonusTesseraPermesso) {
				InputOutput.stampa("Inserisci il numero della tessera permesso che vuoi ottenere");
				inserimentoBonus.set(true);
				while (inserimentoBonus.get())
					;
				String[] prov = { inputString };
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(prov);
			}
			if (oggetto instanceof BonusRiutilizzoCostruzione) {
				InputOutput.stampa(
						"inserisci 0 se la tessera è nella lista delle tessere valide, altrimenti 1. Scrivi 'passa' se non hai tessere");
				inserimentoBonus.set(true);
				while (inserimentoBonus.get())
					;
				String prov = inputString;
				InputOutput.stampa("inserisci il numero della tessera da riciclare");
				inserimentoBonus.set(true);
				while (inserimentoBonus.get())
					;
				String[] array = { prov, inputString };
				this.getConnessione().inviaOggetto(oggetto);
				this.getConnessione().inviaOggetto(array);
			}
		} catch (IOException e) {
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

package client.cli;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
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
import eccezione.NomeGiaScelto;
import server.model.Giocatore;
import server.model.ParseColor;
import server.model.Tabellone;
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
import server.model.componenti.CartaColorata;
import server.model.componenti.CartaPolitica;
import server.model.componenti.Citta;
import server.model.componenti.Consigliere;
import server.model.componenti.Jolly;
import server.model.componenti.OggettoVendibile;
import server.model.componenti.Regione;
import server.model.componenti.TesseraCostruzione;
import server.model.percorso.Casella;
import server.model.percorso.CasellaConBonus;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.StatoGiocatore;
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;
import server.model.stato.giocatore.TurnoMercatoCompraVendita;
import server.model.stato.giocatore.TurnoNormale;
import server.model.stato.gioco.FaseTurnoMercatoCompraVendita;

public class ViewCLI extends View implements Runnable {

	private StatoGiocatore statoAttuale;
	private String inputString;
	private Tabellone tabelloneClient;
	private AtomicBoolean inserimentoBonus;
	private AtomicBoolean inserimentoAzione;
	private Giocatore giocatore;
	private ExecutorService executor;
	private Semaphore semaforo;
	private Semaphore semBonus;

	/**
	 * builds a ViewCLI object
	 */
	public ViewCLI() {
		semaforo = new Semaphore(0);
		semBonus = new Semaphore(0);
		inserimentoBonus = new AtomicBoolean(false);
		inserimentoAzione = new AtomicBoolean(true);
		statoAttuale = new AttesaTurno(giocatore);
		executor = Executors.newCachedThreadPool();
	}

	/**
	 * ask a connection type, the host and the port, then creates a connection
	 * with the server
	 */
	public void impostaConnessione(String nome, String mappa) {
		ConnessioneFactory connessioneFactory = new ConnessioneFactory(this);
		try {
			InputOutput.stampa("Inserisci il tipo di connessione" + "\n" + "0) Socket" + "\n" + "1) RMI");
			int scelta = InputOutput.leggiIntero(false);
			InputOutput.stampa("Inserisci l'indirizzo dell'host");
			String host = InputOutput.leggiStringa(false);
			if (host.equals(""))
				host = new String("127.0.0.1");
			InputOutput.stampa("Inserisci il numero della porta");
			int port = InputOutput.leggiIntero(false);
			this.setConnessione(connessioneFactory.createConnessione(scelta, host, port, nome, mappa));
		} catch (DataFormatException e) {
			InputOutput.stampa(e.getMessage());
			impostaConnessione(nome, mappa);
		} catch (UnknownHostException e) {
			InputOutput.stampa("Indirizzo ip non corretto o non raggiungibile");
			impostaConnessione(nome, mappa);
		} catch (IOException e) {
			InputOutput.stampa("C'è un problema nella connessione");
			impostaConnessione(nome, mappa);
		} catch (InputMismatchException e) {
			InputOutput.stampa("La porta deve essere un numero");
			impostaConnessione(nome, mappa);
		} catch (NotBoundException e) {
			InputOutput.stampa("il nome del registro non è corretto");
			impostaConnessione(nome, mappa);
		} catch (NomeGiaScelto e) {
			InputOutput.stampa(e.getMessage());
			inizializzazione();
		}
	}

	/**
	 * asks the name that the player wants, then starts impostaConnessione
	 */
	public void inizializzazione() {
		InputOutput.stampa("Inserisci il nome:");
		String nome = InputOutput.leggiStringa(false);
		impostaConnessione(nome, inserisciMappa());
	}

	public String inserisciMappa() {
		InputOutput.stampa("Inserisci un numero da 0 a 7 per la mappa, oppure 8 per casuale");
		String mappa = InputOutput.leggiStringa(false);
		try {
			if (Integer.parseInt(mappa) <= 8 && Integer.parseInt(mappa) >= 0)
				return mappa;
			else {
				InputOutput.stampa("Numero non corretto");
				return inserisciMappa();
			}
		} catch (NumberFormatException e) {
			InputOutput.stampa("L'input deve essere un numero");
			return inserisciMappa();
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
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (statoAttuale instanceof TurnoNormale) {
				if (inserimentoBonus.get()) {
					inputString = InputOutput.leggiStringa(false);
					inserimentoBonus.set(false);
					if (semBonus.availablePermits() == 0)
						semBonus.release();
				}
				if (inserimentoAzione.get()) {
					try {
						if (!faseAzione())
							semaforo.release();
					} catch (IOException e) {
						throw new IllegalStateException(e.getMessage());
					}
				}
			}
			if (statoAttuale instanceof TurnoMercatoAggiuntaOggetti) {
				InputOutput.stampa("Turno mercato:");
				faseAggiuntaOggetti();
			}
			if (statoAttuale instanceof TurnoMercatoCompraVendita) {
				InputOutput.stampa("Turno mercato:");
				faseCompraVendita();
			}
		}
	}

	private boolean faseAzione() throws IOException {
		Integer scelta;
		InputOutput.stampa("Inserisci un azione:\n");
		InputOutput.stampa("- Azioni principali:\n");
		InputOutput.stampa("0) Acquista permesso");
		InputOutput.stampa("1) Costruisci emporio con re");
		InputOutput.stampa("2) Eleggi consigliere");
		InputOutput.stampa("3) Costruisci emporio con tessera costruzione");
		InputOutput.stampa("- Azioni rapide:\n");
		InputOutput.stampa("4) Ingaggia aiutante");
		InputOutput.stampa("5) Cambia tessere costruzione in una regione");
		InputOutput.stampa("6) Eleggi consigliere rapido");
		InputOutput.stampa("7) Azione principale aggiuntiva");
		InputOutput.stampa("- Informazioni:\n");
		InputOutput.stampa("8) Scegli cosa stampare dello stato attuale del gioco\n");
		scelta = InputOutput.leggiIntero(false);
		if (scelta < 8) {
			AzioneFactory azioneFactory = new AzioneFactory(null);
			azioneFactory.setTipoAzione(Integer.toString(scelta));
			if (inserimentoParametriAzione(azioneFactory, azioneFactory.createAzione())) {
				this.getConnessione().inviaOggetto(azioneFactory);
			} else
				return false;
		} else if (scelta == 8)
			stampeTabellone();
		else {
			InputOutput.stampa("");
			InputOutput.stampa("Input non valido");
			return false;
		}
		return true;
	}

	private void faseCompraVendita() {
		Integer scelta;
		InputOutput.stampa("1) Per acquistare oggetti dal mercato");
		InputOutput.stampa("2) Per passare il turno");
		OggettoVendibile oggettoDaAcquistare;
		scelta = InputOutput.leggiIntero(false);
		switch (scelta) {
		case 1:
			oggettoDaAcquistare = compraOggetto();
			if (oggettoDaAcquistare == null)
				faseCompraVendita();
			else
				try {
					getConnessione().inviaOggetto(oggettoDaAcquistare);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			break;
		case 2:
			try {
				getConnessione().inviaOggetto("-");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			InputOutput.stampa("Scelta non valida");
			faseCompraVendita();
		}
	}

	private void faseAggiuntaOggetti() {
		Integer scelta;
		InputOutput.stampa("1) Per aggiungere oggetti al mercato");
		InputOutput.stampa("2) Per passare il turno");
		OggettoVendibile oggettoDaAggiungere;
		scelta = InputOutput.leggiIntero(false);
		switch (scelta) {
		case 1:
			oggettoDaAggiungere = aggiungiOggettiAlMercato();
			if (oggettoDaAggiungere == null)
				faseAggiuntaOggetti();
			else
				try {
					getConnessione().inviaOggetto(oggettoDaAggiungere);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			break;
		case 2:
			try {
				getConnessione().inviaOggetto("-");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			InputOutput.stampa("Scelta non valida");
			faseAggiuntaOggetti();
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

	private OggettoVendibile compraOggetto() {
		Integer indice = 0;
		List<OggettoVendibile> mercato = new ArrayList<>();
		for (OggettoVendibile o : ((FaseTurnoMercatoCompraVendita) tabelloneClient.getGioco().getStato()).getMercato()
				.getOggettiInVendita()) {
			if (!o.getGiocatore().equals(giocatore)) {
				mercato.add(o);
				InputOutput.stampa(indice + ") " + o.toString());
				indice++;
			}
		}
		indice = InputOutput.leggiIntero(true);
		if (indice == null)
			return null;
		if (indice >= 0 && indice < mercato.size()) {
			return mercato.get(indice);
		} else {
			InputOutput.stampa("Scelta non valida");
			return compraOggetto();
		}
	}

	/**
	 * Asks the user inputs an object to be added to the sale.if the user wants
	 * to go back returns null
	 * 
	 * @return null to get it back , or the object to be added to the market
	 */
	private OggettoVendibile aggiungiOggettiAlMercato() {
		Integer indice = 0;
		Integer prezzo;
		OggettoVendibile temp;
		for (OggettoVendibile o : giocatore.generaListaOggettiVendibiliNonInVendita()) {
			InputOutput.stampa(indice + ") " + o.toString());
			indice++;
		}
		indice = InputOutput.leggiIntero(true);
		if (indice == null)
			return null;
		if (indice >= 0 && indice < giocatore.generaListaOggettiVendibiliNonInVendita().size()) {
			InputOutput.stampa("Scegli un prezzo");
			prezzo = chiediPrezzo();
			if (prezzo == null)
				return aggiungiOggettiAlMercato();
			temp = giocatore.generaListaOggettiVendibiliNonInVendita().get(indice);
			temp.setPrezzo(prezzo);
		} else {
			InputOutput.stampa("Scelta non valida");
			return aggiungiOggettiAlMercato();
		}
		return temp;
	}

	/**
	 * Asks the user to input a price
	 * 
	 * @return returns a valid value , or null if the user wants to go back
	 */
	private Integer chiediPrezzo() {
		Integer prezzo = InputOutput.leggiIntero(true);
		if (prezzo == null)
			return null;
		if (prezzo <= 0) {
			InputOutput.stampa("Prezzo non valido");
			return chiediPrezzo();
		}
		return prezzo;
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
			int scelta = InputOutput.leggiIntero(false);
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
	 * Print the unused TesserePermessoDiCostruzione of the player in detail
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
						InputOutput.stampa(ParseColor.colorIntToString(((CartaColorata) cPol).getColore().getRGB()));

					else
						InputOutput.stampa("JOLLY");

	}

	/**
	 * Print the state of the player in input
	 * 
	 * @param input
	 */
	private void stampaAvversarioDettagliato() {
		InputOutput.stampa("Inserisci il nome dell'avversario di cui vuoi conoscere i dettagli");
		String nomeAvv = InputOutput.leggiStringa(false);
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
			InputOutput.stampa("- " + ParseColor.colorIntToString(consD.getColore().getRGB()));
	}

	/**
	 * Print the state of all Consigli and relative Regione
	 */
	private void stampaStatoConsigli() {

		for (Regione regi : tabelloneClient.getRegioni()) {
			InputOutput.stampa("Regione: " + regi.getNome() + "\n" + "Stato Consiglio:");
			for (Consigliere con : regi.getConsiglio().getConsiglieri())
				InputOutput.stampa("- " + ParseColor.colorIntToString(con.getColore().getRGB()));
			InputOutput.stampa("Tessere disponibi all'acquisto:");
			for (TesseraCostruzione tess : regi.getTessereCostruzione())
				stampaTesseraPermesso(tess);

		}

		InputOutput.stampa("Consiglio del Re: vale per " + tabelloneClient.getRe().getCitta().getNome());
		for (Consigliere reCon : tabelloneClient.getRe().getConsiglio().getConsiglieri())
			InputOutput.stampa("- " + ParseColor.colorIntToString(reCon.getColore().getRGB()));
	}

	/**
	 * Print on console the state of the city asked to input
	 * 
	 * @param input
	 */
	private void stampaSpecificaCitta() {
		InputOutput.stampa("Inserisci il nome della città di cui vuoi conoscere lo stato");
		String nomeCitta = InputOutput.leggiStringa(false);
		Citta objCitta = tabelloneClient.cercaCitta(nomeCitta);
		if (objCitta == null)
			InputOutput.stampa("La città cercata non esiste");
		else {
			InputOutput.stampa("Regione: " + objCitta.getRegione().getNome());
			InputOutput.stampa("Colore: " + ParseColor.colorIntToString(objCitta.getColore().getRGB()));
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
		if(giocatore.getTessereValide().size()>0)
			InputOutput.stampa("Inserisci la tessera costruzione che vuoi utilizzare (da 0 a "
						+ ((int)giocatore.getTessereValide().size()-1) + ") oppure inserisci 'annulla' per annullare");
		else
			InputOutput.stampa("Non hai tessere, inserisci 'annulla'");
		String numTessera = InputOutput.leggiStringa(false);
		if ("annulla".equalsIgnoreCase(numTessera)) {
			return false;
		}
		try {
			azioneFactory.setTesseraCostruzione(giocatore.getTessereValide().get(Integer.parseInt(numTessera)));
		} catch (IndexOutOfBoundsException e) {
			InputOutput.stampa("Numero tessera non corretto");
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
		String cittaDestinazione = InputOutput.leggiStringa(false);
		if ("annulla".equalsIgnoreCase(cittaDestinazione))
			return false;
		if (tabelloneClient.cercaCitta(cittaDestinazione) != null) {
			azioneFactory.setCitta(tabelloneClient.cercaCitta(cittaDestinazione));
		} else {
			InputOutput.stampa("La città inserita non esiste");
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
		String nomeRegione = InputOutput.leggiStringa(false);
		if ("annulla".equalsIgnoreCase(nomeRegione))
			return false;
		if (tabelloneClient.getRegioneDaNome(nomeRegione) != null) {
			azioneFactory.setRegione(tabelloneClient.getRegioneDaNome(nomeRegione));
		} else {
			InputOutput.stampa("Nome regione non corretto");
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
		InputOutput.stampa("Inserisci la tessera costruzione da acquistare (da 0 a 1) oppure 'annulla' per annullare");
		String numTessera = InputOutput.leggiStringa(false);
		try {
			TesseraCostruzione tesseraTemp = azioneFactory.getConsiglio().getRegione().getTessereCostruzione()
					.get(Integer.parseInt(numTessera));
			azioneFactory.setTesseraCostruzione(tesseraTemp);
		} catch (NumberFormatException e) {
			InputOutput.stampa("L'input deve essere un numero o annulla");
			return false;
		} catch (IndexOutOfBoundsException e) {
			InputOutput.stampa("Il numero inserito non è corretto");
			return false;
		}
		return true;
	}

	/**
	 * searches the business permit tile
	 * 
	 * @param numTessera
	 *            the number of the tile
	 * @return return the business permit tile
	 */
	public TesseraCostruzione selezionaTesseraDaTabellone(int numTessera) {
		if (numTessera < 6 && numTessera >= 0) {
			List<TesseraCostruzione> listaTessere = new ArrayList<TesseraCostruzione>();
			for (Regione r : tabelloneClient.getRegioni()) {
				listaTessere.addAll(r.getTessereCostruzione());
			}
			return (listaTessere.get(numTessera));
		} else {
			InputOutput.stampa("Numero tessera non corretto");
			return null;
		}
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
			String colore = InputOutput.leggiStringa(false);
			if ("annulla".equalsIgnoreCase(colore))
				return false;
			if (("jolly").equalsIgnoreCase(colore)) {
				carteDaUsare.add(new Jolly());
			} else if ("fine".equals(colore)) {
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
		boolean noCarte;
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
		String colore = InputOutput.leggiStringa(false);
		if ("annulla".equalsIgnoreCase(colore))
			return false;
		try {
			if (tabelloneClient.getConsigliereDaColore(ParseColor.colorStringToColor(colore)) != null) {
				azioneFactory
						.setConsigliere(tabelloneClient.getConsigliereDaColore(ParseColor.colorStringToColor(colore)));
			} else {
				InputOutput.stampa("Non c'è un consigliere disponibile di quel colore");
				return false;
			}
		} catch (NoSuchFieldException e) {
			InputOutput.stampa("Il colore inserito non è corretto");
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
		String scelta = InputOutput.leggiStringa(false);
		if ("annulla".equalsIgnoreCase(scelta))
			return false;
		if (("re").equalsIgnoreCase(scelta)) {
			azioneFactory.setConsiglio(tabelloneClient.getRe().getConsiglio());
		} else {
			if (tabelloneClient.getRegioneDaNome(scelta) != null)
				azioneFactory.setConsiglio(tabelloneClient.getRegioneDaNome(scelta).getConsiglio());
			else {
				InputOutput.stampa("La regione inserita non esiste");
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
	public synchronized void riceviOggetto(Object oggetto) {
		try {
			if (oggetto instanceof String) {
				InputOutput.stampa((String) oggetto);
			}
			if (oggetto instanceof Giocatore)
				this.giocatore = (Giocatore) oggetto;
			if (oggetto instanceof Tabellone) {
				tabelloneClient = (Tabellone) oggetto;
				aggiornaGiocatore();
				aggiornaStato();
				semaforo.release();
			}
			if (oggetto instanceof Exception) {
				InputOutput.stampa(((Exception) oggetto).getMessage());
				if (oggetto instanceof NomeGiaScelto)
					inizializzazione();
			}
			if (oggetto instanceof BonusGettoneCitta) {
				InputOutput.stampa("Inserisci il nome di una città dove hai un emporio"
						+ " e di cui vuoi ottenere il bonus, se non hai un'emporio scrivi 'passa'");
				inserimentoBonus.set(true);
				inserimentoAzione.set(false);
				semBonus.acquire();
				((BonusGettoneCitta) oggetto).getCitta().add(new Citta(inputString, null));
				inserimentoAzione.set(true);
				this.getConnessione().inviaOggetto(oggetto);
			}
			if (oggetto instanceof BonusTesseraPermesso) {
				InputOutput.stampa("Inserisci il numero della tessera permesso che vuoi ottenere");
				inserimentoBonus.set(true);
				inserimentoAzione.set(false);
				semBonus.acquire();
				try {
					TesseraCostruzione tmp = selezionaTesseraDaTabellone(Integer.parseInt(inputString));
					if (tmp != null)
						((BonusTesseraPermesso) oggetto).setTessera(tmp);
					else {
						System.out.println("Il numero inserito non è corretto");
						riceviOggetto(oggetto);
					}
				} catch (NumberFormatException e) {
					System.out.println("la stringa deve essere un numero");
					riceviOggetto(oggetto);
				}
				inserimentoAzione.set(true);
				this.getConnessione().inviaOggetto(oggetto);
			}
			if (oggetto instanceof BonusRiutilizzoCostruzione) {
				InputOutput.stampa(
						"inserisci 0 se la tessera è nella lista delle tessere valide, altrimenti 1. Scrivi 'passa' se non hai tessere");
				inserimentoBonus.set(true);
				inserimentoAzione.set(false);
				semBonus.acquire();
				String prov = inputString;
				InputOutput.stampa("inserisci il numero della tessera da riciclare");
				semaforo.release();
				inserimentoBonus.set(true);
				semBonus.acquire();
				try {
					if ("0".equals(prov)) {
						((BonusRiutilizzoCostruzione) oggetto)
								.setTessera(giocatore.getTessereValide().get(Integer.parseInt(inputString)));
					} else if ("1".equals(prov)) {
						((BonusRiutilizzoCostruzione) oggetto)
								.setTessera(giocatore.getTessereUsate().get(Integer.parseInt(inputString)));
					} else if ("passa".equals(prov))
						((BonusRiutilizzoCostruzione) oggetto).setTessera(null);
					inserimentoAzione.set(true);
					this.getConnessione().inviaOggetto(oggetto);
				} catch (IndexOutOfBoundsException | NumberFormatException e){
					e.printStackTrace();
					System.out.println("numero tessera non corretto");
					riceviOggetto(oggetto);
				}
			}
		} catch (IOException | InterruptedException e) {
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

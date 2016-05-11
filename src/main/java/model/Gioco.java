package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Set;
import org.jdom2.JDOMException;

import model.azione.AcquistaPermesso;
import model.azione.AzioneOpzionaleNulla;
import model.azione.AzionePrincipaleAggiuntiva;
import model.azione.AzionePrincipaleNulla;
import model.azione.CambioTessereCostruzione;
import model.azione.CostruisciEmporioConRe;
import model.azione.CostruisciEmporioConTessera;
import model.azione.EleggiConsigliere;
import model.azione.EleggiConsigliereRapido;
import model.azione.IngaggioAiutante;
import model.percorso.Casella;

/**
 * @author Luca
 *
 */
public class Gioco {

	private List<Giocatore> giocatori;
	private Tabellone tabellone;
	private boolean vittoria;
	private int id;
	private Mercato mercato;
	
	public Gioco (List<Giocatore> giocatori) throws JDOMException, IOException//Verrà una roba mooolto lunga
	{
		//Inizializzazione Ambiente di gioco
		String nomeMappaScelta="/source/main/mappacollegamenti0.xml";// Ottenuta dal controller
		this.tabellone=new Tabellone(nomeMappaScelta);
		//ottengo elenco nome giocatori
		int numGiocatore=1;
		for(Giocatore gio:giocatori)
		{
			//id, nome, e colore sono già settati
			//setto empori rimasti a 10
			gio.setEmporiRimasti(10);
			//setto assistenti
			for(int i=0;i<numGiocatore;i++)
				gio.getAssistenti().add(new Assistente());
			//setto posizione iniziale percorso nobiltà e vittoria e Ricchezza
			this.tabellone.getPercorsoNobiltà().getCaselle().get(0).getGiocatori().add(gio);
			this.tabellone.getPercorsoVittoria().getCaselle().get(0).getGiocatori().add(gio);
			this.tabellone.getPercorsoRicchezza().getCaselle().get(10+numGiocatore).getGiocatori().add(gio);
			//setto il gioco
			gio.setGioco(this);
			//setto carte politica iniziali
			for(int i=0;i<6;i++)
			{
				gio.getCartePolitica().add(new CartaPolitica());
			}
			numGiocatore++;
		}			
		this.giocatori=giocatori;
		//Setup aggiuntivo per 2 giocatori
		if(numGiocatore==2)
		{
			Giocatore dummy=new Giocatore(null, "colore");
			for(Regione regi: tabellone.getRegioni() ){
				for(Città cit:regi.getTessereCoperte().get(0).getCittà())
				cit.aggiungiEmporio(dummy);
					Collections.shuffle(regi.getTessereCoperte());
			}
			
		}
		//Setup mercato
		mercato=new Mercato(tabellone.getPercorsoRicchezza());
		
	}//mettere i catch delle eccezioni della lettura xml
	public void gioco() throws JDOMException, IOException{//Throws da rimuovere quando si crea il controller
		vittoria=false;
		Queue<Giocatore> codaGiocatori= new LinkedList<Giocatore>(giocatori);
		int numeroGiocatori=giocatori.size();
		int numeroTurno=0;
		while(!vittoria){
			if(numeroTurno==numeroGiocatori)//Fase mercato
			{
				boolean fineMercato;
				OggettoVendibile oggetto;
				for(Giocatore gio:giocatori){
					fineMercato=false;
					while(!fineMercato){
						//metodo controller per chiedere se vuole vendere altri oggetti
						//setto fineMercato
						oggetto=null;
						//metodo del controller che prende l' oggetto in ingresso
						faseMercatoVendita(gio, oggetto);
					}
				}
				fineMercato=false;
				//fase mercato per l'acquisto
				Collections.shuffle(giocatori);
				for(Giocatore gio:giocatori){
					while(!fineMercato){
						//metodo controller per chiedere se vuole vendere altri oggetti
						//setto fineMercato
						//fineMercato=...
						//mostro la lista degli oggetti in vendita e prendo in ingresso
						//l'oggetto
						oggetto=null;
						faseMercatoAcquisto(gio, oggetto);
					}
				}
				numeroTurno=0;
			}
			Giocatore giocatoreAttuale=codaGiocatori.poll();
			turno(giocatoreAttuale);
			numeroTurno++;
			if(giocatoreAttuale.getEmporiRimasti()==0)
			{
				vittoria=true;
			}
			else
			{
				codaGiocatori.add(giocatoreAttuale);
			}
		}
		//ultimo turno
		while(!codaGiocatori.isEmpty()){
			Giocatore giocatoreAttuale=codaGiocatori.poll();
			turno(giocatoreAttuale);
		}
		/*while(!vittoria){
		//Fase turni
			for(Giocatore gio:this.giocatori)
			{
				//Controllo se gli empori sono finiti
				for(Giocatore gioca: this.giocatori)
					if (gioca.getEmporiRimasti()==0)
					{
						vittoria=true;
						Set<Giocatore> giocatoriRimasti=this.giocatori;
						giocatori.remove(gioca);
						//Inizio ultimo turno
						for (Giocatore ply:giocatoriRimasti)
						{
							turno(ply);
						}
						break;
					}
				turno(gio);
			}
			//Fase Mercato per la vendita
			boolean fineMercato;
			OggettoVendibile oggetto;
			for(Giocatore gio:giocatori){
				fineMercato=false;
				while(!fineMercato){
					//metodo controller per chiedere se vuole vendere altri oggetti
					//setto fineMercato
					oggetto=null;
					//metodo del controller che prende l' oggetto in ingresso
					faseMercatoVendita(gio, oggetto);
				}
			}
			fineMercato=false;
			//fase mercato per l'acquisto
			for(Giocatore gio:giocatori){
				while(!fineMercato){
					//metodo controller per chiedere se vuole vendere altri oggetti
					//setto fineMercato
					//fineMercato=...
					//mostro la lista degli oggetti in vendita e prendo in ingresso
					//l'oggetto
					oggetto=null;
					faseMercatoAcquisto(gio, oggetto);
				}
			}
			
		
		}*/
		//Conteggio punti
		Set<Giocatore> vincitore=calcoloVincitore();
		//Comunico al controller chi è il vincitore
	}
	
	public void turno(Giocatore gio) throws JDOMException, IOException{
		//il giocatore persca una carta
		gio.getCartePolitica().add(new CartaPolitica());
		while((!gio.getAzioneOpzionale())||(!gio.getAzionePrincipale())){
			//Metodo controller che dice al giocatore quale mosse gli mancano da fare (se lenta e/o veloce)
			//Richiesta al controller di input per azione al giocatore gio
			int azione = 11;
			//Il controller comunica quale azione vuole compiere il giocatore
			if(azione==1&&!gio.getAzionePrincipale())//"Eleggi Consigliere"
			{
				//Richiesta al controller di input per scelta consiglio e consigliere da eleggere
				Consiglio consiglioScelto=new Consiglio(null);//inizializzazioni errarte, vanno ovviamente fatte dal controller
				Consigliere consigliereScelto=new Consigliere(null);
				new EleggiConsigliere(consigliereScelto, consiglioScelto, tabellone.getPercorsoRicchezza()).eseguiAzione(gio);
			}
			else if(azione==2&&!gio.getAzionePrincipale())//Acquistare una tessera permesso costruzione
			{
				//Richiesta al controller di input per la tessera da acquistare
				List<CartaPolitica> carteSelezionate=new ArrayList<CartaPolitica>(4);
				TesseraCostruzione tesseraScelta=new TesseraCostruzione(null,null,null);
				new AcquistaPermesso(tesseraScelta, carteSelezionate, tesseraScelta.getRegioneDiAppartenenza().getConsiglio(), this.tabellone.getPercorsoRicchezza()).eseguiAzione(gio);;
			}
			else if(azione==3&&!gio.getAzionePrincipale())//Costruire un emporio usando una tessera permesso
			{
				//Richiesta al controller di input per la tessera da acquistare
				Città cittàScelta=new Città(null, null);
				TesseraCostruzione tesseraScelta=new TesseraCostruzione(null,null,null);
				new CostruisciEmporioConTessera(cittàScelta, tesseraScelta, this.tabellone).eseguiAzione(gio);
			}
			else if(azione==4&&!gio.getAzionePrincipale())//Costruire un emporio con l'aiuto del re
			{
				//Richiesta al controller di input per scegliere carte politica
				//new MuoviRe(tabellone.getRe(), tabellone.getPercorsoRicchezza()).eseguiAzione(gio);
				List<CartaPolitica> carteSelezionate=new ArrayList<CartaPolitica>(4);
				new CostruisciEmporioConRe(tabellone, gio, tabellone.getRe(), carteSelezionate).eseguiAzione(gio);
			}
			else if(azione==5&&!gio.getAzioneOpzionale())//Ingaggia Aiutante
			{
				new IngaggioAiutante(tabellone.getPercorsoRicchezza()).eseguiAzione(gio);
			}
			else if(azione==6&&!gio.getAzioneOpzionale())//Cambiare le tessere permesso di costruzione
			{
				//Richiesta al controller di input per scegliere la regione per cui rimischiare le carte
				Regione regioneScelta=new Regione(null, null, null);
				new CambioTessereCostruzione(regioneScelta).eseguiAzione(gio);
			}
			else if(azione==7&&!gio.getAzioneOpzionale())//Mandare un aiutante ad eleggere un consigliere
			{
				Consiglio consiglioScelto=new Consiglio(null);//inizializzazioni errarte, vanno ovviamente fatte dal controller
				Consigliere consigliereScelto=new Consigliere(null);
				new EleggiConsigliereRapido(consigliereScelto, consiglioScelto).eseguiAzione(gio);
			}
			else if(azione==8&&!gio.getAzioneOpzionale())//Compiere un'azione principale aggiuntiva
			{
				new AzionePrincipaleAggiuntiva().eseguiAzione(gio);
			}
			else if(azione==9&&!gio.getAzioneOpzionale())//Saltare azione Veloce
			{
				new AzioneOpzionaleNulla().eseguiAzione(gio);
			}
			else if(azione==10&&!gio.getAzionePrincipale())//Saltare azione Principale
			{
				new AzionePrincipaleNulla().eseguiAzione(gio);
			}
			else
				System.err.println("Input azione non valido, riprova!");
		}
	}
	
	/**
	 * the player can add an object in the list oggettiInVendita
	 * @param giocatore the player who wants to add an object in the market
	 * @param oggetto the object you want to sell
	 */
	public void faseMercatoVendita(Giocatore giocatore, OggettoVendibile oggetto){
		int prezzo=0;
		oggetto.aggiungiOggetto(mercato);
		//bisogna chiamare il metodo per chiedere il prezzo (controller)
		oggetto.setPrezzo(prezzo);
		oggetto.setGiocatore(giocatore);
	}
	
	/**
	 * a player can buy object in the list of oggettiInVendita
	 * @param giocatore the player who wants to buy the objects
	 * @param oggetto the object the player wants to buy
	 */
	public void faseMercatoAcquisto(Giocatore giocatore, OggettoVendibile oggetto){
		oggetto.transazione(giocatore);
	}
	
	public Set<Giocatore> calcoloVincitore(){
		//Controllo chi è più avanti nel percorso nobiltà e assegno punti
		ListIterator<Casella> itcasella=tabellone.getPercorsoNobiltà().getCaselle().listIterator(tabellone.getPercorsoNobiltà().getCaselle().size());
		while(itcasella.hasPrevious())
		{
			Set<Giocatore> giocatoriPiùAvanti=itcasella.previous().getGiocatori();
			if(!itcasella.hasPrevious())
			{
				//System.out.println("Sei all'inizio del percorso, nella prima casella\n"); 
				break;
			}
			if (!giocatoriPiùAvanti.isEmpty())
			{
				//ho trovato i giocatori più avanzati, gli assegno il punteggio
				for(Giocatore gio: giocatoriPiùAvanti)
					tabellone.getPercorsoVittoria().muoviGiocatore(gio, 5);
				int numGiocatoriPiùAvanti=giocatoriPiùAvanti.size();
				if(numGiocatoriPiùAvanti>1){
					break;
				}
				else
				{
					while(itcasella.hasPrevious()){
						Set<Giocatore> giocatoriSecondi=itcasella.previous().getGiocatori();
							if (!giocatoriSecondi.isEmpty()){
								//Assegno punti ai secondi
								for(Giocatore gio: giocatoriSecondi)
									tabellone.getPercorsoVittoria().muoviGiocatore(gio, 2);
								break;
							}
					}
					break;
				}
			}
			
		}
		//Conto tessere permesso e assegno punti
		int numTesserePermesso=-1;
		Giocatore giocatorePiùPermessi=new Giocatore(null,null);
		for(Giocatore gio: giocatori)
		{
			if((gio.getTessereUsate().size()+gio.getTessereValide().size())>numTesserePermesso)
			{
				numTesserePermesso=(gio.getTessereUsate().size()+gio.getTessereValide().size());
				giocatorePiùPermessi=gio;
			}
		}
		tabellone.getPercorsoVittoria().muoviGiocatore(giocatorePiùPermessi, 3);
		//Calcolo vincitore
		
		itcasella=tabellone.getPercorsoVittoria().getCaselle().listIterator(tabellone.getPercorsoVittoria().getCaselle().size());
		while(itcasella.hasPrevious()){
			Casella casellaVincitori=itcasella.previous();
			Set<Giocatore> vincitore=casellaVincitori.getGiocatori();
			if(vincitore.isEmpty()){
				if(vincitore.size()<1)
					return vincitore;
				else
				{
					int paramVittoria=-1;//Somma tessere e aiutanti
					for(Giocatore gio: vincitore)
					{
						if((gio.getAssistenti().size()+gio.getTessereValide().size()+gio.getTessereValide().size())>paramVittoria)
							paramVittoria=gio.getAssistenti().size()+gio.getTessereValide().size()+gio.getTessereValide().size();
					}
					for(Giocatore gio: vincitore)
					{
						if((gio.getAssistenti().size()+gio.getTessereValide().size()+gio.getTessereValide().size())<paramVittoria)
							vincitore.remove(gio);
					}
					return vincitore;
				}
			}
		}
		throw new IllegalStateException("Errore nel calcolo del percorso");
	}
	
	/**
	 * @return the tabellone
	 */
	public Tabellone getTabellone() {
		return tabellone;
	}
	
}

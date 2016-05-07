package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.jdom2.JDOMException;

/**
 * @author Luca
 *
 */
public class Gioco {

	private Set<Giocatore> giocatori;
	private Tabellone tabellone;
	private boolean vittoria;
	private int id;
	private Mercato mercato;
	
	public Gioco (Set<Giocatore> giocatori) throws JDOMException, IOException//Verrà una roba mooolto lunga
	{
		//Inizializzazione Ambiente di gioco
		String nomeMappaScelta="mappacollegamenti0.xml";// Ottenuta dal controller
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
		if(numGiocatore==2)
		{
			Giocatore dummy=new Giocatore(null, "colore");
			for(Regione regi: tabellone.getRegioni() ){
				for(Città cit:regi.getTessereCoperte().get(0).getCittà())
				cit.aggiungiEmporio(dummy);
					Collections.shuffle(regi.getTessereCoperte());
			}
			
		}
	}//mettere i catch delle eccezioni della lettura xml
	public void gioco() throws JDOMException, IOException{//Throws da rimuovere quando si crea il controller
		vittoria=false;
		while(!vittoria){
		//Fase turni
			for(Giocatore gio:this.giocatori)
			{
				//il giocatore persca una carta
				gio.getCartePolitica().add(new CartaPolitica());
				while((!gio.getAzioneOpzionale())&&(!gio.getAzionePrincipale())){
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
						//Richiesta posizione re
						new MuoviRe(tabellone.getRe(), tabellone.getPercorsoRicchezza()).eseguiAzione(gio);
						List<CartaPolitica> carteSelezionate=new ArrayList<CartaPolitica>(4);
						new CostruisciEmporioConRe(tabellone.getRe(), carteSelezionate).eseguiAzione(gio);
					}
					else if(azione==5&&!gio.getAzioneOpzionale())//Ingaggia Aiutante
					{
						new IngaggioAiutante(tabellone.getPercorsoRicchezza()).eseguiAzione(gio);
					}
					else if(azione==6&&!gio.getAzioneOpzionale())//Cambiare le tessere permesso di costruzione
					{
						//Richiesta al controller di input per scegliere la regione per cui rimischiare le carte
						Regione regioneScelta=new Regione(null, null, null, null, null, null);
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
				for(Giocatore gioca: this.giocatori)
					if (gioca.getEmporiRimasti()==0)
						vittoria=true;
			}
			//Fase Mercato
		
		}
		//Conteggio punti
	
	}
	/**
	 * @return the tabellone
	 */
	public Tabellone getTabellone() {
		return tabellone;
	}
	
}

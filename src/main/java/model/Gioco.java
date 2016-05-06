package model;

import java.io.IOException;
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
			//id, nome,empori rimasti(10) e stringa sono già settati
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
	}//mettere i catch delle eccezioni della lettura xml
/**
	 * @return the tabellone
	 */
	public Tabellone getTabellone() {
		return tabellone;
	}
	public void gioco(){}
}

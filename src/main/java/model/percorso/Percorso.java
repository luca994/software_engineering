package model.percorso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import model.Giocatore;
import model.Tabellone;
import model.bonus.Bonus;
import model.bonus.BonusCreator;
/**
 * This class is used to implement all the routes; the generic type "Casella"
 *allow the program to use only this class to implement every kind of route.
 * @author Massimiliano Ventura
 *
 */
public class Percorso {

	private static final Logger log = Logger.getLogger(Percorso.class.getName());
	private List<Casella> caselle;
	
	/**
	 * @return the caselle
	 */
	public List<Casella> getCaselle() {
		return this.caselle;
	}
	
	/**
	 * build a route from a file
	 * @param nomefile the file path
	 * @param tabellone the game board
	 * @throws JDOMException
	 * @throws IOException if the file doesn't exist or there is an error in the file reading
	 */
	public Percorso(String nomefile, Tabellone tabellone) throws JDOMException, IOException
	{
		BonusCreator bonusCreator = new BonusCreator(tabellone);
		SAXBuilder builderPercorso = new SAXBuilder();
		Document documentPercorso = builderPercorso.build(new File(nomefile));
		Element percorsoRootElement = documentPercorso.getRootElement();
		List<Element> elencoCaselle =percorsoRootElement.getChildren();
		this.caselle=new ArrayList<Casella>();
		boolean senzaB=false;
		for(Element cas:elencoCaselle)
		{
			List<Element> elencoBonus =cas.getChildren();
			Set<Bonus> totBonus = new HashSet<Bonus>();
			for(Element bon:elencoBonus)
			{
				if(bon.getAttributeValue("id").equals("NessunBonus"))
				{
					senzaB=true;
					break;
				}
				totBonus.add(bonusCreator.creaBonus(bon.getAttributeValue("id"), Integer.parseInt(bon.getAttributeValue("attributo"))));
			}
			if(senzaB) this.caselle.add(new CasellaSenzaBonus());
			else this.caselle.add(new CasellaConBonus(totBonus));
		}
			
	}
	
	/**
	 * Moves the player along the route(Percorso) if the number of steps(passi) is negative the player will move backwards.
	 * @throws IndexOutOfBoundsException if giocatore hasn't enough money
	 * @param giocatore the player who wants to move
	 * @param passi the number of steps
	 */
	public void muoviGiocatore (Giocatore giocatore, int passi)
	{
		try{
			if (passi>0)
				muoviGiocatoreAvanti(giocatore, passi);
			else if(passi<0)
			{
				int posizione=posizioneAttualeGiocatore(giocatore);
				if(posizione>=passi)
				{
					passi=0-passi;
					muoviGiocatoreIndietro(giocatore, passi);
				}
				else
				{
					throw new IndexOutOfBoundsException("Soldi insufficienti per eseguire la mossa");
				}
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}
	
	/**
	 * it is called by muoviGiocatore to move the player forward
	 * @param giocatore the player who wants to move
	 * @param passi the number of steps
	 */
	public void muoviGiocatoreAvanti(Giocatore giocatore, int passi)
	{
		Casella casellacorrente = null;
		ListIterator<Casella> itcasella=this.caselle.listIterator();
		while(itcasella.hasNext())//mentre scorro le caselle controllo di non essere in fondo al percorso
		{
			Set<Giocatore> giocatoriCasellaCorrente=itcasella.next().getGiocatori();
			if(!itcasella.hasNext())
			{
				//System.out.println("Sei alla fine del percorso, nell'ultima casella\n"); 
				break;
			}
			if (giocatoriCasellaCorrente.contains(giocatore))
			{
				giocatoriCasellaCorrente.remove(giocatore);
				for(int j=0;j<passi&&itcasella.hasNext();j++)
				{
					casellacorrente=itcasella.next();
					giocatoriCasellaCorrente=casellacorrente.getGiocatori();					
				}
				giocatoriCasellaCorrente.add(giocatore);
				if (casellacorrente instanceof CasellaConBonus) {
					for(Bonus bon : ((CasellaConBonus) casellacorrente).getBonus())
						bon.azioneBonus(giocatore);
				    }
				break;
			}
		}				
	}
	
	/**
	 * it is called by muoviGiocatore to move the player back
	 * @param giocatore
	 * @param passi
	 */
	public void muoviGiocatoreIndietro(Giocatore giocatore, int passi)
	{

		ListIterator<Casella> itcasella=this.caselle.listIterator(caselle.size());
		while(itcasella.hasPrevious())
		{
			Set<Giocatore> giocatoriCasellaCorrente=itcasella.previous().getGiocatori();
			if(!itcasella.hasPrevious())
			{
				//System.out.println("Sei all'inizio del percorso, nella prima casella\n"); 
				break;
			}
			if (giocatoriCasellaCorrente.contains(giocatore))
			{
				giocatoriCasellaCorrente.remove(giocatore);
				for(int j=0;j<passi&&itcasella.hasPrevious();j++)
					giocatoriCasellaCorrente=itcasella.previous().getGiocatori();
				giocatoriCasellaCorrente.add(giocatore);
				break;
			}
		}
				
	}
	
	/**
	 * @returns the position number of giocatore in the current route, 
	 * @throws IllegalArgumentExeption when can't find giocatore in the route
	 * @param giocatore the player of which you want to know the position
	 * 
	 */
	public int posizioneAttualeGiocatore(Giocatore giocatore)
		{
			boolean trovato=false;
			int posizione=-1;
			//funzionamento analogo a muoviGiocatoreAvanti
			Iterator<Casella> itcasella=caselle.iterator();
			while(itcasella.hasNext()&&!trovato)
			{
				posizione++;				
					if(itcasella.next().getGiocatori().contains(giocatore))
						trovato=true;
			}
			if(!trovato)
				throw new IllegalArgumentException("Il giocatore non è stato inizializzato in questo percorso");
			return posizione;
		}
}

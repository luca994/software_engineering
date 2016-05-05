package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
/**
 * This class is used to implement all the routes; the generic type "Casella"
 *allow the program to use only this class to implement every kind of route.
 * @author Massimiliano Ventura
 *
 */
public class Percorso 
{
	private List<Casella> caselle;
	public Percorso()
	{
		this.caselle=new ArrayList<Casella>();
	}
	/**
	 * Moves the player along the route(Percorso) if the number of steps(passi) is negative the player will move backwards.
	 * @Throws IndexOutOfBoundsException if giocatore hasn't enough money
	 * @param giocatore
	 * @param passi
	 */
	public void muoviGiocatore (Giocatore giocatore, int passi)
	{
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
	public void muoviGiocatoreAvanti(Giocatore giocatore, int passi)
	{
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
					giocatoriCasellaCorrente=itcasella.next().getGiocatori();
				giocatoriCasellaCorrente.add(giocatore);
				break;
			}
		}				
	}
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
	 * @returns the position number  of giocatore in the current route, 
	 * @Throws IllegalArgumentExeption 
	 * when can't find giocatore in the route
	 * @param giocatore
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

package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
/**
 * 
 * @author Massimiliano Ventura
 *This class is used to implement all the routes; the generic type "Casella"
 *allow the program to use only this class to implement every kind of route.
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
	 * This method is used to move the player along the route(Percorso) if the number of steps(passi)
	 * is negative the player will move backwards.
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
		else if(passi==0)
			System.out.println("Srsly??, muovi di zero passi?");
	}
	public void muoviGiocatoreAvanti(Giocatore giocatore, int passi)
	{
		boolean trovato;
		//uso il for per ripetere l'avanzamento del percorso il numero di passi richiesto
		for(int i=0;i<passi;i++)
		{
			trovato=false;
			ListIterator<Casella> itcasella=this.caselle.listIterator();
			while(itcasella.hasNext()&&trovato==false)//mentre scorro le caselle controllo di non essere in fondo al percorso
			{
				//inizializzo l'iteratore dei giocatori della prossima casella(il next() fa avanzare di uno il cursore)
				//all'inizio non sono in nessuna casella e la dichiarazione dell'iteratore mi fa spostare sulla prima casella
				Iterator<Giocatore> itgiocatore=itcasella.next().getGiocatori().iterator();
				//siccome la dichiarazione dell'iteratore dei giocatori ha avanzato di uno il cursore, controllo che la casella successiva esista
				if(!itcasella.hasNext())
				{
					System.out.println("Sei alla fine del percorso, nell'ultima casella\n"); 
					break;
				}
				//inizio a scorrere i giocatori, controllo sempre che esistano
				while(itgiocatore.hasNext()&&trovato==false)
				{
					//controllo che il giocatore sia quello passato come parametro
					if(itgiocatore.next().equals(giocatore))
					{
						//se è quello cercato lo levo, incremento la casella e aggiungo un riferimento alla casella successiva con il giocatore del parametro
						itgiocatore.remove();
						itcasella.next().getGiocatori().add(giocatore);
						trovato=true;
					}
				}
			}
		}
				
	}
	public void muoviGiocatoreIndietro(Giocatore giocatore, int passi)
	{
		boolean trovato;
		ListIterator<Casella> itcasella=this.caselle.listIterator(caselle.size());
		for(int i=0;i<passi;i++)
		{
			trovato=false;
			while(itcasella.hasPrevious()&&trovato==false)
			{
				Iterator<Giocatore> itgiocatore=itcasella.previous().getGiocatori().iterator();
				if(!itcasella.hasPrevious())
				{
					System.out.println("Sei all'inizio del percorso, nella prima casella\n"); 
					break;
				}
				while(itgiocatore.hasNext()&&trovato==false)
				{
					if(itgiocatore.next().equals(giocatore))
					{
						itgiocatore.remove();
						itcasella.previous().getGiocatori().add(giocatore);
						trovato=true;
					}
				}
			}
		}
		
	}
	public int posizioneAttualeGiocatore(Giocatore giocatore)
		{
			boolean trovato=false;
			int posizione=0;
			//funzionamento analogo a muoviGiocatoreAvanti
			Iterator<Casella> itcasella=caselle.iterator();
			while(itcasella.hasNext()&&trovato==false)
			{
				Iterator<Giocatore> itgiocatore=itcasella.next().getGiocatori().iterator();
				posizione++;
				while(itcasella.hasNext())
				{
					if(itgiocatore.next().equals(giocatore))
						trovato=true;
				}
			}
			if(trovato==false)
				System.out.println("il giocatore non esiste, o il parametro passato è sbagliato oppure lo è il metodo, spera nella prima\n");
			return posizione;
		}
}

package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Percorso {
	private List<Casella> caselle;
	public Percorso(){
		this.caselle=new ArrayList<Casella>();
	}
	public void muoviGiocatoreAvanti(Giocatore giocatore, int passi){
	
		Iterator<Casella> itcasella=caselle.iterator();
		while(itcasella.hasNext())
		{
			Iterator<Giocatore> itgiocatore=itcasella.next().getGiocatori().iterator();
			if(!itcasella.hasNext())
				{
					System.out.println("Sei alla fine del percorso\n"); 
					break;
				}
			while(itgiocatore.hasNext())
				if(itgiocatore.next().equals(giocatore))
					if(itgiocatore.hasNext())
					{
						itgiocatore.remove();
						itcasella.next().add(giocatore);
					}
		}
		
	}
	
}

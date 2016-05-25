package server.model.azione;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.CartaPolitica;
import server.model.Città;
import server.model.Consiglio;
import server.model.Giocatore;
import server.model.Re;
import server.model.Tabellone;

/**
 * @author Luca
 *
 */
public class CostruisciEmporioConRe implements Azione {	

	private static final Logger log= Logger.getLogger( CostruisciEmporioConRe.class.getName() );
	
	private Città destinazione;
	private List<CartaPolitica> cartePolitica;
	private Tabellone tabellone;

	/**
	 * @param re
	 *
	 */
	public CostruisciEmporioConRe(Tabellone tabellone, Giocatore giocatore, List<CartaPolitica> cartePolitica,Città destinazione) {
		this.destinazione=destinazione;
		this.cartePolitica=cartePolitica;
		this.tabellone=tabellone;
	}
	
	
	/**
	 * Builds an emporio in the same city where the king is situated. 
	 * @throws Exception 
	 * @throws IllegalStateException when giocatore has already an emporio in the city. 
	 * @throws IllegalStateException when the number of CartaPolitica is not correct.
	 * @throws IndexOutOfBoundsException if an error occurs during the count of the cards
	 * @throws IndexOutOfBoundsException if giocatore hasn't enough money to perform the action(from method muoviGiocatore)
	 */
	public void eseguiAzione (Giocatore giocatore) throws Exception{
		try{
			if (giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			if(destinazione.presenzaEmporio(giocatore))
				throw new IllegalStateException("L'emporio del giocatore è già presente in questa città");
			Consiglio consiglioDaSoddisfare=tabellone.getRe().getConsiglio();
			int numeroCartePolitica=cartePolitica.size();
			int jollyUsati=0;
			int counter=0;
			if(numeroCartePolitica<1||numeroCartePolitica>4)
				throw new IllegalStateException("Il numero di carte selezionato non è appropriato");
			//Creazione copie liste dei colori del consiglio
			List<Color> colori = consiglioDaSoddisfare.acquisisciColoriConsiglio();
			for(CartaPolitica car : cartePolitica){
				if(car.getColore().equals(Color.red))
					jollyUsati++;//conto i jolly usati
				for(Color col : colori){
				if(car.getColore().equals(col))
					{
						counter++;
						colori.remove(col);
						break;
					}
				}			
			}
			
			switch(counter){
				case 1: tabellone.getPercorsoRicchezza().muoviGiocatore(giocatore, 0-10-jollyUsati);
					break;
				case 2: tabellone.getPercorsoRicchezza().muoviGiocatore(giocatore, 0-7-jollyUsati);
					break;
				case 3: tabellone.getPercorsoRicchezza().muoviGiocatore(giocatore, 0-4-jollyUsati);
					break;
				case 4: 
					break;
				default: throw new IndexOutOfBoundsException("Errore nel conteggio consiglieri da soddisfare");
			}
		//}
		//Rimozione tessere selezionate dalla mano del giocatore, se il giocatore è stupido e seleziona tessere in più o del colore sbagliato sono affari suoi.
			giocatore.getCartePolitica().removeAll(cartePolitica);
			try{
			tabellone.getPercorsoRicchezza().muoviGiocatore(giocatore, 0-2*tabellone.getRe().contaPassi(destinazione));
			}
			catch(IndexOutOfBoundsException e){
				throw e;
			}
			destinazione.aggiungiEmporio(giocatore);
			giocatore.decrementaEmporiRimasti();
		//Se il giocatore ha finito gli empori guadagna 3 punti vittoria	
			if(giocatore.getEmporiRimasti()==0)
				tabellone.getPercorsoVittoria().muoviGiocatore(giocatore, 3);
		//Mi piglio i bonus di questa e delle città collegate
			List<Città> cittàConBonusDaOttenere=new ArrayList<Città>();
			cittàConBonusDaOttenere.add(destinazione);
			destinazione.cittàVicinaConEmporio(giocatore, cittàConBonusDaOttenere);
			for(Città citt: cittàConBonusDaOttenere)
				citt.eseguiBonus(giocatore);
			giocatore.setAzionePrincipale(true);
			//controllo se ho gli empori in tutte le città di un colore o di una regione e prendo la
			//tessera bonus se mi spetta
			tabellone.prendiTesseraBonus(giocatore, destinazione);
	
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}	
	}
}

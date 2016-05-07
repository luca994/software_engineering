package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Luca
 *
 */
public class CostruisciEmporioConRe implements Azione {

	private Re re;
	private Città città;
	private List<CartaPolitica> cartePolitica;
	private Percorso percorsoRicchezza;
	private Tabellone tabellone;
	
	/**
	 * Builds an emporio in the same city where the king is situated. 
	 * @throws IllegalStateException when giocatore has already an emporio in the city. 
	 * @throws IllegalStateException when the number of CartaPolitica is not correct.
	 * @throws IndexOutOfBoundsException if an error occurs during the count of the cards
	 * @throws IndexOutOfBoundsException if giocatore hasn't enough money to perform the action(from method muoviGiocatore)
	 */
	public void eseguiAzione (Giocatore giocatore){
		try{
			if(città.presenzaEmporio(giocatore))
				throw new IllegalStateException("L'emporio del giocatore è già presente in questa città");
			Consiglio consiglioDaSoddisfare=re.getConsiglio();
			int numeroCartePolitica=cartePolitica.size();
			int jollyUsati=0;
			int counter=0;
			if(numeroCartePolitica<1||numeroCartePolitica>4)
				throw new IllegalStateException("Il numero di carte selezionato non è appropriato");
			//Creazione copie liste dei colori del consiglio
			List<String> colori = consiglioDaSoddisfare.acquisisciColoriConsiglio();
			List<String> cpycolori= new ArrayList<>();
			Collections.copy(cpycolori, colori);
	//		if(giocatore.containsAllCarte(cartePolitica)){
			for(CartaPolitica car : cartePolitica){
				if(car.getColore().equals("JOLLY"))
					jollyUsati++;//conto i jolly usati
				for(String col : cpycolori){
				if(car.getColore().equals(col))
					{
						counter++;
						cpycolori.remove(col);
						break;
					}
				}			
			}
			
			switch(counter){
				case 1: percorsoRicchezza.muoviGiocatore(giocatore, 0-10-jollyUsati);
					break;
				case 2: percorsoRicchezza.muoviGiocatore(giocatore, 0-7-jollyUsati);
					break;
				case 3: percorsoRicchezza.muoviGiocatore(giocatore, 0-4-jollyUsati);
					break;
				case 4: 
					break;
				default: throw new IndexOutOfBoundsException("Errore nel conteggio consiglieri da soddisfare");
			}
		//}
		//Rimozione tessere selezionate dalla mano del giocatore, se il giocatore è stupido e seleziona tessere in più o del colore sbagliato sono affari suoi.
			giocatore.getCartePolitica().removeAll(cartePolitica);
			
			città.aggiungiEmporio(giocatore);
			giocatore.decrementaEmporiRimasti();
		//Mi piglio i bonus di questa e delle città collegate
			List<Città> cittàConBonusDaOttenere=new ArrayList<Città>();
			cittàConBonusDaOttenere.add(città);
			città.cittàVicinaConEmporio(giocatore, cittàConBonusDaOttenere);
			for(Città citt: cittàConBonusDaOttenere)
				citt.eseguiBonus(giocatore);
			giocatore.setAzionePrincipale(true);
			//controllo se ho gli empori in tutte le città di un colore o di una regione e prendo la
			//tessera bonus se mi spetta
			tabellone.prendiTesseraBonus(giocatore, città);
	
		}
		catch(IndexOutOfBoundsException e){
			System.err.println(e.getLocalizedMessage());
			//se viene catturata l' eccezione vuol dire che il giocatore
			//non ha abbastanza soldi, quindi il metodo termina senza 
			//apportare nessuna modifica.
		}
		catch(IllegalStateException e){
			System.err.println(e.getLocalizedMessage());
			//se viene catturata l' eccezione vuol dire che il giocatore ha scelto male le carte oppure
			//possiede già un emporio sulla città, quindi il metodo termina senza 
			//apportare nessuna modifica.
		}
	}

	/**
	 * @param re
	 *
	 */
	public CostruisciEmporioConRe(Re re, List<CartaPolitica> cartePolitica) {
		this.re = re;
		this.città=re.getCittà();
		this.cartePolitica=cartePolitica;
	}
}

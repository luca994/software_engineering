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
	/**
	 * builds an emporio in the same city where the king is situated. 
	 * Throws IllegalStateException when giocatore has already an emporio in the city. 
	 * Throws IllegalStateException when the number of CartaPolitica is not correct.
	 * Throws IndexOutOfBoundsException if an error occurs during the count of the cards
	 * Throws IndexOutOfBoundsException if giocatore hasn't enough money to perform the action(from method muoviGiocatore)
	 */
	public void eseguiAzione (Giocatore giocatore){
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
			giocatore.setAzionePrincipale(true);
		//}
		//Rimozione tessere selezionate dalla mano del giocatore, se il giocatore è stupido e seleziona tessere in più o del colore sbagliato sono affari suoi.
			giocatore.getCartePolitica().removeAll(cartePolitica);
			
			città.aggiungiEmporio(giocatore);
			giocatore.decrementaEmporiRimasti();
			giocatore.setAzionePrincipale(true);
		
		
	}

	/**
	 * @param re
	 * @param emporio
	 */
	public CostruisciEmporioConRe(Re re, Emporio emporio, List<CartaPolitica> cartePolitica) {
		this.re = re;
		this.città=re.getCittà();
		this.cartePolitica=cartePolitica;
	}
}

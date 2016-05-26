package server.model.azione;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.model.CartaPolitica;
import server.model.Citta;
import server.model.Consiglio;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.Regione;

/**
 * @author Luca
 *
 */
public class CostruisciEmporioConRe extends Azione {

	private Citta destinazione;
	private List<CartaPolitica> cartePolitica;

	/**
	 * @param re
	 *
	 */
	public CostruisciEmporioConRe(Gioco gioco, List<CartaPolitica> cartePolitica, Citta destinazione) {
		super(gioco);
		this.destinazione = destinazione;
		this.cartePolitica = cartePolitica;
	}

	/**
	 * Builds an emporio in the same city where the king is situated.
	 * 
	 * @throws IOException
	 * 
	 * 
	 * @throws IllegalStateException
	 *             when the number of CartaPolitica is not correct.
	 * @throws IndexOutOfBoundsException
	 *             if an error occurs during the count of the cards
	 * @throws IndexOutOfBoundsException
	 *             if giocatore hasn't enough money to perform the action(from
	 *             method muoviGiocatore)
	 */
	public void eseguiAzione(Giocatore giocatore) throws IOException {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		Consiglio consiglioDaSoddisfare = gioco.getTabellone().getRe().getConsiglio();
		int numeroCartePolitica = cartePolitica.size();
		int jollyUsati = 0;
		int counter = 0;
		if (numeroCartePolitica < 1 || numeroCartePolitica > 4)
			throw new IllegalArgumentException("Il numero di carte selezionato non è appropriato");
		List<Color> colori = consiglioDaSoddisfare.acquisisciColoriConsiglio();
		for (CartaPolitica car : cartePolitica) {
			if (car.getColore().equals(Color.red))
				jollyUsati++;
			for (Color col : colori) {
				if (car.getColore().equals(col)) {
					counter++;
					colori.remove(col);
					break;
				}
			}
		}

		switch (counter) {
		case 0: 
			throw new IllegalArgumentException("Non hai soddisfatto nessun consigliere");
		case 1:
			gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,-10-jollyUsati);
			break;
		case 2:
			gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,-7-jollyUsati);
			break;
		case 3:
			gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,-4-jollyUsati);
			break;
		case 4:
			break;
		default:
			throw new IndexOutOfBoundsException("Errore nel conteggio consiglieri da soddisfare");
		}
		
		/*
		 * Rimozione tessere selezionate dalla mano del giocatore, se il
		 * giocatore ha selezionato tessere in più o del colore sbagliato
		 * gli vengono comunque rimosse.
		 */
		
		giocatore.getCartePolitica().removeAll(cartePolitica);

		gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
				0 - 2 * gioco.getTabellone().getRe().contaPassi(destinazione));

		destinazione.getEmpori().add(giocatore);
		giocatore.decrementaEmporiRimasti();
		
		/* Se il giocatore ha finito gli empori guadagna 3 punti vittoria */
		if (giocatore.getEmporiRimasti() == 0){
			gioco.getTabellone().getPercorsoVittoria().muoviGiocatore(giocatore, 3);
			giocatore.getStatoGiocatore().tuttiGliEmporiCostruiti();}
		
		/* Prendo i bonus di questa e delle città collegate */
		List<Citta> cittaConBonusDaOttenere = new ArrayList<>();
		cittaConBonusDaOttenere.add(destinazione);
		destinazione.cittaVicinaConEmporio(giocatore, cittaConBonusDaOttenere);
		
		for (Citta citt : cittaConBonusDaOttenere)
			citt.eseguiBonus(giocatore);
		/*
		 * controllo se ho gli empori in
		 * tutte le città di un colore o di una regione e prendo la tessera
		 * bonus se mi spetta
		 */
		gioco.getTabellone().prendiTesseraBonus(giocatore, destinazione);
		
		giocatore.getStatoGiocatore().azionePrincipaleEseguita();
	}

	@Override
	public boolean verificaInput(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		if(destinazione.getEmpori().contains(giocatore))
			return false;
		if(giocatore.getCartePolitica().containsAll(cartePolitica))
			for(Regione reg: gioco.getTabellone().getRegioni())
				for(Citta cit: reg.getCittà())
					if(cit.equals(destinazione))
						return true;
		return false;
	}
	
}

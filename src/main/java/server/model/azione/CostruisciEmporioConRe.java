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
	 * @throws IllegalStateException
	 *             when giocatore has already an emporio in the city.
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
		if (destinazione.presenzaEmporio(giocatore))
			throw new IllegalStateException("L'emporio del giocatore è già presente in questa città");
		Consiglio consiglioDaSoddisfare = gioco.getTabellone().getRe().getConsiglio();
		int numeroCartePolitica = cartePolitica.size();
		int jollyUsati = 0;
		int counter = 0;
		if (numeroCartePolitica < 1 || numeroCartePolitica > 4)
			throw new IllegalStateException("Il numero di carte selezionato non è appropriato");
		// Creazione copie liste dei colori del consiglio
		List<Color> colori = consiglioDaSoddisfare.acquisisciColoriConsiglio();
		for (CartaPolitica car : cartePolitica) {
			if (car.getColore().equals(Color.red))
				jollyUsati++;// conto i jolly usati
			for (Color col : colori) {
				if (car.getColore().equals(col)) {
					counter++;
					colori.remove(col);
					break;
				}
			}
		}

		switch (counter) {
		case 1:
			gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, 0 - 10 - jollyUsati);
			break;
		case 2:
			gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, 0 - 7 - jollyUsati);
			break;
		case 3:
			gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, 0 - 4 - jollyUsati);
			break;
		case 4:
			break;
		default:
			throw new IndexOutOfBoundsException("Errore nel conteggio consiglieri da soddisfare");
		}
		// }
		// Rimozione tessere selezionate dalla mano del giocatore, se il
		// giocatore è stupido e seleziona tessere in più o del colore sbagliato
		// sono affari suoi.
		giocatore.getCartePolitica().removeAll(cartePolitica);

		gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
				0 - 2 * gioco.getTabellone().getRe().contaPassi(destinazione));

		destinazione.aggiungiEmporio(giocatore);
		giocatore.decrementaEmporiRimasti();
		// Se il giocatore ha finito gli empori guadagna 3 punti vittoria
		if (giocatore.getEmporiRimasti() == 0)
			gioco.getTabellone().getPercorsoVittoria().muoviGiocatore(giocatore, 3);
		// Mi piglio i bonus di questa e delle città collegate
		List<Citta> cittàConBonusDaOttenere = new ArrayList<Citta>();
		cittàConBonusDaOttenere.add(destinazione);
		destinazione.cittàVicinaConEmporio(giocatore, cittàConBonusDaOttenere);
		for (Citta citt : cittàConBonusDaOttenere)
			citt.eseguiBonus(giocatore);
		// giocatore.setAzionePrincipale(true);
		// controllo se ho gli empori in tutte le città di un colore o di una
		// regione e prendo la
		// tessera bonus se mi spetta
		gioco.getTabellone().prendiTesseraBonus(giocatore, destinazione);

	}

	@Override
	public boolean verificaInput(Giocatore giocatore) {
		// TODO Auto-generated method stub
		return false;
	}
}

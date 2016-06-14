package server.model.azione;

import java.util.ArrayList;
import java.util.List;

import eccezione.CartePoliticaIncorrette;
import eccezione.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.CartaColorata;
import server.model.componenti.CartaPolitica;
import server.model.componenti.Consiglio;
import server.model.componenti.Jolly;
import server.model.componenti.TesseraCostruzione;

/**
 * Main action that allows you to buy a card allowed after fulfilling the
 * council of a region
 *
 */
public class AcquistaPermesso extends AzionePrincipale {

	private final TesseraCostruzione tesseraScelta;
	private final List<CartaPolitica> cartePoliticaScelte;
	private final Consiglio consiglioDaSoddisfare;

	/**
	 * Constructor
	 * 
	 * @param tessera
	 * @param cartePolitica
	 * @param consiglioDaSoddisfare
	 * @param percorsoRicchezza
	 */
	public AcquistaPermesso(Gioco gioco, TesseraCostruzione tessera, List<CartaPolitica> cartePolitica,
			Consiglio consiglioDaSoddisfare) {
		super(gioco);
		this.tesseraScelta = tessera;
		this.cartePoliticaScelte = cartePolitica;
		this.consiglioDaSoddisfare = consiglioDaSoddisfare;
	}

	/**
	 * Performs the action
	 * 
	 * @throws CartePoliticaIncorrette
	 *             if the number of politics card selected is not appropriate or
	 *             with those politics card is not fulfill even a counselor.
	 * @throws FuoriDalLimiteDelPercorso
	 *             if the player does not have enough money
	 * @throws NullPointerException
	 *             if the player is null
	 * @throws IndexOutOfBoundsException
	 *             if there is an error on counting fulfilled counselor.
	 * 
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette {
		if (giocatore == null)
			throw new NullPointerException();
		if (cartePoliticaScelte.isEmpty() || cartePoliticaScelte.size() > consiglioDaSoddisfare.getConsiglieri().size())
			throw new CartePoliticaIncorrette("Il numero di carte Politica scelte non è corretto");
		List<Jolly> carteJollyUtilizzate = new ArrayList<>();
		for (CartaPolitica c : cartePoliticaScelte)
			if (c instanceof Jolly) {
				carteJollyUtilizzate.add((Jolly) c);
			}
		cartePoliticaScelte.removeAll(carteJollyUtilizzate);
		
		List<CartaColorata> carteColorateUtilizzate = consiglioDaSoddisfare.soddisfaConsiglio(cartePoliticaScelte);

		switch (carteColorateUtilizzate.size() + carteJollyUtilizzate.size()) {
		case 0:
			throw new CartePoliticaIncorrette("Nessun Consigliere soddisfatto");
		case 1:
			getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
					-10 - carteJollyUtilizzate.size());
			break;
		case 2:
			getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
					-7 - carteJollyUtilizzate.size());
			break;
		case 3:
			getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
					-4 - carteJollyUtilizzate.size());
			break;
		case 4:
			getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
					- carteJollyUtilizzate.size());
			break;
		default:
			throw new IndexOutOfBoundsException("Errore nel conteggio consiglieri da soddisfare");
		}

		/* Rimozione c selezionate dalla mano del giocatore */
		giocatore.getCartePolitica().removeAll(carteColorateUtilizzate);
		giocatore.getCartePolitica().removeAll(carteJollyUtilizzate);

	
			/*
			 * Aggiunta tessera acquistata al set di tessere valide del
			 * giocatore
			 */
			giocatore.getTessereValide().add(tesseraScelta);
			tesseraScelta.eseguiBonus(giocatore);

			/* Scopri dal mazzetto una nuova tessera costruzione */
			consiglioDaSoddisfare.getRegione().nuovaTessera(tesseraScelta);

			giocatore.getStatoGiocatore().azioneEseguita(this);
		
	}

}
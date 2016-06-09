package server.model.azione;

import java.util.ArrayList;
import java.util.List;

import eccezione.CartePoliticaIncorrette;
import eccezione.EmporioGiaCostruito;
import eccezione.FuoriDalLimiteDelPercorso;
import eccezione.NumeroAiutantiIncorretto;
import server.model.CartaColorata;
import server.model.CartaPolitica;
import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.Jolly;

/**
 * 
 * Main action that allows you to build an emporium. First you have to fulfill
 * the council of the king , then you can move the king by paying some money,
 * then you can build the store in the city in which is located the king now.
 *
 */
public class CostruisciEmporioConRe extends AzionePrincipale {

	private Citta destinazione;
	private List<CartaPolitica> cartePolitica;

	/**
	 * Constructor
	 *
	 */
	public CostruisciEmporioConRe(Gioco gioco, List<CartaPolitica> cartePolitica, Citta destinazione) {

		super(gioco);
		this.destinazione = destinazione;
		this.cartePolitica = cartePolitica;
	}

	/**
	 * Performs the action
	 * 
	 * @throws FuoriDalLimiteDelPercorso
	 *             if giocatore hasn't enough money to perform the action.
	 * 
	 * @throws CartePoliticaIncorrette
	 *             if the number of Politics cards selected is incorrect, or if
	 *             you have not fulfill even one councilor.
	 * @throws NumeroAiutantiIncorretto
	 *             if the player builds an emporium in a city where there are
	 *             already other emporiums of other player and has no helpers
	 *             needed to build
	 * @throws EmporioGiaCostruito
	 *             if there is already a player's emporium in the destination
	 *             city.
	 * @throws NullPointerException
	 *             if the player is null.
	 * 
	 *
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore)
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		if (giocatore == null)
			throw new NullPointerException();
		if (cartePolitica.isEmpty()
				|| cartePolitica.size() > getGioco().getTabellone().getRe().getConsiglio().getConsiglieri().size())
			throw new CartePoliticaIncorrette("Il numero di carte Politica scelte non è corretto");
		List<Jolly> carteJollyUtilizzate = new ArrayList<>();
		for (CartaPolitica c : cartePolitica)
			if (c instanceof Jolly) {
				carteJollyUtilizzate.add((Jolly) c);
			}
		cartePolitica.removeAll(carteJollyUtilizzate);

		List<CartaColorata> carteColorateUtilizzate = getGioco().getTabellone().getRe().getConsiglio()
				.soddisfaConsiglio(cartePolitica);
		if (destinazione.getEmpori().contains(giocatore))
			throw new EmporioGiaCostruito("Hai già un Emporio in questa città");
		if (destinazione.getEmpori().size() > giocatore.getAssistenti().size())
			throw new NumeroAiutantiIncorretto(
					"Il giocatore non ha abbastanza aiutanti per costruire l'emporio in quella posizione");
		switch (carteColorateUtilizzate.size() + carteJollyUtilizzate.size()) {
		case 0:
			throw new CartePoliticaIncorrette("Nessun Consigliere soddisfatto");
		case 1:
			getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
					-10 - carteJollyUtilizzate.size() - 2 * getGioco().getTabellone().getRe().contaPassi(destinazione));
			break;
		case 2:
			getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
					-7 - carteJollyUtilizzate.size() - 2 * getGioco().getTabellone().getRe().contaPassi(destinazione));
			break;
		case 3:
			getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
					-4 - carteJollyUtilizzate.size() - 2 * getGioco().getTabellone().getRe().contaPassi(destinazione));
			break;
		case 4:
			getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
					-2 * getGioco().getTabellone().getRe().contaPassi(destinazione));
			break;
		default:
			throw new IndexOutOfBoundsException("Errore nel conteggio consiglieri da soddisfare");
		}

		/* Paga gli aiutanti */
		for (int i = 0; i < destinazione.getEmpori().size(); i++)
			giocatore.getAssistenti().remove(0);
		/*
		 * Rimozione carte usate dalla mano del giocatore
		 */

		giocatore.getCartePolitica().removeAll(carteColorateUtilizzate);
		giocatore.getCartePolitica().removeAll(carteJollyUtilizzate);

		destinazione.getEmpori().add(giocatore);
		giocatore.decrementaEmporiRimasti();

		/* Se il giocatore ha finito gli empori guadagna 3 punti vittoria */
		if (giocatore.getEmporiRimasti() == 0) {
			getGioco().getTabellone().getPercorsoVittoria().muoviGiocatore(giocatore, 3);
			giocatore.getStatoGiocatore().tuttiGliEmporiCostruiti();
		}

		/* Prendo i bonus di questa e delle città collegate */
		List<Citta> cittaConBonusDaOttenere = new ArrayList<>();
		cittaConBonusDaOttenere.add(destinazione);
		destinazione.cittaVicinaConEmporio(giocatore, cittaConBonusDaOttenere);

		for (Citta citt : cittaConBonusDaOttenere) {
			if (citt.getBonus()!=null)
				citt.eseguiBonus(giocatore);
		}
		/*
		 * controllo se ho gli empori in tutte le città di un colore o di una
		 * regione e prendo la tessera bonus se mi spetta
		 */
		getGioco().getTabellone().prendiTesseraBonus(giocatore, destinazione);

		giocatore.getStatoGiocatore().azioneEseguita(this);
	}

}

package server.model.azione;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.model.CartaColorata;
import server.model.CartaPolitica;
import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.Jolly;
import server.model.Regione;

/**
 * @author Luca
 *
 */
public class CostruisciEmporioConRe extends AzionePrincipale {

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
		if(giocatore==null)
			throw new NullPointerException();
		List<Jolly> carteJollyUtilizzate = new ArrayList<>();
		for(CartaPolitica c:cartePolitica)
			if(c instanceof Jolly){
				carteJollyUtilizzate.add((Jolly)c);
				cartePolitica.remove(c);
			}
		List<CartaColorata> carteColorateUtilizzate = getGioco().getTabellone().getRe().getConsiglio().soddisfaConsiglio(cartePolitica);
			
		switch (carteColorateUtilizzate.size()+carteJollyUtilizzate.size()) {
			case 0:
				throw new IllegalStateException("Nessun Consigliere soddisfatto");
			case 1:
				getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, -10-carteJollyUtilizzate.size());
				break;
			case 2:
				getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, -7-carteJollyUtilizzate.size());
				break;
			case 3:
				getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, -4-carteJollyUtilizzate.size());
				break;
			case 4:
				break;
			default:
				throw new IndexOutOfBoundsException("Errore nel conteggio consiglieri da soddisfare");
			}
		
		/*
		 * Rimozione carte usate dalla mano del giocatore
		 */
		
		giocatore.getCartePolitica().removeAll(carteColorateUtilizzate);
		giocatore.getCartePolitica().removeAll(carteJollyUtilizzate);

		getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore,
				0 - 2 * getGioco().getTabellone().getRe().contaPassi(destinazione));

		destinazione.getEmpori().add(giocatore);
		giocatore.decrementaEmporiRimasti();
		
		/* Se il giocatore ha finito gli empori guadagna 3 punti vittoria */
		if (giocatore.getEmporiRimasti() == 0){
			getGioco().getTabellone().getPercorsoVittoria().muoviGiocatore(giocatore, 3);
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
		getGioco().getTabellone().prendiTesseraBonus(giocatore, destinazione);
		
		giocatore.getStatoGiocatore().azioneEseguita(this);
	}

	@Override
	public boolean verificaInput(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		if(destinazione.getEmpori().contains(giocatore))
			return false;
		if(giocatore.getCartePolitica().containsAll(cartePolitica))
			for(Regione reg: getGioco().getTabellone().getRegioni())
				for(Citta cit: reg.getCitta())
					if(cit.equals(destinazione))
						return true;
		return false;
	}
	
}

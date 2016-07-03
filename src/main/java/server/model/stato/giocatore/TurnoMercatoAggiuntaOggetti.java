package server.model.stato.giocatore;

import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.azione.Azione;
import server.model.componenti.Mercato;
import server.model.componenti.OggettoVendibile;

/**
 * The class that represents the state in which the player can put on sale objects
 */
public class TurnoMercatoAggiuntaOggetti extends TurnoMercato {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8664434123655986775L;
	
	private Mercato mercato;

	/**
	 * Constructor for TurnoMercatoAggiuntaOggetti
	 * @param Mercato the associated market at that stage of the game where the player can add his objects
	 */
	public TurnoMercatoAggiuntaOggetti(Giocatore giocatore, Mercato mercato) {
		super(giocatore);
		this.mercato = mercato;
	}

	/**
	 * Selling its salable object of the player, with the price chosen by the player
	 */
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere) {
		if(oggettoDaAggiungere.getPrezzo()<=0)
			throw new IllegalArgumentException("Il prezzo deve essere un numero positivo");
		oggettoDaAggiungere.setMercato(mercato);
		oggettoDaAggiungere.setGiocatore(giocatore);
		oggettoDaAggiungere.aggiungiOggetto(mercato);
	}

	/**
	 * sets the state of the player to the next state
	 */
	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new AttesaTurno(giocatore));
	}

	/**
	 * this method can not be called when the player is in the market state
	 */
	@Override
	public void azioneEseguita(Azione azione) {
		throw new IllegalStateException();
	}

	/**
	 * this method can not be called when the player is in the market state
	 */
	@Override
	public void azionePrincipaleAggiuntiva() {
		throw new IllegalStateException();
	}

	/**
	 * this method can not be called when the player is in the market state
	 */
	@Override
	public void azioneRapidaAggiuntiva() {
		throw new IllegalStateException();
	}

	/**
	 * this method can not be called when the player is in the market state
	 */
	@Override
	public void tuttiGliEmporiCostruiti() {
		throw new IllegalStateException();
	}

	/**
	 * this method can not be called when the player is in the market state
	 */
	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) throws FuoriDalLimiteDelPercorso {
		throw new IllegalStateException();
	}
}

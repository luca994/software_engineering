package server.model.stato.giocatore;

import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.azione.Azione;
import server.model.componenti.OggettoVendibile;

/**
 * The class that represents the state in which the player can buy from the market players of other objects
 */
public class TurnoMercatoCompraVendita extends TurnoMercato {

	/**
	 * 
	 */
	private static final long serialVersionUID = 895962931696841870L;

	/**
	 * The constructor for TurnoMercatoCompraVendita
	 */
	public TurnoMercatoCompraVendita(Giocatore giocatore) {
		super(giocatore);
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
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere) {
		throw new IllegalStateException();
	}

	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) throws FuoriDalLimiteDelPercorso {
		oggettoDaAcquistare.compra(giocatore);
	}

}

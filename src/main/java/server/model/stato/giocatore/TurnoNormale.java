package server.model.stato.giocatore;

import server.config.Configurazione;
import server.model.Giocatore;
import server.model.azione.Azione;
import server.model.azione.AzionePrincipale;
import server.model.azione.AzioneRapida;
import server.model.componenti.CartaPoliticaFactory;
import server.model.componenti.OggettoVendibile;

/**
 * The class that represents the state in which the player can perform normal
 * actions
 */
public class TurnoNormale extends StatoGiocatore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2774878766914139008L;

	private int azioniPrincipaliEseguibili;
	private int azioniRapideEseguibili;
	private boolean tuttiGliEmporiCostruiti;

	/**
	 * the constructor for TurnoNormale state
	 * 
	 * @param giocatore
	 */
	public TurnoNormale(Giocatore giocatore) {
		super(giocatore);
		this.azioniPrincipaliEseguibili = Configurazione.AZIONI_PRINCIPALI_PER_TURNO;
		this.azioniRapideEseguibili = Configurazione.AZIONI_RAPIDE_PER_TURNO;
		giocatore.getCartePolitica().add(new CartaPoliticaFactory().creaCartaPolitica());
	}

	/**
	 * This method should be called every time an AzionePrincipale is performed.
	 * decrements the action's counter and checks if it is finished the turn.
	 * 
	 * @throws IllegalStateException
	 *             if the main actions still make include equal to 0 or even a
	 *             negative number
	 */
	public void azioneEseguita(Azione azione) {
		if (azioniPrincipaliEseguibili < 0 || azioniRapideEseguibili < 0) {
			throw new IllegalStateException("Errore nel conteggio delle azioni eseguite");
		}
		if (azione instanceof AzionePrincipale)
			azioniPrincipaliEseguibili--;
		if (azione instanceof AzioneRapida)
			azioniRapideEseguibili--;
		if (azioniPrincipaliEseguibili == 0 && azioniRapideEseguibili == 0) {
			synchronized (this.giocatore.getStatoGiocatore()) {
				prossimoStato();
			}
		}
	}

	/**
	 * increases the counter of main actions still available to the player for
	 * this turn
	 */
	@Override
	public void azionePrincipaleAggiuntiva() {
		azioniPrincipaliEseguibili++;
	}

	/**
	 * increases the counter of rapid actions still available to the player for
	 * this turn
	 */
	@Override
	public void azioneRapidaAggiuntiva() {
		azioniRapideEseguibili++;

	}

	/**
	 * is called when a player has built all its stores and sets the boolean
	 * tuttiGliEmporiCostruiti to true
	 */
	@Override
	public void tuttiGliEmporiCostruiti() {
		tuttiGliEmporiCostruiti = true;
	}

	/**
	 * sets the state of the player to the next state, if the player has built
	 * all emporiums is put in the state of rounds concluded else, the state is
	 * set to attesaTurno
	 */
	@Override
	public void prossimoStato() {
		if (tuttiGliEmporiCostruiti)
			giocatore.setStatoGiocatore(new TurniConclusi(giocatore));
		else
			giocatore.setStatoGiocatore(new AttesaTurno(giocatore));
	}

	/**
	 * this method can not be called in this state
	 */
	@Override
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere) {
		throw new IllegalStateException();
	}

	/**
	 * this method can not be called in this state
	 */
	@Override
	public void compraOggetto(OggettoVendibile oggettoDaAcquistare) {
		throw new IllegalStateException();
	}

	/**
	 * @return the azioniPrincipaliEseguibili
	 */
	public int getAzioniPrincipaliEseguibili() {
		return azioniPrincipaliEseguibili;
	}

	/**
	 * @return the azioniRapideEseguibili
	 */
	public int getAzioniRapideEseguibili() {
		return azioniRapideEseguibili;
	}

}

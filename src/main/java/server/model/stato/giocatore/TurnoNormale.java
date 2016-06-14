package server.model.stato.giocatore;

import server.model.Giocatore;
import server.model.azione.Azione;
import server.model.azione.AzionePrincipale;
import server.model.azione.AzioneRapida;
import server.model.componenti.CartaPoliticaFactory;
import server.model.componenti.OggettoVendibile;

public class TurnoNormale extends StatoGiocatore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2774878766914139008L;

	private static final int AZIONI_PRINCIPALI_PER_TURNO = 1;
	private static final int AZIONI_RAPIDE_PER_TURNO = 1;

	private int azioniPrincipaliEseguibili;
	private int azioniRapideEseguibili;
	private boolean tuttiGliEmporiCostruiti;

	public TurnoNormale(Giocatore giocatore) {
		super(giocatore);
		this.azioniPrincipaliEseguibili = AZIONI_PRINCIPALI_PER_TURNO;
		this.azioniRapideEseguibili = AZIONI_RAPIDE_PER_TURNO;
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

	@Override
	public void azionePrincipaleAggiuntiva() {
		azioniPrincipaliEseguibili++;
	}

	@Override
	public void azioneRapidaAggiuntiva() {
		azioniRapideEseguibili++;

	}

	@Override
	public void tuttiGliEmporiCostruiti() {
		tuttiGliEmporiCostruiti = true;
	}

	@Override
	public void prossimoStato() {
		if (tuttiGliEmporiCostruiti)
			giocatore.setStatoGiocatore(new TurniConclusi(giocatore));
		else
			giocatore.setStatoGiocatore(new AttesaTurno(giocatore));
	}

	@Override
	public void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere) {
		throw new IllegalStateException();
	}

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

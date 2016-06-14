package server.model.azione;

import eccezione.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Consigliere;
import server.model.componenti.Consiglio;

/**
 * Main action that allows you to elect a councilor. Choose one of the
 * counselors available from the board , put it on the board by eliminating the
 * last. Gain 4 coins.
 *
 */
public class EleggiConsigliere extends AzionePrincipale {

	private static final int MONETE_ELEZIONE_CONSIGLIERE = 4;

	private Consigliere consigliere;
	private Consiglio consiglio;

	/**
	 * @param consigliere
	 * @param consiglio
	 * @param percorsoRicchezza
	 */
	public EleggiConsigliere(Gioco gioco, Consigliere consigliere, Consiglio consiglio) {
		super(gioco);
		this.consigliere = consigliere;
		this.consiglio = consiglio;
	}

	/**
	 * Performs the action
	 * 
	 * @throws NullPointerException
	 *             if the player is null
	 * @throws IllegalArgumentException
	 *             if there is an error and the player moves out of the limit.
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		try {
			getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, MONETE_ELEZIONE_CONSIGLIERE);
			consiglio.addConsigliere(consigliere);
			consiglio.removeConsigliere();
		} catch (FuoriDalLimiteDelPercorso e) {
			throw new IllegalArgumentException(e);
		}
		giocatore.getStatoGiocatore().azioneEseguita(this);
	}

	/**
	 * @return the consigliere
	 */
	public Consigliere getConsigliere() {
		return consigliere;
	}

	/**
	 * @param consigliere
	 *            the consigliere to set
	 */
	public void setConsigliere(Consigliere consigliere) {
		this.consigliere = consigliere;
	}

	/**
	 * @return the consiglio
	 */
	public Consiglio getConsiglio() {
		return consiglio;
	}

	/**
	 * @param consiglio
	 *            the consiglio to set
	 */
	public void setConsiglio(Consiglio consiglio) {
		this.consiglio = consiglio;
	}
}

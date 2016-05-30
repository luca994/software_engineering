package server.model.azione;

import server.model.Consigliere;
import server.model.Consiglio;
import server.model.Giocatore;
import server.model.Regione;

/**
 * @author Luca
 *
 */
public class EleggiConsigliereRapido extends Azione {

	private static final int NUM_AIUTANTI_COSTO_ELEGGI_CONSIGLIERE_RAPIDO = 1;

	private Consigliere consigliere;
	private Consiglio consiglio;

	/**
	 * @param consigliere
	 * @param consiglio
	 */
	public EleggiConsigliereRapido(Consigliere consigliere, Consiglio consiglio) {
		super(null);
		this.consigliere = consigliere;
		this.consiglio = consiglio;
	}

	/**
	 * Elects a specified Consigliere,
	 * 
	 * @throws IllegalStateException
	 *             if giocatore hasn't enough Aiutanti
	 * @param giocatore
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		if (giocatore.getAssistenti().size() < NUM_AIUTANTI_COSTO_ELEGGI_CONSIGLIERE_RAPIDO)
			throw new IllegalStateException("Il giocatore non possiede abbastanza aiutanti par eseguire l'azione");

		consiglio.addConsigliere(consigliere);
		consiglio.removeConsigliere();
		giocatore.getAssistenti().remove(0);
		giocatore.getStatoGiocatore().azioneRapidaEseguita();

	}

	public boolean verificaInput(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		boolean consigliereValido = false;
		boolean consiglioValido = false;
		if (getGioco().getTabellone().getConsiglieriDisponibili().contains(consigliere))
			consigliereValido = true;
		for (Regione regione : getGioco().getTabellone().getRegioni()) {
			if (regione.getConsiglio().equals(consiglio)) {
				consiglioValido = true;
				break;
			}
		}
		return consiglioValido && consigliereValido;
	}
}

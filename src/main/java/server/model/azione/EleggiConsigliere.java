package server.model.azione;

import java.io.IOException;

import server.model.Consigliere;
import server.model.Consiglio;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.Regione;

/**
 * @author Luca
 *
 */
public class EleggiConsigliere extends Azione {

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
	 * elects a new counselor
	 * 
	 * @throws IOException
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) throws IOException {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		consiglio.addConsigliere(consigliere);
		consiglio.removeConsigliere();
		getGioco().getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, MONETE_ELEZIONE_CONSIGLIERE);
		giocatore.getStatoGiocatore().azionePrincipaleEseguita();
	}

	@Override
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

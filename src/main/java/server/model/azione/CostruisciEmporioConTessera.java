package server.model.azione;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.model.Citta;
import server.model.Giocatore;
import server.model.TesseraCostruzione;

/**
 * @author Luca
 *
 */
public class CostruisciEmporioConTessera extends Azione {

	private Citta citta;
	private TesseraCostruzione tessera;

	/**
	 * @param citta
	 * @param tessera
	 */
	public CostruisciEmporioConTessera(Citta citta, TesseraCostruzione tessera) {
		super(null);
		this.citta = citta;
		this.tessera = tessera;
	}

	/**
	 * Build a new emporio using a Tessera Costruzione owned by the player
	 * 
	 * @throws NullPointerException if giocatore is null
	 * @throws IOException
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) throws IOException {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		 {
			giocatore.moveTesseraValidaToTesseraUsata(tessera);
			citta.aggiungiEmporio(giocatore);
			giocatore.decrementaEmporiRimasti();

			// Se il giocatore ha finito gli empori guadagna 3 punti vittoria
			if (giocatore.getEmporiRimasti() == 0)
				gioco.getTabellone().getPercorsoVittoria().muoviGiocatore(giocatore, 3);

			// Il giocatore ottiene i bonus di questa e delle città collegate

			List<Citta> cittàConBonusDaOttenere = new ArrayList<Citta>();
			cittàConBonusDaOttenere.add(citta);
			citta.cittàVicinaConEmporio(giocatore, cittàConBonusDaOttenere);

			/*
			 * la lista cittàConBonusDaOttenere che viene creata è un lista di
			 * città in cui viene messa inizialmente la città corrente, poi
			 * viene chiamato il metodo cittàVicinaConEmporio che riempe la
			 * lista con tutte le città adiacenti che hanno un emporio del
			 * giocatore.
			 */

			for (Citta citt : cittàConBonusDaOttenere)
				citt.eseguiBonus(giocatore);
			// giocatore.setAzionePrincipale(true);

			/*
			 * controllo se ho gli empori in tutte le città di un colore o di
			 * una regione e prendo la tessera bonus se mi spetta (IL controllo
			 * viene fatto direttamente dal metodo del tabellone
			 * prendiTesseraBonus)
			 */

			gioco.getTabellone().prendiTesseraBonus(giocatore, citta);
		} 
			throw new IllegalStateException("L'emporio è già presente oppure la tessera non è appropriata");
	}

	@Override
	public boolean verificaInput(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		boolean tesseraValida = false;
		boolean cittaValida = false;
		if (giocatore.getTessereValide().contains(tessera))
			tesseraValida = true;
		if (tessera.getCittà().contains(citta))
			cittaValida = true;
		return tesseraValida && cittaValida;
	}

	/**
	 * @return the città
	 */
	public Citta getCittà() {
		return citta;
	}

	/**
	 * @param citta
	 *            the città to set
	 */
	public void setCittà(Citta citta) {
		this.citta = citta;
	}

	/**
	 * @return the tessera
	 */
	public TesseraCostruzione getTessera() {
		return tessera;
	}

	/**
	 * @param tessera
	 *            the tessera to set
	 */
	public void setTessera(TesseraCostruzione tessera) {
		this.tessera = tessera;
	}

}

package server.model.stato.giocatore;

import java.io.Serializable;

import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.azione.Azione;
import server.model.componenti.OggettoVendibile;
import server.model.stato.Stato;

/**
 * The abstract class that represents a generic state of a player
 *
 */
public abstract class StatoGiocatore implements Stato, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2019327985471094584L;
	
	protected final Giocatore giocatore;

	/**
	 * The generic constructor for the class statoGiocatore
	 */
	public StatoGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}

	public abstract void azioneEseguita(Azione azione);

	public abstract void azionePrincipaleAggiuntiva();

	public abstract void azioneRapidaAggiuntiva();

	public abstract void tuttiGliEmporiCostruiti();

	public abstract void prossimoStato();

	public abstract void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere);

	public abstract void compraOggetto(OggettoVendibile oggettoDaAcquistare) throws FuoriDalLimiteDelPercorso;
}

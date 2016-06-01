package server.model.stato.giocatore;

import eccezione.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.OggettoVendibile;
import server.model.azione.Azione;
import server.model.stato.Stato;

public abstract class StatoGiocatore implements Stato {

	protected final Giocatore giocatore;

	public StatoGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}

	public abstract void azioneEseguita(Azione azione);

	public abstract void azionePrincipaleAggiuntiva();

	public abstract void azioneRapidaAggiuntiva();

	public abstract void tuttiGliEmporiCostruiti();

	public abstract void prossimoStato();

	public abstract void mettiInVenditaOggetto(OggettoVendibile oggettoDaAggiungere, int prezzo);

	public abstract void compraOggetto(OggettoVendibile oggettoDaAcquistare) throws FuoriDalLimiteDelPercorso;
}

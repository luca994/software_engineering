package server.model.stato.gioco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.Mercato;
import server.model.OggettoVendibile;
import server.model.stato.giocatore.TurnoMercatoCompraVendita;

public class FaseTurnoMercatoCompraVendita extends FaseTurnoMercato {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2147829160107829341L;
	private Mercato mercato;

	public FaseTurnoMercatoCompraVendita(Gioco gioco, Mercato mercato) {
		super(gioco);
		this.mercato = mercato;
	}

	@Override
	public void eseguiFase() {
		List<Giocatore> listaTurniMercatoGiocatori = new ArrayList<>();
		Collections.copy(listaTurniMercatoGiocatori, getGiocatori());
		Collections.shuffle(listaTurniMercatoGiocatori);
		for (Giocatore giocat : listaTurniMercatoGiocatori) {
			giocat.setStatoGiocatore(new TurnoMercatoCompraVendita(giocat));
			while (true) {
				synchronized (giocat.getStatoGiocatore()) {
					if (!(giocat.getStatoGiocatore() instanceof TurnoMercatoCompraVendita))
						break;
				}
			}
		}
		/*
		 * Ora che il mercato è finito, posso svuotarlo e resettare gli
		 * attributi degli oggetti invenduti lasciando il vecchio
		 * proprietario,ma togliendo il reference al mercato. Poi posso
		 * rimuovere l'oggetto dalla lista degli oggetti in vendita. Nessuno
		 * avrà più il reference al mercato, che sarà quindi cancellato dal
		 * GarbageCollector
		 */
		for (OggettoVendibile ogg : mercato.getOggettiInVendita()) {
			ogg.resettaAttributiOggettoVendibile(ogg.getGiocatore());
			mercato.getOggettiInVendita().remove(ogg);
		}

	}

	@Override
	public void prossimoStato() {
		getGioco().setStato(new FaseTurnoSemplice(getGioco()));
	}
}

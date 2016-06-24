package server.model.stato.gioco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Mercato;
import server.model.componenti.OggettoVendibile;
import server.model.stato.giocatore.Sospeso;
import server.model.stato.giocatore.TurnoMercatoCompraVendita;

public class FaseTurnoMercatoCompraVendita extends FaseTurnoMercato{

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
		listaTurniMercatoGiocatori.addAll(getGiocatori());
		Collections.shuffle(listaTurniMercatoGiocatori);
		for (Giocatore giocat : listaTurniMercatoGiocatori) {
			if(!(giocat.getStatoGiocatore() instanceof Sospeso)){
				//avvio il thread che controlla il tempo per l'azione
				setGiocatoreCorrente(giocat);
				Thread threadTempo = new Thread(this);
				threadTempo.start();
				
				giocat.setStatoGiocatore(new TurnoMercatoCompraVendita(giocat));
				getGioco().notificaObservers(getGioco().getTabellone());
				while (true) {
					synchronized (giocat.getStatoGiocatore()) {
						if (!(giocat.getStatoGiocatore() instanceof TurnoMercatoCompraVendita))
							break;
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		setGiocatoreCorrente(null);
		/*
		 * Ora che il mercato è finito, posso svuotarlo e resettare gli
		 * attributi degli oggetti invenduti togliendo il reference al mercato. Poi posso
		 * rimuovere l'oggetto dalla lista degli oggetti in vendita. Nessuno
		 * avrà più il reference al mercato, che sarà quindi cancellato dal
		 * GarbageCollector
		 */
		for (OggettoVendibile ogg : mercato.getOggettiInVendita()) {
			ogg.resettaAttributiOggettoVendibile();
		}
		mercato.getOggettiInVendita().clear();
	}

	@Override
	public void prossimoStato() {
		getGioco().setStato(new FaseTurnoSemplice(getGioco()));
	}

	/**
	 * @return the mercato
	 */
	public Mercato getMercato() {
		return mercato;
	}
}

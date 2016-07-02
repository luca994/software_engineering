/**
 * 
 */
package server.controller;

import java.util.ArrayList;
import java.util.List;

import client.view.MessaggioChat;
import server.eccezioni.EccezioneConsiglioDeiQuattro;
import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.Azione;
import server.model.azione.AzionePrincipale;
import server.model.azione.AzioneRapida;
import server.model.bonus.Bonus;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.componenti.Citta;
import server.model.componenti.OggettoVendibile;
import server.model.componenti.Regione;
import server.model.componenti.TesseraCostruzione;
import server.model.stato.giocatore.Sospeso;
import server.model.stato.giocatore.TurnoMercato;
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;
import server.model.stato.giocatore.TurnoMercatoCompraVendita;
import server.model.stato.giocatore.TurnoNormale;
import server.model.stato.gioco.FaseTurnoMercatoCompraVendita;
import server.observer.Observer;

/**
 * @author Massimiliano Ventura
 *
 */
public class Controller implements Observer<Object, Bonus> {

	private final Gioco gioco;

	public Controller(Gioco gioco) {
		if(gioco==null)
			throw new NullPointerException();
		this.gioco = gioco;
	}

	public synchronized void updateBonus(Bonus cambiamento, Giocatore giocatore) {
		if (cambiamento instanceof BonusGettoneCitta) {
			try {
				if (((BonusGettoneCitta) cambiamento).getCittaPerCompletamentoBonus() != null) {
					Citta citta = this.gioco.getTabellone()
							.cercaCitta(((BonusGettoneCitta) cambiamento).getCittaPerCompletamentoBonus().getNome());
					if (citta == null) {
						((BonusGettoneCitta) cambiamento).setCittaGiusta(false);
						gioco.notificaObservers("nome città non corretto", giocatore);
						return;
					}
					if (!((BonusGettoneCitta) cambiamento).getCitta().add(citta)) {
						((BonusGettoneCitta) cambiamento).setCittaGiusta(false);
						gioco.notificaObservers("la città inserita è già stata scelta", giocatore);
					} else
						return;
				} else {
					return;
				}
			} catch (IllegalArgumentException e) {
				gioco.notificaObservers(e.getMessage(), giocatore);
			}
		}
		if (cambiamento instanceof BonusTesseraPermesso) {
			boolean tesseraTrovata = false;
			if(((BonusTesseraPermesso) cambiamento).getTessera()==null){
				((BonusTesseraPermesso) cambiamento).setTesseraCorretta(true);
				tesseraTrovata= true;
				return;
			}
			for (Regione r : gioco.getTabellone().getRegioni()) {
				for (TesseraCostruzione t : r.getTessereCostruzione()) {
					if (t.isUguale(((BonusTesseraPermesso) cambiamento).getTessera())) {
						((BonusTesseraPermesso) cambiamento).setTessera(t);
						tesseraTrovata = true;
						((BonusTesseraPermesso) cambiamento).setTesseraCorretta(true);
					}
				}
			}
			if (!tesseraTrovata) {
				gioco.notificaObservers("tessera inserita non corretta", giocatore);
				((BonusTesseraPermesso) cambiamento).setTesseraCorretta(false);
			}
		}
		if (cambiamento instanceof BonusRiutilizzoCostruzione) {
			if (((BonusRiutilizzoCostruzione) cambiamento).getTessera() == null) {
				((BonusRiutilizzoCostruzione) cambiamento).setTesseraCostruzioneCorretta(true);
				return;
			}
			List<TesseraCostruzione> tmp = new ArrayList<>(giocatore.getTessereValide());
			tmp.addAll(giocatore.getTessereUsate());
			boolean tesseraCorretta = false;
			for (TesseraCostruzione t : tmp) {
				if (((BonusRiutilizzoCostruzione) cambiamento).getTessera().isUguale(t)) {
					((BonusRiutilizzoCostruzione) cambiamento).setTessera(t);
					((BonusRiutilizzoCostruzione) cambiamento).setTesseraCostruzioneCorretta(true);
					tesseraCorretta = true;
				}
			}
			if (!tesseraCorretta) {
				gioco.notificaObservers("tessera non corretta", giocatore);
			}
		}
	}

	@Override
	public synchronized void update(Object oggetto, Giocatore giocatore) {
		if (oggetto instanceof Azione) {
			if (giocatore.getStatoGiocatore() instanceof TurnoNormale) {
				try {
					if (oggetto instanceof AzionePrincipale
							&& ((TurnoNormale) giocatore.getStatoGiocatore()).getAzioniPrincipaliEseguibili() <= 0) {
						gioco.notificaObservers("Non hai più azioni principali", giocatore);
						gioco.notificaObservers(gioco.getTabellone(), giocatore);
					} else if (oggetto instanceof AzioneRapida
							&& ((TurnoNormale) giocatore.getStatoGiocatore()).getAzioniRapideEseguibili() <= 0) {
						gioco.notificaObservers("Non hai più azioni rapide", giocatore);
						gioco.notificaObservers(gioco.getTabellone(), giocatore);
					} else {
						((Azione) oggetto).eseguiAzione(giocatore);
						gioco.notificaObservers("Azione Eseguita!", giocatore);
						if (giocatore.getStatoGiocatore() instanceof TurnoNormale){
							gioco.notificaObservers(gioco.getTabellone(), giocatore);
						}
					}
				} catch (EccezioneConsiglioDeiQuattro e) {
					gioco.notificaObservers(e, giocatore);
					gioco.notificaObservers(gioco.getTabellone(), giocatore);
				}
			} else {
				gioco.notificaObservers("Non è il tuo turno", giocatore);
			}
		}
		else if (oggetto instanceof String && "Sospendi".equalsIgnoreCase((String) oggetto)) {
			giocatore.setStatoGiocatore(new Sospeso(giocatore));
		}
		else if (oggetto instanceof String && "-".equals((String) oggetto)
				&& giocatore.getStatoGiocatore() instanceof TurnoMercato) {
			synchronized (giocatore.getStatoGiocatore()) {
				giocatore.getStatoGiocatore().prossimoStato();
			}
		}
		else if (oggetto instanceof OggettoVendibile) {
			if (giocatore.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti) {
				giocatore.getStatoGiocatore().mettiInVenditaOggetto((OggettoVendibile) oggetto);
				gioco.notificaObservers("Oggetto aggiunto con successo", giocatore);
				gioco.notificaObservers(gioco.getTabellone(), giocatore);
			}
			if (giocatore.getStatoGiocatore() instanceof TurnoMercatoCompraVendita) {
				try {
					giocatore.getStatoGiocatore().compraOggetto(((FaseTurnoMercatoCompraVendita) gioco.getStato())
							.getMercato().cercaOggetto((OggettoVendibile) oggetto));
					gioco.notificaObservers("Oggetto acquistato", giocatore);
				} catch (FuoriDalLimiteDelPercorso e) {
					gioco.notificaObservers("Non hai abbastanza soldi per acquistare questo oggetto", giocatore);
				} finally {
					gioco.notificaObservers(gioco.getTabellone(), giocatore);
				}
			}
		}
		else if (oggetto instanceof Bonus) {
			updateBonus((Bonus) oggetto, giocatore);
		}
		else
			throw new IllegalStateException("Non conosco l'oggetto ricevuto");
	}

	@Override
	public synchronized void update(Object cambiamento) {
		if (cambiamento instanceof MessaggioChat) {
			gioco.notificaObservers(cambiamento);
		}
		else
			throw new UnsupportedOperationException();
	}

	/**Not used*/
	@Override
	public void update(Bonus cambiamento, List<String> input) {
		throw new UnsupportedOperationException();
	}
}

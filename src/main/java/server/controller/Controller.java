/**
 * 
 */
package server.controller;

import java.util.ArrayList;
import java.util.List;

import eccezione.CartePoliticaIncorrette;
import eccezione.EmporioGiaCostruito;
import eccezione.FuoriDalLimiteDelPercorso;
import eccezione.NumeroAiutantiIncorretto;
import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.OggettoVendibile;
import server.model.Regione;
import server.model.TesseraCostruzione;
import server.model.azione.Azione;
import server.model.azione.AzionePrincipale;
import server.model.azione.AzioneRapida;
import server.model.bonus.Bonus;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.stato.giocatore.AttesaTurno;
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

	private Gioco gioco;

	public Controller(Gioco gioco) {
		this.gioco = gioco;
	}

	public void updateBonus(Bonus cambiamento, Giocatore giocatore) {
		if (cambiamento instanceof BonusGettoneCitta) {
			try {
				List<Citta> tmp = new ArrayList<>(((BonusGettoneCitta) cambiamento).getCitta());
				if (!tmp.isEmpty()) {
					Citta citta = this.gioco.getTabellone().cercaCitta(tmp.get(0).getNome());
					if(citta==null){
						((BonusGettoneCitta) cambiamento).setCittaGiusta(false);
						gioco.notificaObservers("nome città non corretto", giocatore);
						return;
					}
					if (!((BonusGettoneCitta) cambiamento).getCitta().add(citta)) {
						((BonusGettoneCitta) cambiamento).setCittaGiusta(false);
						gioco.notificaObservers("la città inserita è già stata scelta", giocatore);
					} else
						return;
				}
				else{
					return;
				}
			} catch (IllegalArgumentException e) {
				gioco.notificaObservers(e.getMessage());
			}
		}
		if (cambiamento instanceof BonusTesseraPermesso) {
			boolean tesseraTrovata = false;
			for(Regione r:gioco.getTabellone().getRegioni()){
				for(TesseraCostruzione t: r.getTessereCostruzione()){
					if(t.isUguale(((BonusTesseraPermesso) cambiamento).getTessera())){
						((BonusTesseraPermesso) cambiamento).setTessera(t);
						tesseraTrovata=true;
						((BonusTesseraPermesso) cambiamento).setTesseraCorretta(true);
					}
				}
			}
			if(!tesseraTrovata){
				gioco.notificaObservers("tessera inserita non corretta", giocatore);
				((BonusTesseraPermesso) cambiamento).setTesseraCorretta(false);
			}
		}
		if (cambiamento instanceof BonusRiutilizzoCostruzione) {
			if(((BonusRiutilizzoCostruzione) cambiamento).getTessera()==null){
				((BonusRiutilizzoCostruzione) cambiamento).setTesseraCostruzioneCorretta(true);
				return;
			}
			List<TesseraCostruzione> tmp = new ArrayList<>(giocatore.getTessereValide());
			tmp.addAll(giocatore.getTessereUsate());
			boolean tesseraCorretta = false;
			for(TesseraCostruzione t:tmp){
				if(((BonusRiutilizzoCostruzione) cambiamento).getTessera().isUguale(t)){
					((BonusRiutilizzoCostruzione) cambiamento).setTessera(t);
					((BonusRiutilizzoCostruzione) cambiamento).setTesseraCostruzioneCorretta(true);
					tesseraCorretta=true;
				}
			}
			if(!tesseraCorretta){
				gioco.notificaObservers("tessera non corretta", giocatore);
			}
		}
	}

	@Override
	public void update(Object oggetto, Giocatore giocatore) {
		if (oggetto instanceof Azione) {
			if (giocatore.getStatoGiocatore() instanceof TurnoNormale) {
				try {
					if (oggetto instanceof AzionePrincipale
							&& ((TurnoNormale) giocatore.getStatoGiocatore()).getAzioniPrincipaliEseguibili() <= 0)
						gioco.notificaObservers("Non hai più azioni principali", giocatore);
					else if (oggetto instanceof AzioneRapida
							&& ((TurnoNormale) giocatore.getStatoGiocatore()).getAzioniRapideEseguibili() <= 0)
						gioco.notificaObservers("Non hai più azioni rapide", giocatore);
					else {
						((Azione) oggetto).eseguiAzione(giocatore);
						gioco.notificaObservers("Azione Eseguita!", giocatore);
					}
				} catch (FuoriDalLimiteDelPercorso | CartePoliticaIncorrette | NumeroAiutantiIncorretto
						| EmporioGiaCostruito e) {
					gioco.notificaObservers(e, giocatore);
					gioco.notificaObservers(gioco.getTabellone(), giocatore);
				}
			} else {
				gioco.notificaObservers("Non è il tuo turno", giocatore);
			}
		}
		if (oggetto instanceof String && ((String) oggetto).equalsIgnoreCase("fine")
				&& giocatore.getStatoGiocatore() instanceof TurnoMercato)
			giocatore.getStatoGiocatore().prossimoStato();
		if (oggetto instanceof OggettoVendibile) {
			if (giocatore.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti)
				giocatore.getStatoGiocatore().mettiInVenditaOggetto((OggettoVendibile) oggetto);
			if (giocatore.getStatoGiocatore() instanceof TurnoMercatoCompraVendita)
				try {
					giocatore.getStatoGiocatore().compraOggetto(((FaseTurnoMercatoCompraVendita) gioco.getStato())
							.getMercato().cercaOggetto((OggettoVendibile) oggetto));
				} catch (FuoriDalLimiteDelPercorso e) {
					gioco.notificaObservers("Non hai abbastanza soldi per acquistare questo oggetto", giocatore);
				}
		}
		if (!(giocatore.getStatoGiocatore() instanceof AttesaTurno))
			gioco.notificaObservers(gioco.getTabellone());
		if(oggetto instanceof Bonus){
			updateBonus((Bonus) oggetto,giocatore);
		}
	}

	@Override
	public void update(Object cambiamento) {
	}

	@Override
	public void update(Bonus cambiamento, List<String> input) {
		// TODO Auto-generated method stub
		
	}
}

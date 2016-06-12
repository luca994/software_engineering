package server.view;

import server.model.Assistente;
import server.model.CartaPolitica;
import server.model.Giocatore;
import server.model.OggettoVendibile;
import server.model.TesseraCostruzione;
import server.model.bonus.Bonus;
import server.observer.Observable;
import server.observer.Observer;

public abstract class ServerView extends Observable<Object, Bonus> implements Observer<Object, Bonus> {

	private Giocatore giocatore;

	protected OggettoVendibile cercaOggettoVendibile(OggettoVendibile oggettoVendibileDaCercare) {
		if (oggettoVendibileDaCercare.getGiocatore() == null && oggettoVendibileDaCercare.getMercato() == null
				&& oggettoVendibileDaCercare.getPrezzo() > 0) {
			if (oggettoVendibileDaCercare instanceof CartaPolitica) {
				getGiocatore().cercaCarta((CartaPolitica) oggettoVendibileDaCercare)
						.setPrezzo(oggettoVendibileDaCercare.getPrezzo());
				return getGiocatore().cercaCarta((CartaPolitica) oggettoVendibileDaCercare);
			}
			if (oggettoVendibileDaCercare instanceof TesseraCostruzione) {
				getGiocatore().cercaTesseraCostruzione((TesseraCostruzione) oggettoVendibileDaCercare)
						.setPrezzo(oggettoVendibileDaCercare.getPrezzo());
				return getGiocatore().cercaTesseraCostruzione((TesseraCostruzione) oggettoVendibileDaCercare);
			}
			if (oggettoVendibileDaCercare instanceof Assistente && !getGiocatore().getAssistenti().isEmpty()) {
				getGiocatore().getAssistenti().get(0).setPrezzo(oggettoVendibileDaCercare.getPrezzo());
				return getGiocatore().getAssistenti().get(0);
			}
		}
		if (oggettoVendibileDaCercare.getGiocatore() != null && oggettoVendibileDaCercare.getMercato() != null
				&& oggettoVendibileDaCercare.getPrezzo() > 0)
			return oggettoVendibileDaCercare;
		return null;
	}

	/**
	 * @return the giocatore
	 */
	public Giocatore getGiocatore() {
		return giocatore;
	}

	/**
	 * @param giocatore
	 *            the giocatore to set
	 */
	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}
}

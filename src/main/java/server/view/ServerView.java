package server.view;

import server.model.Giocatore;
import server.model.bonus.Bonus;
import server.model.componenti.Assistente;
import server.model.componenti.CartaPolitica;
import server.model.componenti.OggettoVendibile;
import server.model.componenti.TesseraCostruzione;
import server.observer.Observable;
import server.observer.Observer;

/**
 * the abstract class that represents the generic view server
 */
public abstract class ServerView extends Observable<Object, Bonus> implements Observer<Object, Bonus> {

	private Giocatore giocatore;

	/**
	 * looks for the copy of salable object in input into the model. If the
	 * object is found in the model, it returns the reference, else, returns
	 * null.
	 * 
	 * @param oggettoVendibileDaCercare
	 *            the object to find
	 * @return the reference to the object in the model
	 */
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
	public synchronized Giocatore getGiocatore() {
		return giocatore;
	}

	/**
	 * @param giocatore
	 *            the giocatore to set
	 */
	public synchronized void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}

}

package server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import server.model.percorso.Percorso;

/**
 * @author Riccardo
 *
 */
public class Mercato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4801537094662620814L;

	private List<OggettoVendibile> oggettiInVendita;
	private Percorso percorsoRicchezza;

	/**
	 * build the object
	 */
	public Mercato(Percorso percorsoRicchezza) {
		oggettiInVendita = new ArrayList<>();
		this.percorsoRicchezza = percorsoRicchezza;
	}

	public OggettoVendibile cercaOggetto(OggettoVendibile oggettoDaCercare) {
		for (OggettoVendibile o : oggettiInVendita) {
			if (oggettoDaCercare instanceof CartaPolitica && o instanceof CartaPolitica) {
				if (((CartaPolitica) oggettoDaCercare).isUguale((CartaPolitica) o)
						&& oggettoDaCercare.confrontaParametri(o))
					return o;
			}
			if (oggettoDaCercare instanceof TesseraCostruzione && o instanceof TesseraCostruzione) {
				if (((TesseraCostruzione) oggettoDaCercare).isUguale((TesseraCostruzione) o)
						&& oggettoDaCercare.confrontaParametri(o))
					return o;
			}
			if (oggettoDaCercare instanceof Assistente && o instanceof Assistente) {
				if (((Assistente) oggettoDaCercare).isUguale((Assistente) o) && oggettoDaCercare.confrontaParametri(o))
					return o;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return the list of oggettiInVendita
	 */
	public List<OggettoVendibile> getOggettiInVendita() {
		return oggettiInVendita;
	}

	/**
	 * 
	 * @return the money route of the game
	 */
	public Percorso getPercorsoRicchezza() {
		return percorsoRicchezza;
	}

}

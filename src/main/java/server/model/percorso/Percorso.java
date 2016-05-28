package server.model.percorso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import server.model.Giocatore;
import server.model.Tabellone;
import server.model.bonus.Bonus;
import server.model.bonus.BonusCreator;

/**
 * This class is used to implement all the routes; the generic type "Casella"
 * allow the program to use only this class to implement every kind of route.
 * 
 * @author Massimiliano Ventura
 *
 */
public class Percorso {

	private List<Casella> caselle;

	/**
	 * builds a route from a file
	 * 
	 * @param nomefile
	 *            the file path
	 * @param tabellone
	 *            the game board
	 * @throws JDOMException
	 * @throws IOException
	 *             if the file doesn't exist or there is an error in the file
	 *             reading
	 */
	public Percorso(String nomefile, Tabellone tabellone) throws JDOMException, IOException {
		if (nomefile == null || tabellone == null)
			throw new NullPointerException("il nomefile e il tabellone non possono essere nulli");
		BonusCreator bonusCreator = new BonusCreator(tabellone);
		SAXBuilder builderPercorso = new SAXBuilder();
		Document documentPercorso = builderPercorso.build(new File(nomefile));
		Element percorsoRootElement = documentPercorso.getRootElement();
		List<Element> elencoCaselle = percorsoRootElement.getChildren();
		this.caselle = new ArrayList<>();
		boolean senzaB;
		for (Element cas : elencoCaselle) {
			senzaB = false;
			List<Element> elencoBonus = cas.getChildren();
			Set<Bonus> totBonus = new HashSet<>();
			for (Element bon : elencoBonus) {
				if ("NessunBonus".equals(bon.getAttributeValue("id"))) {
					senzaB = true;
					break;
				}
				if (bon.getAttributeValue("attributo") != null) {
					totBonus.add(bonusCreator.creaBonus(bon.getAttributeValue("id"),
							Integer.parseInt(bon.getAttributeValue("attributo")), tabellone.getGioco()));
				} else {
					totBonus.add(bonusCreator.creaBonus(bon.getAttributeValue("id"), 0, tabellone.getGioco()));
				}
			}
			if (senzaB)
				this.caselle.add(new CasellaSenzaBonus());
			else
				this.caselle.add(new CasellaConBonus(totBonus));
		}
	}

	/**
	 * Moves the player along the route(Percorso) if the number of steps(passi)
	 * is negative the player will move backwards.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if giocatore hasn't enough money
	 * @param giocatore
	 *            the player who wants to move
	 * @param passi
	 *            the number of steps
	 * @throws IOException
	 */
	public void muoviGiocatore(Giocatore giocatore, int passi) {
		if (passi > 0)
			muoviGiocatoreAvanti(giocatore, passi);
		else if (passi < 0) {
			int posizione = posizioneAttualeGiocatore(giocatore);
			if (posizione >= (0 - passi)) {
				passi = 0 - passi;
				muoviGiocatoreIndietro(giocatore, passi);
			} else {
				throw new IndexOutOfBoundsException("Soldi insufficienti per eseguire la mossa");
			}
		}
	}

	/**
	 * it is called by muoviGiocatore to move the player forward
	 * 
	 * @param giocatore
	 *            the player who wants to move
	 * @param passi
	 *            the number of steps
	 * @throws IOException
	 */
	private void muoviGiocatoreAvanti(Giocatore giocatore, int passi) {
		Casella casellacorrente = null;
		ListIterator<Casella> itcasella = this.caselle.listIterator();
		while (itcasella.hasNext()) {
			Set<Giocatore> giocatoriCasellaCorrente = itcasella.next().getGiocatori();
			if (!itcasella.hasNext()) {
				break;
			}
			if (giocatoriCasellaCorrente.contains(giocatore)) {
				giocatoriCasellaCorrente.remove(giocatore);
				for (int j = 0; j < passi && itcasella.hasNext(); j++) {
					casellacorrente = itcasella.next();
					giocatoriCasellaCorrente = casellacorrente.getGiocatori();
				}
				giocatoriCasellaCorrente.add(giocatore);
				if (casellacorrente instanceof CasellaConBonus) {
					for (Bonus bon : ((CasellaConBonus) casellacorrente).getBonus())
						bon.azioneBonus(giocatore);
				}
				break;
			}
		}
	}

	/**
	 * it is called by muoviGiocatore to move the player back
	 * 
	 * @param giocatore
	 *            the player who wants to move
	 * @param passi
	 *            the number of steps
	 */
	private void muoviGiocatoreIndietro(Giocatore giocatore, int passi) {

		ListIterator<Casella> itcasella = this.caselle.listIterator(caselle.size());
		while (itcasella.hasPrevious()) {
			Set<Giocatore> giocatoriCasellaCorrente = itcasella.previous().getGiocatori();
			if (!itcasella.hasPrevious()) {
				break;
			}
			if (giocatoriCasellaCorrente.contains(giocatore)) {
				giocatoriCasellaCorrente.remove(giocatore);
				for (int j = 0; j < passi && itcasella.hasPrevious(); j++)
					giocatoriCasellaCorrente = itcasella.previous().getGiocatori();
				giocatoriCasellaCorrente.add(giocatore);
				break;
			}
		}

	}

	/**
	 * @returns the position number of giocatore in the current route,
	 * @throws IllegalArgumentExeption
	 *             when can't find giocatore in the route
	 * @param giocatore
	 *            the player of which you want to know the position
	 * 
	 */
	public int posizioneAttualeGiocatore(Giocatore giocatore) {
		boolean trovato = false;
		int posizione = -1;
		Iterator<Casella> itcasella = caselle.iterator();
		while (itcasella.hasNext() && !trovato) {
			posizione++;
			if (itcasella.next().getGiocatori().contains(giocatore))
				trovato = true;
		}
		if (!trovato)
			throw new IllegalArgumentException("Il giocatore non è stato inizializzato in questo percorso");
		return posizione;
	}

	/**
	 * creates and returns a list of non- empty caselle, order from last to
	 * first .
	 * 
	 * 
	 * @return the list that contains no empty caselle ordered . The last
	 *         non-empty casella in the route is the first of the list returned.
	 */
	public List<Casella> caselleNonVuotePiuAvanti() {
		List<Casella> ordineInvertito = new ArrayList<>();
		List<Casella> caselleNonVuote = new ArrayList<>();
		Collections.copy(ordineInvertito, caselle);
		Collections.reverse(ordineInvertito);
		for (Casella c : ordineInvertito) {
			if (!c.getGiocatori().isEmpty())
				caselleNonVuote.add(c);
		}
		return caselleNonVuote;
	}

	/**
	 * @return the caselle
	 */
	public List<Casella> getCaselle() {
		return this.caselle;
	}
}

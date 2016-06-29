package server.model.percorso;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.Tabellone;
import server.model.bonus.Bonus;
import server.model.bonus.BonusCreator;

/**
 * This class is used to implement all the routes; the generic type "Casella"
 * allow the program to use only this class to implement every kind of route.
 *
 */
public class Percorso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1214238482188402647L;
	
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
	 * is negative the player will move backwards. If the player would exceed
	 * the end of the path remains on the last box. If the player is already in
	 * the last box , before you move forward , the player does not move and
	 * does not take its bonus.
	 * 
	 * @param giocatore
	 *            the player who wants to move
	 * @param passi
	 *            the number of steps
	 * @throws FuoriDalLimiteDelPercorso
	 *             if the player , making steps back , would exceed the box 0
	 * 
	 * 
	 */
	public void muoviGiocatore(Giocatore giocatore, int passi) throws FuoriDalLimiteDelPercorso {
		int posizioneFinale = posizioneAttualeGiocatore(giocatore) + passi;
		if(posizioneFinale-passi==-1)
			throw new NullPointerException("errore nel calcolo della posizione del giocatore");
		if (posizioneFinale < 0)
			throw new FuoriDalLimiteDelPercorso("Non hai abbastanza soldi");
		if (posizioneFinale > caselle.size() - 1)
			posizioneFinale = caselle.size() - 1;
		if (posizioneAttualeGiocatore(giocatore) == caselle.size() - 1&& passi>0)
			return;
		caselle.get(posizioneAttualeGiocatore(giocatore)).getGiocatori().remove(giocatore);
		caselle.get(posizioneFinale).getGiocatori().add(giocatore);
		if (caselle.get(posizioneFinale) instanceof CasellaConBonus && passi > 0)
			for (Bonus bon : ((CasellaConBonus) caselle.get(posizioneFinale)).getBonus())
				bon.azioneBonus(giocatore);
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
		if (giocatore == null)
			throw new NullPointerException("Il giocatore è nullo");
		for (Casella c : caselle)
			if (c.getGiocatori().contains(giocatore))
				return caselle.indexOf(c);
		throw new IllegalArgumentException("Il giocatore non è stato inizializzato in questo percorso");
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
		List<Casella> ordineInvertito = new ArrayList<>(caselle);
		List<Casella> caselleNonVuote = new ArrayList<>();
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

package server.model.componenti;

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

import server.model.Tabellone;
import server.model.bonus.Bonus;
import server.model.bonus.BonusCreator;

/**
 * @author Riccardo
 *
 */
public class Regione implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1282686235365734913L;
	
	private Set<Citta> citta;
	private Consiglio consiglio;
	private String nome;
	private List<TesseraCostruzione> tessereCostruzione;
	private List<TesseraCostruzione> tessereCoperte;

	/**
	 * build the region
	 * 
	 * @param nomeRegione
	 *            the name of the region
	 * @param nomiCitta
	 *            the list of the cities names
	 * @param tabellone
	 *            the game board
	 *           @throws NullPointerException if an input parameter is null
	 *            
	 */
	public Regione(String nomeRegione, List<String> nomiCitta, Tabellone tabellone) {
		if(nomeRegione==null || nomiCitta==null || tabellone==null)
			throw new NullPointerException();
		this.nome = nomeRegione;
		this.tessereCostruzione = new ArrayList<>();
		this.tessereCoperte = new ArrayList<>();
		this.citta = new HashSet<>();
		creaCitta(nomiCitta);
		this.consiglio = new Consiglio(tabellone);
		this.consiglio.setRegione(this);
		creaTesserePermesso(tabellone);
	}

	/**
	 * creates permit tiles reading them from a file. Then assigns the tiles to
	 * the list of tiles of the region
	 * 
	 * @param tabellone
	 *            the game board
	 * @throws JDOMException
	 * @throws IOException
	 *             if the file doesn't exist or there is an error in the file
	 *             reading
	 */
	public void creaTesserePermesso(Tabellone tabellone) {
		BonusCreator bonusCreator = new BonusCreator(tabellone);
		// Creo il nome del file a partire dal nome della regione
		String nomefile = "TessereCostruzione" + this.nome + ".xml";
		// Leggo file e creo le TesserePermessoDiCostruzione
		SAXBuilder builderTessereCostruzione = new SAXBuilder();
		Document documentTessereCostruzione;
		try {
			documentTessereCostruzione = builderTessereCostruzione.build(getClass().getClassLoader().getResource(nomefile));
		} catch (JDOMException | IOException e) {
			throw new IllegalStateException(e);
		}
		Element tessereRootElement = documentTessereCostruzione.getRootElement();
		List<Element> elencoTessere = tessereRootElement.getChildren();
		List<TesseraCostruzione> elencoRiferimentiTessere = new ArrayList<>(15);
		for (Element tessere : elencoTessere)// scorro le tessere nel file
		{
			List<Element> elencoSet = tessere.getChildren();
			Set<Bonus> bonusTessera = new HashSet<>(3);
			Set<Citta> elencoRiferimentiCitta = new HashSet<>(3);
			String idTessera = tessere.getAttributeValue("id");
			for (Element set : elencoSet)// Scorro i set di bonus e città nel
											// file
			{
				if ("SetCittà".equals(set.getName())) {

					List<Element> elencoCitta = set.getChildren();
					for (Element cit : elencoCitta) {
						elencoRiferimentiCitta.add(tabellone.cercaCitta(cit.getAttributeValue("id"), this));
					}
				} else if ("SetBonus".equals(set.getName())) {
					List<Element> elencoBonus = set.getChildren();
					for (Element bon : elencoBonus) {
						if (bon.getAttributeValue("attributo") != null)
							bonusTessera.add(bonusCreator.creaBonus(bon.getAttributeValue("id"),
									Integer.parseInt(bon.getAttributeValue("attributo")), tabellone.getGioco()));
						else {
							bonusTessera
									.add(bonusCreator.creaBonus(bon.getAttributeValue("id"), 0, tabellone.getGioco()));
						}
					}
				}
			}
			elencoRiferimentiTessere.add(new TesseraCostruzione(bonusTessera, elencoRiferimentiCitta, this, idTessera));
		}
		setTessereCoperte(elencoRiferimentiTessere);
	}

	/**
	 * creates the cities of a region
	 * 
	 * @param nomiCitta
	 *            the list of names of the cities
	 */
	public void creaCitta(List<String> nomiCitta) {
		for (String nomeCit : nomiCitta) {
			this.citta.add(new Citta(nomeCit, this));
		}
	}

	/**
	 * sets the covered permit tiles of a region.
	 * 
	 * @param elencoRiferimentiTessere
	 *            the list of permit tiles to set in the region
	 */
	public void setTessereCoperte(List<TesseraCostruzione> elencoRiferimentiTessere) {
		this.tessereCoperte = elencoRiferimentiTessere;
		Collections.shuffle(this.tessereCoperte);
		this.tessereCostruzione.add(this.tessereCoperte.remove(0));
		this.tessereCostruzione.add(this.tessereCoperte.remove(0));
	}

	/**
	 * Removes the parameter tessera from the list of obtainable Tessere
	 * Permesso Costruzione and moves the first element of the covered Tessere
	 * Permesso Costruzione in to the list of obtainable Tessere
	 * 
	 * @param tessera
	 */
	public void nuovaTessera(TesseraCostruzione tessera) {
		if (this.tessereCostruzione.remove(tessera)) {
			this.tessereCostruzione.add(this.tessereCoperte.get(0));
			this.tessereCoperte.remove(0);
		}
	}

	/**
	 * @return the città
	 */
	public Set<Citta> getCitta() {
		return citta;
	}

	/**
	 * @return the consiglio
	 */
	public Consiglio getConsiglio() {
		return consiglio;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the tessereCostruzione
	 */
	public List<TesseraCostruzione> getTessereCostruzione() {
		return tessereCostruzione;
	}

	/**
	 * @return the tessereCoperte
	 */
	public List<TesseraCostruzione> getTessereCoperte() {
		return tessereCoperte;
	}

}

package server.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import server.model.bonus.Bonus;

import server.model.bonus.BonusCreator;
import server.model.percorso.Percorso;
import server.model.tesserebonus.TesseraBonusCitta;
import server.model.tesserebonus.TesseraBonusCreator;
import server.model.tesserebonus.TesseraBonusRegione;
import server.model.tesserebonus.TesseraPremioRe;

/**
 * @author Riccardo
 *
 */
public class Tabellone {

	private static final int NUMERO_TOTALE_CONSIGLIERI = 24;
	private static final int NUMERO_TOTALE_GETTONI = 14;

	private List<Regione> regioni;
	private Set<TesseraBonusRegione> tessereBonusRegione;
	private Set<TesseraBonusCitta> tessereBonusCitta;
	private List<TesseraPremioRe> tesserePremioRe;
	private List<Consigliere> consiglieriDisponibili;
	private Percorso percorsoNobilta;
	private Percorso percorsoRicchezza;
	private Percorso percorsoVittoria;
	private Re re;
	private Gioco gioco;

	/**
	 * It builds the board by calling methods : creaPercorsi() ,
	 * creaConsiglieriDisponibili() , creaRegioni() , creaGettoniCittà() ,
	 * collegaCittà(String , Gioco) , creaTessereBonus().
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 * @throws NoSuchElementException
	 *             if there's an error during the reading of the number of
	 *             Consiglieri
	 */
	public Tabellone(String nomeFileMappa, Gioco gioco) throws JDOMException, IOException {

		this.gioco = gioco;

		// Creo percorsi passando al costruttore del percorso il nome del file
		// che dovrà usare per creare il percorso
		creaPercorsi();
		// Creazione consiglieriDisponibili
		creaConsiglieriDisponibili();

		// Creo regioni (da file ovviamente):passo al costruttore della regione
		// una lista di nomi di città
		// Sarà il costruttore delle Regioni a collegarle
		creaRegioni();
		// Creo bonus per le città(praticamente una lista di bonus
		// corrispondente ai Gettoni Città)
		// Creo la lista di bonus per le città(Gettoni città da file), la
		// mischio
		List<Set<Bonus>> gettoniCitta = new ArrayList<>(NUMERO_TOTALE_GETTONI);
		gettoniCitta = creaGettoniCitta();
		// Collego, coloro le città(devo passare attraverso le regioni), creo e
		// assegno bonus, piazzo il re
		collegaCitta(nomeFileMappa, gettoniCitta);
		// Crea le tessere bonus
		creaTessereBonus();
	}

	/**
	 * creates the various paths by calling the respective constructors.
	 * This method is called only by tabellone's constructor
	 * @throws JDOMException
	 * @throws IOException
	 */
	private void creaPercorsi() throws JDOMException, IOException {
		this.percorsoVittoria = new Percorso("src/main/resources/PercorsoVittoria.xml", this);
		this.percorsoRicchezza = new Percorso("src/main/resources/percorsoRicchezza.xml", this);
		this.percorsoNobilta = new Percorso("src/main/resources/percorsoNobiltà.xml", this);
	}

	/**
	 * creates a list of initial counselors available in the board by reading
	 * the file the total number of directors to be instantiated and the color
	 * of each of them .
	 * This method is called only by tabellone's constructor
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 */
	private void creaConsiglieriDisponibili() throws JDOMException, IOException {
		this.consiglieriDisponibili = new ArrayList<>(NUMERO_TOTALE_CONSIGLIERI);
		SAXBuilder builderConsiglieri = new SAXBuilder();
		Document documentConsiglieri = builderConsiglieri.build(new File("src/main/resources/Consiglieri.xml"));
		Element consiglieriRootElement = documentConsiglieri.getRootElement();
		// acquisizione del numero dei consiglieri nel file
		// Creo direttamente la lista di Consiglieri
		List<Element> elencoConsiglieri = consiglieriRootElement.getChildren();
		for (Element consi : elencoConsiglieri) {
			// Aggiungo un nuovo consigliere alla lista creandolo direttamente
			// col costruttore
			// passandogli come parametro il valore dell'attributo colore letto
			// dagli elementi del file xml
			this.consiglieriDisponibili
					.add(new Consigliere(ParseColor.colorStringToColor(consi.getAttributeValue("colore"))));
		}

		Collections.shuffle(consiglieriDisponibili);
	}

	/**
	 * creates regions by reading the name of the file, also adds to each region
	 * the list of cities that comprise it. Even the latter obviously is read
	 * from file.
	 * This method is called only by tabellone's constructor
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 */
	private void creaRegioni() throws JDOMException, IOException {
		this.regioni = new ArrayList<>();
		SAXBuilder builderRegioni = new SAXBuilder();
		Document documentRegioni = builderRegioni.build(new File("src/main/resources/Regioni.xml"));
		Element regioniRootElement = documentRegioni.getRootElement();
		List<Element> elencoRegioni = regioniRootElement.getChildren();
		for (Element regione : elencoRegioni) {
			// Leggo il nome della regione
			List<String> nomiCitta = new ArrayList<>();
			List<Element> elencoCittaRegione = regione.getChildren();
			// Leggo valore dell'unico attributo(nome della città), lo copio
			// nella lista dei nomi
			// e la passo al costruttore della regione, la quale creerà le città
			// e penserà a collegarle
			for (Element citta : elencoCittaRegione) {
				nomiCitta.add(citta.getAttributeValue("id"));
			}
			// Costruisco la regione
			this.regioni.add(new Regione(regione.getAttributeValue("id"), nomiCitta, this));
		}
	}

	private List<Set<Bonus>> creaGettoniCitta() throws JDOMException, IOException {
		// Creo la lista di bonus per le città(Gettoni città da file), la
		// mischio
		List<Set<Bonus>> gettoniCitta = new ArrayList<>(NUMERO_TOTALE_GETTONI);
		BonusCreator bonusCreator = new BonusCreator(this);
		// Leggo i set di bonus
		SAXBuilder builderGettoni = new SAXBuilder();
		Document documentGettoni = builderGettoni.build(new File("src/main/resources/BonusCittà.xml"));
		Element bonusCittaRootElement = documentGettoni.getRootElement();
		List<Element> elencoSetBonus = bonusCittaRootElement.getChildren();
		for (Element set : elencoSetBonus) {
			List<Element> elencoBonus = set.getChildren();
			Set<Bonus> bonus = new HashSet<>();
			for (Element bon : elencoBonus) { // Leggo i set di bonus, li
												// inizializzo e li copio nella
												// lista di bonus
					bonus.add(bonusCreator.creaBonus(bon.getAttributeValue("id"),
							Integer.parseInt(bon.getAttributeValue("attributo")), gioco));
			}
			gettoniCitta.add(bonus);// Aggiungo i set di bonus alla lista di
									// bonus
		}
		Collections.shuffle(gettoniCitta);
		return gettoniCitta;

	}

	private void collegaCitta(String nomeFileMappa, List<Set<Bonus>> gettoniCitta) throws JDOMException, IOException {
		SAXBuilder builderCollegamenti = new SAXBuilder();
		Document documentCollegamenti = builderCollegamenti.build(new File(nomeFileMappa));
		Element collegamentiRootElement = documentCollegamenti.getRootElement();
		List<Element> elencoCitta = collegamentiRootElement.getChildren();
		for (Regione reg : regioni) {
			for (Citta cit : reg.getCittà()) {
				// ora devo leggere il file delle città e trovare il match, con
				// il nome, coloro e collego
				for (Element cittaMappa : elencoCitta) {
					if (cittaMappa.getAttributeValue("nome").equals(cit.getNome())) {
						cit.setColore(ParseColor.colorStringToColor(cittaMappa.getAttributeValue("colore")));
						if (cit.getColore().equals(ParseColor.colorStringToColor("magenta"))) {
							Re re = new Re(cit, new Consiglio(this));
							cit.setRe(re);
							this.setRe(re);
						} else {
							// cit.getBonus().addAll(gettoniCittà.get(0));
							cit.setBonus(gettoniCitta.get(0));
							gettoniCitta.remove(0);
						}
						List<Element> elencoCollegamenti = cittaMappa.getChildren();
						for (Element coll : elencoCollegamenti) {
							cit.getCittàVicina().add(cercaCitta(coll.getText()));
						}
					}
				}
			}
		}
	}

	/**
	 * initialize the tiles tessereBonusRegione, tessereBonusCittà,
	 * tesserePremioRe reading them from a file
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 *             throw an exception if the method doesn't found the file or
	 *             there is another error about the file
	 */
	private void creaTessereBonus() throws JDOMException, IOException {
		BonusCreator bonusCreator = new BonusCreator(this);
		TesseraBonusCreator tesseraBonusCreator = new TesseraBonusCreator(this);
		// inizializzo i set di tessere
		this.tessereBonusCitta = new HashSet<>();
		this.tessereBonusRegione = new HashSet<>();
		this.tesserePremioRe = new ArrayList<>();
		SAXBuilder builderTessereBonus = new SAXBuilder();
		// leggo il documento xml per le tessere
		Document documentTessereBonus = builderTessereBonus.build(new File("src/main/resources/TessereBonus.xml"));
		Element tessereBonusRootElement = documentTessereBonus.getRootElement();
		List<Element> tessere = tessereBonusRootElement.getChildren();
		for (Element t : tessere) {

			// leggo il tipo di bonus e l' attributo da assegnare alla tessera
			Element tipoBonus = t.getChild("Bonus");
			Set<Bonus> bonus = new HashSet<>();
				bonus.add(bonusCreator.creaBonus(tipoBonus.getAttributeValue("id"),
						Integer.parseInt(tipoBonus.getAttributeValue("attributo")), gioco));
				// creo le tessere bonus per la regione
				if ("regione".equals(t.getAttributeValue("id"))) {
					tessereBonusRegione.add((TesseraBonusRegione) tesseraBonusCreator
							.creaTesseraBonus(t.getAttributeValue("id"), t.getAttributeValue("attributo"), bonus));
				}
				// costruisco le tessere bonus per le città
				else if ("città".equals(t.getAttributeValue("id"))) {
					tessereBonusCitta.add((TesseraBonusCitta) tesseraBonusCreator
							.creaTesseraBonus(t.getAttributeValue("id"), t.getAttributeValue("attributo"), bonus));
				}
				// costruisco le tessere premio del re
				else if ("premioRe".equals(t.getAttributeValue("id"))) {
					tesserePremioRe.add((TesseraPremioRe) tesseraBonusCreator
							.creaTesseraBonus(t.getAttributeValue("id"), t.getAttributeValue("attributo"), bonus));
				}
		}
	}

	/**
	 * 
	 * @param nome
	 *            the name of the city
	 * @return return the city which has nome as name
	 * @throws NullPointerException
	 *             if nome is null
	 * @throws IllegalArgumentException
	 *             if the name of the city isn't correct
	 */
	public Citta cercaCitta(String nome) {
		if (nome == null)
			throw new NullPointerException("il nome della città non può essere nullo");
		for (Regione regione : this.regioni) {
			for (Citta cit : regione.getCittà()) {
				if (cit.getNome().equals(nome)) {
					return cit;
				}
			}
		}
		throw new IllegalArgumentException("la città inserita non è corretta");
	}

	/**
	 * 
	 * @param nome
	 *            the name of the city
	 * @param regione
	 *            the region of the city
	 * @return the city which has nome as name
	 * @throws NullPointerException
	 *             if nome is null
	 * @throws IllegalArgumentException
	 *             if the name of the city isn't correct
	 */
	public Citta cercaCitta(String nome, Regione regione) {
		if (nome == null)
			throw new NullPointerException("il nome della città non può essere nullo");
		for (Citta cit : regione.getCittà()) {
			if (cit.getNome().equals(nome)) {
				return cit;
			}
		}
		throw new IllegalArgumentException("la città inserita non è corretta");
	}


	/**
	 * check if a player has an emporium every the city of a color
	 * 
	 * @param giocatore
	 *            the player who has to check the bonus
	 * @param citta
	 *            the city in which the method take the color
	 * @author riccardo
	 * @return return true if the player has an emporium in every city of a
	 *         color
	 */
	public boolean verificaEmporioColoreBonus(Giocatore giocatore, Citta citta) {
		for (Regione r : regioni) {
			for (Citta c : r.getCittà()) {
				if (citta.getColore() == c.getColore() && !c.getEmpori().contains(giocatore)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @param giocatore
	 *            the player who builds the emporium
	 * @param citta
	 *            the city in which the player build the emporium
	 * @return return true if the player has an emporium in every city of a
	 *         region
	 */
	public boolean verificaEmporioRegioneBonus(Giocatore giocatore, Citta citta) {
		for (Citta c : citta.getRegione().getCittà()) {
			if (!c.getEmpori().contains(giocatore)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * check if the player who has built the emporium can take the bonus for
	 * having built an emporium in every city of a color or in every city of a
	 * region. Then assigns the tile/tiles.
	 * 
	 * @author riccardo
	 * @param giocatore
	 *            the player who has built the emporium
	 * @param citta
	 *            the city where the player has built the emporium
	 */
	public void prendiTesseraBonus(Giocatore giocatore, Citta citta) {
		if (verificaEmporioColoreBonus(giocatore, citta)) {
			for (TesseraBonusCitta t : tessereBonusCitta) {
				if (citta.getColore().equals(t.getColore())) {
					t.eseguiBonus(giocatore);
					tessereBonusCitta.remove(t);
				}
			}
		}
		if (verificaEmporioRegioneBonus(giocatore, citta)) {
			for (TesseraBonusRegione t : tessereBonusRegione) {
				if (t.getRegione().equals(citta.getRegione())) {
					t.eseguiBonus(giocatore);
					tessereBonusRegione.remove(t);
				}
			}
		}
		if (!tesserePremioRe.isEmpty()) {
			tesserePremioRe.get(0).eseguiBonus(giocatore);
			tesserePremioRe.remove(0);
		}
	}

	/**
	 * 
	 * @param nomeRegione
	 *            the name of the region you wnat to find
	 * @return return the region with nomeRegione as name
	 * @throws NullPointerException
	 *             if nomeRegione is null
	 * @throws IllegalArgumentException
	 *             if nomeRegioe isn't correct
	 */
	public Regione getRegioneDaNome(String nomeRegione) {
		if (nomeRegione == null) {
			throw new NullPointerException("nomeRegione non può essere nullo");
		}
		for (Regione r : regioni) {
			if (r.getNome().equals(nomeRegione)) {
				return r;
			}
		}
		throw new IllegalArgumentException("il nome della regione inserito non esiste");
	}

	/**
	 * creating a graph , it puts the cities in each region as a vertex, then I
	 * created branches connecting each city with its neighbors
	 * 
	 * @return the map in the form of graph
	 */
	public UndirectedGraph<Citta, DefaultEdge> generaGrafo() {

		UndirectedGraph<Citta, DefaultEdge> mappa = new SimpleGraph<>(DefaultEdge.class);

		for (Regione reg : regioni) {
			for (Citta cit : reg.getCittà()) {
				mappa.addVertex(cit);
			}
		}

		for (Regione reg : regioni) {
			for (Citta cit : reg.getCittà()) {
				for (Citta cit1 : cit.getCittàVicina()) {
					mappa.addEdge(cit, cit1);
				}
			}
		}
		return mappa;
	}

	/**
	 * @return the regioni
	 */
	public List<Regione> getRegioni() {
		return regioni;
	}

	/**
	 * @return the tessereBonusRegione
	 */
	public Set<TesseraBonusRegione> getTessereBonusRegione() {
		return tessereBonusRegione;
	}

	/**
	 * @return the consiglieriDisponibili
	 */
	public List<Consigliere> getConsiglieriDisponibili() {
		return consiglieriDisponibili;
	}

	/**
	 * @return the percorsoNobiltà
	 */
	public Percorso getPercorsoNobilta() {
		return percorsoNobilta;
	}

	/**
	 * @return the percorsoRicchezza
	 */
	public Percorso getPercorsoRicchezza() {
		return percorsoRicchezza;
	}

	/**
	 * @return the percorsoVittoria
	 */
	public Percorso getPercorsoVittoria() {
		return percorsoVittoria;
	}

	/**
	 * @return the re
	 */
	public Re getRe() {
		return re;
	}

	/**
	 * @param re
	 *            the re to set
	 */
	public void setRe(Re re) {
		this.re = re;
	}

	/**
	 * @return the gioco
	 */
	public Gioco getGioco() {
		return gioco;
	}
}

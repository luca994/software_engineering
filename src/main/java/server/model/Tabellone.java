package server.model;

import java.awt.Color;
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
public class Tabellone implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6970346938035769588L;
	
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
	private transient Gioco gioco;

	/**
	 * It builds the board by calling methods : creaPercorsi() ,
	 * creaConsiglieriDisponibili() , creaRegioni() , creaGettoniCittà() ,
	 * collegaCittà(nomeFileMappa,gettoniCitta) , creaTessereBonus().
	 * 
	 * @throws IllegalArgumentException
	 *             if there is an error in reading files
	 * 
	 * @throws NullPointerException
	 *             if nomeFileMappa is null
	 */
	public Tabellone(String nomeFileMappa, Gioco gioco) {
		if (nomeFileMappa == null)
			throw new NullPointerException();
		this.gioco = gioco;

		/*
		 * Creo percorsi passando al costruttore del percorso il nome del file
		 * che dovrà usare per creare il percorso
		 */
		creaPercorsi();
		/* Creazione consiglieriDisponibili */
		creaConsiglieriDisponibili();

		/*
		 * Creo regioni (da file ovviamente):passo al costruttore della regione
		 * una lista di nomi di città Sarà il costruttore delle Regioni a
		 * collegarle
		 */
		creaRegioni();
		/*
		 * Creo bonus per le città(praticamente una lista di bonus
		 * corrispondente ai Gettoni Città) Creo la lista di bonus per le
		 * città(Gettoni città da file), la mischio
		 */
		List<Set<Bonus>> gettoniCitta = new ArrayList<>(NUMERO_TOTALE_GETTONI);
		gettoniCitta = creaGettoniCitta();
		/*
		 * Collego, coloro le città(devo passare attraverso le regioni), creo e
		 * assegno bonus, piazzo il re
		 */
		collegaCitta(nomeFileMappa, gettoniCitta);
		/* Crea le tessere bonus */
		creaTessereBonus();
	}

	/**
	 * creates the various paths by calling the respective constructors. This
	 * method is called only by tabellone's constructor
	 * 
	 * @throws IllegalArgumentException
	 *             if there is an error in reading files
	 */
	private void creaPercorsi() {
		try {
			this.percorsoVittoria = new Percorso("src/main/resources/PercorsoVittoria.xml", this);
			this.percorsoRicchezza = new Percorso("src/main/resources/percorsoRicchezza.xml", this);
			this.percorsoNobilta = new Percorso("src/main/resources/percorsoNobiltà.xml", this);
		} catch (JDOMException | IOException e) {
			throw new IllegalArgumentException("Errore nella lettura dei file xml dei percorsi", e);
		}
	}

	/**
	 * creates a list of initial counselors available in the board by reading
	 * the file the total number of directors to be instantiated and the color
	 * of each of them. This method is called only by tabellone's constructor
	 * 
	 * @throws IllegalArgumentException
	 *             if there is an error in reading files
	 */
	private void creaConsiglieriDisponibili() {
		this.consiglieriDisponibili = new ArrayList<>(NUMERO_TOTALE_CONSIGLIERI);
		SAXBuilder builderConsiglieri = new SAXBuilder();
		Document documentConsiglieri;
		try {
			documentConsiglieri = builderConsiglieri.build(new File("src/main/resources/Consiglieri.xml"));
		} catch (JDOMException | IOException e1) {
			throw new IllegalArgumentException("Errore nella lettura del file Consiglieri.xml", e1);
		}
		Element consiglieriRootElement = documentConsiglieri.getRootElement();
		List<Element> elencoConsiglieri = consiglieriRootElement.getChildren();
		for (Element consi : elencoConsiglieri) {
			try {
				this.consiglieriDisponibili
						.add(new Consigliere(ParseColor.colorStringToColor(consi.getAttributeValue("colore"))));
			} catch (NoSuchFieldException e) {
				throw new IllegalStateException("i colori nel file dei consiglieri non sono corretti");
			}
		}

		Collections.shuffle(consiglieriDisponibili);
	}

	/**
	 * creates regions by reading the name of the file, also adds to each region
	 * the list of cities that comprise it. Even the latter obviously is read
	 * from file. This method is called only by tabellone's constructor
	 * 
	 * @throws IllegalArgumentException
	 *             if there is an error in reading files
	 */
	private void creaRegioni() {
		this.regioni = new ArrayList<>();
		SAXBuilder builderRegioni = new SAXBuilder();
		Document documentRegioni;
		try {
			documentRegioni = builderRegioni.build(new File("src/main/resources/Regioni.xml"));
		} catch (JDOMException | IOException e) {
			throw new IllegalArgumentException("Errore nella lettura del file Regioni.xml", e);
		}
		Element regioniRootElement = documentRegioni.getRootElement();
		List<Element> elencoRegioni = regioniRootElement.getChildren();
		for (Element regione : elencoRegioni) {
			// Leggo il nome della regione
			List<String> nomiCitta = new ArrayList<>();
			List<Element> elencoCittaRegione = regione.getChildren();
			/*
			 * Leggo il valore dell'unico attributo(nome della città), lo copio
			 * nella lista dei nomi e la passo al costruttore della regione, la
			 * quale creerà le città e in seguito le collegherà tra loro.
			 */
			for (Element citta : elencoCittaRegione) {
				nomiCitta.add(citta.getAttributeValue("id"));
			}
			// Costruisco la regione
			this.regioni.add(new Regione(regione.getAttributeValue("id"), nomiCitta, this));
		}
	}

	/**
	 * creates gettoniBonus by reading them from file and assigns them to the
	 * respective city. This method is called only by tabellone's constructor.
	 * 
	 * @return
	 * 
	 * @throws IllegalArgumentException
	 *             if there is an error in reading files
	 */
	private List<Set<Bonus>> creaGettoniCitta() {
		// Creo la lista di bonus per le città(Gettoni città da file), la
		// mischio
		List<Set<Bonus>> gettoniCitta = new ArrayList<>(NUMERO_TOTALE_GETTONI);
		BonusCreator bonusCreator = new BonusCreator(this);
		// Leggo i set di bonus
		SAXBuilder builderGettoni = new SAXBuilder();
		Document documentGettoni;
		try {
			documentGettoni = builderGettoni.build(new File("src/main/resources/BonusCittà.xml"));
		} catch (JDOMException | IOException e) {
			throw new IllegalArgumentException("Errore nella lettura del file BonusCittà.xml", e);
		}
		Element bonusCittaRootElement = documentGettoni.getRootElement();
		List<Element> elencoSetBonus = bonusCittaRootElement.getChildren();
		for (Element set : elencoSetBonus) {
			List<Element> elencoBonus = set.getChildren();
			Set<Bonus> bonus = new HashSet<>();
			for (Element bon : elencoBonus) {
				bonus.add(bonusCreator.creaBonus(bon.getAttributeValue("id"),
						Integer.parseInt(bon.getAttributeValue("attributo")), gioco));
			}
			gettoniCitta.add(bonus);
		}
		Collections.shuffle(gettoniCitta);
		return gettoniCitta;

	}

	/**
	 * Reads from the files of the city links and appropriately interconnects.
	 * This method is called only by tabellone's constructor.
	 * 
	 * @param nomeFileMappa
	 * @param gettoniCitta
	 * 
	 * @throws IllegalArgumentException
	 *             if there is an error in reading files
	 * 
	 */
	private void collegaCitta(String nomeFileMappa, List<Set<Bonus>> gettoniCitta) {
		SAXBuilder builderCollegamenti = new SAXBuilder();
		Document documentCollegamenti;
		try {
			documentCollegamenti = builderCollegamenti.build(new File(nomeFileMappa));
		} catch (JDOMException | IOException e1) {
			throw new IllegalArgumentException(e1);
		}
		Element collegamentiRootElement = documentCollegamenti.getRootElement();
		List<Element> elencoCitta = collegamentiRootElement.getChildren();
		for (Regione reg : regioni) {
			for (Citta cit : reg.getCitta()) {
				/*
				 * ora devo leggere il file delle città e trovare il match, con
				 * il nome, coloro e collego
				 */
				for (Element cittaMappa : elencoCitta) {
					if (cittaMappa.getAttributeValue("nome").equals(cit.getNome())) {
						try {
							cit.setColore(ParseColor.colorStringToColor(cittaMappa.getAttributeValue("colore")));
							if (cit.getColore().equals(ParseColor.colorStringToColor("magenta"))) {
								re = new Re(cit, new Consiglio(this), this);
								cit.setRe(re);
							} else {
								cit.setBonus(gettoniCitta.get(0));
								gettoniCitta.remove(0);
							}
							List<Element> elencoCollegamenti = cittaMappa.getChildren();
							for (Element coll : elencoCollegamenti) {
								cit.getCittaVicina().add(cercaCitta(coll.getText()));
							}
						} catch (NoSuchFieldException e) {
							throw new IllegalArgumentException("i colori delle città nei file non sono corretti");
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
	 * @throws IllegalArgumentException
	 *             if there is an error in reading files
	 */
	private void creaTessereBonus() {
		BonusCreator bonusCreator = new BonusCreator(this);
		TesseraBonusCreator tesseraBonusCreator = new TesseraBonusCreator(this);
		// inizializzo i set di tessere
		this.tessereBonusCitta = new HashSet<>();
		this.tessereBonusRegione = new HashSet<>();
		this.tesserePremioRe = new ArrayList<>();
		SAXBuilder builderTessereBonus = new SAXBuilder();
		// leggo il documento xml per le tessere
		Document documentTessereBonus;
		try {
			documentTessereBonus = builderTessereBonus.build(new File("src/main/resources/TessereBonus.xml"));
		} catch (JDOMException | IOException e) {
			throw new IllegalArgumentException("Errore nella lettura del file TessereBonus.xml", e);
		}
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
				tesserePremioRe.add((TesseraPremioRe) tesseraBonusCreator.creaTesseraBonus(t.getAttributeValue("id"),
						t.getAttributeValue("attributo"), bonus));
			}
		}
	}

	/**
	 * searches for the city with the input name you entered and returns the
	 * reference to this , if it exists. The name in input is not case
	 * sensitive.
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
			for (Citta cit : regione.getCitta()) {
				if (cit.getNome().toLowerCase().equals(nome.toLowerCase())) {
					return cit;
				}
			}
		}
		throw new IllegalArgumentException("la città inserita non è corretta");
	}

	/**
	 * searches for the city with the input name you entered and returns the
	 * reference to this , if it exists.The name in input is not case sensitive.
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
		if (nome == null || regione == null)
			throw new NullPointerException("il nome della città non può essere nullo");
		for (Citta cit : regione.getCitta()) {
			if (cit.getNome().toLowerCase().equals(nome.toLowerCase())) {
				return cit;
			}
		}
		throw new IllegalArgumentException("la città inserita non è corretta");
	}

	/**
	 * checks if a player who has just built an emporium in every city of a
	 * color
	 * 
	 * @param giocatore
	 *            the player who has to check the bonus
	 * @param citta
	 *            the city in which the method take the color
	 * 
	 * @return return true if the player has an emporium in every city of a
	 *         color
	 */
	public boolean verificaEmporioColoreBonus(Giocatore giocatore, Citta citta) {
		for (Regione r : regioni) {
			for (Citta c : r.getCitta())
				if (citta.getColore() == c.getColore() && !c.getEmpori().contains(giocatore))
					return false;
		}
		return true;
	}

	/**
	 * checks if a player who has just built an emporium in every city of a
	 * region.
	 * 
	 * @param giocatore
	 *            the player who builds the emporium
	 * @param citta
	 *            the city in which the player has just built the emporium
	 * @return return true if the player has an emporium in every city of a
	 *         region
	 * @throws NullPointerException
	 *             if citta is null.
	 */
	public boolean verificaEmporioRegioneBonus(Giocatore giocatore, Citta citta) {
		if (citta == null)
			throw new NullPointerException();
		for (Citta c : citta.getRegione().getCitta()) {
			if (!c.getEmpori().contains(giocatore)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checks if the player who has just built the emporium can take the bonus
	 * for having built an emporium in every city of a color or in every city of
	 * a region. Then assigns the tile/tiles.
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
	 *            the name of the region you want to find.(non case sensitive)
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
			if (r.getNome().toLowerCase().equals(nomeRegione.toLowerCase())) {
				return r;
			}
		}
		return null;
	}

	/**
	 * creates a graph , it puts the cities in each region as a vertex, then
	 * created branches connecting each city with its neighbors
	 * 
	 * @return the map in the form of graph
	 */
	public UndirectedGraph<Citta, DefaultEdge> generaGrafo() {

		UndirectedGraph<Citta, DefaultEdge> mappa = new SimpleGraph<>(DefaultEdge.class);

		for (Regione reg : regioni) {
			for (Citta cit : reg.getCitta()) {
				mappa.addVertex(cit);
			}
		}

		for (Regione reg : regioni) {
			for (Citta cit : reg.getCitta()) {
				for (Citta cit1 : cit.getCittaVicina()) {
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
	 * searches a councillor in the list consiglieriDisponibili   
	 * @param colore the color of the councillor you want to search
	 * @return return the councillor or null if there isn't a councillor of color colore
	 */
	public Consigliere getConsigliereDaColore(Color colore){
		for(Consigliere c: consiglieriDisponibili){
			if(c.getColore().equals(colore))
				return c;
		}
		return null;
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

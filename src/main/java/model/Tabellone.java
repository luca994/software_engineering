package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import model.bonus.Bonus;
import model.bonus.BonusAssistenti;
import model.bonus.BonusAzionePrincipale;
import model.bonus.BonusCartaPolitica;
import model.bonus.BonusCreator;
import model.bonus.BonusMoneta;
import model.bonus.BonusPercorsoNobiltà;
import model.bonus.BonusPuntoVittoria;
import model.percorso.Percorso;
import model.tesserebonus.TesseraBonus;
import model.tesserebonus.TesseraBonusCittà;
import model.tesserebonus.TesseraBonusCreator;
import model.tesserebonus.TesseraBonusRegione;
import model.tesserebonus.TesseraPremioRe;

/**
 * @author Riccardo
 *
 */
public class Tabellone {

	private static final Logger log = Logger.getLogger(Tabellone.class.getName());
	
	private Set<Regione> regioni;
	private Set<TesseraBonusRegione> tessereBonusRegione;
	private Set<TesseraBonusCittà> tessereBonusCittà;
	private List<TesseraPremioRe> tesserePremioRe;
	private List<Consigliere> consiglieriDisponibili;
	private Set<Consiglio> consigli;
	private Percorso percorsoNobiltà;
	private Percorso percorsoRicchezza;
	private Percorso percorsoVittoria;
	private Re re;
	

	/**
	 * @return the re
	 */
	public Re getRe() {
		return re;
	}

	/**
	 * @param re the re to set
	 */
	public void setRe(Re re) {
		this.re = re;
	}

	/**
	 * @return the regioni
	 */
	public Set<Regione> getRegioni() {
		return regioni;
	}

	/**
	 * @return the consiglieriDisponibili
	 */
	public List<Consigliere> getConsiglieriDisponibili() {
		return consiglieriDisponibili;
	}


	/**
	 * @return the consigli
	 */
	public Set<Consiglio> getConsigli() {
		return consigli;
	}


	/**
	 * @return the percorsoNobiltà
	 */
	public Percorso getPercorsoNobiltà() {
		return percorsoNobiltà;
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
	 * constructor called by gioco.setup()
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws NoSuchElementException if there's an error during the reading of the number of Consiglieri
	 */
	public Tabellone(String nomeFileMappa) throws JDOMException, IOException{
		
		
		//Creo percorsi passando al costruttore del percorso il nome del file che dovrà usare per creare il percorso
		creaPercorsi();
		//Creazione consiglieriDisponibili
		creaConsiglieriDisponibili();
		
		
		//Creo regioni (da file ovviamente):passo al costruttore della regione una lista di nomi di città
		//Sarà il costruttore delle Regioni a collegarle
		creaRegioni();
		//Creo bonus per le città(praticamente una lista di bonus corrispondente ai Gettoni Città)
		//Creo la lista di bonus per le città(Gettoni città da file), la mischio
		List<Set<Bonus>> gettoniCittà=new ArrayList<Set<Bonus>>(14);
		gettoniCittà=creaGettoniCittà();
		//Collego, coloro le città(devo passare attraverso le regioni), creo e assegno bonus, piazzo il re
		collegaCittà(nomeFileMappa, gettoniCittà);
		//Crea le tessere bonus
		creaTessereBonus();
	}
	
	public void creaPercorsi() throws JDOMException, IOException{
		this.percorsoVittoria=new Percorso("/source/main/PercorsoVittoria", this);	
		this.percorsoRicchezza=new Percorso("/source/main/percorsoRicchezza", this);
		this.percorsoNobiltà= new Percorso("source/main/percorsoNobiltà", this);
	}
	
	public void creaConsiglieriDisponibili() throws JDOMException, IOException{
		this.consiglieriDisponibili= new ArrayList<Consigliere>(24);
		SAXBuilder builderConsiglieri = new SAXBuilder();
		Document documentConsiglieri = builderConsiglieri.build(new File("/source/main/Consiglieri.xml"));
		Element consiglieriRootElement = documentConsiglieri.getRootElement();
		//acquisizione del numero dei consiglieri nel file
		//Creo direttamente la lista di Consiglieri
		List<Element> elencoConsiglieri =consiglieriRootElement.getChildren();
		for(Element consi:elencoConsiglieri){
		   //Aggiungo un nuovo consigliere alla lista creandolo direttamente col costruttore 
		   //passandogli come parametro il valore dell'attributo colore letto dagli elementi del file xml
		   this.consiglieriDisponibili.add(new Consigliere(consi.getAttributeValue("colore")));
		} 
		Collections.shuffle(consiglieriDisponibili);
	}
	
	public void creaRegioni () throws JDOMException, IOException{
		this.regioni=new HashSet<Regione>();
		SAXBuilder builderRegioni = new SAXBuilder();
		Document documentRegioni = builderRegioni.build(new File("/source/main/Regioni.xml"));
		Element regioniRootElement=documentRegioni.getRootElement();
		List<Element> elencoRegioni =regioniRootElement.getChildren();
		for(Element regione:elencoRegioni)
		{	
			//Leggo il nome della regione
			List<String>nomiCittà=new ArrayList<String>();
			List<Element> elencoCittàRegione =regione.getChildren();
			//Leggo valore dell'unico attributo(nome della città), lo copio nella lista dei nomi 
			//e la passo al costruttore della regione, la quale creerà le città e penserà a collegarle
			for(Element città: elencoCittàRegione)
			{
				nomiCittà.add(città.getAttributeValue("id"));
			}
			//Costruisco la regione
			this.regioni.add(new Regione(regione.getAttributeValue("id"),nomiCittà,this));
		}
	}
	
	public List<Set<Bonus>> creaGettoniCittà() throws JDOMException, IOException{
		//Creo la lista di bonus per le città(Gettoni città da file), la mischio
		List<Set<Bonus>> gettoniCittà=new ArrayList<Set<Bonus>>(14);
		BonusCreator bonusCreator = new BonusCreator(this);
				//Leggo i set di bonus
				SAXBuilder builderGettoni = new SAXBuilder();
				Document documentGettoni = builderGettoni.build(new File("/source/main/BonusCittà.xml"));
				Element bonusCittàRootElement=documentGettoni.getRootElement();
				List<Element> elencoSetBonus =bonusCittàRootElement.getChildren();
				for(Element set:elencoSetBonus){
					List<Element> elencoBonus =set.getChildren();
					Set<Bonus> bonus=new HashSet<Bonus>();
					for(Element bon:elencoBonus){ //Leggo i set di bonus, li inizializzo e li copio nella lista di bonus
						try{
							bonus.add(bonusCreator.creaBonus(bon.getAttributeValue("id"), Integer.parseInt(bon.getAttributeValue("attributo"))));
						}
						catch(Exception e){
							log.log(Level.WARNING,e.getMessage(), e);
						}
					}
					gettoniCittà.add(bonus);//Aggiungo i set di bonus alla lista di bonus
				}
				Collections.shuffle(gettoniCittà);
				return gettoniCittà;
				
	}
	
	public void collegaCittà(String nomeFileMappa,List<Set<Bonus>> gettoniCittà) throws JDOMException, IOException {
		SAXBuilder builderCollegamenti = new SAXBuilder();
		Document documentCollegamenti = builderCollegamenti.build(new File(nomeFileMappa));
		Element collegamentiRootElement=documentCollegamenti.getRootElement();
		List<Element> elencoCittà =collegamentiRootElement.getChildren();
		for(Regione reg:regioni)
		{
			for(Città cit:reg.getCittà())
			{
				//ora devo leggere il file delle città e trovare il match, con il nome, coloro e collego
				for(Element cittàMappa: elencoCittà)
				{
					if(cittàMappa.getAttributeValue("nome")==cit.getNome())
					{
						cit.setColore(cittàMappa.getAttributeValue("colore"));
						if(cit.getColore().equals("Viola"))
						{
							Re re=new Re("Blallo", cit, new Consiglio(this));//Quale dovrebbe essere il colore del re??
							cit.setRe(re);
							this.setRe(re);
						}
						else
						{
							cit.getBonus().addAll(gettoniCittà.get(0));
							gettoniCittà.remove(0);
						}
						List<Element> elencoCollegamenti =cittàMappa.getChildren();
						for(Element coll:elencoCollegamenti)
						{
							cit.getCittàVicina().add(cercaCittà(coll.getText()));
						}
					}
				}
			}
		}
	}
	
	/**
	 * initialize the tiles tessereBonusRegione, tessereBonusCittà, tesserePremioRe
	 * reading them from a file
	 * @throws JDOMException
	 * @throws IOException throw an exception if the method doesn't found the file or
	 *         there is another error about the file
	 */
	public void creaTessereBonus() throws JDOMException, IOException{
		BonusCreator bonusCreator = new BonusCreator(this);
		TesseraBonusCreator tesseraBonusCreator = new TesseraBonusCreator(this);
		//inizializzo i set di tessere
		this.tessereBonusCittà=new HashSet<TesseraBonusCittà>();
		this.tessereBonusRegione=new HashSet<TesseraBonusRegione>();
		this.tesserePremioRe=new ArrayList<TesseraPremioRe>();
		SAXBuilder builderTessereBonus = new SAXBuilder();
		//leggo il documento xml per le tessere
		Document documentTessereBonus = builderTessereBonus.build(new File("/source/main/TessereBonus.xml"));
		Element tessereBonusRootElement = documentTessereBonus.getRootElement();
		List<Element> tessere = tessereBonusRootElement.getChildren();
		for(Element t:tessere){
			
			//leggo il tipo di bonus e l' attributo da assegnare alla tessera
			Element tipoBonus = t.getChild("Bonus");
			Set<Bonus> bonus= new HashSet<Bonus>();
			try{
				bonus.add(bonusCreator.creaBonus(tipoBonus.getAttributeValue("id"), Integer.parseInt(tipoBonus.getAttributeValue("attributo"))));
				//creo le tessere bonus per la regione
				if(t.getAttributeValue("id").equals("regione")){
					tessereBonusRegione.add((TesseraBonusRegione) tesseraBonusCreator.creaTesseraBonus(t.getAttributeValue("id"), t.getAttributeValue("attributo"), bonus));
				}
				//costruisco le tessere bonus per le città
				else if(t.getAttributeValue("id").equals("città")){
					tessereBonusCittà.add((TesseraBonusCittà) tesseraBonusCreator.creaTesseraBonus(t.getAttributeValue("id"), t.getAttributeValue("attributo"), bonus));
				}
				//costruisco le tessere premio del re
				else if(t.getAttributeValue("id").equals("premioRe")){
					tesserePremioRe.add((TesseraPremioRe) tesseraBonusCreator.creaTesseraBonus(t.getAttributeValue("id"), t.getAttributeValue("attributo"), bonus));
				}
			}
			catch(Exception e){
				log.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}
	
	public Città cercaCittà(String nome)
	{
		for(Regione regione:this.regioni)
			for(Città cit: regione.getCittà())
				if(cit.getNome().equals(nome))
					return cit;
		return null;
	}
	
	/**
	 * 
	 * @param consigliereInTab
	 * @return Returns true if this list contains the specified element
	 */
	public boolean ifConsigliereDisponibile(Consigliere consigliereInTab){
			return consiglieriDisponibili.contains(consigliereInTab);
	}
	
	
	/**
	 * @author Luca
	 * This method appends the specified element to the end of this list
	 * @param consigliereDaAggiungere
	 * 
	 */
	public void addConsigliereDisponibile(Consigliere consigliereDaAggiungere){
		try{
			consiglieriDisponibili.add(consigliereDaAggiungere);
		}
		catch(UnsupportedOperationException e1){
			System.err.println("L'operazione aggiungi non è supportata dalla lista"
					+ "consiglieriDisponibili del Tabellone");
		}
		catch(ClassCastException e2){
			System.err.println("La classe del tipo specificato non è adatta al tipo della lista"
					+ "consiglieriDisponibili del Tabellone");
		}
		catch(NullPointerException e3){
			System.err.println("L'elemento specificato è null, non si può aggiungere alla"
					+ "lista consiglieriDisponibili del Tabellone" );
		}
		catch(IllegalArgumentException e4){
			System.err.println("Alcune proprietà dell'elemento impediscono di aggiungerlo"
					+ "correttamente alla lista consiglierDisponibili del Tabellone");
		}}
	
	/**
	 * check if a player has an emporium every the city of a color
	 * @param giocatore the player who has to check the bonus
	 * @param città the city in which the method take the color
	 * @author riccardo
	 * @return return true if the player has an emporium in every city of a color
	 */
	public boolean verificaEmporioColoreBonus(Giocatore giocatore,Città città){
		for(Regione r:regioni){
			for(Città c:r.getCittà()){
				if (città.getColore()==c.getColore()){
					if(!c.getEmpori().contains(giocatore)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param giocatore the player who builds the emporium
	 * @param città the city in which the player build the emporium
	 * @return return true if the player has an emporium in every city of a region
	 */
	public boolean verificaEmporioRegioneBonus(Giocatore giocatore,Città città){
		for(Città c:città.getRegione().getCittà()){
			if(!c.getEmpori().contains(giocatore)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * check if the player who has built the emporium can take the bonus for 
	 * having built an emporium in every city of a color or in every city of a region.
	 * Then assigns the tile/tiles.
	 * @author riccardo
	 * @param giocatore the player who has built the emporium
	 * @param città the city where the player has built the emporium
	 */
	public void prendiTesseraBonus(Giocatore giocatore,Città città){
		if(verificaEmporioColoreBonus(giocatore, città)){
			for(TesseraBonusCittà t:tessereBonusCittà){
				if(città.getColore().equals(t.getColore())){
					t.eseguiBonus(giocatore);
					tessereBonusCittà.remove(t);
				}
			}
		}
		if(verificaEmporioRegioneBonus(giocatore, città)){
			for(TesseraBonusRegione t:tessereBonusRegione){
				if(t.getRegione().equals(città.getRegione())){
					t.eseguiBonus(giocatore);
					tessereBonusRegione.remove(t);
				}
			}
		}
		if(!tesserePremioRe.isEmpty()){
			tesserePremioRe.get(0).eseguiBonus(giocatore);
			tesserePremioRe.remove(0);
		}
	}
	
	/**
	 * Removes the first occurrence of the specified 
	 * element from this list, if it is present
	 * @author Luca
	 * @param consigliereDaRimuovere
	 */
	public void removeConsigliereDisponibile(Consigliere consigliereDaRimuovere){
		try{
			consiglieriDisponibili.remove(consigliereDaRimuovere);
		}
		catch(ClassCastException e1){
			System.err.println("Il tipo specificato non è compatibile con la lista");
		}
		catch(NullPointerException e2){
			System.err.println("Il tipo specificato è Null ");
		}
		catch(UnsupportedOperationException e3){
			System.err.println("l'operazione di rimozione non è supportata dalla lista");
		}
	}
	
	/**
	 * 
	 * @param nomeRegione the name of the region you wnat to find
	 * @return return the region with nomeRegione as name
	 * @throws NullPointerException if nomeRegione is null
	 * @throws IllegalArgumentException if nomeRegioe isn't correct
	 */
	public Regione getRegioneDaNome(String nomeRegione){
		if(nomeRegione==null){
			throw new NullPointerException("nomeRegione non può essere nullo");
		}
		for(Regione r:regioni){
			if(r.getNome().equals(nomeRegione)){
				return r;
			}
		}
		throw new IllegalArgumentException("il nome della regione inserito non esiste");
	}
}

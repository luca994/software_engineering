package model;

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

/**
 * @author Riccardo
 *
 */
public class Tabellone {

	private Set<Regione> regioni;
	private Set<OggettoConBonus> tessereBonus;
	private List<Consigliere> consiglieriDisponibili;
	private Set<Consiglio> consigli;
	private Percorso percorsoNobiltà;
	private Percorso percorsoRicchezza;
	private Percorso percorsoVittoria;
	

	/**
	 * @return the regioni
	 */
	public Set<Regione> getRegioni() {
		return regioni;
	}


	/**
	 * @return the tessereBonus
	 */
	public Set<OggettoConBonus> getTessereBonus() {
		return tessereBonus;
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
		this.percorsoVittoria=new Percorso("PercorsoVittoria", this);	
		this.percorsoRicchezza=new Percorso("percorsoRicchezza", this);
		this.percorsoNobiltà= new Percorso("percorsoNobiltà", this);
		//Creazione consiglieriDisponibili
		this.consiglieriDisponibili= new ArrayList<Consigliere>(24);
		SAXBuilder builderConsiglieri = new SAXBuilder();
		Document documentConsiglieri = builderConsiglieri.build(new File("Consiglieri.xml"));
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
		
		
		//Creo regioni (da file ovviamente):passo al costruttore della regione una lista di nomi di città
		//Sarà il costruttore delle Regioni a collegarle
		this.regioni=new HashSet<Regione>();
		SAXBuilder builderRegioni = new SAXBuilder();
		Document documentRegioni = builderRegioni.build(new File("Regioni.xml"));
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
			this.regioni.add(new Regione(percorsoRicchezza,percorsoNobiltà,percorsoVittoria,regione.getAttributeValue("id"),nomiCittà,this));
		}
		//Creo la lista di bonus per le città(Gettoni città da file), la mischio
		List<Set<Bonus>> gettoniCittà=new ArrayList<Set<Bonus>>(14);
		//Leggo i set di bonus
		SAXBuilder builderGettoni = new SAXBuilder();
		Document documentGettoni = builderGettoni.build(new File("BonusCittà.xml"));
		Element bonusCittàRootElement=documentGettoni.getRootElement();
		List<Element> elencoSetBonus =bonusCittàRootElement.getChildren();
		for(Element set:elencoSetBonus){
			List<Element> elencoBonus =set.getChildren();
			Set<Bonus> bonus=new HashSet<Bonus>();
			for(Element bon:elencoBonus){ //Leggo i set di bonus, li inizializzo e li copio nella lista di bonus
				if(bon.getAttributeValue("id").equals("BonusMoneta"))
					bonus.add(new BonusMoneta(percorsoRicchezza, Integer.parseInt(bon.getAttributeValue("step"))));
				else if(bon.getAttributeValue("id").equals("BonusPuntoVittoria"))
					bonus.add(new BonusPuntoVittoria(percorsoVittoria, Integer.parseInt(bon.getAttributeValue("passi"))));
				else if(bon.getAttributeValue("id").equals("BonusCartaPolitica"))
					bonus.add(new BonusCartaPolitica(Integer.parseInt(bon.getAttributeValue("numeroCarte"))));
				else if(bon.getAttributeValue("id").equals("BonusAssistenti"))
					bonus.add(new BonusAssistenti(Integer.parseInt(bon.getAttributeValue("numeroAssistenti"))));
				else if(bon.getAttributeValue("id").equals("BonusPercorsoNobiltà"))
					bonus.add(new BonusPercorsoNobiltà(percorsoNobiltà, Integer.parseInt(bon.getAttributeValue("steps"))));
				else if(bon.getAttributeValue("id").equals("AzionePrincipale"))
					bonus.add(new BonusAzionePrincipale());
			}
			gettoniCittà.add(bonus);//Aggiungo i set di bonus alla lista di bonus
		}
		Collections.shuffle(gettoniCittà);
		
		//Collego, coloro le città(devo passare attraverso le regioni), creo e assegno bonus, piazzo il re
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
							cit.setRe(new Re("Blallo", cit, new Consiglio(this)));//Quale dovrebbe essere il colore del re??
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
	 * This method removes the first occurrence of the specified 
	 * element from this list, if it is present
	 * @author Luca
	 * @param consigliereDaRimuovere
	 */
	public void removeConsigliereDisponibile(Consigliere consigliereDaRimuovere){
		try{
			consiglieriDisponibili.remove(consigliereDaRimuovere);
		}
		catch(ClassCastException e1){
			System.out.println("Il tipo specificato non è compatibile con la lista");
		}
		catch(NullPointerException e2){
			System.out.println("Il tipo specificato è Null ");
		}
		catch(UnsupportedOperationException e3){
			System.out.println("l'operazione di rimozione non è supportata dalla lista");
		}
	}	
}

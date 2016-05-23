package server.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import server.model.bonus.Bonus;
import server.model.bonus.BonusCreator;

/**
 * @author Riccardo
 *
 */
public class Regione {

	Logger log = Logger.getLogger(Regione.class.getName());
	
	private Set<Città> città;
	private Consiglio consiglio;
	private String nome;
	private List<TesseraCostruzione> tessereCostruzione;
	private List<TesseraCostruzione> tessereCoperte;
	private Tabellone tabellone;
	
	/**
	 * build the region
	 * @param nomeRegione the name of the region
	 * @param nomiCittà the list of the cities names
	 * @param tabellone the game board
	 * @throws JDOMException
	 * @throws IOException if the file doesn't exist or there is an error in the file reading
	 */
	public Regione(String nomeRegione, List<String> nomiCittà, Tabellone tabellone) throws JDOMException, IOException{
		this.tabellone=tabellone;
		this.nome=nomeRegione;
		this.tessereCostruzione=new ArrayList<TesseraCostruzione>();
		this.tessereCoperte=new ArrayList<TesseraCostruzione>();
		this.città= new HashSet<Città>();
		creaCittà(nomiCittà);		
		//Creo consiglio, andrà lui a pigliarsi i primi quattro consiglieri dalla lista dei consiglieri disponibili
		//Devo passagli il tabellone, altrimenti non so dove andare a prendere i nuovi consiglieri
		this.consiglio=new Consiglio(tabellone);
		this.consiglio.setRegione(this);
		//Crea tessere permesso
		creaTesserePermesso(tabellone);
	}
	
	/**
	 * creates permit tiles reading them from a file. Then assigns the tiles to the list of tiles of the region 
	 * @param tabellone the game board
	 * @throws JDOMException
	 * @throws IOException if the file doesn't exist or there is an error in the file reading 
	 */
	public void creaTesserePermesso(Tabellone tabellone) throws JDOMException, IOException{
		BonusCreator bonusCreator = new BonusCreator(tabellone);
		//Creo il nome del file a partire dal nome della regione
		String nomefile = new String("src/main/resources/TessereCostruzione"+this.nome+".xml");
		//Leggo file e creo le TesserePermessoDiCostruzione
		SAXBuilder builderTessereCostruzione = new SAXBuilder();
		Document documentTessereCostruzione = builderTessereCostruzione.build(new File(nomefile));
		Element tessereRootElement=documentTessereCostruzione.getRootElement();
		List<Element> elencoTessere =tessereRootElement.getChildren();
		List<TesseraCostruzione> elencoRiferimentiTessere =new ArrayList<TesseraCostruzione>(15);
		for(Element tessere: elencoTessere)//scorro le tessere nel file
		{
			List<Element> elencoSet =tessere.getChildren();
			Set<Bonus> bonusTessera =new HashSet<Bonus>(3);
			Set<Città> elencoRiferimentiCittà=new HashSet<Città>(3);
			for(Element set:elencoSet)//Scorro i set di bonus e città nel file
			{
				if(set.getName().equals("SetCittà"))
				{
					
					List<Element> elencoCittà=set.getChildren();
					for(Element cit:elencoCittà){
						//System.out.println(cit.getAttributeValue("id")+"  "+this.nome); //da cancellare
						elencoRiferimentiCittà.add(tabellone.cercaCittà(cit.getAttributeValue("id"),this));
					}
				}
				else if(set.getName().equals("SetBonus"))
				{
					List<Element> elencoBonus=set.getChildren();
					for(Element bon:elencoBonus)
					{
						if(bon.getAttributeValue("attributo")!=null)
							bonusTessera.add(bonusCreator.creaBonus(bon.getAttributeValue("id"), Integer.parseInt(bon.getAttributeValue("attributo")),tabellone.getGioco()));
						else{
							bonusTessera.add(bonusCreator.creaBonus(bon.getAttributeValue("id"), 0,tabellone.getGioco()));
						}
					}
				}
			}
			elencoRiferimentiTessere.add(new TesseraCostruzione(bonusTessera,elencoRiferimentiCittà,this));
		}
		setTessereCoperte(elencoRiferimentiTessere);
		
		//da cancellare
		/*
		for(TesseraCostruzione t:elencoRiferimentiTessere){
			System.out.println(t.getRegioneDiAppartenenza().getNome()+"     "+t);
			System.out.println("    ");
			for(Città c:t.getCittà()){
				System.out.println(c);
			}
		}*/
	}
	
	/**
	 * creates the cities of a region
	 * @param nomiCittà the list of names of the cities
	 */
	public void creaCittà(List<String> nomiCittà){
		for(String nome: nomiCittà)//Creo città
		{
			this.città.add(new Città(nome,this));
		}
		//da cancellare
		/*for(Città c:this.città){
			System.out.println(c.getNome()+"    "+c);
		}*/
	}
	
	/**
	 * sets the covered permit tiles of a region.
	 * @param elencoRiferimentiTessere the list of permit tiles to set in the region
	 */
	public void setTessereCoperte(List<TesseraCostruzione> elencoRiferimentiTessere){
		this.tessereCoperte=elencoRiferimentiTessere;
		Collections.shuffle(this.tessereCoperte);
		this.tessereCostruzione.add(this.tessereCoperte.remove(0));
		this.tessereCostruzione.add(this.tessereCoperte.remove(0));
	}
	
	/**
	 *Removes the parameter tessera from the list of obtainable Tessere Permesso Costruzione and moves the first
	 *element of the covered Tessere Permesso Costruzione in to the list of obtainable Tessere
	 * @param tessera
	 */
	public void nuovaTessera(TesseraCostruzione tessera)
	{
		if(this.tessereCostruzione.remove(tessera)){
			this.tessereCostruzione.add(this.tessereCoperte.get(0));
			this.tessereCoperte.remove(0);
		}
	}
	
	/**
	 * @return the città
	 */
	public Set<Città> getCittà() {
		return città;
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

package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author Riccardo
 *
 */
public class Regione {

	private Set<Città> città;
	private Consiglio consiglio;
	private String nome;
	private List<TesseraCostruzione> tessereCostruzione;
	private List<TesseraCostruzione> tessereCoperte;
	
	public Regione(Percorso percorsoRicchezza,Percorso percorsoNobiltà, Percorso percorsoVittoria, String nomeRegione, List<String> nomiCittà, Tabellone tabellone) throws JDOMException, IOException{
		this.nome=nomeRegione;
		this.città=new HashSet<Città>();
		for(String nome: nomiCittà)//Creo città, ricordati di collegarle
		{
			this.città.add(new Città(nome,this));
		}		
		//Creo consiglio, andrà lui a pigliarsi i primi quattro consiglieri dalla lista dei consiglieri disponibili
		//Devo passagli il tabellone, altrimenti non so dove andare a prendere i nuovi consiglieri
		this.consiglio=new Consiglio(tabellone);
		//Creo il nome del file a partire dal nome della regione
		String nomefile = new String("TessereCostruzione"+nomeRegione);
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
					for(Element cit:elencoCittà)
						elencoRiferimentiCittà.add(tabellone.cercaCittà(cit.getAttributeValue("id")));
				}
				else if(set.getName().equals("SetBonus"))
				{
					List<Element> elencoBonus=set.getChildren();
					for(Element bon:elencoBonus)
					{
						if(bon.getAttributeValue("id").equals("BonusMoneta"))
							bonusTessera.add(new BonusMoneta(percorsoRicchezza, Integer.parseInt(bon.getAttributeValue("step"))));
						else if(bon.getAttributeValue("id").equals("BonusPuntoVittoria"))
							bonusTessera.add(new BonusPuntoVittoria(percorsoVittoria, Integer.parseInt(bon.getAttributeValue("passi"))));
						else if(bon.getAttributeValue("id").equals("BonusCartaPolitica"))
							bonusTessera.add(new BonusCartaPolitica(Integer.parseInt(bon.getAttributeValue("numeroCarte"))));
						else if(bon.getAttributeValue("id").equals("BonusAssistenti"))
							bonusTessera.add(new BonusAssistenti(Integer.parseInt(bon.getAttributeValue("numeroAssistenti"))));
						else if(bon.getAttributeValue("id").equals("BonusPercorsoNobiltà"))
							bonusTessera.add(new BonusPercorsoNobiltà(percorsoNobiltà, Integer.parseInt(bon.getAttributeValue("steps"))));
						else if(bon.getAttributeValue("id").equals("AzionePrincipale"))
							bonusTessera.add(new BonusAzionePrincipale());
					}
				}
			}
			elencoRiferimentiTessere.add(new TesseraCostruzione(elencoRiferimentiCittà,bonusTessera));
		}
		setTessereCoperte(elencoRiferimentiTessere);
	}
	
	public void setTessereCoperte(List<TesseraCostruzione> elencoRiferimentiTessere){
		this.tessereCoperte=elencoRiferimentiTessere;
		Collections.shuffle(this.tessereCoperte);
		this.tessereCostruzione.add(this.tessereCoperte.get(0));
		this.tessereCostruzione.add(this.tessereCoperte.get(1));
		for(OggettoConBonus tessera: this.tessereCostruzione)
		{
			this.tessereCoperte.remove(tessera);
		}
			
	}
	/**
	 *Removes the parameter tessera from the list of obtainable Tessere Permesso Costruzione and moves the first
	 *element of the covered Tessere Permesso Costruzione in to the list of obtainable Tessere
	 * @param tessera
	 */
	public void nuovaTessera(OggettoConBonus tessera)
	{
		this.tessereCostruzione.remove(tessera);
		this.tessereCostruzione.add(this.tessereCoperte.get(0));
		this.tessereCoperte.remove(0);
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

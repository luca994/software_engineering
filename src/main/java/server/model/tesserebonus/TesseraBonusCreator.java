package server.model.tesserebonus;

import java.util.Set;

import server.model.Tabellone;
import server.model.bonus.Bonus;

public class TesseraBonusCreator {

	private Tabellone tabellone;
	
	/**
	 * build an object of TesseraBonusCreator
	 * @param tabellone the game board
	 */
	public TesseraBonusCreator(Tabellone tabellone){
		this.tabellone=tabellone;
	}
	
	/**
	 * build the bonus tile based on the type of the tile
	 * @param tipoTessera the type of the tile
	 * @param attributo the attribute of the tile (it can be the name of the region or the color of the city)
	 * @param bonus the set of bonus of the tile
	 * @return return the bonus tile
	 * @throws NullPointerException if tipoTessera is null
	 * @throws IllegalArgumentException if tipoTessera isn't correct
	 */
	public TesseraBonus creaTesseraBonus(String tipoTessera,String attributo,Set<Bonus> bonus){
		if(tipoTessera==null){
			throw new NullPointerException("tipoTessera non può essere nulla");
		}
		if(tipoTessera.equals("regione")){
			return new TesseraBonusRegione(bonus, tabellone.getRegioneDaNome(attributo));
		}
		else if(tipoTessera.equals("città")){
			return new TesseraBonusCitta(bonus, attributo);
		}
		else if(tipoTessera.equals("premioRe")){
			return new TesseraPremioRe(bonus);
		}
		throw new IllegalArgumentException("il tipo di tessera inserito non è corretto");
	}
}

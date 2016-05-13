package model.bonus;

import model.Tabellone;

public class BonusCreator {

	Tabellone tabellone;
	
	public BonusCreator(Tabellone tabellone){
		this.tabellone=tabellone;
	}
	
	/**
	 * build the bonus based on the type
	 * @param tipoBonus the type of bonus you want to build
	 * @param percorso the route of the game (it can be money, victory or nobility)
	 * @param attributo the attribute that must be passed to the constructor
	 * @return return the bonus
	 * @throws NullPointerException if tipoBonus is null
	 * @throws IllegalArgumentException if tipoBonus isn't correct
	 */
	public Bonus creaBonus(String tipoBonus, int attributo){
		if(tipoBonus==null){
			throw new NullPointerException("il tipo di bonus non può essere nullo");
		}
		if("BonusMoneta".equals(tipoBonus)) 
			return new BonusMoneta(tabellone.getPercorsoRicchezza(),attributo);
		else if("BonusPuntoVittoria".equals(tipoBonus)) 
			return new BonusPuntoVittoria(tabellone.getPercorsoVittoria(), attributo);
		else if("BonusCartaPolitica".equals(tipoBonus)) 
			return new BonusCartaPolitica(attributo);
		else if("BonusAssistenti".equals(tipoBonus)) 
			return new BonusAssistenti(attributo);
		else if("BonusPercorsoNobiltà".equals(tipoBonus)) 
			return new BonusPercorsoNobiltà(tabellone.getPercorsoNobiltà(), attributo);
		else if("BonusGettoneCittà".equals(tipoBonus)) 
			return new BonusGettoneCittà(attributo);
		else if("BonusAzionePrincipale".equals(tipoBonus))
			return new BonusAzionePrincipale();
		else if("BonusRiutilizzoCostruzione".equals(tipoBonus)) 
			return new BonusRiutilizzoCostruzione();
		else if("BonusTesseraPermesso".equals(tipoBonus))
			return new BonusTesseraPermesso();
		throw new IllegalArgumentException("il tipo di bonus non è corretto o non esiste");
	}
}

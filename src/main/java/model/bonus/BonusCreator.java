package model.bonus;

import model.Gioco;
import model.Tabellone;
import model.percorso.Percorso;

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
	public Bonus creaBonus(String tipoBonus, int attributo, Gioco gioco){
		if(tipoBonus==null){
			throw new NullPointerException("il tipo di bonus non può essere nullo");
		}
		if(tipoBonus.equals("BonusMoneta")) return new BonusMoneta(tabellone.getPercorsoRicchezza(),attributo);
		else if(tipoBonus.equals("BonusPuntoVittoria")) return new BonusPuntoVittoria(tabellone.getPercorsoVittoria(), attributo);
		else if(tipoBonus.equals("BonusCartaPolitica")) return new BonusCartaPolitica(attributo);
		else if(tipoBonus.equals("BonusAssistenti")) return new BonusAssistenti(attributo);
		else if(tipoBonus.equals("BonusPercorsoNobiltà")) return new BonusPercorsoNobiltà(tabellone.getPercorsoNobiltà(), attributo);
		else if(tipoBonus.equals("BonusGettoneCittà")) return new BonusGettoneCittà(attributo,gioco);
		else if(tipoBonus.equals("BonusAzionePrincipale")) return new BonusAzionePrincipale();
		else if(tipoBonus.equals("BonusRiutilizzoCostruzione")) return new BonusRiutilizzoCostruzione(gioco);
		else if(tipoBonus.equals("BonusTesseraPermesso")) return new BonusTesseraPermesso();
		throw new IllegalArgumentException("il tipo di bonus non è corretto o non esiste");
	}
}

package bonus;

import percorso.Percorso;

public class BonusCreator {

	public Bonus creaBonus(String tipoBonus,Percorso percorso, int attributo){
		if(tipoBonus==null){
			throw new NullPointerException("il tipo di bonus non può essere nullo");
		}
		if(tipoBonus.equals("BonusMoneta")) return new BonusMoneta(percorso,attributo);
		else if(tipoBonus.equals("BonusPuntoVittoria")) return new BonusPuntoVittoria(percorso, attributo);
		else if(tipoBonus.equals("BonusCartaPolitica")) return new BonusCartaPolitica(attributo);
		else if(tipoBonus.equals("BonusAssistenti")) return new BonusAssistenti(attributo);
		else if(tipoBonus.equals("BonusPercorsoNobiltà")) return new BonusPercorsoNobiltà(percorso, attributo);
		else if(tipoBonus.equals("BonusGettoneCittà")) return new BonusGettoneCittà(attributo);
		else if(tipoBonus.equals("BonusAzionePrincipale")) return new BonusAzionePrincipale();
		else if(tipoBonus.equals("BonusRiutilizzoCostruzione")) return new BonusRiutilizzoCostruzione();
		else if(tipoBonus.equals("BonusTesseraPermesso")) return new BonusTesseraPermesso();
		throw new IllegalArgumentException("il tipo di bonus non è corretto o non esiste");
	}
}

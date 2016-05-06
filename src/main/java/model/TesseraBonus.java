package model;

import java.util.Set;

/**
 * 
 * @author Riccardo
 *
 */
public class TesseraBonus extends OggettoConBonus {

	private Regione regione;
	private String colore;

	public TesseraBonus(Regione regione, String colore, Set<Bonus> bonus){
		super(bonus);
		this.regione=regione;
		this.colore=colore;
	}
}

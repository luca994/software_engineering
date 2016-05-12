package model;

/**
 * 
 * @author Riccardo
 *
 */
public class Re {

	private final String colore;
	private Consiglio consiglio;
	private Città città;
	
	/**
	 * build the king
	 * @param colore the color of the king
	 */
	public Re(String colore, Città città, Consiglio consiglio){
		this.colore=colore;
		this.città=città;
		this.consiglio=consiglio;
	}
	
	/**
	 * @return the consiglio
	 */
	public Consiglio getConsiglio() {
		return consiglio;
	}

	/**
	 * @return the città
	 */
	public Città getCittà() {
		return città;
	}
	
	

	public void mossa(Città città){
		if(!this.città.getCittàVicina().contains(città)){
			System.out.println("La città inserita non è collegata a questa città");
			return;
		}
		città.setRe(this.città.getRe());
		this.città.setRe(null);
	}
}

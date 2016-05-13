package model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Riccardo
 *
 */
public class Re {

	private static final Logger log= Logger.getLogger( Re.class.getName() );
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
		try{
			if(città==null)
				throw new NullPointerException("La città non può essere nulla");
			if(!this.città.getCittàVicina().contains(città)){
				System.out.println("La città inserita non è collegata a questa città");
				return;
			}
			città.setRe(this.città.getRe());
			this.città.setRe(null);
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
	}
	
}

package model;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.bonus.Bonus;

/**
 * 
 * @author Riccardo
 *
 */

public class Città extends OggettoConBonus {

	private static final Logger log= Logger.getLogger( Città.class.getName() );
	private final String nome;
	private String colore;
	private Set<Giocatore> empori;
	private Re re;
	private Set<Città> cittàVicina;
	private final Regione regione;

	
	/**
	 * 
	 * @return return the set of emporiums built in the city
	 */
	public Set<Giocatore> getEmpori() {
		return empori;
	}

	/**
	 * 
	 * @return return the name of the city
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * 
	 * @return return the color of the city
	 */
	public String getColore() {
		return colore;
	}

	/**
	 * 
	 * @return return the reference to the king, if it is in the city
	 */
	public Re getRe() {
		return re;
	}

	/**
	 * 
	 * @return return the region of the city
	 */
	public Regione getRegione() {
		return regione;
	}

	/**
	 * 
	 * @return return the set of near cities
	 */
	public Set<Città> getCittàVicina() {
		return cittàVicina;
	}

	/**
	 * 
	 * @return the set of bonus of the city
	 */
	public Set<Bonus> getBonus() {
		return this.getBonus();
	}
	
	/**
	 * set the reference to the king
	 * @param re the reference to the king
	 */
	public void setRe(Re re) {
		this.re = re;
	}
	
	/**
	 * @param colore the colore to set
	 */
	public void setColore(String colore) {
		this.colore = colore;
	}
	
	
	/**
	 * @param nome
	 * @param regione
	 */
	public Città(String nome, Regione regione) {
		super(null);
		this.nome = nome;
		this.regione = regione;
	}

	/**
	 * 
	 * @param giocatore the owner of the emporium
	 * @return return true if there is an emporium of the player
	 */
	public boolean presenzaEmporio(Giocatore giocatore){
		try{
			if (giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			if(empori.contains(giocatore)){
				return true;
			}
		return false;
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}
		return false;
	}
	
	/**
	 * 
	 * @param giocatore the owner of the emporium
	 * @return return a list of near cities which have the player' s emporium
	 */
	//cittàVicineConEmpori inizialmente vuota
	public List<Città> cittàVicinaConEmporio(Giocatore giocatore,List<Città> cittàVicineConEmpori){
		for(Città c:cittàVicina){
			if (c.presenzaEmporio(giocatore)&&!cittàVicineConEmpori.contains(c)){
				cittàVicineConEmpori.add(c);
				c.cittàVicinaConEmporio(giocatore, cittàVicineConEmpori);
			}
		}
		return cittàVicineConEmpori;
	}
	
	/**
	 * checks if there is already a player's emporium.
	 * If there is not, add a player's emporium.
	 * @param giocatore the player who wants to add the emporium  
	 * @return return true if there is a player's emporium
	 */
	public boolean aggiungiEmporio(Giocatore giocatore){
		if(!presenzaEmporio(giocatore)){
			empori.add(giocatore);
			return true;
		}
		return false;
	}
	
}

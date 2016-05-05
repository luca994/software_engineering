package model;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Riccardo
 *
 */

public class Città extends OggettoConBonus {

	private final String nome;
	private final String colore;
	private Set<Giocatore> empori;
	private Re re;
	private Set<Città> cittàVicina;
	private Set<Bonus> bonus;
	
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
	 * @return return the set of near cities
	 */
	public Set<Città> getCittàVicina() {
		return cittàVicina;
	}

	/**
	 * 
	 * @return return the set of bonus of the city
	 */
	public Set<Bonus> getBonus() {
		return bonus;
	}
	
	/**
	 * set the reference to the king
	 * @param re the reference to the king
	 */
	public void setRe(Re re) {
		this.re = re;
	}

	/**
	 * build a city
	 * @param nome the name of the city
	 * @param colore the color of the city
	 */
	public Città(String nome,String colore){
		this.nome=nome;
		this.colore=colore;
	}
	
	/**
	 * 
	 * @param giocatore the owner of the emporium
	 * @return return true if there is an emporium of the player
	 */
	public boolean presenzaEmporio(Giocatore giocatore){
		if(empori.contains(giocatore)){
			return true;
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
	
	/**
	 * it does the action of the bonuses in the city
	 * @param giocatore the player who wants to add the emporium
	 */
	public void eseguiAzioneBonus(Giocatore giocatore){
		Iterator<Bonus> itrBonus= bonus.iterator();
		while(itrBonus.hasNext()){
			itrBonus.next().azioneBonus(giocatore);
		}
	}
	
}

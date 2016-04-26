package model;

import java.util.ArrayList;
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
	public List<Città> cittàVicinaConEmporio(Giocatore giocatore){
		List<Città> cittàVicineConEmpori=new ArrayList<Città>();
		for(Città c:cittàVicina){
			if (c.presenzaEmporio(giocatore)){
				cittàVicineConEmpori.add(c);
			}
		}
		return cittàVicineConEmpori;
	}
	
	public boolean aggiungiEmporio(Giocatore giocatore){
		if(presenzaEmporio(giocatore)==false){
			empori.add(giocatore);
			return true;
		}
		return false;
	}
	
	public void eseguiAzioneBonus(Giocatore giocatore){
		Iterator<Bonus> itrBonus= bonus.iterator();
		while(itrBonus.hasNext()){
			itrBonus.next().azioneBonus(giocatore);
		}
	}
	
}

package model;

import java.util.ArrayList;
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
	private Set<Emporio> empori;
	private Re re;
	private Set<Città> cittàVicina;
	private Set<Bonus> bonus;
	
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
		for(Emporio e:empori){
			if(e.getGiocatore()==giocatore){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param giocatore the owner of the emporium
	 * @return return a list of near cities which have the player' s emporium
	 */
	public List<Città> cittàVicinaConEmporio(Giocatore giocatore){
		List<Città> cittàVicineConEmpori=new ArrayList();
		for(Città c:cittàVicina){
			if (c.presenzaEmporio(giocatore)){
				cittàVicineConEmpori.add(c);
			}
		}
		return cittàVicineConEmpori;
	}
	
}

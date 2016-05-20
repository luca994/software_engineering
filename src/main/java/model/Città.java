package model;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 * @author Riccardo
 *
 */

public class Città extends OggettoConBonus {

	private static final Logger log= Logger.getLogger( Città.class.getName() );
	private final String nome;
	private final Regione regione;
	private Color colore;
	private Set<Giocatore> empori;
	private Re re;
	private Set<Città> cittàVicina;
	
	
	/**
	 * @param nome
	 * @param regione
	 */
	public Città(String nome, Regione regione) {
		super(null);
		this.nome = nome;
		this.regione = regione;
		this.empori=new HashSet<Giocatore>();
		this.cittàVicina= new HashSet<Città>();
	}
	
	/**
	 * checks if there is already a player's emporium.
	 * If there is not, add a player's emporium.
	 * @param giocatore the player who wants to add the emporium  
	 * @return return true if there is a player's emporium
	 * @throws NullPointerException if giocatore is null
	 * @throws IllegalStateException if there is already a player's emporium
	 */
	public void aggiungiEmporio(Giocatore giocatore){
		try{
			if (giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			if(!presenzaEmporio(giocatore)){
				empori.add(giocatore);
			}
			else{
				throw new IllegalStateException("Hai già un'emporio in questa città");
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
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
			else{
				return false;
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}
	
	/**
	 * 
	 * @param giocatore the owner of the emporium
	 * @return return a list of near cities which have the player' s emporium
	 */
	//cittàVicineConEmpori inizialmente vuota si riempe con la ricorsione
	public List<Città> cittàVicinaConEmporio(Giocatore giocatore,List<Città> cittàVicineConEmpori){
		try{
			if (giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			for(Città c:cittàVicina){
				if (c.presenzaEmporio(giocatore)&&!cittàVicineConEmpori.contains(c)){
					cittàVicineConEmpori.add(c);
					c.cittàVicinaConEmporio(giocatore, cittàVicineConEmpori);
				}
			}
		return cittàVicineConEmpori;
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}

	/**
	 * @return the empori
	 */
	public Set<Giocatore> getEmpori() {
		return empori;
	}

	/**
	 * @param empori the empori to set
	 */
	public void setEmpori(Set<Giocatore> empori) {
		this.empori = empori;
	}

	/**
	 * @return the re
	 */
	public Re getRe() {
		return re;
	}

	/**
	 * @param re the re to set
	 */
	public void setRe(Re re) {
		this.re = re;
	}

	/**
	 * @return the cittàVicina
	 */
	public Set<Città> getCittàVicina() {
		return cittàVicina;
	}

	/**
	 * @param cittàVicina the cittàVicina to set
	 */
	public void setCittàVicina(Set<Città> cittàVicina) {
		this.cittàVicina = cittàVicina;
	}

	/**
	 * @return the log
	 */
	public static Logger getLog() {
		return log;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the regione
	 */
	public Regione getRegione() {
		return regione;
	}

	/**
	 * @return the colore
	 */
	public Color getColore() {
		return colore;
	}

	/**
	 * @param colore the colore to set
	 */
	public void setColore(Color colore) {
		this.colore = colore;
	}
	
	
}

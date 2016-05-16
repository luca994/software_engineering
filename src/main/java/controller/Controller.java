/**
 * 
 */
package controller;

import model.Città;
import model.Giocatore;
import model.Gioco;
import view.View;

/**
 * @author Massimiliano Ventura
 *
 */
public class Controller {
	private Gioco gioco;
	public Controller(Gioco gioco)
	{
		this.gioco=gioco;
	}
	public String ottieniNomeMappa(){
		return new View().acquisisciNomeMappa();
	}
	public Città ottieniCittàBonus(Giocatore giocatore){
		
		String nomeCittà= new View().acquisisciCittàBonus();
		Città temp=gioco.getTabellone().cercaCittà(nomeCittà);
		if (temp.getEmpori().contains(giocatore))
			return temp;
		else
		{
			new View().messaggioErrore("Non ci sono empori di "+giocatore.getNome()+" nella città, scegline un'altra");
			return ottieniCittàBonus(giocatore);
		}
	}
}

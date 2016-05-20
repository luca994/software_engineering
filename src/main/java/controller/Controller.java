/**
 * 
 */
package controller;

import client.Observer;
import model.Città;
import model.Gioco;
import model.bonus.BonusGettoneCittà;
import view.View;

/**
 * @author Massimiliano Ventura
 *
 */
public class Controller implements Observer{
	
	private Gioco gioco;
	public Controller(Gioco gioco, View view)
	{
		view.registerObserver(this);
		this.gioco=gioco;
	}
	
	public String ottieniNomeMappa(){
		return new View(gioco).acquisisciNomeMappa();
	}
	/*
	public Città ottieniCittàBonus(Giocatore giocatore){
		
		String nomeCittà= new View(gioco).acquisisciCittàBonus();
		Città temp=gioco.getTabellone().cercaCittà(nomeCittà);
		if (temp.getEmpori().contains(giocatore))
			return temp;
		else
		{
			new View(gioco).messaggioErrore("Non ci sono empori di "+giocatore.getNome()+" nella città, scegline un'altra");
			return ottieniCittàBonus(giocatore);
		}
	}*/

	/*public void update(BonusGettoneCittà cambiamento,String input){
		Città città = this.gioco.getTabellone().cercaCittà(input);
		if(!cambiamento.getCittà().contains(città)){
			cambiamento.getCittà().add(città);
		}
		else{
			throw new IllegalStateException("Hai già scelto questa città");
		}
	}*/

	@Override
	public void update(Object cambiamento) { 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object cambiamento, String input) {
		if(cambiamento instanceof BonusGettoneCittà){
			try{	
				Città città = this.gioco.getTabellone().cercaCittà(input);
				if(!((BonusGettoneCittà) cambiamento).getCittà().add(città)){
					((BonusGettoneCittà) cambiamento).setCittàGiusta(false);
					gioco.notificaObservers("la città inserita è già stata scelta");
				}
			}
			catch(IllegalArgumentException e){
				((BonusGettoneCittà) cambiamento).setCittàGiusta(false);
				gioco.notificaObservers(e.getMessage());
			}
		}
	}
}

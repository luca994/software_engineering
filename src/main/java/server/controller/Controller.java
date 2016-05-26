/**
 * 
 */
package server.controller;

import java.util.ArrayList;
import java.util.List;

import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.Regione;
import server.model.TesseraCostruzione;
import server.model.bonus.Bonus;
import server.model.bonus.BonusGettoneCittà;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.observer.Observer;

/**
 * @author Massimiliano Ventura
 *
 */
public class Controller implements Observer{
	
	private Gioco gioco;
	public Controller(Gioco gioco)
	{
		this.gioco=gioco;
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
	public <Bonus> void update(Bonus cambiamento, String[] input) {
		if(cambiamento instanceof BonusGettoneCittà){
			try{	
				if(!input[0].equals("passa")){
					Citta citta = this.gioco.getTabellone().cercaCittà(input[0]);
					if(!((BonusGettoneCittà) cambiamento).getCittà().add(citta)){
						((BonusGettoneCittà) cambiamento).setCittàGiusta(false);
						gioco.notificaObservers("la città inserita è già stata scelta");
					}
					else return;
					}
			}
			catch(IllegalArgumentException e){
				((BonusGettoneCittà) cambiamento).setCittàGiusta(false);
				try {
					gioco.notificaObservers(e.getMessage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(cambiamento instanceof BonusTesseraPermesso){
			if(Integer.parseInt(input[0])<6 && Integer.parseInt(input[0])>=0){
				List<TesseraCostruzione> listaTessere = new ArrayList<TesseraCostruzione>();
				for(Regione r:gioco.getTabellone().getRegioni()){
					listaTessere.addAll(r.getTessereCostruzione());
				}
				((BonusTesseraPermesso) cambiamento).setTessera(listaTessere.get(Integer.parseInt(input[0])));
				for(Regione r:gioco.getTabellone().getRegioni()){
					r.nuovaTessera(listaTessere.get(Integer.parseInt(input[0])));
				}
			}
			else{
				throw new IllegalArgumentException("il numero inserito non è corretto");
			}
		}
		if(cambiamento instanceof BonusRiutilizzoCostruzione){
			Giocatore gio=((BonusRiutilizzoCostruzione) cambiamento).getGiocatore();
			if(input[0].equals("passa"))
				return;
			if(Integer.parseInt(input[0])==0){//sto usando la lista di tessere valide
				if(Integer.parseInt(input[0])<gio.getTessereValide().size() && Integer.parseInt(input[0])>=0){
					TesseraCostruzione tessera= gio.getTessereValide().get(Integer.parseInt(input[0]));
					((BonusRiutilizzoCostruzione) cambiamento).setTessera(tessera);
				}
				else{
					throw new IllegalArgumentException("input incoerente");
				}
			}
			else if(Integer.parseInt(input[0])==1){
				if(Integer.parseInt(input[0])<gio.getTessereUsate().size() && Integer.parseInt(input[0])>=0){
					TesseraCostruzione tessera= gio.getTessereUsate().get(Integer.parseInt(input[0]));
					((BonusRiutilizzoCostruzione) cambiamento).setTessera(tessera);
				}
				else{
					throw new IllegalArgumentException("input incoerente");
				}
			}
			else
				throw new IllegalArgumentException("lista sbagliata");
		}
	}
}

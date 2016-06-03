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
import server.model.bonus.BonusGettoneCitta;
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
	
	@Override
	public void update(Object cambiamento) { 
		
	}

	@Override
	public <Bonus> void update(Bonus cambiamento, String[] input) {
		if(cambiamento instanceof BonusGettoneCitta){
			try{	
				if(!input[0].equals("passa")){
					Citta citta = this.gioco.getTabellone().cercaCitta(input[0]);
					if(!((BonusGettoneCitta) cambiamento).getCitta().add(citta)){
						((BonusGettoneCitta) cambiamento).setCittaGiusta(false);
						gioco.notificaObservers("la città inserita è già stata scelta");
					}
					else return;
				}
			}
			catch(IllegalArgumentException e){
				gioco.notificaObservers(e.getMessage());
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
				((BonusTesseraPermesso) cambiamento).setTesseraCorretta(true);
			}
			else{
				gioco.notificaObservers("tessera inserita non valida");
			}
		}
		if(cambiamento instanceof BonusRiutilizzoCostruzione){
			Giocatore gio=((BonusRiutilizzoCostruzione) cambiamento).getGiocatore();
			if(input[0].equals("passa")){
				((BonusRiutilizzoCostruzione) cambiamento).setTesseraCostruzioneCorretta(true);
				return;
			}
			if(Integer.parseInt(input[0])==0){//sto usando la lista di tessere valide
				if(Integer.parseInt(input[0])<gio.getTessereValide().size() && Integer.parseInt(input[0])>=0){
					TesseraCostruzione tessera= gio.getTessereValide().get(Integer.parseInt(input[0]));
					((BonusRiutilizzoCostruzione) cambiamento).setTessera(tessera);
					((BonusRiutilizzoCostruzione) cambiamento).setTesseraCostruzioneCorretta(true);
				}
				else{
					gioco.notificaObservers("input incoerente");
				}
			}
			else if(Integer.parseInt(input[0])==1){
				if(Integer.parseInt(input[0])<gio.getTessereUsate().size() && Integer.parseInt(input[0])>=0){
					TesseraCostruzione tessera= gio.getTessereUsate().get(Integer.parseInt(input[0]));
					((BonusRiutilizzoCostruzione) cambiamento).setTessera(tessera);
					((BonusRiutilizzoCostruzione) cambiamento).setTesseraCostruzioneCorretta(true);
				}
				else{
					gioco.notificaObservers("input incoerente");
				}
			}
			else
				gioco.notificaObservers("lista sbagliata");
		}
	}
}

package server.observer;

import java.util.ArrayList;
import java.util.List;

import server.model.Giocatore;

public abstract class Observable <O,B>{

	private List<Observer<O,B>> observers;
	
	public Observable(){
		observers=new ArrayList<>();
	}
	
	public void notificaObservers(B cambiamento,String[] input){
		for(Observer<O,B> o:observers){
			o.update(cambiamento,input);
		}
	}
	
	public void notificaObservers(O cambiamento){
		for(Observer<O,B> o:observers){
			o.update(cambiamento);
		}
	}
	
	public void notificaObservers(O cambiamento, Giocatore attributo){
		for(Observer<O,B> o:observers){
			o.update(cambiamento, attributo);
		}
	}
	
	public void registerObserver(Observer<O,B> osservatore){
		observers.add(osservatore);
	}
	
	public void removeObserver(Observer<O,B> osservatore){
		observers.remove(osservatore);
	}	
}

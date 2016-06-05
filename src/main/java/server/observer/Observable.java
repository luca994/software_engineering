package server.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

	private List<Observer> observers;
	
	public Observable(){
		observers=new ArrayList<Observer>();
	}
	
	public <T> void notificaObservers(T cambiamento,String[] input){
		for(Observer o:observers){
			o.update(cambiamento,input);
		}
	}
	
	public <T> void notificaObservers(T cambiamento){
		for(Observer o:observers){
			o.update(cambiamento);
		}
	}
	
	public <T,S> void notificaObservers(T cambiamento, S attributo){
		for(Observer o:observers){
			o.update(cambiamento, attributo);
		}
	}
	
	public void registerObserver(Observer osservatore){
		observers.add(osservatore);
	}
	
	public void removeObserver(Observer osservatore){
		observers.remove(osservatore);
	}	
}

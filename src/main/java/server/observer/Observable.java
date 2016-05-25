package server.observer;

import java.io.IOException;
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
	
	public <T> void notificaObservers(T cambiamento) throws IOException {
		for(Observer o:observers){
			o.update(cambiamento);
		}
	}
	
	public void registerObserver(Observer osservatore){
		observers.add(osservatore);
	}
	
	public void removeObserver(Observer osservatore){
		observers.remove(osservatore);
	}	
}

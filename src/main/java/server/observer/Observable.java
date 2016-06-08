package server.observer;

import java.util.ArrayList;
import java.util.List;

import server.model.Giocatore;

public abstract class Observable <A,B>{

	private List<Observer<A,B>> observers;
	
	public Observable(){
		observers=new ArrayList<>();
	}
	
	public void notificaObservers(B cambiamento,String[] input){
		for(Observer<A,B> o:observers){
			o.update(cambiamento,input);
		}
	}
	
	public void notificaObservers(A cambiamento){
		for(Observer<A,B> o:observers){
			o.update(cambiamento);
		}
	}
	
	public void notificaObservers(A cambiamento, Giocatore attributo){
		for(Observer<A,B> o:observers){
			o.update(cambiamento, attributo);
		}
	}
	
	public void registerObserver(Observer<A,B> osservatore){
		observers.add(osservatore);
	}
	
	public void removeObserver(Observer<A,B> osservatore){
		observers.remove(osservatore);
	}	
}

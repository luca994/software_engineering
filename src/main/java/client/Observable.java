package client;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {

	private List<Observer<T>> observers;
	
	public Observable(){
		observers=new ArrayList<Observer<T>>();
	}
	
	public void notificaObservers(T cambiamento,String input){
		for(Observer o:observers){
			o.update(cambiamento,input);
		}
	}
	
	public void registerObserver(Observer osservatore){
		observers.add(osservatore);
	}
	
	public void removeObserver(Observer osservatore){
		observers.remove(osservatore);
	}	
}

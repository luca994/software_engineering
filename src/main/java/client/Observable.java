package client;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {

	private List<Observer<T>> observers;
	
	public Observable(){
		observers=new ArrayList<Observer<T>>();
	}
	
	public void notificaObservers(T cambiamento,String input){
		for(Observer<T> o:observers){
			o.update(cambiamento,input);
		}
	}
	
	public void notificaObservers(T cambiamento){
		for(Observer<T> o:observers){
			o.update(cambiamento);
		}
	}
	
	public void registerObserver(Observer<T> osservatore){
		observers.add(osservatore);
	}
	
	public void removeObserver(Observer<T> osservatore){
		observers.remove(osservatore);
	}	
}

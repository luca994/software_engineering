package server.view;

import server.model.bonus.Bonus;
import server.observer.Observable;
import server.observer.Observer;

public abstract class ServerView extends Observable<Object, Bonus> implements Observer<Object, Bonus>{
	
}

package server.observer;

import server.model.Giocatore;

public interface Observer <A,B>{

	public void update(A cambiamento);
	
	public void update(B cambiamento, String[] input);
	
	public void update(A cambiamento, Giocatore attributo);
}

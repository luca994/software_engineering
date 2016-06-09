package server.observer;

import server.model.Giocatore;

public interface Observer <O,B>{

	public void update(O cambiamento);
	
	public void update(B cambiamento, String[] input);
	
	public void update(O cambiamento, Giocatore attributo);
}

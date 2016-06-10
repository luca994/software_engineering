package server.observer;

import java.util.List;

import server.model.Giocatore;

public interface Observer <O,B>{

	public void update(O cambiamento);
	
	public void update(B cambiamento, List<String> input);
	
	public void update(O cambiamento, Giocatore attributo);
}

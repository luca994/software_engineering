package server.observer;

public interface Observer {

	public <T> void update(T cambiamento);
	
	public <T> void update(T cambiamento,String[] input);
	
	public <T,S> void update(T cambiamento, S attributo);
}

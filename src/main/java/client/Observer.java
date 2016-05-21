package client;

public interface Observer {

	public <T> void update(T cambiamento);
	
	public <T> void update(T cambiamento,String[] input);
	
}

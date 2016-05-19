package client;

public interface Observer<T> {

	public void update(T cambiamento);
	
	public void update(T cambiamento,String input);
	
}

package client;

public interface Observer<T> {

	//public void update();
	
	public default void update(T cambiamento,String input){
		System.out.println("");
	}
	
}

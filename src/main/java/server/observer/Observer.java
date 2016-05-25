package server.observer;

import java.io.IOException;

public interface Observer {

	public <T> void update(T cambiamento) throws IOException;
	
	public <T> void update(T cambiamento,String[] input);
	
}

package client;

public abstract class View {

	private Connessione connessione;

	/**
	 * is called by the connection which gives an object
	 * @param oggetto the object passed by the connection
	 */
	public abstract void riceviOggetto(Object oggetto);
	
	/**
	 * 
	 * @return the connection
	 */
	public Connessione getConnessione() {
		return connessione;
	}

	/**
	 * set the connection
	 * @param connessione the connection to set
	 */
	public void setConnessione(Connessione connessione) {
		this.connessione = connessione;
	}
	
	
}

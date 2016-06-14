package server.model.componenti;

import java.awt.Color;
import java.io.Serializable;

/**
 * 
 * @author Riccardo
 *
 */
public class Consigliere implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452516340892571431L;
	
	private final Color colore;

	/**
	 * @param colore
	 */
	public Consigliere(Color colore) {
		this.colore = colore;
	}

	/**
	 * @return the colore
	 */
	public Color getColore() {
		return colore;
	}

}

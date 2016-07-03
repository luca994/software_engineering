package server.model.componenti;

import java.awt.Color;
import java.io.Serializable;

/**
 * the class that represents the counselors who preside over the board
 */
public class Consigliere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452516340892571431L;

	/** the color of the counselor */
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

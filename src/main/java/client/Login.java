/**
 * 
 */
package client;

import java.awt.Color;
import java.io.Serializable;

/**
 * @author Massimiliano Ventura
 *
 */
public class Login implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4947073034355854501L;
	private final String nome;
	private final Color colore;
	/**
	 * @param nome
	 * @param colore
	 */
	public Login(String nome, Color colore) {
		this.nome = nome;
		this.colore = colore;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @return the colore
	 */
	public Color getColore() {
		return colore;
	}
	
}

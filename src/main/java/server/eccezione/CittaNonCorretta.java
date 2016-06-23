/**
 * 
 */
package server.eccezione;

/**
 * @author Luca
 *
 */
public class CittaNonCorretta extends EccezioneConsiglioDeiQuattro {

	/**
	 * 
	 */
	private static final long serialVersionUID = -811364039862771161L;

	public CittaNonCorretta(String message) {
		super(message);
	}

	public CittaNonCorretta(Throwable cause) {
		super(cause);
	}

	public CittaNonCorretta(String message, Throwable cause) {
		super(message, cause);
	}
}

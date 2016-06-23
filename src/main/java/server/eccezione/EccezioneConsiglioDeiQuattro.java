package server.eccezione;

public class EccezioneConsiglioDeiQuattro extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2930088252417883329L;

	public EccezioneConsiglioDeiQuattro(){
		super();
	}
	
	
	public EccezioneConsiglioDeiQuattro(String message) {
		super(message);
	}

	public EccezioneConsiglioDeiQuattro(Throwable cause) {
		super(cause);
	}

	public EccezioneConsiglioDeiQuattro(String message, Throwable cause) {
		super(message, cause);
	}
}

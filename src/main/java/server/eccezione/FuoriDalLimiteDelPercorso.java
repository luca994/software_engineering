package server.eccezione;

public class FuoriDalLimiteDelPercorso extends EccezioneConsiglioDeiQuattro {


	private static final long serialVersionUID = 1875792286778184877L;


	public FuoriDalLimiteDelPercorso(String message) {
		super(message);
	}

	public FuoriDalLimiteDelPercorso(Throwable cause) {
		super(cause);
	}

	public FuoriDalLimiteDelPercorso(String message, Throwable cause) {
		super(message, cause);
	}
}

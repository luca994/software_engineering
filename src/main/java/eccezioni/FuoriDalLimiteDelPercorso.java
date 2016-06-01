package eccezioni;

public class FuoriDalLimiteDelPercorso extends Exception {


	private static final long serialVersionUID = 1875792286778184877L;

	public FuoriDalLimiteDelPercorso() {
	}

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

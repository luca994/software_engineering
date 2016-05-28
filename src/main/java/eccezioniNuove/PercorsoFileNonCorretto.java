package eccezioniNuove;

public class PercorsoFileNonCorretto extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public PercorsoFileNonCorretto(String message) {
		super(message);
	}

	public PercorsoFileNonCorretto(Throwable cause) {
		super(cause);
	}

	public PercorsoFileNonCorretto(String message, Throwable cause) {
		super(message, cause);
	}
}

package eccezione;

public class NumeroAiutantiIncorretto extends Exception {

	private static final long serialVersionUID = 4591104765548988808L;

	public NumeroAiutantiIncorretto(String message) {
		super(message);
	}

	public NumeroAiutantiIncorretto(Throwable cause) {
		super(cause);
	}

	public NumeroAiutantiIncorretto(String message, Throwable cause) {
		super(message, cause);
	}

}

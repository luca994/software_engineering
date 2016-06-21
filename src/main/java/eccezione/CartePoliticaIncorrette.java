package eccezione;

public class CartePoliticaIncorrette extends Exception {

	private static final long serialVersionUID = -5077267466359299485L;

	public CartePoliticaIncorrette(String message) {
		super(message);
	}

	public CartePoliticaIncorrette(Throwable cause) {
		super(cause);
	}

	public CartePoliticaIncorrette(String message, Throwable cause) {
		super(message, cause);
	}

}

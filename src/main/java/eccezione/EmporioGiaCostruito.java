package eccezione;

public class EmporioGiaCostruito extends EccezioneConsiglioDeiQuattro {

	private static final long serialVersionUID = -7538637834425289247L;

	public EmporioGiaCostruito(String message) {
		super(message);
	}

	public EmporioGiaCostruito(Throwable cause) {
		super(cause);
	}

	public EmporioGiaCostruito(String message, Throwable cause) {
		super(message, cause);
	}
}

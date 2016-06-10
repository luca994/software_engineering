package server.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.StatoGiocatore;

/**
 * @author Luca
 *
 */
/**
 * @author Luca
 *
 */
public class Giocatore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6264310867945058171L;

	private final String nome;
	private List<Assistente> assistenti;
	private List<CartaPolitica> cartePolitica;
	private Color colore;
	private List<TesseraCostruzione> tessereUsate;
	private List<TesseraCostruzione> tessereValide;
	private int emporiRimasti;
	private StatoGiocatore statoGiocatore;

	/**
	 * Constructor for the class Giocatore, creates a giocatore, and sets
	 * nome,colore and StatoGiocatore to AttesaTurno.
	 * 
	 * @param nome
	 *            to set
	 * @param colore
	 *            to set
	 * @throws IllegalArgumentException
	 *             if nome or colore are null
	 */
	public Giocatore(String nome) {
		if (nome == null)
			throw new IllegalArgumentException();
		this.nome = nome;

		// creo il colore casualmente
		Random random = new Random();
		final float hue = random.nextFloat();
		final float saturation = 0.9f;// 1.0 for brilliant, 0.0 for dull
		final float luminance = 1.0f; // 1.0 for brighter, 0.0 for black
		this.colore = Color.getHSBColor(hue, saturation, luminance);

		this.assistenti = new ArrayList<>();
		this.cartePolitica = new ArrayList<>();
		this.tessereUsate = new ArrayList<>();
		this.tessereValide = new ArrayList<>();
		statoGiocatore = new AttesaTurno(this);
	}

	/**
	 * @return decrement of one unit integer EmporiRimasti
	 * @throws IllegalArgumentException
	 *             if emporiRimasti is equal or less than 0.
	 */
	public void decrementaEmporiRimasti() {
		if (emporiRimasti <= 0)
			throw new IllegalArgumentException();
		emporiRimasti--;
	}

	/**
	 * this method move TesseraCostruzione from list tessereValide to list
	 * tessereUsate
	 * 
	 * @param tesseraToMove
	 *            is the Tessera to move from tessereValide to tessereUsate
	 */
	public void spostaTesseraValidaInTesseraUsata(TesseraCostruzione tesseraToMove) {
		if (!tessereValide.contains(tesseraToMove)) {
			throw new IllegalArgumentException("La tessera da spostare non è nelle tessereValide del giocatore");
		}
		tessereValide.remove(tesseraToMove);
		tessereUsate.add(tesseraToMove);
	}

	/**
	 * searches for a politic card with the same parameters in the list of cards
	 * owned by the player, if the player hasn't got the card,it returns null.
	 * 
	 * @param cartaCercata
	 *            is the card to search
	 * @return the reference to the similar card owned by the player
	 */
	public CartaPolitica cercaCarta(CartaPolitica cartaCercata) {
		for (CartaPolitica c : cartePolitica)
			if (c.isUguale(cartaCercata))
				return c;
		return null;
	}

	/**
	 * searches for a tesseraCostruzione with the same parameters in the list of
	 * tessereValide owned by the player, if the player hasn't got the
	 * tessera,it returns null.
	 * 
	 * @param tesseraCercata
	 *            is the tessera to search
	 * @return the reference to the similar tessera owned by the player
	 */
	public TesseraCostruzione cercaTesseraCostruzione(TesseraCostruzione tesseraCercata) {
		for (TesseraCostruzione t : tessereValide)
			if (t.isUguale(tesseraCercata))
				return t;
		return null;
	}

	/**
	 * generates a list of all OggettiVendibili of a player that are not in the
	 * market list (assuming that an object is in sale only if it has a valid
	 * price)
	 * 
	 * @return the list of oggettiVendibili generated
	 */
	public List<OggettoVendibile> generaListaOggettiVendibiliNonInVendita() {
		List<OggettoVendibile> oggetti = new ArrayList<>();
		for (CartaPolitica c : cartePolitica) {
			if (c.getPrezzo() == 0)
				oggetti.add(c);
		}
		for (Assistente a : assistenti) {
			if (a.getPrezzo() == 0)
				oggetti.add(a);
		}
		for (TesseraCostruzione t : tessereValide) {
			if (t.getPrezzo() == 0)
				oggetti.add(t);
		}
		return oggetti;
	}

	/**
	 * calculate and return the total number of tessereCostruzione of the player
	 * 
	 * @return the number of numeroPermessiTotali
	 */
	public int numeroPermessiTotali() {
		return tessereUsate.size() + tessereValide.size();
	}

	/**
	 * calculate and return the sum of aiutanti and cartePolitica of the player
	 * 
	 * @return the number of numeroPermessiTotali
	 */
	public int numeroAiutantiECartePolitica() {
		return assistenti.size() + cartePolitica.size();
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the assistenti
	 */
	public List<Assistente> getAssistenti() {
		return assistenti;
	}

	/**
	 * @return the cartePolitica
	 */
	public List<CartaPolitica> getCartePolitica() {
		return cartePolitica;
	}

	/**
	 * @return the colore
	 */
	public Color getColore() {
		return colore;
	}

	/**
	 * @param colore
	 *            the colore to set
	 */
	public void setColore(Color colore) {
		this.colore = colore;
	}

	/**
	 * @return the tessereUsate
	 */
	public List<TesseraCostruzione> getTessereUsate() {
		return tessereUsate;
	}

	/**
	 * @param tessereUsate
	 *            the tessereUsate to set
	 */
	public void setTessereUsate(List<TesseraCostruzione> tessereUsate) {
		this.tessereUsate = tessereUsate;
	}

	/**
	 * @return the tessereValide
	 */
	public List<TesseraCostruzione> getTessereValide() {
		return tessereValide;
	}

	/**
	 * @param tessereValide
	 *            the tessereValide to set
	 */
	public void setTessereValide(List<TesseraCostruzione> tessereValide) {
		this.tessereValide = tessereValide;
	}

	/**
	 * @return the emporiRimasti
	 */
	public int getEmporiRimasti() {
		return emporiRimasti;
	}

	/**
	 * @param emporiRimasti
	 *            the emporiRimasti to set
	 * @throws IllegalArgumentException
	 *             if you try to set emporiRimasti to a negative value
	 */
	public void setEmporiRimasti(int emporiRimasti) {
		if (emporiRimasti < 0)
			throw new IllegalArgumentException();
		this.emporiRimasti = emporiRimasti;
	}

	/**
	 * @return the statoGiocatore
	 */
	public StatoGiocatore getStatoGiocatore() {
		return statoGiocatore;
	}

	/**
	 * @param statoGiocatore
	 *            the statoGiocatore to set
	 */
	public void setStatoGiocatore(StatoGiocatore statoGiocatore) {
		this.statoGiocatore = statoGiocatore;
	}

	/**
	 * @param assistenti
	 *            the assistenti to set
	 */
	public void setAssistenti(List<Assistente> assistenti) {
		this.assistenti = assistenti;
	}

	/**
	 * @param cartePolitica
	 *            the cartePolitica to set
	 */
	public void setCartePolitica(List<CartaPolitica> cartePolitica) {
		this.cartePolitica = cartePolitica;
	}

}
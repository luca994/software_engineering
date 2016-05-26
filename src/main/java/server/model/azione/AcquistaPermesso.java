package server.model.azione;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.model.CartaPolitica;
import server.model.Consiglio;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.Regione;
import server.model.TesseraCostruzione;

/**
 * AcquistaPermesso: an object of this type allows the player to take this
 * action within the game that is passed as a parameter to the constructor
 *
 */
public class AcquistaPermesso extends Azione {

	
	private TesseraCostruzione tesseraScelta;
	private List<CartaPolitica> cartePoliticaScelte;
	private Consiglio consiglioDaSoddisfare;

	/**
	 * Constructor
	 * 
	 * @param tessera
	 * @param cartePolitica
	 * @param consiglioDaSoddisfare
	 * @param percorsoRicchezza
	 */
	public AcquistaPermesso(Gioco gioco, TesseraCostruzione tessera, List<CartaPolitica> cartePolitica,
			Consiglio consiglioDaSoddisfare) {
		super(gioco);
		this.tesseraScelta = tessera;
		this.cartePoliticaScelte = cartePolitica;
		this.consiglioDaSoddisfare = consiglioDaSoddisfare;
	}


	/**
	 * Executes the action of buying a Tessera Permesso di Costruzione;
	 * 
	 * @throws IOException
	 * 
	 * @throws IllegalStateException
	 * 			   if the number of numeroConsiglieriSoddisfatti is equal of 0
	 * @throws IllegalArgumentException
	 *             if the number of Carta Politica is less than one or more than
	 *             four
	 * @throws IndexOutOfBoundsException
	 *             if an error occurs during the count of the cards
	 * @throws IndexOutOfBoundsException
	 *             if giocatore hasn't enough money to perform the action(from
	 *             method muoviGiocatore)
	 * @throws NullPointerException if giocatore is null
	 */
	@Override
	public void eseguiAzione(Giocatore giocatore) throws IOException {

		if (giocatore == null)
			throw new NullPointerException ("In input è stato passato un giocatore null");
		int numeroCartePolitica = cartePoliticaScelte.size();
		int jollyUsati = 0;
		int numeroConsiglieriSoddisfatti = 0;
		List<Color> colori = consiglioDaSoddisfare.acquisisciColoriConsiglio();
		List<CartaPolitica> cartePoliticaUsate = new ArrayList<>();
		if (numeroCartePolitica < 1 || numeroCartePolitica > 4)
			throw new IllegalArgumentException("Il numero di carte selezionato non è appropriato");
		
		for (CartaPolitica car : cartePoliticaScelte) {
			if (car.getColore().equals(Color.red))
				jollyUsati++;

			for (Color col : colori) {
				if (car.getColore().equals(col)) {
					numeroConsiglieriSoddisfatti++;
					colori.remove(col);
					cartePoliticaUsate.add(car);
					break;
				}
			}
		}
			switch (numeroConsiglieriSoddisfatti) {
			case 0:
				throw new IllegalStateException("Nessuno Consigliere soddisfatto");
			case 1:
				gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, -10-jollyUsati);
				break;
			case 2:
				gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, -7-jollyUsati);
				break;
			case 3:
				gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(giocatore, -4-jollyUsati);
				break;
			case 4:
				break;
			default:
				throw new IndexOutOfBoundsException("Errore nel conteggio consiglieri da soddisfare");
			}
			
		/*  Rimozione tessere selezionate dalla mano del giocatore */
		giocatore.getCartePolitica().removeAll(cartePoliticaUsate);

		List<TesseraCostruzione> tessereDaScegliere = consiglioDaSoddisfare.getRegione().getTessereCostruzione();
		if (tessereDaScegliere.contains(tesseraScelta)) {
			/* Aggiunta tessera acquistata al set di tessere valide del giocatore */
			giocatore.getTessereValide().add(tesseraScelta);
			tesseraScelta.eseguiBonus(giocatore);

			/* Scopri dal mazzetto una nuova tessera costruzione */
			consiglioDaSoddisfare.getRegione().nuovaTessera(tesseraScelta);
			
			giocatore.getStatoGiocatore().azionePrincipaleEseguita();
		}}
	
	@Override
	public boolean verificaInput(Giocatore giocatore){
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere nullo");
		
		boolean consiglioValido=false;
		boolean cartePoliticaValide=false;
		boolean tesseraValida=false;
		Regione regioneDelConsiglio = null;
		
		for(Regione regione: gioco.getTabellone().getRegioni()){
			if(regione.getConsiglio().equals(consiglioDaSoddisfare)){
				consiglioValido=true;
				regioneDelConsiglio=regione;
				break;
			}
		}
		if(!consiglioValido)
			return false;
		if(giocatore.getCartePolitica().containsAll(cartePoliticaScelte) && !cartePoliticaScelte.isEmpty())
			cartePoliticaValide=true;
		if(regioneDelConsiglio.getTessereCostruzione().contains(tesseraScelta))
			tesseraValida = true;
		return cartePoliticaValide && tesseraValida ;
	}
	
	
	

	/**
	 * @param tesseraScelta the tesseraScelta to set
	 */
	public void setTesseraScelta(TesseraCostruzione tesseraScelta) {
		this.tesseraScelta = tesseraScelta;
	}


	/**
	 * @param cartePoliticaScelte the cartePoliticaScelte to set
	 */
	public void setCartePoliticaScelte(List<CartaPolitica> cartePoliticaScelte) {
		this.cartePoliticaScelte = cartePoliticaScelte;
	}


	/**
	 * @param consiglioDaSoddisfare the consiglioDaSoddisfare to set
	 */
	public void setConsiglioDaSoddisfare(Consiglio consiglioDaSoddisfare) {
		this.consiglioDaSoddisfare = consiglioDaSoddisfare;
	}
	
	
}
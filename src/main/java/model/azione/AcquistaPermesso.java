package model.azione;

import java.awt.Color;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.CartaPolitica;
import model.Consiglio;
import model.Giocatore;
import model.TesseraCostruzione;
import model.percorso.Percorso;

/**
 *
 * @author Luca
 *
 */
public class AcquistaPermesso implements Azione {

	private static final Logger log= Logger.getLogger( AcquistaPermesso.class.getName() );
	
	private TesseraCostruzione tessera;
	private List<CartaPolitica> cartePolitica;
	private Consiglio consiglioDaSoddisfare;
	private Percorso percorsoRicchezza;
	
	
	/**
	 * @param tessera
	 * @param cartePolitica
	 * @param consiglioDaSoddisfare
	 * @param percorsoRicchezza
	 */
	public AcquistaPermesso(TesseraCostruzione tessera, List<CartaPolitica> cartePolitica,
			Consiglio consiglioDaSoddisfare, Percorso percorsoRicchezza) {
		super();
		this.tessera = tessera;
		this.cartePolitica = cartePolitica;
		this.consiglioDaSoddisfare = consiglioDaSoddisfare;
		this.percorsoRicchezza = percorsoRicchezza;
	}

	/**
	 * @param tessera the tessera to set
	 */
	public void setTessera(TesseraCostruzione tessera) {
		this.tessera = tessera;
	}

	/**
	 * @param consiglioDaSoddisfare the consiglioDaSoddisfare to set
	 */
	public void setConsiglioDaSoddisfare(Consiglio consiglioDaSoddisfare) {
		this.consiglioDaSoddisfare = consiglioDaSoddisfare;
	}

	/**
	 * @param cartePolitica the cartePolitica to set
	 */
	public void setCartePolitica(List<CartaPolitica> cartePolitica) {
		this.cartePolitica = cartePolitica;
	}

	
	/**
	 * Executes the action of buying a Tessera Permesso di Costruzione; 
	 * @throws IllegalStateException if the number of Carta Politica is less than one or more than four,
	 * @throws IndexOutOfBoundsException if an error occurs during the count of the cards
	 * @throws IndexOutOfBoundsException if giocatore hasn't enough money to perform the action(from method muoviGiocatore)
	 */
	@Override
	public void eseguiAzione (Giocatore giocatore){
		
		//controllo che le carte politica che voglio usare per acquistare il permesso siano minori o uguali a 4
		
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			int numeroCartePolitica=cartePolitica.size();
			int jollyUsati=0;
			int counter=0;
			if(numeroCartePolitica<1||numeroCartePolitica>4)
				throw new IllegalStateException("Il numero di carte selezionato non è appropriato");
			
			//Creazione copie liste dei colori del consiglio
			
			List<Color> colori = consiglioDaSoddisfare.acquisisciColoriConsiglio();
			for(CartaPolitica car : cartePolitica){
				//conto i jolly usati
				if(car.getColore().equals(Color.red))
					jollyUsati++;
			
				for(Color col : colori){
				if(car.getColore().equals(col))
					{
						counter++;
						colori.remove(col);
						break;
					}
				}}
			
		switch(counter)
		{
			case 1: percorsoRicchezza.muoviGiocatore(giocatore, 0-10-jollyUsati);
					break;
			case 2: percorsoRicchezza.muoviGiocatore(giocatore, 0-7-jollyUsati);
					break;
			case 3: percorsoRicchezza.muoviGiocatore(giocatore, 0-4-jollyUsati);
					break;
			case 4: 
					break;
			default: throw new IndexOutOfBoundsException("Errore nel conteggio consiglieri da soddisfare");
		}
		
		//Rimozione tessere selezionate dalla mano del giocatore
			giocatore.getCartePolitica().removeAll(cartePolitica);
		
		List<TesseraCostruzione> tessereDaScegliere=consiglioDaSoddisfare.getRegione().getTessereCostruzione();
		if(tessereDaScegliere.contains(tessera)){
		//Aggiunta tessera acquistata al set di tessere valide del giocatore
			giocatore.getTessereValide().add(tessera);
			tessera.eseguiBonus(giocatore);
			//Scopri dal mazzetto una nuova tessera costruzione
			consiglioDaSoddisfare.getRegione().nuovaTessera(tessera);
			giocatore.setAzionePrincipale(true);
		}}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}
}

package server.model.azione;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.Città;
import server.model.Giocatore;
import server.model.Tabellone;
import server.model.TesseraCostruzione;

/**
 * @author Luca
 *
 */
public class CostruisciEmporioConTessera implements Azione {

	private static final Logger log= Logger.getLogger( CostruisciEmporioConTessera.class.getName() );
	
	private Città città;
	private TesseraCostruzione tessera;
	private Tabellone tabellone;
	
	/**
	 * @param città
	 * @param tessera
	 */
	public CostruisciEmporioConTessera(Città città, TesseraCostruzione tessera, Tabellone tabellone) {
		super();
		this.città = città;
		this.tessera = tessera;
		this.tabellone=tabellone;
	}


	
	/**
	 * @return the città
	 */
	public Città getCittà() {
		return città;
	}



	/**
	 * @param città the città to set
	 */
	public void setCittà(Città città) {
		this.città = città;
	}



	/**
	 * @return the tessera
	 */
	public TesseraCostruzione getTessera() {
		return tessera;
	}



	/**
	 * @param tessera the tessera to set
	 */
	public void setTessera(TesseraCostruzione tessera) {
		this.tessera = tessera;
	}



	/**
	 * @return the tabellone
	 */
	public Tabellone getTabellone() {
		return tabellone;
	}



	/**
	 * @param tabellone the tabellone to set
	 */
	public void setTabellone(Tabellone tabellone) {
		this.tabellone = tabellone;
	}



	/**
	 * @return the log
	 */
	public static Logger getLog() {
		return log;
	}



	/**
	 * Build a new emporio using a Tessera Costruzione owned by the player
	 * Throws IllegalStateException if Tessera Costruzione is not appropriated for this city or the player alredy has an emporio
	 */
	@Override
	public void eseguiAzione (Giocatore giocatore){
		try{
			if (giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			if(giocatore.getTessereValide().contains(tessera) && tessera.getCittà().contains(città)){
				giocatore.moveTesseraValidaToTesseraUsata(tessera);
				città.aggiungiEmporio(giocatore);
				giocatore.decrementaEmporiRimasti();
				
				//Se il giocatore ha finito gli empori guadagna 3 punti vittoria	
				if(giocatore.getEmporiRimasti()==0)
					tabellone.getPercorsoVittoria().muoviGiocatore(giocatore, 3);
				
				//Il giocatore ottiene i bonus di questa e delle città collegate
				
				List<Città> cittàConBonusDaOttenere=new ArrayList<Città>();
				cittàConBonusDaOttenere.add(città);
				città.cittàVicinaConEmporio(giocatore, cittàConBonusDaOttenere);
				
				/*la lista cittàConBonusDaOttenere che viene creata è un lista di città
				in cui viene messa inizialmente la città corrente, poi viene chiamato il metodo cittàVicinaConEmporio che
				riempe la lista con tutte le città adiacenti che hanno un emporio del giocatore.
				*/
				
				for(Città citt: cittàConBonusDaOttenere)
					citt.eseguiBonus(giocatore);
				giocatore.setAzionePrincipale(true);
				
				/*controllo se ho gli empori in tutte le città di un colore o di una regione e prendo la
				tessera bonus se mi spetta
				(IL controllo viene fatto direttamente dal metodo del tabellone prendiTesseraBonus)
				*/
				
				tabellone.prendiTesseraBonus(giocatore, città);
			}
			else 
				throw new IllegalStateException("L'emporio è già presente oppure la tessera non è appropriata");
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
			throw e;
		}
	}
}

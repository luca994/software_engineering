/**
 * 
 */
package model.azione;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.Città;
import model.Giocatore;
import model.Re;
import model.percorso.Percorso;

/**
 * @author Massimiliano Ventura
 *
 */
public class MuoviRe implements Azione {

	private static final Logger log= Logger.getLogger( MuoviRe.class.getName() );
	/* (non-Javadoc)
	 * @see model.Azione#eseguiAzione(model.Giocatore)
	 */
	private Re re;
	private Percorso percorsoRicchezza;
	/**
	 * @param re
	 * @param percorsoRicchezza
	 */
	public MuoviRe(Re re, Percorso percorsoRicchezza) {
		super();
		this.re = re;
		this.percorsoRicchezza = percorsoRicchezza;
	}
	@Override
	public void eseguiAzione(Giocatore giocatore) {
		try{
			if(giocatore==null)
				throw new NullPointerException("Il giocatore non può essere nullo");
			boolean azioneFinita=false;
			//Richiesta dal controller di input per la città successiva, se non vuole muovere mette azione finita a true
			Città cittàSuccessiva= new Città(null,null);
			while(!azioneFinita)
			{
				re.mossa(cittàSuccessiva);
				percorsoRicchezza.muoviGiocatore(giocatore, 0-2);
		
			//metodo che chiede al giocatore se ha finito, se si mette azione finita a true
			}
		}
		catch(Exception e){
			log.log( Level.WARNING,e.getLocalizedMessage(),e );
		}	
	}

}

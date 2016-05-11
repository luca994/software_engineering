/**
 * 
 */
package azione;

import model.Città;
import model.Giocatore;
import model.Re;
import percorso.Percorso;

/**
 * @author Massimiliano Ventura
 *
 */
public class MuoviRe implements Azione {

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

}

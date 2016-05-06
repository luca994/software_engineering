/**
 * 
 */
package model;

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
	@Override
	public void eseguiAzione(Giocatore giocatore) {
		boolean azioneFinita=false;
		Città cittàSuccessiva= new Città("Ciao",null);//ovviamente è solo per non far dare errore da jenkins
		//metodo che chiede al giocatore la città di destinazione
		while(!azioneFinita)
		{
			re.mossa(cittàSuccessiva);
			percorsoRicchezza.muoviGiocatore(giocatore, 0-2);
		
		//metodo che chiede al giocatore se ha finito, se si mette azione finita a true
		}
	}

}

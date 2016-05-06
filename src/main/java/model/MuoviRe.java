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
		Città cittàSuccessiva= new Città("ciao",null);
		//		while(!azioneFinita)
		{
			re.mossa(cittàSuccessiva);
			percorsoRicchezza.muoviGiocatore(giocatore, 0-2);
		
		//metodo che chiede al giocatore se ha finito, se si mette azione finita a true
		}
	}

}

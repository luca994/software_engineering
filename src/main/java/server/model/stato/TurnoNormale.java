package server.model.stato;

import server.model.Giocatore;

public class TurnoNormale extends StatoGiocatore {
	
	private static final int AZIONI_PRINCIPALI_PER_TURNO = 1;
	private static final int AZIONI_RAPIDE_PER_TURNO = 1;
	
	private int AzioniPrincipaliEseguibili;
	private int AzioniRapideEseguibili;
	private Giocatore giocatore;
	
	
	
	public TurnoNormale(Giocatore giocatore){
		this.giocatore=giocatore;
		this.AzioniPrincipaliEseguibili=AZIONI_PRINCIPALI_PER_TURNO;
		this.AzioniRapideEseguibili=AZIONI_RAPIDE_PER_TURNO;
	}
	
	
	
	
	/**
	 * This method should be called every time an AzionePrincipale is performed.
	 * decrements the action's counter and checks if it is finished the turn.
	 * @throws IllegalStateException
	 */
	public void azionePrincipaleEseguita() throws IllegalStateException{
		if(AzioniPrincipaliEseguibili<=0){
			throw new IllegalStateException("Errore nel conteggio delle azioni principali eseguite");
		}
		AzioniPrincipaliEseguibili--;
		if(AzioniPrincipaliEseguibili == 0 && AzioniRapideEseguibili == 0){
			prossimoStato(this.giocatore);
		}
	}
	
	
	
	/**
	 * This method should be called every time an AzioneRapida is performed.
	 * decrements the action's counter and checks if it is finished the turn.
	 * @throws IllegalStateException launched when the number of shares that have been performed 
	 * 	has exceeded the maximum number
	 */
	public void azioneRapidaEseguita() throws IllegalStateException{
		if(AzioniRapideEseguibili<=0){
			throw new IllegalStateException("Errore nel conteggio delle azioni principali eseguite");
		}
		AzioniRapideEseguibili--;
		if(AzioniPrincipaliEseguibili == 0 && AzioniRapideEseguibili == 0){
			prossimoStato(this.giocatore);
		}
	}
	
	
	
	
	@Override
	public void prossimoStato(Giocatore giocatore) {
		giocatore.setStatoGiocatore(new Attesa());
		
	}

	
}

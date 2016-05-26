package server.model.stato.giocatore;

import server.model.Giocatore;

public class TurnoNormale extends StatoGiocatore {
	
	private static final int AZIONI_PRINCIPALI_PER_TURNO = 1;
	private static final int AZIONI_RAPIDE_PER_TURNO = 1;
	
	private int azioniPrincipaliEseguibili;
	private int azioniRapideEseguibili;
	
	
	
	public TurnoNormale(Giocatore giocatore){
		super(giocatore);
		this.azioniPrincipaliEseguibili=AZIONI_PRINCIPALI_PER_TURNO;
		this.azioniRapideEseguibili=AZIONI_RAPIDE_PER_TURNO;
	}
	
	
	
	
	/**
	 * This method should be called every time an AzionePrincipale is performed.
	 * decrements the action's counter and checks if it is finished the turn.
	 * @throws IllegalStateException if the main actions still make include equal to 0 or even a negative number
	 */
	public void azionePrincipaleEseguita(){
		if(azioniPrincipaliEseguibili<=0){
			throw new IllegalStateException("Errore nel conteggio delle azioni principali eseguite");
		}
		azioniPrincipaliEseguibili--;
		if(azioniPrincipaliEseguibili == 0 && azioniRapideEseguibili == 0){
			prossimoStato();
		}
	}
	
	
	
	/**
	 * This method should be called every time an AzioneRapida is performed.
	 * decrements the action's counter and checks if it is finished the turn.
	 * @throws IllegalStateException if the quick actions still make include equal to 0 or even a negative number
	 */
	public void azioneRapidaEseguita(){
		if(azioniRapideEseguibili<=0){
			throw new IllegalStateException("Errore nel conteggio delle azioni principali eseguite");
		}
		azioniRapideEseguibili--;
		if(azioniPrincipaliEseguibili == 0 && azioniRapideEseguibili == 0){
			prossimoStato();
		}
	}
	



	@Override
	public void prossimoStato() {
		giocatore.setStatoGiocatore(new AttesaTurno(giocatore));
	}

	
}

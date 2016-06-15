package testAzioni;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.Azione;
import server.model.azione.AzioneRapidaNulla;
import server.model.azione.IngaggioAiutante;
import server.model.stato.giocatore.TurnoNormale;

public class TestAzioneRapidaNulla {

	private Gioco giocoTester;
	private Giocatore g1;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo");
		this.g1 = g1;
		Giocatore g2 = new Giocatore("paolo");
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
		giocoTester.inizializzaPartita("0");
	}
	
	@Test
	public void testAzioneRapidaNulla() {
		Azione azione = new AzioneRapidaNulla(giocoTester);
		assertEquals(giocoTester, azione.getGioco());
	}
	
	@Test
	public void testEseguiAzione(){
		AzioneRapidaNulla azione = new AzioneRapidaNulla(giocoTester);
		g1.getStatoGiocatore().prossimoStato();
		azione.eseguiAzione(g1);
		assertEquals(0, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniRapideEseguibili());
	}
	
	@Test
	public void testEseguiAzioniCon0AzioniRapideDisponibili(){
		AzioneRapidaNulla azione = new AzioneRapidaNulla(giocoTester);
		g1.getStatoGiocatore().prossimoStato();
		((TurnoNormale)g1.getStatoGiocatore()).azioneEseguita(new IngaggioAiutante(giocoTester));
		azione.eseguiAzione(g1);
		assertEquals(-1, ((TurnoNormale)g1.getStatoGiocatore()).getAzioniRapideEseguibili());
		/*
		 * non lancia eccezioni, il controllore controlla se il giocatore ha ancora azioni disponibili
		 * prima di eseguire l'azione
		 */
	}



}

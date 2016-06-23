package testAzioni;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.IngaggioAiutante;
import server.model.stato.giocatore.TurnoNormale;

public class IngaggioAiutanteTest {

	private Gioco giocoTest;
	private Giocatore g1;
	
	@Before
	public void inizializzazioneTest(){
		giocoTest = new Gioco();
		g1 = new Giocatore("pippo");
		Giocatore g2 = new Giocatore("Paolo");
		List<Giocatore> giocatori = new ArrayList<>();
		giocatori.add(g1);
		giocatori.add(g2);
		giocoTest.setGiocatori(giocatori);
		giocoTest.inizializzaPartita("8");
		g1.setStatoGiocatore(new TurnoNormale(g1));
	}
	
	
	@Test
	public void testEseguiAzioneCorrettamenteConSoldi() throws FuoriDalLimiteDelPercorso {
		g1.getAssistenti().clear();
		assertEquals(10,giocoTest.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(0, g1.getAssistenti().size());
		IngaggioAiutante azioneTest = new IngaggioAiutante(giocoTest);
		azioneTest.eseguiAzione(g1);
		assertEquals(7,giocoTest.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(1, g1.getAssistenti().size());
	}
	
	@Test(expected=FuoriDalLimiteDelPercorso.class)
	public void testEseguiAzioneSenzaSoldi() throws FuoriDalLimiteDelPercorso {
		g1.getAssistenti().clear();
		giocoTest.getTabellone().getPercorsoRicchezza().muoviGiocatore(g1, -10);
		assertEquals(0,giocoTest.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(0, g1.getAssistenti().size());
		IngaggioAiutante azioneTest = new IngaggioAiutante(giocoTest);
		azioneTest.eseguiAzione(g1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testEseguiAzioneGiocatoreNullo() throws FuoriDalLimiteDelPercorso {
		IngaggioAiutante azioneTest = new IngaggioAiutante(giocoTest);
		azioneTest.eseguiAzione(null);
	}

	@Test
	public void testIngaggioAiutanteGiocoValido() {
		IngaggioAiutante azioneTest = new IngaggioAiutante(giocoTest);
		assertNotNull(azioneTest);
	}
	
	@Test
	public void testIngaggioAiutanteGiocoNull() {
		IngaggioAiutante azioneTest = new IngaggioAiutante(null);
		assertNotNull(azioneTest);
	}

}

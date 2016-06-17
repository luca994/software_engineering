package testAzioni;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eccezione.NumeroAiutantiIncorretto;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.Azione;
import server.model.azione.EleggiConsigliereRapido;
import server.model.componenti.Assistente;
import server.model.componenti.Consigliere;
import server.model.componenti.Consiglio;
import server.model.stato.giocatore.TurnoNormale;

public class EleggiConsigliereRapidoTest {

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
		giocoTest.inizializzaPartita("3");
		g1.setStatoGiocatore(new TurnoNormale(g1));
	}
	
	
	@Test(expected=NullPointerException.class)
	public void testEseguiAzioneGiocatoreNull() throws NumeroAiutantiIncorretto {
		Consigliere consigliereTest = new Consigliere(Color.black);
		Consiglio consiglioTest = new Consiglio(giocoTest.getTabellone());
		EleggiConsigliereRapido eleggiConsigliereRapidoTest = new EleggiConsigliereRapido(consigliereTest, consiglioTest);
		eleggiConsigliereRapidoTest.eseguiAzione(null);
	}
	
	@Test(expected=NumeroAiutantiIncorretto.class)
	public void testEseguiAzioneAiutantiInsufficienti() throws NumeroAiutantiIncorretto {
		Consigliere consigliereTest = new Consigliere(Color.black);
		Consiglio consiglioTest = new Consiglio(giocoTest.getTabellone());
		EleggiConsigliereRapido eleggiConsigliereRapidoTest = new EleggiConsigliereRapido(consigliereTest, consiglioTest);
		g1.getAssistenti().clear();
		eleggiConsigliereRapidoTest.eseguiAzione(g1);
	}
	
	@Test
	public void testEseguiAzione1Aiutante() throws NumeroAiutantiIncorretto {
		List<Assistente> assistenti = new ArrayList<>();
		Assistente a1= new Assistente();
		assistenti.add(a1);
		Consigliere head = giocoTest.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().element();
		Consigliere cTest= giocoTest.getTabellone().getConsiglieriDisponibili().get(0);
		Consiglio consiglioTest= giocoTest.getTabellone().getRegioni().get(0).getConsiglio();
		EleggiConsigliereRapido eleggiConsigliereRapidoTest = new EleggiConsigliereRapido(cTest, consiglioTest);
		g1.getAssistenti().clear();
		g1.setAssistenti(assistenti);
		assertTrue(consiglioTest.getConsiglieri().contains(head));
		assertTrue(!consiglioTest.getConsiglieri().contains(cTest));
		assertEquals(4, consiglioTest.getConsiglieri().size());
		eleggiConsigliereRapidoTest.eseguiAzione(g1);
		assertEquals(0, g1.getAssistenti().size());
		assertTrue(consiglioTest.getConsiglieri().contains(cTest));
		assertTrue(!consiglioTest.getConsiglieri().contains(head));
		assertEquals(4, consiglioTest.getConsiglieri().size());
	}

	@Test
	public void testEleggiConsigliereRapidoParametriValidi() {
		Consigliere consigliereTest = new Consigliere(Color.black);
		Consiglio consiglioTest = new Consiglio(giocoTest.getTabellone());
		Azione eleggiConsigliereRapidoTest = new EleggiConsigliereRapido(consigliereTest, consiglioTest);
		assertNotNull(eleggiConsigliereRapidoTest);
	}
	
	@Test
	public void testEleggiConsigliereRapidoParametriNull() {
		Azione eleggiConsigliereRapidoTest = new EleggiConsigliereRapido(null,null);
		assertNotNull(eleggiConsigliereRapidoTest);
	}
	
	@Test
	public void testEleggiConsigliereRapidoConsiglioNull() {
		Consigliere consigliereTest = new Consigliere(Color.green);
		Azione eleggiConsigliereRapidoTest = new EleggiConsigliereRapido(consigliereTest,null);
		assertNotNull(eleggiConsigliereRapidoTest);
	}
	
	@Test
	public void testEleggiConsigliereRapidoConsigliereNull() {
		Consiglio consiglioTest = new Consiglio(giocoTest.getTabellone());
		Azione eleggiConsigliereRapidoTest = new EleggiConsigliereRapido(null,consiglioTest);
		assertNotNull(eleggiConsigliereRapidoTest);
	}

}

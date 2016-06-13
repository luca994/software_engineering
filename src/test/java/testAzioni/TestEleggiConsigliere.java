package testAzioni;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.model.Consigliere;
import server.model.Consiglio;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.EleggiConsigliere;

public class TestEleggiConsigliere {

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
		giocoTest.inizializzaPartita("0");
	}
	
	@Test(expected=NullPointerException.class)
	public void testEseguiAzioneConGiocatoreNull() {
		Consigliere consigliere = new Consigliere(Color.pink);
		Consiglio consiglio = giocoTest.getTabellone().getRegioni().get(0).getConsiglio();
		EleggiConsigliere azione = new EleggiConsigliere(giocoTest, consigliere, consiglio);
		List<Consigliere> consiglioPrecedente = new ArrayList<>(consiglio.getConsiglieri());
		List<Consigliere> consiglieriDisponibiliPrecedenti = new ArrayList<>(giocoTest.getTabellone().getConsiglieriDisponibili());
		azione.eseguiAzione(null);
		List<Consigliere> consiglioAttuale = new ArrayList<>(consiglio.getConsiglieri());
		List<Consigliere> consiglieriDisponibiliAttuali = new ArrayList<>(giocoTest.getTabellone().getConsiglieriDisponibili());
		for(int i=0;i<consiglio.getConsiglieri().size();i++)
			assertEquals(consiglioPrecedente.get(i), consiglioAttuale.get(i));
		for(int i=0;i<consiglio.getConsiglieri().size();i++)
			assertEquals(consiglieriDisponibiliPrecedenti.get(i), consiglieriDisponibiliAttuali.get(i));
	}

	@Test
	public void testEseguiAzione(){
		g1.getStatoGiocatore().prossimoStato();
		Consigliere consigliere = new Consigliere(Color.pink);
		giocoTest.getTabellone().getConsiglieriDisponibili().set(0, consigliere);
		Consiglio consiglio = giocoTest.getTabellone().getRegioni().get(0).getConsiglio();
		EleggiConsigliere azione = new EleggiConsigliere(giocoTest, consigliere, consiglio);
		azione.eseguiAzione(g1);
		assertEquals(14, giocoTest.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		List<Consigliere> consiglioCopia = new ArrayList<>(consiglio.getConsiglieri());
		assertEquals(consiglioCopia.get(consiglioCopia.size()-1), consigliere);
	}
	
	@Test
	public void testEleggiConsigliere() {
		Consigliere consigliere = new Consigliere(Color.pink);
		Consiglio consiglio = giocoTest.getTabellone().getRegioni().get(0).getConsiglio();
		EleggiConsigliere azione = new EleggiConsigliere(giocoTest, consigliere, consiglio);
		assertEquals(giocoTest, azione.getGioco());
		assertEquals(consigliere, azione.getConsigliere());
		assertEquals(consiglio, azione.getConsiglio());
	}

	@Test
	public void testGetConsigliere() {
		Consigliere consigliere = new Consigliere(Color.pink);
		Consiglio consiglio = giocoTest.getTabellone().getRegioni().get(0).getConsiglio();
		EleggiConsigliere azione = new EleggiConsigliere(giocoTest, consigliere, consiglio);
		assertEquals(consigliere, azione.getConsigliere());
	}

	@Test
	public void testSetConsigliere() {
		Consigliere consigliere = new Consigliere(Color.pink);
		Consiglio consiglio = giocoTest.getTabellone().getRegioni().get(0).getConsiglio();
		EleggiConsigliere azione = new EleggiConsigliere(giocoTest, consigliere, consiglio);
		assertEquals(consigliere, azione.getConsigliere());
		Consigliere consigliere2 = new Consigliere(Color.orange);
		azione.setConsigliere(consigliere2);
		assertEquals(consigliere2, azione.getConsigliere());
	}

	@Test
	public void testGetConsiglio() {
		Consigliere consigliere = new Consigliere(Color.pink);
		Consiglio consiglio = giocoTest.getTabellone().getRegioni().get(0).getConsiglio();
		EleggiConsigliere azione = new EleggiConsigliere(giocoTest, consigliere, consiglio);
		assertEquals(consiglio, azione.getConsiglio());
	}

	@Test
	public void testSetConsiglio() {
		Consigliere consigliere = new Consigliere(Color.pink);
		Consiglio consiglio = giocoTest.getTabellone().getRegioni().get(0).getConsiglio();
		EleggiConsigliere azione = new EleggiConsigliere(giocoTest, consigliere, consiglio);
		assertEquals(consiglio, azione.getConsiglio());
		Consiglio consiglio2 = new Consiglio(giocoTest.getTabellone());
		azione.setConsiglio(consiglio2);
		assertEquals(consiglio2, azione.getConsiglio());
	}

}

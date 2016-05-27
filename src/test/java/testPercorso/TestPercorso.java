package testPercorso;

import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.Tabellone;
import server.model.percorso.Percorso;

public class TestPercorso {
	
	private Tabellone tabellone;
	private Giocatore g1;
	private Giocatore g2;
	private Set<Giocatore> giocatori;
	
	@Before
	public void oggettiPerTest() throws JDOMException, IOException{
		tabellone = new Tabellone("src/main/resources/mappacollegamenti0.xml", new Gioco());
		g1 = new Giocatore("pippo", Color.black);
		g2 = new Giocatore("piero", Color.cyan);
		giocatori = new HashSet<>();
		giocatori.add(g1);
		giocatori.add(g2);
	}
	
	@Test
	public void testGetCaselle() {
		fail("Not yet implemented");
	}

	@Test(expected = NullPointerException.class)
	public void testIfNomeFileIsNull() throws JDOMException, IOException {
		Percorso percorso= new Percorso(null,tabellone);
	}
	
	@Test(expected = NullPointerException.class)
	public void testIfTabelloneIsNull() throws JDOMException, IOException{
		Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml",null);
	}

	@Test
	public void testMuoviGiocatore() throws Exception {
		Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml",tabellone);
		percorso.getCaselle().get(0).setGiocatori(giocatori);
		percorso.muoviGiocatore(g1, 4);	
	}

	@Test
	public void testMuoviGiocatoreAvanti() {
		fail("Not yet implemented");
	}

	@Test
	public void testMuoviGiocatoreIndietro() {
		fail("Not yet implemented");
	}

	@Test
	public void testPosizioneAttualeGiocatore() {
		fail("Not yet implemented");
	}

}

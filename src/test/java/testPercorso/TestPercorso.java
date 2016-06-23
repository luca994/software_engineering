package testPercorso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.Tabellone;
import server.model.bonus.Bonus;
import server.model.bonus.BonusMoneta;
import server.model.percorso.Casella;
import server.model.percorso.CasellaConBonus;
import server.model.percorso.CasellaSenzaBonus;
import server.model.percorso.Percorso;

public class TestPercorso {

	private Tabellone tabellone;
	private Giocatore g1;
	private Giocatore g2;
	private Set<Giocatore> giocatori;

	@Before
	public void oggettiPerTest() throws JDOMException, IOException {
		tabellone = new Tabellone("src/main/resources/mappacollegamenti0.xml", new Gioco(),"0");
		g1 = new Giocatore("pippo");
		g2 = new Giocatore("piero");
		giocatori = new HashSet<>();
		giocatori.add(g1);
		giocatori.add(g2);
	}

	@Test
	public void testGetCaselle() throws JDOMException, IOException {
		Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml", tabellone);
		List<Casella> caselleProva = new ArrayList<>();
		for (int i = 0; i < 6; i++)
			caselleProva.add(new CasellaSenzaBonus());
		percorso.getCaselle().clear();
		percorso.getCaselle().addAll(caselleProva);
		assertEquals(caselleProva, percorso.getCaselle());
	}

	@Test(expected = NullPointerException.class)
	public void testIfNomeFileIsNull() throws JDOMException, IOException {
		Percorso percorso = new Percorso(null, tabellone);
		assertNotNull(percorso);
	}

	@Test(expected = NullPointerException.class)
	public void testIfTabelloneIsNull() throws JDOMException, IOException {
		Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml", null);
		assertNotNull(percorso);
	}

	@Test
	public void testMuoviGiocatoreWithPositiveSteps() throws Exception {
		Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml", tabellone);
		percorso.getCaselle().get(0).setGiocatori(giocatori);
		percorso.muoviGiocatore(g1, 50);
		assertEquals(20, percorso.posizioneAttualeGiocatore(g1));
		percorso.muoviGiocatore(g1, 8);
		assertEquals(20, percorso.posizioneAttualeGiocatore(g1));
	}

	@Test(expected = server.eccezione.FuoriDalLimiteDelPercorso.class)
	public void testMuoviGiocatoreWithNegativeSteps() throws Exception {
		Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml", tabellone);
		percorso.getCaselle().get(0).setGiocatori(giocatori);
		percorso.muoviGiocatore(g1, -5);
		assertEquals(0, percorso.posizioneAttualeGiocatore(g1));
	}

	@Test
	public void testMuoviGiocatoreWithNegativeStepsWithoutException() throws Exception {
		Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml", tabellone);
		percorso.getCaselle().get(4).setGiocatori(giocatori);
		percorso.muoviGiocatore(g1, -4);
		percorso.muoviGiocatore(g2, -2);
		assertEquals(0, percorso.posizioneAttualeGiocatore(g1));
		assertEquals(2, percorso.posizioneAttualeGiocatore(g2));
	}
	
	@Test
	public void testPosizioneAttualeGiocatore() throws JDOMException, IOException {
		Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml", tabellone);
		percorso.getCaselle().get(14).setGiocatori(giocatori);
		assertEquals(14, percorso.posizioneAttualeGiocatore(g1));
	}
	
	@Test(expected=NullPointerException.class)
	public void testPosizioneAttualeGiocatoreNull() throws JDOMException, IOException {
		Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml", tabellone);
		percorso.posizioneAttualeGiocatore(null);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testPosizioneAttualeGiocatoreNonInizializzatoNelPercorso() throws JDOMException, IOException {
		Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml", tabellone);
		percorso.posizioneAttualeGiocatore(g2);
	}

	

	  @Test public void testBonusRoute() throws Exception{
	  Percorso percorso = new Percorso("src/main/resources/percorsoRicchezza.xml",tabellone);
	  percorso.getCaselle().get(0).getGiocatori().add(g1);
	  Set<Bonus> bonus = new HashSet<>(); 
	  bonus.add(new BonusMoneta(percorso,6)); 
	  percorso.getCaselle().set(5,new CasellaConBonus(bonus));
	  percorso.muoviGiocatore(g1, 5); 
	  assertEquals(11,percorso.posizioneAttualeGiocatore(g1));
	  }
	 

}

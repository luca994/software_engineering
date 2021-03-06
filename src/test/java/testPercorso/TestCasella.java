package testPercorso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.percorso.Casella;
import server.model.percorso.CasellaSenzaBonus;

public class TestCasella {

	private Set<Giocatore> giocatori;
	
	@Before
	public void oggettiTest(){
		giocatori=new HashSet<>();
		Giocatore g1 = new Giocatore("pippo");
		Giocatore g2 = new Giocatore("pluto");
		giocatori.add(g1);
		giocatori.add(g2);
	}

	@Test
	public void testSetGiocatori() {
		Casella casella = new CasellaSenzaBonus();
		assertTrue(casella.getGiocatori().isEmpty());
		casella.setGiocatori(giocatori);
		assertNotNull(casella.getGiocatori());
		assertEquals(giocatori, casella.getGiocatori());
	}

	@Test
	public void testGetGiocatori() {
		Casella casella = new CasellaSenzaBonus();
		casella.setGiocatori(giocatori);
		assertEquals(giocatori, casella.getGiocatori());
	}

}

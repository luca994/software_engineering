/**
 * 
 */
package testModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;

/**
 * @author Luca
 *
 */
public class CittaTest {

	private Gioco giocoTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo");
		Giocatore g2 = new Giocatore("paolo");
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
		gioco.inizializzaPartita();
	}

	/**
	 * Test method for
	 * {@link server.model.Citta#Citta(java.lang.String, server.model.Regione)}.
	 */
	@Test
	public void testCittaNomeNull() {
		Citta cittaTester = new Citta(null, giocoTester.getTabellone().getRegioni().get(1));
		assertNotNull(cittaTester);
		assertNotNull(cittaTester.getCittaVicina());
		assertNotNull(cittaTester.getEmpori());
		assertNull(cittaTester.getNome());
		assertEquals(giocoTester.getTabellone().getRegioni().get(1), cittaTester.getRegione());
	}

	/**
	 * Test method for
	 * {@link server.model.Citta#Citta(java.lang.String, server.model.Regione)}.
	 */
	@Test
	public void testCittaRegioneNull() {
		Citta cittaTester = new Citta("test", null);
		assertNotNull(cittaTester);
		assertNotNull(cittaTester.getCittaVicina());
		assertNotNull(cittaTester.getEmpori());
		assertEquals("test", cittaTester.getNome().toString());
		assertNull(cittaTester.getRegione());
	}

	/**
	 * Test method for
	 * {@link server.model.Citta#Citta(java.lang.String, server.model.Regione)}.
	 */
	@Test
	public void testCittaValida() {
		Citta cittaTester = new Citta("test", giocoTester.getTabellone().getRegioni().get(2));
		assertNotNull(cittaTester);
		assertNotNull(cittaTester.getCittaVicina());
		assertNotNull(cittaTester.getEmpori());
		assertEquals("test", cittaTester.getNome().toString());
		assertEquals(giocoTester.getTabellone().getRegioni().get(2), cittaTester.getRegione());
	}

	/**
	 * Test method for
	 * {@link server.model.Citta#cittaVicinaConEmporio(server.model.Giocatore, java.util.List)}
	 * .
	 */
	@Test
	public void testCittaVicinaConEmporio() {
		assertEquals(0, giocoTester.getTabellone().cercaCitta("esti")
				.cittaVicinaConEmporio(giocoTester.getGiocatori().get(0), new ArrayList<Citta>()).size());
		giocoTester.getTabellone().cercaCitta("Arkon").getEmpori().add(giocoTester.getGiocatori().get(0));
		giocoTester.getTabellone().cercaCitta("burgen").getEmpori().add(giocoTester.getGiocatori().get(0));
		assertEquals(2, giocoTester.getTabellone().cercaCitta("esti")
				.cittaVicinaConEmporio(giocoTester.getGiocatori().get(0), new ArrayList<Citta>()).size());
		giocoTester.getTabellone().cercaCitta("esti").getEmpori().add(giocoTester.getGiocatori().get(0));
		giocoTester.getTabellone().cercaCitta("Indur").getEmpori().add(giocoTester.getGiocatori().get(0));
		assertEquals(3, giocoTester.getTabellone().cercaCitta("esti")
				.cittaVicinaConEmporio(giocoTester.getGiocatori().get(0), new ArrayList<Citta>()).size());
	}

	/**
	 * Test method for
	 * {@link server.model.Citta#cittaVicinaConEmporio(server.model.Giocatore, java.util.List)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCittaVicinaConEmporioListaCittaInputNull() {
		assertEquals(0, giocoTester.getTabellone().cercaCitta("esti")
				.cittaVicinaConEmporio(giocoTester.getGiocatori().get(0), null).size());
	}

	/**
	 * Test method for
	 * {@link server.model.Citta#cittaVicinaConEmporio(server.model.Giocatore, java.util.List)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCittaVicinaConEmporioGiocatoreInputNull() {
		assertEquals(0, giocoTester.getTabellone().cercaCitta("esti")
				.cittaVicinaConEmporio(null, new ArrayList<Citta>()).size());
	}

	/**
	 * Test method for {@link server.model.Citta#setRe(server.model.Re)}.
	 */
	@Test
	public void testSetGetRe() {
		giocoTester.getTabellone().cercaCitta("burgen").setRe(giocoTester.getTabellone().getRe());
		assertEquals(giocoTester.getTabellone().getRe(), giocoTester.getTabellone().cercaCitta("burgen").getRe());
	}
}

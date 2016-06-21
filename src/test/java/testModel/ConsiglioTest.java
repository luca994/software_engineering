/**
 * 
 */
package testModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Consigliere;
import server.model.componenti.Consiglio;

/**
 * @author Luca
 *
 */
public class ConsiglioTest {

	private Gioco giocoTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo");
		Giocatore g2 = new Giocatore("paolo");
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
		giocoTester.inizializzaPartita("0");
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Consiglio#Consiglio(server.model.Tabellone)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testConsiglioNull() {
		assertNotNull(new Consiglio(null));
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Consiglio#Consiglio(server.model.Tabellone)}.
	 */
	@Test
	public void testConsiglioValido() {
		assertNotNull(new Consiglio(giocoTester.getTabellone()));
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Consiglio#acquisisciColoriConsiglio()}.
	 */
	@Test
	public void testAcquisisciColoriConsiglio() {
		assertEquals(4,
				giocoTester.getTabellone().getRegioni().get(0).getConsiglio().acquisisciColoriConsiglio().size());
	}

	/**
	 * Test method for {@link server.model.componenti.Consiglio#removeConsigliere()}.
	 */
	@Test
	public void testRemoveConsigliere() {
		int a = giocoTester.getTabellone().getConsiglieriDisponibili().size();
		assertEquals(4, giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().size());
		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().removeConsigliere();
		assertEquals(3, giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().size());
		assertEquals(a + 1, giocoTester.getTabellone().getConsiglieriDisponibili().size());
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Consiglio#addConsigliere(server.model.componenti.Consigliere)}.
	 */
	@Test
	public void testAddConsigliere() {
		Consigliere consigliereTester = giocoTester.getTabellone().getConsiglieriDisponibili().get(0);
		assertEquals(4, giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().size());
		giocoTester.getTabellone().getRegioni().get(0).getConsiglio()
				.addConsigliere(giocoTester.getTabellone().getConsiglieriDisponibili().get(0));
		assertTrue(giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri()
				.contains(consigliereTester));
		assertEquals(5, giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().size());
		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().removeConsigliere();
		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().removeConsigliere();
		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().removeConsigliere();
		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().removeConsigliere();
		assertTrue(giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri()
				.contains(consigliereTester));
		assertEquals(1, giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().size());
	}

	/**
	 * Test method for {@link server.model.componenti.Consiglio#getTabellone()}.
	 */
	@Test
	public void testGetTabellone() {
		assertEquals(giocoTester.getTabellone(),
				giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getTabellone());
		assertEquals(giocoTester.getTabellone(),
				giocoTester.getTabellone().getRegioni().get(1).getConsiglio().getTabellone());
		assertEquals(giocoTester.getTabellone(),
				giocoTester.getTabellone().getRegioni().get(2).getConsiglio().getTabellone());
	}

	/**
	 * Test method for {@link server.model.componenti.Consiglio#getRegione()}.
	 */
	@Test
	public void testGetRegione() {
		assertEquals(giocoTester.getTabellone().getRegioni().get(0),
				giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getRegione());
		assertEquals(giocoTester.getTabellone().getRegioni().get(1),
				giocoTester.getTabellone().getRegioni().get(1).getConsiglio().getRegione());
		assertEquals(giocoTester.getTabellone().getRegioni().get(2),
				giocoTester.getTabellone().getRegioni().get(2).getConsiglio().getRegione());
		assertNotEquals(giocoTester.getTabellone().getRegioni().get(1),
				giocoTester.getTabellone().getRegioni().get(2).getConsiglio().getRegione());
	}

}

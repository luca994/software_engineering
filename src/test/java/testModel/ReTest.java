/**
 * 
 */
package testModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.Re;

/**
 * @author Luca
 *
 */
public class ReTest {

	private Gioco giocoTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo");
		Giocatore g2 = new Giocatore("paolo");
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
		giocoTester.inizializzaPartita();
	}

	/**
	 * Test method for
	 * {@link server.model.Re#Re(server.model.Citta, server.model.Consiglio)}.
	 */
	@Test
	public void testReNull() {
		assertNotNull(new Re(null, null, null));
	}

	/**
	 * Test method for
	 * {@link server.model.Re#Re(server.model.Citta, server.model.Consiglio)}.
	 */
	@Test
	public void testReInputValidi() {
		assertNotNull(new Re(giocoTester.getTabellone().cercaCitta("indur"),
				giocoTester.getTabellone().getRegioni().get(1).getConsiglio(), giocoTester.getTabellone()));
	}

	/**
	 * Test method for {@link server.model.Re#contaPassi(server.model.Citta)}.
	 */
	@Test
	public void testContaPassi() {
		assertEquals(2, giocoTester.getTabellone().getRe().contaPassi(giocoTester.getTabellone().cercaCitta("Osium")));
		assertEquals(3, giocoTester.getTabellone().getRe().contaPassi(giocoTester.getTabellone().cercaCitta("naris")));
		assertEquals(0,
				giocoTester.getTabellone().getRe().contaPassi(giocoTester.getTabellone().cercaCitta("juvelar")));
		assertEquals(4, giocoTester.getTabellone().getRe().contaPassi(giocoTester.getTabellone().cercaCitta("arkon")));
	}

	/**
	 * Test method for {@link server.model.Re#muoviRe(server.model.Citta)}.
	 */
	@Test
	public void testMuoviRe() {
		giocoTester.getTabellone().getRe().muoviRe(giocoTester.getTabellone().cercaCitta("esti"));
		assertNull(giocoTester.getTabellone().cercaCitta("juvelar").getRe());
		assertEquals(giocoTester.getTabellone().getRe(), giocoTester.getTabellone().cercaCitta("esti").getRe());
		assertEquals(2, giocoTester.getTabellone().getRe().contaPassi(giocoTester.getTabellone().cercaCitta("arkon")));
		assertEquals(3,
				giocoTester.getTabellone().getRe().contaPassi(giocoTester.getTabellone().cercaCitta("castrum")));
		assertEquals(4, giocoTester.getTabellone().getRe().contaPassi(giocoTester.getTabellone().cercaCitta("naris")));
	}

	/**
	 * Test method for {@link server.model.Re#getCittà()}.
	 */
	@Test
	public void testGetCittà() {
		assertEquals(giocoTester.getTabellone().cercaCitta("juvelar"), giocoTester.getTabellone().getRe().getCittà());
	}

}

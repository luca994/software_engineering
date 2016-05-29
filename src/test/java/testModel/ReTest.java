/**
 * 
 */
package testModel;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.Re;
import server.model.Tabellone;

/**
 * @author Luca
 *
 */
public class ReTest {

	private Gioco giocoTester;
	private Tabellone tabelloneTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo", Color.blue);
		Giocatore g2 = new Giocatore("paolo", Color.black);
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
		assertNotNull(new Re(tabelloneTester.cercaCitta("indur"), tabelloneTester.getRegioni().get(1).getConsiglio(),
				tabelloneTester));
	}

	/**
	 * Test method for {@link server.model.Re#contaPassi(server.model.Citta)}.
	 */
	@Test
	public void testContaPassi() {
		assertEquals(2, tabelloneTester.getRe().contaPassi(tabelloneTester.cercaCitta("Osium")));
		assertEquals(3, tabelloneTester.getRe().contaPassi(tabelloneTester.cercaCitta("naris")));
		assertEquals(0, tabelloneTester.getRe().contaPassi(tabelloneTester.cercaCitta("juvelar")));
		assertEquals(4, tabelloneTester.getRe().contaPassi(tabelloneTester.cercaCitta("arkon")));
	}

	/**
	 * Test method for {@link server.model.Re#muoviRe(server.model.Citta)}.
	 */
	@Test
	public void testMuoviRe() {
		tabelloneTester.getRe().muoviRe(tabelloneTester.cercaCitta("esti"));
		assertNull(tabelloneTester.cercaCitta("juvelar").getRe());
		assertEquals(tabelloneTester.getRe(), tabelloneTester.cercaCitta("esti").getRe());
		assertEquals(2, tabelloneTester.getRe().contaPassi(tabelloneTester.cercaCitta("arkon")));
		assertEquals(3, tabelloneTester.getRe().contaPassi(tabelloneTester.cercaCitta("castrum")));
		assertEquals(4, tabelloneTester.getRe().contaPassi(tabelloneTester.cercaCitta("naris")));
	}

	/**
	 * Test method for {@link server.model.Re#getCittà()}.
	 */
	@Test
	public void testGetCittà() {
		assertEquals(tabelloneTester.cercaCitta("juvelar"), tabelloneTester.getRe().getCittà());
	}

}

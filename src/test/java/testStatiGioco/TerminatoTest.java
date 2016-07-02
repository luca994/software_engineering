/**
 * 
 */
package testStatiGioco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import server.eccezioni.FuoriDalLimiteDelPercorso;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.gioco.Terminato;

/**
 * @author Luca
 *
 */
public class TerminatoTest {

	private Gioco giocoTester;
	private Giocatore g1;
	private Giocatore g2;

	@Before
	public void inizializzaOggettiPerTest() {
		g1 = new Giocatore("pippo");
		g2 = new Giocatore("paolo");
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
		gioco.inizializzaPartita("4");
	}

	/**
	 * Test method for {@link server.model.stato.gioco.Terminato#eseguiFase()}.
	 * 
	 * @throws FuoriDalLimiteDelPercorso
	 */
	@Test
	public void testEseguiFaseVincitoreG1() throws FuoriDalLimiteDelPercorso {
		giocoTester.getTabellone().getPercorsoVittoria().muoviGiocatore(g1, 30);
		giocoTester.getTabellone().getPercorsoVittoria().muoviGiocatore(g2, 20);
		giocoTester.setStato(new Terminato(giocoTester));
		assertNull(((Terminato) giocoTester.getStato()).getVincitore());
		giocoTester.getStato().eseguiFase();
		assertEquals(g1, ((Terminato) giocoTester.getStato()).getVincitore());
	}
}

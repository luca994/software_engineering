/**
 * 
 */
package testModel;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Assistente;
import server.model.componenti.CartaColorata;
import server.model.componenti.CartaPolitica;
import server.model.componenti.OggettoVendibile;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.TurnoNormale;

/**
 * @author Luca
 *
 */
public class GiocatoreTest {

	private Giocatore giocatoreTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo");
		giocatoreTester = g1;
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#Giocatore(java.lang.String, java.awt.Color)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGiocatoreNomeNull() {
		Giocatore g1 = new Giocatore(null);
		assertNotNull(g1);
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#Giocatore(java.lang.String, java.awt.Color)}
	 * .
	 */
	@Test
	public void testGiocatoreValido() {
		assertNotNull(giocatoreTester);
		assertEquals("pippo", giocatoreTester.getNome());
		assertTrue(giocatoreTester.getStatoGiocatore() instanceof AttesaTurno);
	}

	/**
	 * Test method for {@link server.model.Giocatore#decrementaEmporiRimasti()}.
	 */
	@Test
	public void testDecrementaEmporiRimastiNumeroAlto() {
		giocatoreTester.setEmporiRimasti(1000000);
		giocatoreTester.decrementaEmporiRimasti();
		assertEquals(999999, giocatoreTester.getEmporiRimasti());
	}

	/**
	 * Test method for {@link server.model.Giocatore#decrementaEmporiRimasti()}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDecrementaEmporiRimastiNumeroUgualeAZero() {
		giocatoreTester.setEmporiRimasti(0);
		giocatoreTester.decrementaEmporiRimasti();
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#generaListaOggettiVendibiliNonInVendita()}
	 */
	public void testGeneraListaOggettiVendibiliNonInVenditaVuota() {
		assertTrue(giocatoreTester.generaListaOggettiVendibiliNonInVendita().isEmpty());
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#generaListaOggettiVendibiliNonInVendita()}
	 */
	public void testGeneraListaOggettiVendibiliNonInVenditaConCarteInizialiEUnAssistente() {
		Gioco giocoTester = new Gioco();
		Giocatore g2 = new Giocatore("paolo");
		giocoTester.getGiocatori().add(giocatoreTester);
		giocoTester.getGiocatori().add(g2);
		giocoTester.inizializzaPartita("1");
		List<OggettoVendibile> listaOggetti = giocatoreTester.generaListaOggettiVendibiliNonInVendita();
		assertTrue(listaOggetti.size() == 7);
		assertTrue(g2.generaListaOggettiVendibiliNonInVendita().size() == 8);
		for (CartaPolitica c : giocatoreTester.getCartePolitica())
			assertTrue(listaOggetti.contains(c));
		for (Assistente a : giocatoreTester.getAssistenti())
			assertTrue(listaOggetti.contains(a));
	}

	/**
	 * Test method for {@link server.model.Giocatore#cercaCarta()}
	 */
	public void testCercaCartaNeraEsistente() {
		CartaPolitica carta1 = new CartaColorata(Color.black);
		CartaPolitica carta2 = new CartaColorata(Color.black);
		giocatoreTester.getCartePolitica().add(carta1);
		assertEquals(carta1, giocatoreTester.cercaCarta(carta2));
	}

	/**
	 * Test method for {@link server.model.Giocatore#cercaCarta()}
	 */
	public void testCercaCartaCyanInesistente() {
		CartaPolitica carta1 = new CartaColorata(Color.black);
		CartaPolitica carta2 = new CartaColorata(Color.cyan);
		giocatoreTester.getCartePolitica().add(carta1);
		assertNotEquals(carta1, giocatoreTester.cercaCarta(carta2));
		assertNull(giocatoreTester.cercaCarta(carta2));
	}
	

	/**
	 * Test method for {@link server.model.Giocatore#getNome()}.
	 */
	@Test
	public void testGetNomeValido() {
		assertEquals("pippo", giocatoreTester.getNome());
	}

	/**
	 * Test method for {@link server.model.Giocatore#getAssistenti()}.
	 */
	@Test
	public void testSetAndGetAssistente() {
		List<Assistente> assistenti = new ArrayList<>();
		giocatoreTester.setAssistenti(assistenti);
		assertEquals(assistenti, giocatoreTester.getAssistenti());
		assertTrue(assistenti == giocatoreTester.getAssistenti());
	}

	/**
	 * Test method for {@link server.model.Giocatore#getCartePolitica()}.
	 */
	@Test
	public void testSetAndGetCartePolitica() {
		List<CartaPolitica> carte = new ArrayList<>();
		giocatoreTester.setCartePolitica(carte);
		assertEquals(carte, giocatoreTester.getCartePolitica());
		assertTrue(carte == giocatoreTester.getCartePolitica());
	}

	/**
	 * Test method for {@link server.model.Giocatore#getColore()}.
	 */
	@Test
	public void testSetAndGetColore() {
		giocatoreTester.setColore(Color.red);
		assertEquals(Color.red, giocatoreTester.getColore());
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#setStatoGiocatore(server.model.stato.giocatore.StatoGiocatore)}
	 * .
	 */
	@Test
	public void testSetandGetStatoGiocatore() {
		assertTrue(giocatoreTester.getStatoGiocatore() instanceof AttesaTurno);
		giocatoreTester.setStatoGiocatore(new TurnoNormale(giocatoreTester));
		assertTrue(giocatoreTester.getStatoGiocatore() instanceof TurnoNormale);
	}

}

/**
 * 
 */
package testModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.bonus.Bonus;
import server.model.componenti.Assistente;
import server.model.componenti.CartaColorata;
import server.model.componenti.CartaPolitica;
import server.model.componenti.CartaPoliticaFactory;
import server.model.componenti.Jolly;
import server.model.componenti.OggettoVendibile;
import server.model.componenti.TesseraCostruzione;
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
	@Test
	public void testGeneraListaOggettiVendibiliNonInVenditaVuota() {
		assertTrue(giocatoreTester.generaListaOggettiVendibiliNonInVendita().isEmpty());
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#generaListaOggettiVendibiliNonInVendita()}.
	 */
	@Test
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
	 * Test method for
	 * {@link server.model.Giocatore#cercaCarta(server.model.componenti.CartaPolitica)}
	 * .
	 */
	@Test
	public void testCercaCartaNeraEsistente() {
		CartaPolitica carta1 = new CartaColorata(Color.black);
		CartaPolitica carta2 = new CartaColorata(Color.black);
		giocatoreTester.getCartePolitica().add(carta1);
		giocatoreTester.getCartePolitica().add(new CartaColorata(Color.white));
		giocatoreTester.getCartePolitica().add(new CartaColorata(Color.magenta));
		giocatoreTester.getCartePolitica().add(new CartaColorata(Color.black));
		assertEquals(carta1, giocatoreTester.cercaCarta(carta2));
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#cercaCarta(server.model.componenti.CartaPolitica)}
	 * .
	 */
	@Test
	public void testCercaPrimaCartaNeraEsistenteConDueCarteNerePossedute() {
		CartaPolitica carta1 = new CartaColorata(Color.black);
		CartaPolitica carta2 = new CartaColorata(Color.black);
		CartaPolitica carta3 = new CartaColorata(Color.black);
		giocatoreTester.getCartePolitica().add(carta1);
		giocatoreTester.getCartePolitica().add(new CartaColorata(Color.white));
		giocatoreTester.getCartePolitica().add(new CartaColorata(Color.magenta));
		giocatoreTester.getCartePolitica().add(carta3);
		assertEquals(carta1, giocatoreTester.cercaCarta(carta1));
		assertEquals(carta1, giocatoreTester.cercaCarta(carta2));
		assertEquals(carta1, giocatoreTester.cercaCarta(carta3));
		assertNotEquals(carta3, giocatoreTester.cercaCarta(carta1));
		assertNotEquals(carta3, giocatoreTester.cercaCarta(carta2));
		assertNotEquals(carta3, giocatoreTester.cercaCarta(carta3));
		giocatoreTester.getCartePolitica().remove(0);
		assertEquals(carta3, giocatoreTester.cercaCarta(carta1));
		assertEquals(carta3, giocatoreTester.cercaCarta(carta2));
		assertEquals(carta3, giocatoreTester.cercaCarta(carta3));
		giocatoreTester.getCartePolitica().remove(2);
		assertNull(giocatoreTester.cercaCarta(carta1));
		assertNull(giocatoreTester.cercaCarta(carta2));
		assertNull(giocatoreTester.cercaCarta(carta3));
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#cercaCarta(server.model.componenti.CartaPolitica)}
	 * .
	 */
	@Test
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

	/**
	 * Test method for
	 * {@link server.model.Giocatore#spostaTesseraValidaInTesseraUsata(server.model.componenti.TesseraCostruzione)}
	 * .
	 */
	@Test
	public void testSpostaTesseraValidaInTesseraUsata() {
		TesseraCostruzione tess1 = new TesseraCostruzione(null, null, null, null);
		TesseraCostruzione tess2 = new TesseraCostruzione(null, null, null, null);
		giocatoreTester.getTessereValide().add(tess1);
		giocatoreTester.getTessereValide().add(tess2);
		giocatoreTester.spostaTesseraValidaInTesseraUsata(tess2);
		assertTrue(!giocatoreTester.getTessereUsate().contains(tess1));
		assertTrue(giocatoreTester.getTessereUsate().contains(tess2));
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#spostaTesseraValidaInTesseraUsata(server.model.componenti.TesseraCostruzione)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSpostaTesseraUsataInTesseraUsata() {
		TesseraCostruzione tess1 = new TesseraCostruzione(null, null, null, null);
		giocatoreTester.getTessereUsate().add(tess1);
		giocatoreTester.spostaTesseraValidaInTesseraUsata(tess1);
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#spostaTesseraValidaInTesseraUsata(server.model.componenti.TesseraCostruzione)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSpostaTesseraNonAggiuntaAlGiocatoreInTesseraUsata() {
		TesseraCostruzione tess1 = new TesseraCostruzione(null, null, null, null);
		giocatoreTester.spostaTesseraValidaInTesseraUsata(tess1);
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#cercaTesseraCostruzione(server.model.componenti.TesseraCostruzione)}
	 * .
	 */
	@Test
	public void testCercaTesseraCostruzioneDaCopiaValida() {
		Giocatore g2 = new Giocatore("paolo");
		Gioco gTest = new Gioco();
		gTest.getGiocatori().add(giocatoreTester);
		gTest.getGiocatori().add(g2);
		gTest.inizializzaPartita("8");
		TesseraCostruzione tesseraGiocatore = gTest.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0);
		TesseraCostruzione tesseraSimile = new TesseraCostruzione(tesseraGiocatore.getBonus(),
				tesseraGiocatore.getCitta(), tesseraGiocatore.getRegioneDiAppartenenza(), tesseraGiocatore.getId());
		giocatoreTester.getTessereValide().add(tesseraGiocatore);
		assertNotNull(giocatoreTester.cercaTesseraCostruzione(tesseraSimile));
		assertNull(g2.cercaTesseraCostruzione(tesseraSimile));
		assertNull(g2.cercaTesseraCostruzione(tesseraGiocatore));
		assertEquals(tesseraGiocatore, giocatoreTester.cercaTesseraCostruzione(tesseraSimile));
		assertEquals(tesseraGiocatore, giocatoreTester.cercaTesseraCostruzione(tesseraGiocatore));
		assertNotEquals(tesseraSimile, giocatoreTester.cercaTesseraCostruzione(tesseraSimile));
		assertNotEquals(tesseraSimile, giocatoreTester.cercaTesseraCostruzione(tesseraGiocatore));
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#cercaTesseraCostruzione(server.model.componenti.TesseraCostruzione)}
	 * .
	 */
	@Test
	public void testCercaTesseraCostruzioneDaCopiaConRegioneDiversa() {
		Giocatore g2 = new Giocatore("paolo");
		Gioco gTest = new Gioco();
		gTest.getGiocatori().add(giocatoreTester);
		gTest.getGiocatori().add(g2);
		gTest.inizializzaPartita("8");
		TesseraCostruzione tesseraGiocatore = gTest.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0);
		TesseraCostruzione tesseraSimile = new TesseraCostruzione(tesseraGiocatore.getBonus(),
				tesseraGiocatore.getCitta(), tesseraGiocatore.getRegioneDiAppartenenza(), tesseraGiocatore.getId());
		tesseraSimile.setRegioneDiAppartenenza(gTest.getTabellone().getRegioni().get(1));
		giocatoreTester.getTessereValide().add(tesseraGiocatore);
		assertNull(giocatoreTester.cercaTesseraCostruzione(tesseraSimile));
		assertNull(g2.cercaTesseraCostruzione(tesseraSimile));
		assertNull(g2.cercaTesseraCostruzione(tesseraGiocatore));
		assertEquals(tesseraGiocatore, giocatoreTester.cercaTesseraCostruzione(tesseraGiocatore));
		assertNotEquals(tesseraSimile, giocatoreTester.cercaTesseraCostruzione(tesseraGiocatore));
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#cercaTesseraCostruzione(server.model.componenti.TesseraCostruzione)}
	 * .
	 */
	@Test
	public void testCercaTesseraCostruzioneDaCopiaConCittaDiverse() {
		Giocatore g2 = new Giocatore("paolo");
		Gioco gTest = new Gioco();
		gTest.getGiocatori().add(giocatoreTester);
		gTest.getGiocatori().add(g2);
		gTest.inizializzaPartita("8");
		TesseraCostruzione tesseraGiocatore = gTest.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0);
		TesseraCostruzione tesseraSimile = new TesseraCostruzione(tesseraGiocatore.getBonus(),
				tesseraGiocatore.getCitta(), tesseraGiocatore.getRegioneDiAppartenenza(), tesseraGiocatore.getId());
		tesseraSimile.setCitta(gTest.getTabellone().getRegioni().get(2).getCitta());
		giocatoreTester.getTessereValide().add(tesseraGiocatore);
		assertNull(giocatoreTester.cercaTesseraCostruzione(tesseraSimile));
		assertNull(g2.cercaTesseraCostruzione(tesseraSimile));
		assertNull(g2.cercaTesseraCostruzione(tesseraGiocatore));
		assertNotEquals(tesseraGiocatore, giocatoreTester.cercaTesseraCostruzione(tesseraSimile));
		assertEquals(tesseraGiocatore, giocatoreTester.cercaTesseraCostruzione(tesseraGiocatore));
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#cercaTesseraCostruzione(server.model.componenti.TesseraCostruzione)}
	 * .
	 */
	@Test
	public void testCercaTesseraCostruzioneDaCopiaConBonusDiversi() {
		Giocatore g2 = new Giocatore("paolo");
		Gioco gTest = new Gioco();
		gTest.getGiocatori().add(giocatoreTester);
		gTest.getGiocatori().add(g2);
		gTest.inizializzaPartita("8");
		TesseraCostruzione tesseraGiocatore = gTest.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0);
		TesseraCostruzione tesseraSimile = new TesseraCostruzione(tesseraGiocatore.getBonus(),
				tesseraGiocatore.getCitta(), tesseraGiocatore.getRegioneDiAppartenenza(), tesseraGiocatore.getId());
		tesseraSimile.setBonus(new HashSet<Bonus>());
		giocatoreTester.getTessereValide().add(tesseraGiocatore);
		assertNull(giocatoreTester.cercaTesseraCostruzione(tesseraSimile));
		assertNull(g2.cercaTesseraCostruzione(tesseraSimile));
		assertNull(g2.cercaTesseraCostruzione(tesseraGiocatore));
		assertEquals(tesseraGiocatore, giocatoreTester.cercaTesseraCostruzione(tesseraGiocatore));
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#cercaTesseraCostruzione(server.model.componenti.TesseraCostruzione)}
	 * .
	 */
	@Test
	public void testCercaTesseraCostruzioneDaCopiaConIdDiverso() {
		/*
		 * id è un parametro che non viene confrontato tra le due tessere,
		 * quindi deve considerarla comunque come una tesseraSimile e ritornare
		 * il reference giusto.
		 */
		Giocatore g2 = new Giocatore("paolo");
		Gioco gTest = new Gioco();
		gTest.getGiocatori().add(giocatoreTester);
		gTest.getGiocatori().add(g2);
		gTest.inizializzaPartita("8");
		TesseraCostruzione tesseraGiocatore = gTest.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0);
		TesseraCostruzione tesseraSimile = new TesseraCostruzione(tesseraGiocatore.getBonus(),
				tesseraGiocatore.getCitta(), tesseraGiocatore.getRegioneDiAppartenenza(), "100000");
		giocatoreTester.getTessereValide().add(tesseraGiocatore);
		assertNotNull(giocatoreTester.cercaTesseraCostruzione(tesseraSimile));
		assertNull(g2.cercaTesseraCostruzione(tesseraSimile));
		assertNull(g2.cercaTesseraCostruzione(tesseraGiocatore));
		assertEquals(tesseraGiocatore, giocatoreTester.cercaTesseraCostruzione(tesseraSimile));
		assertEquals(tesseraGiocatore, giocatoreTester.cercaTesseraCostruzione(tesseraGiocatore));
		assertNotEquals(tesseraSimile, giocatoreTester.cercaTesseraCostruzione(tesseraSimile));
		assertNotEquals(tesseraSimile, giocatoreTester.cercaTesseraCostruzione(tesseraGiocatore));
	}

	/**
	 * Test method for {@link server.model.Giocatore#numeroPermessiTotali()}.
	 */
	@Test
	public void testNumeroPermessiTotali5UsatiE3Validi() {
		giocatoreTester.getTessereUsate().add(new TesseraCostruzione(null, null, null, null));
		giocatoreTester.getTessereUsate().add(new TesseraCostruzione(null, null, null, null));
		giocatoreTester.getTessereValide().add(new TesseraCostruzione(null, null, null, null));
		giocatoreTester.getTessereUsate().add(new TesseraCostruzione(null, null, null, null));
		giocatoreTester.getTessereValide().add(new TesseraCostruzione(null, null, null, null));
		giocatoreTester.getTessereUsate().add(new TesseraCostruzione(null, null, null, null));
		giocatoreTester.getTessereUsate().add(new TesseraCostruzione(null, null, null, null));
		assertEquals(7, giocatoreTester.numeroPermessiTotali());
	}

	/**
	 * Test method for {@link server.model.Giocatore#numeroPermessiTotali()}.
	 */
	@Test
	public void testNumeroPermessiTotali0permessi() {
		assertEquals(0, giocatoreTester.numeroPermessiTotali());
	}

	/**
	 * Test method for {@link server.model.Giocatore#numeroPermessiTotali()}.
	 */
	@Test
	public void testNumeroPermessiTotali1permessoValido() {
		giocatoreTester.getTessereValide().add(new TesseraCostruzione(null, null, null, null));
		assertEquals(1, giocatoreTester.numeroPermessiTotali());
	}

	/**
	 * Test method for {@link server.model.Giocatore#numeroPermessiTotali()}.
	 */
	@Test
	public void testNumeroPermessiTotali1permessoUsato() {
		giocatoreTester.getTessereUsate().add(new TesseraCostruzione(null, null, null, null));
		assertEquals(1, giocatoreTester.numeroPermessiTotali());
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#numeroAiutantiECartePolitica()}.
	 */
	@Test
	public void testNumeroAiutantiECartePolitica0CarteE0Assistenti() {
		assertEquals(0, giocatoreTester.numeroAiutantiECartePolitica());
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#numeroAiutantiECartePolitica()}.
	 */
	@Test
	public void testNumeroAiutantiECartePolitica1Assistente() {
		giocatoreTester.getAssistenti().add(new Assistente());
		assertEquals(1, giocatoreTester.numeroAiutantiECartePolitica());
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#numeroAiutantiECartePolitica()}.
	 */
	@Test
	public void testNumeroAiutantiECartePolitica1CartaPolitica() {
		CartaPoliticaFactory cF = new CartaPoliticaFactory();
		giocatoreTester.getCartePolitica().add(cF.creaCartaPolitica());
		assertEquals(1, giocatoreTester.numeroAiutantiECartePolitica());
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#numeroAiutantiECartePolitica()}.
	 */
	@Test
	public void testNumeroAiutantiECartePolitica3CartaPolitica4Assistenti() {
		CartaPoliticaFactory cF = new CartaPoliticaFactory();
		giocatoreTester.getCartePolitica().add(cF.creaCartaPolitica());
		giocatoreTester.getCartePolitica().add(cF.creaCartaPolitica());
		giocatoreTester.getCartePolitica().add(new Jolly());
		giocatoreTester.getAssistenti().add(new Assistente());
		giocatoreTester.getAssistenti().add(new Assistente());
		giocatoreTester.getAssistenti().add(new Assistente());
		giocatoreTester.getAssistenti().add(new Assistente());
		assertEquals(7, giocatoreTester.numeroAiutantiECartePolitica());
	}

}

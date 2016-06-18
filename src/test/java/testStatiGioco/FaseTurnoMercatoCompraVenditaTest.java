package testStatiGioco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Mercato;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.gioco.FaseTurnoMercatoAggiuntaOggetti;
import server.model.stato.gioco.FaseTurnoMercatoCompraVendita;
import server.model.stato.gioco.FaseTurnoSemplice;
import server.model.stato.gioco.Terminato;

public class FaseTurnoMercatoCompraVenditaTest {

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
		gioco.inizializzaPartita("6");
	}

	@Test
	public void testEseguiFaseRegolare() throws InterruptedException {
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
		assertTrue(g2.getStatoGiocatore() instanceof AttesaTurno);
		giocoTester.setStato(new FaseTurnoMercatoAggiuntaOggetti(giocoTester));
		Thread t = new Thread(giocoTester);
		t.start();
		while (true) {
			if (!(g1.getStatoGiocatore() instanceof AttesaTurno)) {
				g1.getStatoGiocatore().prossimoStato();
				break;
			}
			if (!(g2.getStatoGiocatore() instanceof AttesaTurno)) {
				g2.getStatoGiocatore().prossimoStato();
				break;
			}
		}
		while (true) {
			if (!(g1.getStatoGiocatore() instanceof AttesaTurno)) {
				giocoTester.setStato(new Terminato(giocoTester));
				g1.getStatoGiocatore().prossimoStato();
				break;
			}
			if (!(g2.getStatoGiocatore() instanceof AttesaTurno)) {
				giocoTester.setStato(new Terminato(giocoTester));
				g2.getStatoGiocatore().prossimoStato();
				break;
			}
		}
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
		assertTrue(g2.getStatoGiocatore() instanceof AttesaTurno);
	}

	@Test
	public void testProssimoStato() {
		giocoTester.setStato(new FaseTurnoMercatoCompraVendita(giocoTester,
				new Mercato(giocoTester.getTabellone().getPercorsoRicchezza())));
		giocoTester.getStato().prossimoStato();
		assertTrue(giocoTester.getStato() instanceof FaseTurnoSemplice);
	}

	@Test
	public void testFaseTurnoMercatoCompraVenditaGiocoValido() {
		assertNotNull(new FaseTurnoMercatoCompraVendita(giocoTester,
				new Mercato(giocoTester.getTabellone().getPercorsoRicchezza())));
	}

	@Test(expected = NullPointerException.class)
	public void testFaseTurnoMercatoCompraVenditaGiocoNullMercatoValido() {
		assertNotNull(new FaseTurnoMercatoCompraVendita(null,
				new Mercato(giocoTester.getTabellone().getPercorsoRicchezza())));
	}

	@Test
	public void testFaseTurnoMercatoCompraVenditaGiocoValidoMercatoNull() {
		assertNotNull(new FaseTurnoMercatoCompraVendita(giocoTester, null));
	}

	@Test(expected = NullPointerException.class)
	public void testFaseTurnoMercatoCompraVenditaGiocoNullMercatoNull() {
		assertNotNull(new FaseTurnoMercatoCompraVendita(null, null));
	}

	@Test
	public void testGetMercatoValido() {
		Mercato mercatoTest = new Mercato(giocoTester.getTabellone().getPercorsoRicchezza());
		FaseTurnoMercatoCompraVendita faseTester = new FaseTurnoMercatoCompraVendita(giocoTester, mercatoTest);
		assertNotNull(faseTester);
		assertEquals(mercatoTest, faseTester.getMercato());
	}

	@Test
	public void testGetMercatoNull() {
		FaseTurnoMercatoCompraVendita faseTester = new FaseTurnoMercatoCompraVendita(giocoTester, null);
		assertNotNull(faseTester);
		assertEquals(null, faseTester.getMercato());
	}
}

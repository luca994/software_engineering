package testStatiGioco;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;
import server.model.stato.gioco.FaseTurnoMercatoAggiuntaOggetti;
import server.model.stato.gioco.FaseTurnoMercatoCompraVendita;
import server.model.stato.gioco.FaseTurnoSemplice;
import server.model.stato.gioco.Terminato;

public class FaseTurnoMercatoAggiuntaOggettiTest {

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
		giocoTester.setStato(new FaseTurnoSemplice(giocoTester));
		Thread t = new Thread(giocoTester);
		t.start();
		while (g1.getStatoGiocatore() instanceof AttesaTurno)
			Thread.sleep(10);
		assertTrue(g1.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti);
		assertTrue(g2.getStatoGiocatore() instanceof AttesaTurno);
		g1.getStatoGiocatore().prossimoStato();
		while (g2.getStatoGiocatore() instanceof AttesaTurno)
			Thread.sleep(10);
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
		assertTrue(g2.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti);
		giocoTester.setStato(new Terminato(giocoTester));
		g2.getStatoGiocatore().prossimoStato();
		while (!(g2.getStatoGiocatore() instanceof AttesaTurno))
			Thread.sleep(10);
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
		assertTrue(g2.getStatoGiocatore() instanceof AttesaTurno);
	}

	@Test
	public void testProssimoStato() {
		giocoTester.setStato(new FaseTurnoMercatoAggiuntaOggetti(giocoTester));
		giocoTester.getStato().prossimoStato();
		assertTrue(giocoTester.getStato() instanceof FaseTurnoMercatoCompraVendita);
	}

	@Test
	public void testFaseTurnoMercatoAggiuntaOggettiGiocoValido() {
		assertNotNull(new FaseTurnoMercatoAggiuntaOggetti(giocoTester));
	}

	@Test(expected = NullPointerException.class)
	public void testFaseTurnoMercatoAggiuntaOggettiGiocoNull() {
		assertNotNull(new FaseTurnoMercatoAggiuntaOggetti(null));
	}

}

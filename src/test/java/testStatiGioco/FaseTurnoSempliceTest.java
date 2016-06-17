package testStatiGioco;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.TurniConclusi;
import server.model.stato.giocatore.TurnoNormale;
import server.model.stato.gioco.FaseTurnoMercatoAggiuntaOggetti;
import server.model.stato.gioco.FaseTurnoSemplice;
import server.model.stato.gioco.Terminato;

public class FaseTurnoSempliceTest {

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
		Thread t = new Thread(giocoTester);
		t.start();
		while (g1.getStatoGiocatore() instanceof AttesaTurno)
			Thread.sleep(10);
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertTrue(g2.getStatoGiocatore() instanceof AttesaTurno);
		g1.getStatoGiocatore().prossimoStato();
		while (g2.getStatoGiocatore() instanceof AttesaTurno)
			Thread.sleep(10);
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
		assertTrue(g2.getStatoGiocatore() instanceof TurnoNormale);
		giocoTester.setStato(new Terminato(giocoTester));
		g2.getStatoGiocatore().prossimoStato();
		while (!(g2.getStatoGiocatore() instanceof AttesaTurno))
			Thread.sleep(10);
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
		assertTrue(g2.getStatoGiocatore() instanceof AttesaTurno);
	}

	@Test
	public void testEseguiFaseUltimoTurno() throws InterruptedException {
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
		assertTrue(g2.getStatoGiocatore() instanceof AttesaTurno);
		Thread t = new Thread(giocoTester);
		t.start();
		while (g1.getStatoGiocatore() instanceof AttesaTurno)
			Thread.sleep(10);
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertTrue(g2.getStatoGiocatore() instanceof AttesaTurno);
		g1.getStatoGiocatore().prossimoStato();
		while (g2.getStatoGiocatore() instanceof AttesaTurno)
			Thread.sleep(10);
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
		assertTrue(g2.getStatoGiocatore() instanceof TurnoNormale);
		g2.getStatoGiocatore().tuttiGliEmporiCostruiti();
		g2.getStatoGiocatore().prossimoStato();
		while (g1.getStatoGiocatore() instanceof AttesaTurno)
			Thread.sleep(10);
		assertTrue(g2.getStatoGiocatore() instanceof TurniConclusi);
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		giocoTester.setStato(new Terminato(giocoTester));
		g1.getStatoGiocatore().prossimoStato();
		while (!(g1.getStatoGiocatore() instanceof TurniConclusi))
			Thread.sleep(10);
		assertTrue(g1.getStatoGiocatore() instanceof TurniConclusi);
		assertTrue(g2.getStatoGiocatore() instanceof TurniConclusi);
	}

	@Test
	public void testProssimoStatoMercatoAggiuntaOggetti() {
		giocoTester.setStato(new FaseTurnoSemplice(giocoTester));
		giocoTester.getStato().prossimoStato();
		assertTrue(giocoTester.getStato() instanceof FaseTurnoMercatoAggiuntaOggetti);
	}

	@Test
	public void testFaseTurnoSempliceValido() {
		assertNotNull(new FaseTurnoSemplice(giocoTester));
	}
	
	@Test(expected=NullPointerException.class)
	public void testFaseTurnoSempliceNull() {
		assertNotNull(new FaseTurnoSemplice(null));
	}

}

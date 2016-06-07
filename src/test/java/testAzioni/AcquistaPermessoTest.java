/**
 * 
 */
package testAzioni;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import eccezione.CartePoliticaIncorrette;
import eccezione.EmporioGiaCostruito;
import eccezione.FuoriDalLimiteDelPercorso;
import eccezione.NumeroAiutantiIncorretto;
import server.model.CartaColorata;
import server.model.CartaPolitica;
import server.model.Citta;
import server.model.Consigliere;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.Jolly;
import server.model.TesseraCostruzione;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.stato.giocatore.TurnoNormale;

/**
 * @author Luca
 *
 */
public class AcquistaPermessoTest {

	private Gioco giocoTester;
	private Giocatore g1;
	private AzioneFactory creaAzioniTester;
	private Azione azioneTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo");
		this.g1 = g1;
		Giocatore g2 = new Giocatore("paolo");
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
		giocoTester.inizializzaPartita();
		creaAzioniTester = new AzioneFactory(giocoTester);
	}

	/**
	 * Test method for
	 * {@link server.model.azione.AcquistaPermesso#AcquistaPermesso(server.model.Gioco, server.model.TesseraCostruzione, java.util.List, server.model.Consiglio)}
	 * .
	 */
	@Test
	public void testAcquistaPermesso() {
		Set<Citta> s = new HashSet<>();
		List<CartaPolitica> carteTest = new ArrayList<>();
		Queue<Consigliere> consiglieri = new LinkedList<>();
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));
		s.add(giocoTester.getTabellone().cercaCitta("arkon"));
		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new Jolly());
		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().setConsiglieri(consiglieri);
		TesseraCostruzione tesseraTest = new TesseraCostruzione(null, s,
				giocoTester.getTabellone().getRegioneDaNome("mare"));
		creaAzioniTester.setTesseraCostruzione(tesseraTest);
		creaAzioniTester.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		creaAzioniTester.setCartePolitica(carteTest);
		creaAzioniTester.setTipoAzione("0");
		assertNotNull(azioneTester = creaAzioniTester.createAzione());
	}

	/**
	 * Test method for
	 * {@link server.model.azione.AcquistaPermesso#eseguiAzione(server.model.Giocatore)}
	 * .
	 * 
	 * @throws EmporioGiaCostruito
	 * @throws NumeroAiutantiIncorretto
	 * @throws CartePoliticaIncorrette
	 * @throws FuoriDalLimiteDelPercorso
	 */
	@Test
	public void testEseguiAzioneSoddisfa1Consigliere()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		/* Imposto tutti i parametri dell'azione */

		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));

		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().setConsiglieri(consiglieri);

		TesseraCostruzione tesseraTester = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione()
				.get(0);
		/*
		 * Tolgo i bonus dalla tessera per verificare la posizione finale nel
		 * percorso ricchezza senza errori
		 */
		tesseraTester.setBonus(new HashSet<>());
		creaAzioniTester.setTesseraCostruzione(tesseraTester);
		creaAzioniTester.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		creaAzioniTester.setCartePolitica(carteTest);

		creaAzioniTester.setTipoAzione("0");
		azioneTester = creaAzioniTester.createAzione();
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertTrue(g1.getTessereValide().isEmpty());
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		azioneTester.eseguiAzione(g1);
		assertEquals(0, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(1, g1.getTessereValide().size());
		assertTrue(g1.getTessereValide().contains(tesseraTester));
	}

	/**
	 * Test method for
	 * {@link server.model.azione.AcquistaPermesso#eseguiAzione(server.model.Giocatore)}
	 * .
	 * 
	 * @throws EmporioGiaCostruito
	 * @throws NumeroAiutantiIncorretto
	 * @throws CartePoliticaIncorrette
	 * @throws FuoriDalLimiteDelPercorso
	 */
	@Test
	public void testEseguiAzioneSoddisfa2Consiglieri()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		/* Imposto tutti i parametri dell'azione */

		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.magenta));

		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().setConsiglieri(consiglieri);

		TesseraCostruzione tesseraTester = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione()
				.get(0);
		/*
		 * Tolgo i bonus dalla tessera per verificare la posizione finale nel
		 * percorso ricchezza senza errori
		 */
		tesseraTester.setBonus(new HashSet<>());
		creaAzioniTester.setTesseraCostruzione(tesseraTester);
		creaAzioniTester.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		creaAzioniTester.setCartePolitica(carteTest);

		creaAzioniTester.setTipoAzione("0");
		azioneTester = creaAzioniTester.createAzione();
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertTrue(g1.getTessereValide().isEmpty());
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		azioneTester.eseguiAzione(g1);
		assertEquals(3, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(1, g1.getTessereValide().size());
		assertTrue(g1.getTessereValide().contains(tesseraTester));
	}

	/**
	 * Test method for
	 * {@link server.model.azione.AcquistaPermesso#eseguiAzione(server.model.Giocatore)}
	 * .
	 * 
	 * @throws EmporioGiaCostruito
	 * @throws NumeroAiutantiIncorretto
	 * @throws CartePoliticaIncorrette
	 * @throws FuoriDalLimiteDelPercorso
	 */
	@Test
	public void testEseguiAzioneSoddisfa3Consiglieri()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		/* Imposto tutti i parametri dell'azione */

		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.magenta));
		carteTest.add(new CartaColorata(Color.white));

		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().setConsiglieri(consiglieri);

		TesseraCostruzione tesseraTester = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione()
				.get(0);
		/*
		 * Tolgo i bonus dalla tessera per verificare la posizione finale nel
		 * percorso ricchezza senza errori
		 */
		tesseraTester.setBonus(new HashSet<>());
		creaAzioniTester.setTesseraCostruzione(tesseraTester);
		creaAzioniTester.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		creaAzioniTester.setCartePolitica(carteTest);

		creaAzioniTester.setTipoAzione("0");
		azioneTester = creaAzioniTester.createAzione();
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertTrue(g1.getTessereValide().isEmpty());
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		azioneTester.eseguiAzione(g1);
		assertEquals(6, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(1, g1.getTessereValide().size());
		assertTrue(g1.getTessereValide().contains(tesseraTester));
	}

	/**
	 * Test method for
	 * {@link server.model.azione.AcquistaPermesso#eseguiAzione(server.model.Giocatore)}
	 * .
	 * 
	 * @throws EmporioGiaCostruito
	 * @throws NumeroAiutantiIncorretto
	 * @throws CartePoliticaIncorrette
	 * @throws FuoriDalLimiteDelPercorso
	 */
	@Test
	public void testEseguiAzioneSoddisfa4Consiglieri()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		/* Imposto tutti i parametri dell'azione */

		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.magenta));
		carteTest.add(new CartaColorata(Color.white));
		carteTest.add(new CartaColorata(Color.white));

		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().setConsiglieri(consiglieri);

		TesseraCostruzione tesseraTester = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione()
				.get(0);
		/*
		 * Tolgo i bonus dalla tessera per verificare la posizione finale nel
		 * percorso ricchezza senza errori
		 */
		tesseraTester.setBonus(new HashSet<>());
		creaAzioniTester.setTesseraCostruzione(tesseraTester);
		creaAzioniTester.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		creaAzioniTester.setCartePolitica(carteTest);

		creaAzioniTester.setTipoAzione("0");
		azioneTester = creaAzioniTester.createAzione();
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertTrue(g1.getTessereValide().isEmpty());
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		azioneTester.eseguiAzione(g1);
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(1, g1.getTessereValide().size());
		assertTrue(g1.getTessereValide().contains(tesseraTester));
	}

	/**
	 * Test method for
	 * {@link server.model.azione.AcquistaPermesso#eseguiAzione(server.model.Giocatore)}
	 * .
	 * 
	 * @throws EmporioGiaCostruito
	 * @throws NumeroAiutantiIncorretto
	 * @throws CartePoliticaIncorrette
	 * @throws FuoriDalLimiteDelPercorso
	 */
	@Test
	public void testEseguiAzioneSoddisfa4ConsiglieriCon4Jolly()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		/* Imposto tutti i parametri dell'azione */

		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new Jolly());
		carteTest.add(new Jolly());
		carteTest.add(new Jolly());
		carteTest.add(new Jolly());

		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().setConsiglieri(consiglieri);

		TesseraCostruzione tesseraTester = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione()
				.get(0);
		/*
		 * Tolgo i bonus dalla tessera per verificare la posizione finale nel
		 * percorso ricchezza senza errori
		 */
		tesseraTester.setBonus(new HashSet<>());
		creaAzioniTester.setTesseraCostruzione(tesseraTester);
		creaAzioniTester.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		creaAzioniTester.setCartePolitica(carteTest);

		creaAzioniTester.setTipoAzione("0");
		azioneTester = creaAzioniTester.createAzione();
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertTrue(g1.getTessereValide().isEmpty());
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		azioneTester.eseguiAzione(g1);
		assertEquals(6, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(1, g1.getTessereValide().size());
		assertTrue(g1.getTessereValide().contains(tesseraTester));
	}

	/**
	 * Test method for
	 * {@link server.model.azione.AcquistaPermesso#eseguiAzione(server.model.Giocatore)}
	 * .
	 * 
	 * @throws EmporioGiaCostruito
	 * @throws NumeroAiutantiIncorretto
	 * @throws CartePoliticaIncorrette
	 * @throws FuoriDalLimiteDelPercorso
	 */
	@Test
	public void testEseguiAzioneSoddisfa2ConsiglieriCon2Jolly()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		/* Imposto tutti i parametri dell'azione */

		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new Jolly());
		carteTest.add(new Jolly());

		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().setConsiglieri(consiglieri);

		TesseraCostruzione tesseraTester = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione()
				.get(0);
		/*
		 * Tolgo i bonus dalla tessera per verificare la posizione finale nel
		 * percorso ricchezza senza errori
		 */
		tesseraTester.setBonus(new HashSet<>());
		creaAzioniTester.setTesseraCostruzione(tesseraTester);
		creaAzioniTester.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		creaAzioniTester.setCartePolitica(carteTest);

		creaAzioniTester.setTipoAzione("0");
		azioneTester = creaAzioniTester.createAzione();
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertTrue(g1.getTessereValide().isEmpty());
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		azioneTester.eseguiAzione(g1);
		assertEquals(1, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(1, g1.getTessereValide().size());
		assertTrue(g1.getTessereValide().contains(tesseraTester));
	}

	/**
	 * Test method for
	 * {@link server.model.azione.AcquistaPermesso#eseguiAzione(server.model.Giocatore)}
	 * .
	 * 
	 * @throws EmporioGiaCostruito
	 * @throws NumeroAiutantiIncorretto
	 * @throws CartePoliticaIncorrette
	 * @throws FuoriDalLimiteDelPercorso
	 */
	@Test(expected = FuoriDalLimiteDelPercorso.class)
	public void testEseguiAzioneSoddisfa1ConsiglieriCon1Jolly()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		/* Imposto tutti i parametri dell'azione */

		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new Jolly());

		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().setConsiglieri(consiglieri);

		TesseraCostruzione tesseraTester = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione()
				.get(0);
		/*
		 * Tolgo i bonus dalla tessera per verificare la posizione finale nel
		 * percorso ricchezza senza errori
		 */
		tesseraTester.setBonus(new HashSet<>());
		creaAzioniTester.setTesseraCostruzione(tesseraTester);
		creaAzioniTester.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		creaAzioniTester.setCartePolitica(carteTest);

		creaAzioniTester.setTipoAzione("0");
		azioneTester = creaAzioniTester.createAzione();
		g1.getStatoGiocatore().prossimoStato();

		azioneTester.eseguiAzione(g1);

	}

	/**
	 * Test method for
	 * {@link server.model.azione.AcquistaPermesso#eseguiAzione(server.model.Giocatore)}
	 * .
	 * 
	 * @throws EmporioGiaCostruito
	 * @throws NumeroAiutantiIncorretto
	 * @throws CartePoliticaIncorrette
	 * @throws FuoriDalLimiteDelPercorso
	 */
	@Test(expected = CartePoliticaIncorrette.class)
	public void testEseguiAzioneSoddisfa0Consiglieri()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		/* Imposto tutti i parametri dell'azione */

		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new CartaColorata(Color.pink));

		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		giocoTester.getTabellone().getRegioni().get(0).getConsiglio().setConsiglieri(consiglieri);

		TesseraCostruzione tesseraTester = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione()
				.get(0);
		/*
		 * Tolgo i bonus dalla tessera per verificare la posizione finale nel
		 * percorso ricchezza senza errori
		 */
		tesseraTester.setBonus(new HashSet<>());
		creaAzioniTester.setTesseraCostruzione(tesseraTester);
		creaAzioniTester.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		creaAzioniTester.setCartePolitica(carteTest);

		creaAzioniTester.setTipoAzione("0");
		azioneTester = creaAzioniTester.createAzione();
		g1.getStatoGiocatore().prossimoStato();
		azioneTester.eseguiAzione(g1);

	}
}

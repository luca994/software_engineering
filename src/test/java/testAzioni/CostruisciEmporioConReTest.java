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

import org.junit.Before;
import org.junit.Test;

import eccezione.CartePoliticaIncorrette;
import eccezione.EmporioGiaCostruito;
import eccezione.FuoriDalLimiteDelPercorso;
import eccezione.NumeroAiutantiIncorretto;
import server.model.CartaColorata;
import server.model.CartaPolitica;
import server.model.Consigliere;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.stato.giocatore.TurnoNormale;

/**
 * @author Luca
 *
 */
public class CostruisciEmporioConReTest {

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
	 * {@link server.model.azione.CostruisciEmporioConRe#eseguiAzione(server.model.Giocatore)}
	 * .
	 * @throws EmporioGiaCostruito 
	 * @throws NumeroAiutantiIncorretto 
	 * @throws CartePoliticaIncorrette 
	 * @throws FuoriDalLimiteDelPercorso 
	 */
	@Test
	public void testEseguiAzioneValidaSpesa9Soldi() throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		// Setup carte
		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		// Setup consiglieri
		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		giocoTester.getTabellone().getRe().getConsiglio().setConsiglieri(consiglieri);

		creaAzioniTester.setCartePolitica(carteTest);
		creaAzioniTester.setCitta(giocoTester.getTabellone().cercaCitta("indur"));
		creaAzioniTester.setTipoAzione("1");
		azioneTester = creaAzioniTester.createAzione();
		//Tolgo il bonus dalla citta indur
		giocoTester.getTabellone().cercaCitta("indur").setBonus(new HashSet<>());
		
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(10,g1.getEmporiRimasti());
		azioneTester.eseguiAzione(g1);
		assertEquals(9,g1.getEmporiRimasti());
		assertEquals(1,giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));

	}

	/**
	 * Test method for
	 * {@link server.model.azione.CostruisciEmporioConRe#eseguiAzione(server.model.Giocatore)}
	 * .
	 * @throws EmporioGiaCostruito 
	 * @throws NumeroAiutantiIncorretto 
	 * @throws CartePoliticaIncorrette 
	 * @throws FuoriDalLimiteDelPercorso 
	 */
	@Test(expected=FuoriDalLimiteDelPercorso.class)
	public void testEseguiAzioneValidaSpesa12Soldi() throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		// Setup carte
		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		// Setup consiglieri
		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		giocoTester.getTabellone().getRe().getConsiglio().setConsiglieri(consiglieri);

		creaAzioniTester.setCartePolitica(carteTest);
		creaAzioniTester.setCitta(giocoTester.getTabellone().cercaCitta("indur"));
		creaAzioniTester.setTipoAzione("1");
		azioneTester = creaAzioniTester.createAzione();
		//Tolgo il bonus dalla citta indur
		giocoTester.getTabellone().cercaCitta("indur").setBonus(new HashSet<>());
		
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(10,g1.getEmporiRimasti());
		azioneTester.eseguiAzione(g1);
	}
	
	/**
	 * Test method for
	 * {@link server.model.azione.CostruisciEmporioConRe#eseguiAzione(server.model.Giocatore)}
	 * .
	 * @throws EmporioGiaCostruito 
	 * @throws NumeroAiutantiIncorretto 
	 * @throws CartePoliticaIncorrette 
	 * @throws FuoriDalLimiteDelPercorso 
	 */
	@Test(expected=NumeroAiutantiIncorretto.class)
	public void testEseguiAzioneSenzaAiutantiSuCittaConEmporioAvversario() throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		// Setup carte
		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		// Setup consiglieri
		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		//Azzero aiutanti
		g1.setAssistenti(new ArrayList<>());
		giocoTester.getTabellone().getRe().getConsiglio().setConsiglieri(consiglieri);

		creaAzioniTester.setCartePolitica(carteTest);
		creaAzioniTester.setCitta(giocoTester.getTabellone().cercaCitta("indur"));
		creaAzioniTester.setTipoAzione("1");
		azioneTester = creaAzioniTester.createAzione();
		//Tolgo il bonus dalla citta indur
		giocoTester.getTabellone().cercaCitta("indur").setBonus(new HashSet<>());
		giocoTester.getTabellone().cercaCitta("indur").getEmpori().add(new Giocatore("prova"));
		
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(10,g1.getEmporiRimasti());
		azioneTester.eseguiAzione(g1);
	}
	
	/**
	 * Test method for
	 * {@link server.model.azione.CostruisciEmporioConRe#eseguiAzione(server.model.Giocatore)}
	 * .
	 * @throws EmporioGiaCostruito 
	 * @throws NumeroAiutantiIncorretto 
	 * @throws CartePoliticaIncorrette 
	 * @throws FuoriDalLimiteDelPercorso 
	 */
	@Test(expected=EmporioGiaCostruito.class)
	public void testEseguiAzioneCostruisciSuCittaCheHaGiaEmporioGiocatore() throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		// Setup carte
		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		// Setup consiglieri
		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		//Azzero aiutanti
		g1.setAssistenti(new ArrayList<>());
		giocoTester.getTabellone().getRe().getConsiglio().setConsiglieri(consiglieri);

		creaAzioniTester.setCartePolitica(carteTest);
		creaAzioniTester.setCitta(giocoTester.getTabellone().cercaCitta("indur"));
		creaAzioniTester.setTipoAzione("1");
		azioneTester = creaAzioniTester.createAzione();
		//Tolgo il bonus dalla citta indur
		giocoTester.getTabellone().cercaCitta("indur").setBonus(new HashSet<>());
		giocoTester.getTabellone().cercaCitta("indur").getEmpori().add(g1);
		
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(10,g1.getEmporiRimasti());
		azioneTester.eseguiAzione(g1);
	}
	
	/**
	 * Test method for
	 * {@link server.model.azione.CostruisciEmporioConRe#eseguiAzione(server.model.Giocatore)}
	 * .
	 * @throws EmporioGiaCostruito 
	 * @throws NumeroAiutantiIncorretto 
	 * @throws CartePoliticaIncorrette 
	 * @throws FuoriDalLimiteDelPercorso 
	 */
	@Test(expected=CartePoliticaIncorrette.class)
	public void testEseguiAzioneSenzaCartePolitica() throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		// Setup carte
		List<CartaPolitica> carteTest = new ArrayList<>();

		// Setup consiglieri
		Queue<Consigliere> consiglieri = new LinkedList<>();

		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.white));
		consiglieri.add(new Consigliere(Color.black));
		consiglieri.add(new Consigliere(Color.magenta));

		//Azzero aiutanti
		g1.setAssistenti(new ArrayList<>());
		giocoTester.getTabellone().getRe().getConsiglio().setConsiglieri(consiglieri);

		creaAzioniTester.setCartePolitica(carteTest);
		creaAzioniTester.setCitta(giocoTester.getTabellone().cercaCitta("indur"));
		creaAzioniTester.setTipoAzione("1");
		azioneTester = creaAzioniTester.createAzione();
		//Tolgo il bonus dalla citta indur
		giocoTester.getTabellone().cercaCitta("indur").setBonus(new HashSet<>());
		
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(10,g1.getEmporiRimasti());
		azioneTester.eseguiAzione(g1);
	}
	
	/**
	 * Test method for
	 * {@link server.model.azione.CostruisciEmporioConRe#CostruisciEmporioConRe(server.model.Gioco, java.util.List, server.model.Citta)}
	 * .
	 */
	@Test
	public void testCostruisciEmporioConReValida() {
		// Setup carte
		List<CartaPolitica> carteTest = new ArrayList<>();

		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		carteTest.add(new CartaColorata(Color.black));
		// Setup parametri nel factory
		creaAzioniTester.setCartePolitica(carteTest);
		creaAzioniTester.setCitta(giocoTester.getTabellone().cercaCitta("indur"));

		creaAzioniTester.setTipoAzione("1");
		assertNotNull(azioneTester = creaAzioniTester.createAzione());
	}

	/**
	 * Test method for
	 * {@link server.model.azione.CostruisciEmporioConRe#CostruisciEmporioConRe(server.model.Gioco, java.util.List, server.model.Citta)}
	 * .
	 */
	@Test
	public void testCostruisciEmporioConReInputNull() {
		creaAzioniTester.setTipoAzione("1");
		assertNotNull(azioneTester = creaAzioniTester.createAzione());
	}

}

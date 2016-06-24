/**
 * 
 */
package testAzioni;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.AcquistaPermesso;
import server.model.azione.AzioneFactory;
import server.model.azione.AzionePrincipaleAggiuntiva;
import server.model.azione.AzioneRapidaNulla;
import server.model.azione.CambioTessereCostruzione;
import server.model.azione.CostruisciEmporioConRe;
import server.model.azione.CostruisciEmporioConTessera;
import server.model.azione.EleggiConsigliere;
import server.model.azione.EleggiConsigliereRapido;
import server.model.azione.IngaggioAiutante;
import server.model.componenti.CartaColorata;
import server.model.componenti.CartaPolitica;
import server.model.componenti.Consigliere;
import server.model.componenti.Consiglio;

/**
 * @author Luca
 *
 */
public class AzioneFactoryTest {

	private Gioco giocoTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo");
		Giocatore g2 = new Giocatore("paolo");
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
		giocoTester.inizializzaPartita("0");
	}

	/**
	 * Test method for
	 * {@link server.model.azione.AzioneFactory#AzioneFactory(server.model.Gioco)}
	 * .
	 */
	@Test
	public void testAzioneFactoryGiocoNull() {
		assertNotNull(new AzioneFactory(null));
	}

	/**
	 * Test method for
	 * {@link server.model.azione.AzioneFactory#AzioneFactory(server.model.Gioco)}
	 * .
	 */
	@Test
	public void testAzioneFactoryGiocoNonNull() {
		assertNotNull(new AzioneFactory(giocoTester));
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneEleggiConsigliereConsigliereNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("2");
		aFacTest.setConsigliere(null);
		aFacTest.setConsiglio(new Consiglio(giocoTester.getTabellone()));
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneEleggiConsigliereConsiglioNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("2");
		aFacTest.setConsigliere(new Consigliere(Color.black));
		aFacTest.setConsiglio(null);
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneEleggiConsigliereParametriCompleti() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("2");
		aFacTest.setConsigliere(new Consigliere(Color.black));
		aFacTest.setConsiglio(new Consiglio(giocoTester.getTabellone()));
		assertTrue(aFacTest.createAzione() instanceof EleggiConsigliere);
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testCreateAzioneNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione(null);
		aFacTest.createAzione();
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneAcquistaPermessoTesseraCostruzioneNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		List<CartaPolitica> cTest = new ArrayList<>();
		cTest.add(new CartaColorata(Color.cyan));
		aFacTest.setTipoAzione("0");
		aFacTest.setTesseraCostruzione(null);
		aFacTest.setConsiglio(new Consiglio(giocoTester.getTabellone()));
		aFacTest.setCartePolitica(cTest);
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneAcquistaPermessoCartePoliticaVuote() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("0");
		aFacTest.setTesseraCostruzione(giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		aFacTest.setConsiglio(new Consiglio(giocoTester.getTabellone()));
		aFacTest.setCartePolitica(new ArrayList<>());
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneAcquistaPermessoConsiglioNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("0");
		aFacTest.setTesseraCostruzione(giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		aFacTest.setConsiglio(null);
		aFacTest.setCartePolitica(new ArrayList<>());
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneAcquistaPermessoParametriCorretti() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		List<CartaPolitica> cTest = new ArrayList<>();
		cTest.add(new CartaColorata(Color.cyan));
		aFacTest.setTipoAzione("0");
		aFacTest.setTesseraCostruzione(giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		aFacTest.setConsiglio(new Consiglio(giocoTester.getTabellone()));
		aFacTest.setCartePolitica(cTest);
		assertTrue(aFacTest.createAzione() instanceof AcquistaPermesso);
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneAzionePrincipaleAggiuntiva() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("7");
		assertTrue(aFacTest.createAzione() instanceof AzionePrincipaleAggiuntiva);
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneCambioTessereCostruzioneRegioneNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("5");
		aFacTest.setRegione(null);
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneCambioTessereCostruzioneParametriCorretti() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("5");
		aFacTest.setRegione(giocoTester.getTabellone().getRegioni().get(0));
		assertTrue(aFacTest.createAzione() instanceof CambioTessereCostruzione);
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneCostruisciEmporioConReCartePoliticaVuote() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		List<CartaPolitica> cTest = new ArrayList<>();
		aFacTest.setTipoAzione("1");
		aFacTest.setCitta(giocoTester.getTabellone().cercaCitta("arkon"));
		aFacTest.setCartePolitica(cTest);
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneCostruisciEmporioConReCittaNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		List<CartaPolitica> cTest = new ArrayList<>();
		cTest.add(new CartaColorata(Color.cyan));
		aFacTest.setTipoAzione("1");
		aFacTest.setCitta(null);
		aFacTest.setCartePolitica(cTest);
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneCostruisciEmporioConReParametriCorretti() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		List<CartaPolitica> cTest = new ArrayList<>();
		cTest.add(new CartaColorata(Color.cyan));
		aFacTest.setTipoAzione("1");
		aFacTest.setCitta(giocoTester.getTabellone().cercaCitta("arkon"));
		aFacTest.setCartePolitica(cTest);
		assertTrue(aFacTest.createAzione() instanceof CostruisciEmporioConRe);
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneCostruisciEmporioConTesseraCittaNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("3");
		aFacTest.setCitta(null);
		aFacTest.setTesseraCostruzione(giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneCostruisciEmporioConTesseraTesseraNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("3");
		aFacTest.setCitta(giocoTester.getTabellone().cercaCitta("arkon"));
		aFacTest.setTesseraCostruzione(null);
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneCostruisciEmporioConTesseraParametriCorretti() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("3");
		aFacTest.setCitta(giocoTester.getTabellone().cercaCitta("arkon"));
		aFacTest.setTesseraCostruzione(giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		assertTrue(aFacTest.createAzione() instanceof CostruisciEmporioConTessera);
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneEleggiConsigliereRapidoConsigliereNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("6");
		aFacTest.setConsigliere(null);
		aFacTest.setConsiglio(new Consiglio(giocoTester.getTabellone()));
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneEleggiConsigliereRapidoConsiglioNull() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("6");
		aFacTest.setConsigliere(new Consigliere(Color.black));
		aFacTest.setConsiglio(null);
		assertNull(aFacTest.createAzione());
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneEleggiConsigliereRapidoParametriCompleti() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("6");
		aFacTest.setConsigliere(new Consigliere(Color.black));
		aFacTest.setConsiglio(new Consiglio(giocoTester.getTabellone()));
		assertTrue(aFacTest.createAzione() instanceof EleggiConsigliereRapido);
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneIngaggiaAiutante() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("4");
		assertTrue(aFacTest.createAzione() instanceof IngaggioAiutante);
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test
	public void testCreateAzioneAzioneRapidaNulla() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("8");
		assertTrue(aFacTest.createAzione() instanceof AzioneRapidaNulla);
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testCreateAzioneNulla() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.createAzione();
	}

	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test(expected = NumberFormatException.class)
	public void testCreateAzioneInvalidaString() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("stringa");
		aFacTest.createAzione();
	}
	
	/**
	 * Test method for {@link server.model.azione.AzioneFactory#createAzione()}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testCreateAzioneInvalidaInt() {
		AzioneFactory aFacTest = new AzioneFactory(giocoTester);
		aFacTest.setTipoAzione("10");
		aFacTest.createAzione();
	}

}

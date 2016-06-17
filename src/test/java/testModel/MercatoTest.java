/**
 * 
 */
package testModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.componenti.Assistente;
import server.model.componenti.CartaColorata;
import server.model.componenti.Mercato;
import server.model.componenti.OggettoVendibile;
import server.model.componenti.TesseraCostruzione;

/**
 * @author Luca
 *
 */
public class MercatoTest {

	private Gioco giocoTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo");
		Giocatore g2 = new Giocatore("paolo");
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
		gioco.inizializzaPartita("0");
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Mercato#Mercato(server.model.percorso.Percorso)}.
	 */
	@Test
	public void testMercatoValido() {
		assertNotNull(new Mercato(giocoTester.getTabellone().getPercorsoRicchezza()));
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Mercato#Mercato(server.model.percorso.Percorso)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testMercatoNull() {
		assertNotNull(new Mercato(null));
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Mercato#cercaOggetto(server.model.componenti.OggettoVendibile)}.
	 */
	@Test
	public void testCercaOggettoAssistente() {
		Mercato mercatoTester = new Mercato(giocoTester.getTabellone().getPercorsoRicchezza());
		Giocatore venditore = giocoTester.getGiocatori().get(0);
		Giocatore gTester = new Giocatore(venditore.getNome());
		OggettoVendibile tester = new Assistente();
		OggettoVendibile oggettoDaAggiungere = venditore.getAssistenti().get(0);
		oggettoDaAggiungere.setPrezzo(2);
		oggettoDaAggiungere.setMercato(mercatoTester);
		oggettoDaAggiungere.setGiocatore(venditore);
		oggettoDaAggiungere.aggiungiOggetto(mercatoTester);
		tester.setPrezzo(2);
		tester.setGiocatore(gTester);
		assertEquals(oggettoDaAggiungere, mercatoTester.cercaOggetto(tester));
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Mercato#cercaOggetto(server.model.componenti.OggettoVendibile)}.
	 */
	@Test
	public void testCercaOggettoCartaColorata() {
		Mercato mercatoTester = new Mercato(giocoTester.getTabellone().getPercorsoRicchezza());
		Giocatore venditore = giocoTester.getGiocatori().get(0);
		Giocatore gTester = new Giocatore(venditore.getNome());
		OggettoVendibile tester = new CartaColorata(Color.black);
		OggettoVendibile oggettoDaAggiungere = new CartaColorata(Color.black);
		oggettoDaAggiungere.setPrezzo(2);
		oggettoDaAggiungere.setMercato(mercatoTester);
		oggettoDaAggiungere.setGiocatore(venditore);
		oggettoDaAggiungere.aggiungiOggetto(mercatoTester);
		tester.setPrezzo(2);
		tester.setGiocatore(gTester);
		assertEquals(oggettoDaAggiungere, mercatoTester.cercaOggetto(tester));
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Mercato#cercaOggetto(server.model.componenti.OggettoVendibile)}.
	 */
	@Test
	public void testCercaOggettoCartaColorataNomeDiverso() {
		Mercato mercatoTester = new Mercato(giocoTester.getTabellone().getPercorsoRicchezza());
		Giocatore venditore = giocoTester.getGiocatori().get(0);
		Giocatore gTester = new Giocatore("test");
		OggettoVendibile tester = new CartaColorata(Color.black);
		OggettoVendibile oggettoDaAggiungere = new CartaColorata(Color.black);
		oggettoDaAggiungere.setPrezzo(2);
		oggettoDaAggiungere.setMercato(mercatoTester);
		oggettoDaAggiungere.setGiocatore(venditore);
		oggettoDaAggiungere.aggiungiOggetto(mercatoTester);
		tester.setPrezzo(2);
		tester.setGiocatore(gTester);
		assertNull(mercatoTester.cercaOggetto(tester));
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Mercato#cercaOggetto(server.model.componenti.OggettoVendibile)}.
	 */
	@Test
	public void testCercaOggettoCartaColorataConPrezzoDiverso() {
		Mercato mercatoTester = new Mercato(giocoTester.getTabellone().getPercorsoRicchezza());
		Giocatore venditore = giocoTester.getGiocatori().get(0);
		Giocatore gTester = new Giocatore(giocoTester.getGiocatori().get(0).getNome());
		OggettoVendibile tester = new CartaColorata(Color.black);
		OggettoVendibile oggettoDaAggiungere = new CartaColorata(Color.black);
		oggettoDaAggiungere.setPrezzo(2);
		oggettoDaAggiungere.setMercato(mercatoTester);
		oggettoDaAggiungere.setGiocatore(venditore);
		oggettoDaAggiungere.aggiungiOggetto(mercatoTester);
		tester.setPrezzo(1);
		tester.setGiocatore(gTester);
		assertNull(mercatoTester.cercaOggetto(tester));
	}

	/**
	 * Test method for
	 * {@link server.model.componenti.Mercato#cercaOggetto(server.model.componenti.OggettoVendibile)}.
	 */
	@Test
	public void testCercaOggettoTesseraPermesso() {
		Mercato mercatoTester = new Mercato(giocoTester.getTabellone().getPercorsoRicchezza());
		Giocatore venditore = giocoTester.getGiocatori().get(0);
		Giocatore gTester = new Giocatore(giocoTester.getGiocatori().get(0).getNome());
		OggettoVendibile oggettoDaAggiungere = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione()
				.get(0);
		OggettoVendibile tester = new TesseraCostruzione(((TesseraCostruzione) oggettoDaAggiungere).getBonus(),
				((TesseraCostruzione) oggettoDaAggiungere).getCitta(),
				((TesseraCostruzione) oggettoDaAggiungere).getRegioneDiAppartenenza(), "0");
		oggettoDaAggiungere.setPrezzo(2);
		oggettoDaAggiungere.setMercato(mercatoTester);
		oggettoDaAggiungere.setGiocatore(venditore);
		oggettoDaAggiungere.aggiungiOggetto(mercatoTester);
		tester.setPrezzo(2);
		tester.setGiocatore(gTester);
		assertEquals(oggettoDaAggiungere, mercatoTester.cercaOggetto(tester));
	}

	/**
	 * Test method for {@link server.model.componenti.Mercato#getOggettiInVendita()}.
	 */
	@Test
	public void testGetOggettiInVenditaGet0() {
		Mercato mercatoTester = new Mercato(giocoTester.getTabellone().getPercorsoRicchezza());
		Giocatore venditore = giocoTester.getGiocatori().get(0);
		OggettoVendibile oggettoDaAggiungere = venditore.getAssistenti().get(0);
		oggettoDaAggiungere.setPrezzo(2);
		oggettoDaAggiungere.setMercato(mercatoTester);
		oggettoDaAggiungere.setGiocatore(venditore);
		oggettoDaAggiungere.aggiungiOggetto(mercatoTester);
		assertEquals(oggettoDaAggiungere, mercatoTester.getOggettiInVendita().get(0));
	}

	/**
	 * Test method for {@link server.model.componenti.Mercato#getOggettiInVendita()}.
	 */
	@Test
	public void testGetOggettiInVenditaGetVuoto() {
		Mercato mercatoTester = new Mercato(giocoTester.getTabellone().getPercorsoRicchezza());
		assertNotNull(mercatoTester.getOggettiInVendita());
		assertTrue(mercatoTester.getOggettiInVendita().isEmpty());

	}

	/**
	 * Test method for {@link server.model.componenti.Mercato#getPercorsoRicchezza()}.
	 */
	@Test
	public void testGetPercorsoRicchezza() {
		Mercato mercatoTester = new Mercato(giocoTester.getTabellone().getPercorsoRicchezza());
		assertEquals(giocoTester.getTabellone().getPercorsoRicchezza(), mercatoTester.getPercorsoRicchezza());
	}

}

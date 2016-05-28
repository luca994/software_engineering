/**
 * 
 */
package testModel;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.Regione;
import server.model.Tabellone;

/**
 * @author Luca
 *
 */
public class TabelloneTest {

	private Gioco giocoTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo", Color.blue);
		Giocatore g2 = new Giocatore("paolo", Color.black);
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#Tabellone(java.lang.String, server.model.Gioco)}
	 * .
	 */
	@Test
	public void testTabelloneConMappaCollegamenti0() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester);
		assertNotNull(tabelloneTester);
		assertNotNull(tabelloneTester.getConsiglieriDisponibili());
		assertNotNull(tabelloneTester.getRegioni());
		assertNotNull(tabelloneTester.getPercorsoNobilta());
		assertNotNull(tabelloneTester.getPercorsoRicchezza());
		assertNotNull(tabelloneTester.getPercorsoVittoria());
		assertNotNull(tabelloneTester.getTessereBonusRegione());
		assertNotNull(tabelloneTester.getRe());
		for (Regione reg : tabelloneTester.getRegioni())
			for (Citta cit : reg.getCitta()) {
				assertNotNull(cit);
				for (Citta citt : cit.getCittàVicina())
					assertNotNull(citt);
			}
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testCercaCittaNull() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester);
		tabelloneTester.cercaCitta(null);
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCercaCittaInesistente() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester);
		tabelloneTester.cercaCitta("pippo");
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCercaCittaValida() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester);
		Citta cittaTester = new Citta("test", tabelloneTester.getRegioni().get(0));
		tabelloneTester.getRegioni().get(0).getCitta().add(cittaTester);
		assertEquals(cittaTester, tabelloneTester.cercaCitta("test"));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String, server.model.Regione)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCercaCittaConRegioneNull() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester);
		Citta cittaTester = new Citta("test", tabelloneTester.getRegioni().get(0));
		tabelloneTester.getRegioni().get(0).getCitta().add(cittaTester);
		tabelloneTester.cercaCitta("test", null);
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String, server.model.Regione)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCercaCittaNullConRegione() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester);
		tabelloneTester.cercaCitta(null, tabelloneTester.getRegioni().get(0));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String, server.model.Regione)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCercaCittaConRegioneNonValida() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester);
		Citta cittaTester = new Citta("test", tabelloneTester.getRegioni().get(0));
		tabelloneTester.getRegioni().get(0).getCitta().add(cittaTester);
		tabelloneTester.cercaCitta("test", tabelloneTester.getRegioni().get(1));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String, server.model.Regione)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCercaCittaConRegioneValida() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester);
		Citta cittaTester = new Citta("test", tabelloneTester.getRegioni().get(0));
		tabelloneTester.getRegioni().get(0).getCitta().add(cittaTester);
		assertEquals(cittaTester, tabelloneTester.cercaCitta("test", tabelloneTester.getRegioni().get(0)));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#verificaEmporioColoreBonus(server.model.Giocatore, server.model.Citta)}
	 * .
	 */
	@Test
	public void testVerificaEmporioColoreBonus() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#verificaEmporioRegioneBonus(server.model.Giocatore, server.model.Citta)}
	 * .
	 */
	@Test
	public void testVerificaEmporioRegioneBonus() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#prendiTesseraBonus(server.model.Giocatore, server.model.Citta)}
	 * .
	 */
	@Test
	public void testPrendiTesseraBonus() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#getRegioneDaNome(java.lang.String)}.
	 */
	@Test
	public void testGetRegioneDaNome() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link server.model.Tabellone#generaGrafo()}.
	 */
	@Test
	public void testGeneraGrafo() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link server.model.Tabellone#getRegioni()}.
	 */
	@Test
	public void testGetRegioni() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link server.model.Tabellone#getTessereBonusRegione()}.
	 */
	@Test
	public void testGetTessereBonusRegione() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#getConsiglieriDisponibili()}.
	 */
	@Test
	public void testGetConsiglieriDisponibili() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link server.model.Tabellone#getPercorsoNobilta()}.
	 */
	@Test
	public void testGetPercorsoNobilta() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link server.model.Tabellone#getPercorsoRicchezza()}.
	 */
	@Test
	public void testGetPercorsoRicchezza() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link server.model.Tabellone#getPercorsoVittoria()}.
	 */
	@Test
	public void testGetPercorsoVittoria() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link server.model.Tabellone#getRe()}.
	 */
	@Test
	public void testGetRe() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link server.model.Tabellone#setRe(server.model.Re)}.
	 */
	@Test
	public void testSetRe() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link server.model.Tabellone#getGioco()}.
	 */
	@Test
	public void testGetGioco() {
		fail("Not yet implemented"); // TODO
	}

}

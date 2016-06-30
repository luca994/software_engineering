/**
 * 
 */
package testModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.Tabellone;
import server.model.componenti.Citta;
import server.model.componenti.Regione;

/**
 * @author Luca
 *
 */
public class TabelloneTest {

	private Gioco giocoTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo");
		Giocatore g2 = new Giocatore("paolo");
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
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
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
				for (Citta citt : cit.getCittaVicina())
					assertNotNull(citt);
			}
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#Tabellone(java.lang.String, server.model.Gioco)}
	 * .
	 */
	@Test
	public void testTabelloneConInputGiocoNull() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", null,"0");
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
				for (Citta citt : cit.getCittaVicina())
					assertNotNull(citt);
			}
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#Tabellone(java.lang.String, server.model.Gioco)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testTabelloneConInputPercorsoEGiocoNull() {
		Tabellone tabelloneTester = new Tabellone(null, null,null);
		assertNotNull(tabelloneTester);
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testCercaCittaNull() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester,"0");
		tabelloneTester.cercaCitta(null);
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String)}.
	 */
	@Test
	public void testCercaCittaInesistente() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		assertEquals(tabelloneTester.cercaCitta("pippo"),null);
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String)}.
	 */
	@Test
	public void testCercaCittaValida() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		Citta cittaTester = new Citta("test", tabelloneTester.getRegioni().get(0));
		tabelloneTester.getRegioni().get(0).getCitta().add(cittaTester);
		assertEquals(cittaTester, tabelloneTester.cercaCitta("tEsT"));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String, server.model.componenti.Regione)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCercaCittaConRegioneNull() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester,"0");
		Citta cittaTester = new Citta("test", tabelloneTester.getRegioni().get(0));
		tabelloneTester.getRegioni().get(0).getCitta().add(cittaTester);
		tabelloneTester.cercaCitta("test", null);
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String, server.model.componenti.Regione)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCercaCittaNullConRegione() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester,"0");
		tabelloneTester.cercaCitta(null, tabelloneTester.getRegioni().get(0));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String, server.model.componenti.Regione)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCercaCittaConRegioneNonValida() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		Citta cittaTester = new Citta("test", tabelloneTester.getRegioni().get(0));
		tabelloneTester.getRegioni().get(0).getCitta().add(cittaTester);
		tabelloneTester.cercaCitta("test", tabelloneTester.getRegioni().get(1));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#cercaCitta(java.lang.String, server.model.componenti.Regione)}
	 * .
	 */
	@Test
	public void testCercaCittaConRegioneValida() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		Citta cittaTester = new Citta("test", tabelloneTester.getRegioni().get(0));
		tabelloneTester.getRegioni().get(0).getCitta().add(cittaTester);
		assertEquals(cittaTester, tabelloneTester.cercaCitta("test", tabelloneTester.getRegioni().get(0)));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#verificaEmporioColoreBonus(server.model.Giocatore, server.model.componenti.Citta)}
	 * .
	 */
	@Test
	public void testVerificaEmporioColoreBonusFalso() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		tabelloneTester.cercaCitta("Arkon").getEmpori().add(giocoTester.getGiocatori().get(0));
		assertFalse(tabelloneTester.verificaEmporioColoreBonus(giocoTester.getGiocatori().get(0),
				tabelloneTester.cercaCitta("Arkon")));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#verificaEmporioColoreBonus(server.model.Giocatore, server.model.componenti.Citta)}
	 * .
	 */
	@Test
	public void testVerificaEmporioColoreBonusVero() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		tabelloneTester.cercaCitta("Arkon").getEmpori().add(giocoTester.getGiocatori().get(0));
		tabelloneTester.cercaCitta("Merkatim").getEmpori().add(giocoTester.getGiocatori().get(0));
		assertTrue(tabelloneTester.verificaEmporioColoreBonus(giocoTester.getGiocatori().get(0),
				tabelloneTester.cercaCitta("Arkon")));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#verificaEmporioRegioneBonus(server.model.Giocatore, server.model.componenti.Citta)}
	 * .
	 */
	@Test
	public void testVerificaEmporioRegioneBonusVero() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		tabelloneTester.cercaCitta("arkon").getEmpori().add(giocoTester.getGiocatori().get(0));
		tabelloneTester.cercaCitta("burgen").getEmpori().add(giocoTester.getGiocatori().get(0));
		tabelloneTester.cercaCitta("castrum").getEmpori().add(giocoTester.getGiocatori().get(0));
		tabelloneTester.cercaCitta("dortid").getEmpori().add(giocoTester.getGiocatori().get(0));
		tabelloneTester.cercaCitta("esti").getEmpori().add(giocoTester.getGiocatori().get(0));
		assertTrue(tabelloneTester.verificaEmporioRegioneBonus(giocoTester.getGiocatori().get(0),
				tabelloneTester.cercaCitta("esti")));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#verificaEmporioRegioneBonus(server.model.Giocatore, server.model.componenti.Citta)}
	 * .
	 */
	@Test
	public void testVerificaEmporioRegioneBonusFalso() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		tabelloneTester.cercaCitta("arkon").getEmpori().add(giocoTester.getGiocatori().get(0));
		assertFalse(tabelloneTester.verificaEmporioRegioneBonus(giocoTester.getGiocatori().get(0),
				tabelloneTester.cercaCitta("arkon")));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#verificaEmporioRegioneBonus(server.model.Giocatore, server.model.componenti.Citta)}
	 * .
	 */
	@Test
	public void testVerificaEmporioRegioneBonusNullGiocatore() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		tabelloneTester.cercaCitta("arkon").getEmpori().add(giocoTester.getGiocatori().get(0));
		assertFalse(tabelloneTester.verificaEmporioRegioneBonus(null, tabelloneTester.cercaCitta("arkon")));
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#verificaEmporioRegioneBonus(server.model.Giocatore, server.model.componenti.Citta)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testVerificaEmporioRegioneBonusNullRegione() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester,"0");
		tabelloneTester.cercaCitta("arkon").getEmpori().add(giocoTester.getGiocatori().get(0));
		tabelloneTester.verificaEmporioRegioneBonus(null, null);
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#prendiTesseraBonus(server.model.Giocatore, server.model.Citta)}
	 * .
	 */
	
	/**
	 * Test method for
	 * {@link server.model.Tabellone#getRegioneDaNome(java.lang.String)}.
	 */
	@Test
	public void testGetRegioneInesistenteDaNome() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		assertEquals(tabelloneTester.getRegioneDaNome("test"),null);
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#getRegioneDaNome(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetRegioneNullDaNome() {
		Tabellone tabelloneTester = new Tabellone("src/main/resources/mappacollegamenti0.xml", giocoTester,"0");
		tabelloneTester.getRegioneDaNome(null);
	}

	/**
	 * Test method for
	 * {@link server.model.Tabellone#getRegioneDaNome(java.lang.String)}.
	 */
	@Test
	public void testGetRegioneValidaDaNome() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		assertEquals(tabelloneTester.getRegioni().get(0), tabelloneTester.getRegioneDaNome("mare"));
	}

	/**
	 * Test method for {@link server.model.Tabellone#generaGrafo()}.
	 */
	@Test
	public void testGeneraGrafo() {
		Tabellone tabelloneTester = new Tabellone("mappacollegamenti0.xml", giocoTester,"0");
		UndirectedGraph<Citta, DefaultEdge> mappaTest = tabelloneTester.generaGrafo();
		assertNotNull(mappaTest);
		for (Regione r : tabelloneTester.getRegioni())
			for (Citta c : r.getCitta())
				assertTrue(mappaTest.containsVertex(c));
		assertTrue(mappaTest.containsEdge(tabelloneTester.cercaCitta("burgen"), tabelloneTester.cercaCitta("esti")));
		assertTrue(mappaTest.containsEdge(tabelloneTester.cercaCitta("esti"), tabelloneTester.cercaCitta("burgen")));
		assertTrue(mappaTest.containsEdge(tabelloneTester.cercaCitta("kultos"), tabelloneTester.cercaCitta("indur")));
		assertTrue(mappaTest.containsEdge(tabelloneTester.cercaCitta("indur"), tabelloneTester.cercaCitta("kultos")));
		assertFalse(mappaTest.containsEdge(tabelloneTester.cercaCitta("osium"), tabelloneTester.cercaCitta("juvelar")));
	}

}

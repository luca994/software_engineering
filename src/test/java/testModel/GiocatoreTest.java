/**
 * 
 */
package testModel;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.stato.giocatore.AttesaTurno;

/**
 * @author Luca
 *
 */
public class GiocatoreTest {

	private Giocatore giocatoreTester;
	
	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo",Color.black );
		giocatoreTester = g1;
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#Giocatore(java.lang.String, java.awt.Color)}
	 * .
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGiocatoreNomeNull() {
		Giocatore g1 = new Giocatore(null, Color.black);
		assertNotNull(g1);
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#Giocatore(java.lang.String, java.awt.Color)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGiocatoreColoreNull() {
		Giocatore g1 = new Giocatore("pippo", null);
		assertNotNull(g1);
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#Giocatore(java.lang.String, java.awt.Color)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGiocatoreParametriNull() {
		Giocatore g1 = new Giocatore(null, null);
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
		assertEquals(Color.black, giocatoreTester.getColore());
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
	 * {@link server.model.Giocatore#spostaTesseraValidaInTesseraUsata(server.model.TesseraCostruzione)}
	 * .
	 */
	@Test
	public void testSpostaTesseraValidaInTesseraUsata() {
		fail("Not yet implemented");
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
	public void testGetAssistenti() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#setAssistenti(java.util.List)}.
	 */
	@Test
	public void testSetAssistenti() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Giocatore#getCartePolitica()}.
	 */
	@Test
	public void testGetCartePolitica() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#setCartePolitica(java.util.List)}.
	 */
	@Test
	public void testSetCartePolitica() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Giocatore#getColore()}.
	 */
	@Test
	public void testGetColore() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Giocatore#setColore(java.awt.Color)}.
	 */
	@Test
	public void testSetColore() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Giocatore#getTessereUsate()}.
	 */
	@Test
	public void testGetTessereUsate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#setTessereUsate(java.util.List)}.
	 */
	@Test
	public void testSetTessereUsate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Giocatore#getTessereValide()}.
	 */
	@Test
	public void testGetTessereValide() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#setTessereValide(java.util.List)}.
	 */
	@Test
	public void testSetTessereValide() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Giocatore#getEmporiRimasti()}.
	 */
	@Test
	public void testGetEmporiRimasti() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Giocatore#setEmporiRimasti(int)}.
	 */
	@Test
	public void testSetEmporiRimasti() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Giocatore#getStatoGiocatore()}.
	 */
	@Test
	public void testGetStatoGiocatore() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link server.model.Giocatore#setStatoGiocatore(server.model.stato.giocatore.StatoGiocatore)}
	 * .
	 */
	@Test
	public void testSetStatoGiocatore() {
		fail("Not yet implemented");
	}

}

/**
 * 
 */
package testModel;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.gioco.Attesa;

/**
 * @author Luca
 *
 */
public class GiocoTest {

	private Gioco giocoTester;
	
	@Before
	public void setGiocoTester(){
		Gioco g=new Gioco();
		giocoTester=g;
	}
	
	/**
	 * Test method for {@link server.model.Gioco#Gioco()}.
	 */
	@Test
	public void testGioco() {
		Gioco gioco = new Gioco();
		assertNotNull(gioco);
		assertNotNull(gioco.getGiocatori());
		assertTrue(gioco.getStato() instanceof Attesa);
		assertNull(gioco.getTabellone());
	}

	

	/**
	 * Test method for {@link server.model.Gioco#inizializzaPartita()}.
	 */
	@Test
	public void testInizializzaPartita2Giocatori() {
		giocoTester.getGiocatori().add(new Giocatore("pippo",Color.blue));
		giocoTester.getGiocatori().add(new Giocatore("paolo",Color.black));
		giocoTester.inizializzaPartita();
	}
	
	/**
	 * Test method for {@link server.model.Gioco#inizializzaPartita()}.
	 */
	@Test
	public void testInizializzaPartita3Giocatori() {
		giocoTester.getGiocatori().add(new Giocatore("pippo",Color.blue));
		giocoTester.getGiocatori().add(new Giocatore("paolo",Color.black));
		giocoTester.getGiocatori().add(new Giocatore("pluto", Color.yellow));
		giocoTester.inizializzaPartita();
	}

	/**
	 * Test method for {@link server.model.Gioco#eseguiPartita()}.
	 */
	@Test
	public void testEseguiPartita() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Gioco#getTabellone()}.
	 */
	@Test
	public void testGetTabellone() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Gioco#getGiocatori()}.
	 */
	@Test
	public void testGetGiocatori() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Gioco#getStato()}.
	 */
	@Test
	public void testGetStato() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link server.model.Gioco#setStato(server.model.stato.gioco.StatoGioco)}.
	 */
	@Test
	public void testSetStato() {
		fail("Not yet implemented");
	}

}

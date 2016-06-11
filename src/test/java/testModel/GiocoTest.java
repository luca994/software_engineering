/**
 * 
 */
package testModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.stato.gioco.Attesa;
import server.model.stato.gioco.FaseTurnoMercatoCompraVendita;
import server.model.stato.gioco.FaseTurnoSemplice;
import server.model.stato.gioco.StatoGioco;
import server.model.stato.gioco.Terminato;

/**
 * @author Luca
 *
 */
public class GiocoTest {

	private Gioco giocoTester;

	@Before
	public void setGiocoTester() {
		Gioco g = new Gioco();
		giocoTester = g;
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
	@Test(expected = IllegalArgumentException.class)
	public void testInizializzaPartita0Giocatori() {
		giocoTester.inizializzaPartita("0");
	}

	/**
	 * Test method for {@link server.model.Gioco#inizializzaPartita()}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInizializzaPartita1Giocatore() {
		giocoTester.getGiocatori().add(new Giocatore("pippo"));
		giocoTester.inizializzaPartita("0");
	}

	/**
	 * Test method for {@link server.model.Gioco#inizializzaPartita()}.
	 */
	@Test
	public void testInizializzaPartita2Giocatori() {
		int i = 0;
		giocoTester.getGiocatori().add(new Giocatore("pippo"));
		giocoTester.getGiocatori().add(new Giocatore("paolo"));
		giocoTester.inizializzaPartita("0");
		for (Giocatore giocat : giocoTester.getGiocatori()) {
			if(giocat.getNome()!="dummy"){
			assertEquals(10, giocat.getEmporiRimasti());
			assertTrue(giocoTester.getTabellone().getPercorsoRicchezza().getCaselle().get(10 + i).getGiocatori()
					.contains(giocat));
			assertTrue(giocoTester.getTabellone().getPercorsoNobilta().getCaselle().get(0).getGiocatori()
					.contains(giocat));
			assertTrue(giocoTester.getTabellone().getPercorsoVittoria().getCaselle().get(0).getGiocatori()
					.contains(giocat));
			assertEquals(i + 1, giocat.getAssistenti().size());
			i++;
		}}
		assertNotNull(giocoTester.getTabellone());
		assertEquals(giocoTester.getTabellone(),giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getTabellone());
	}

	/**
	 * Test method for {@link server.model.Gioco#inizializzaPartita()}.
	 */
	@Test
	public void testInizializzaPartita3Giocatori() {
		int i = 0;
		giocoTester.getGiocatori().add(new Giocatore("pippo"));
		giocoTester.getGiocatori().add(new Giocatore("paolo"));
		giocoTester.getGiocatori().add(new Giocatore("pluto"));
		giocoTester.inizializzaPartita("0");
		for (Giocatore giocat : giocoTester.getGiocatori()) {
			assertEquals(10, giocat.getEmporiRimasti());
			assertTrue(giocoTester.getTabellone().getPercorsoRicchezza().getCaselle().get(10 + i).getGiocatori()
					.contains(giocat));
			assertTrue(giocoTester.getTabellone().getPercorsoNobilta().getCaselle().get(0).getGiocatori()
					.contains(giocat));
			assertTrue(giocoTester.getTabellone().getPercorsoVittoria().getCaselle().get(0).getGiocatori()
					.contains(giocat));
			assertEquals(i + 1, giocat.getAssistenti().size());
			i++;
		}
	}

	/**
	 * Test method for {@link server.model.Gioco#inizializzaPartita()}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testInizializzaPartita2GiocatoriFaseTurnoSemplice() {
		giocoTester.getGiocatori().add(new Giocatore("pippo"));
		giocoTester.getGiocatori().add(new Giocatore("paolo"));
		giocoTester.setStato(new FaseTurnoSemplice(giocoTester));
		giocoTester.inizializzaPartita("0");
	}



	/**
	 * Test method for {@link server.model.Gioco#inizializzaPartita()}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testInizializzaPartita2GiocatoriFaseTurnoMercatoCompraVendita() {
		giocoTester.getGiocatori().add(new Giocatore("pippo"));
		giocoTester.getGiocatori().add(new Giocatore("paolo"));
		giocoTester.setStato(new FaseTurnoMercatoCompraVendita(giocoTester, null));
		giocoTester.inizializzaPartita("0");
	}

	/**
	 * Test method for {@link server.model.Gioco#eseguiPartita()}.
	 */
	@Test(expected=IllegalStateException.class)
	public void testEseguiPartita2GiocatoriGiaInEsecuzione() {
		giocoTester.getGiocatori().add(new Giocatore("pippo"));
		giocoTester.getGiocatori().add(new Giocatore("paolo"));
		giocoTester.inizializzaPartita("0");
		giocoTester.setStato(new FaseTurnoSemplice(giocoTester));
		giocoTester.eseguiPartita();
	}
	/**
	 * Test method for {@link server.model.Gioco#eseguiPartita()}.
	 */
	@Test(expected=IllegalStateException.class)
	public void testEseguiPartita2GiocatoriConStatoInizialeTerminata() {
		giocoTester.getGiocatori().add(new Giocatore("pippo"));
		giocoTester.getGiocatori().add(new Giocatore("paolo"));
		giocoTester.inizializzaPartita("0");
		giocoTester.setStato(new Terminato(giocoTester));
		giocoTester.eseguiPartita();
	}

	/**
	 * Test method for {@link server.model.Gioco#getTabellone()}.
	 */
	@Test
	public void testGetNullTabellone() {
		assertTrue(giocoTester.getTabellone()==null);
	}
	
	/**
	 * Test method for {@link server.model.Gioco#getTabellone()}.
	 */
	@Test
	public void testGetNotNullTabellone() {
		giocoTester.getGiocatori().add(new Giocatore("pippo"));
		giocoTester.getGiocatori().add(new Giocatore("paolo"));
		giocoTester.inizializzaPartita("0");
		assertTrue(giocoTester.getTabellone()!=null);
	}

	/**
	 * Test method for {@link server.model.Gioco#getGiocatori()}.
	 */
	@Test
	public void testGetGiocatori() {
		Giocatore g1=new Giocatore("pippo");
		giocoTester.getGiocatori().add(g1);
		assertTrue(giocoTester.getGiocatori().contains(g1));
	}

	/**
	 * Test method for {@link server.model.Gioco#getStato()}.
	 */
	@Test
	public void testGetStato() {
		assertTrue(giocoTester.getStato() instanceof Attesa);
	}

	/**
	 * Test method for
	 * {@link server.model.Gioco#setStato(server.model.stato.gioco.StatoGioco)}.
	 */
	@Test
	public void testSetStato() {
		giocoTester.getGiocatori().add(new Giocatore("pippo"));
		giocoTester.getGiocatori().add(new Giocatore("paolo"));
		giocoTester.inizializzaPartita("0");
		StatoGioco statoDaSettare = new FaseTurnoSemplice(giocoTester);
		giocoTester.setStato(statoDaSettare);
		assertTrue(giocoTester.getStato()==statoDaSettare);
	}

}

/**
 * 
 */
package testBonus;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.bonus.Bonus;
import server.model.bonus.BonusPuntoVittoria;

/**
 * @author Massimiliano Ventura
 *
 */
public class TestBonusPuntoVittoria {

	Gioco giocoTester;
	Bonus bonusPuntoVittoria;
	Giocatore g1;
	Giocatore g2;

	@Before
	public void setUp() {
		giocoTester = new Gioco();
		g1 = new Giocatore("squinzio");
		g2 = new Giocatore("radobaldo");
		giocoTester.getGiocatori().add(g1);
		giocoTester.getGiocatori().add(g2);
		giocoTester.inizializzaPartita("0");
	}

	@Test(expected = IllegalStateException.class)
	public void testCostruzioneBonusPuntoVittoriaConParametroPassiNegativo() {
		bonusPuntoVittoria = new BonusPuntoVittoria(giocoTester.getTabellone().getPercorsoVittoria(), -12);
	}

	@Test(expected = IllegalStateException.class)
	public void testCostruzioneBonusPuntoVittoriaConPercorsoRicchezzaNullo() {
		bonusPuntoVittoria = new BonusPuntoVittoria(null, 3);
	}

	@Test(expected = NullPointerException.class)
	public void testAzioneBonusConGiocatoreNullo() {
		bonusPuntoVittoria = new BonusPuntoVittoria(giocoTester.getTabellone().getPercorsoVittoria(), 3);
		bonusPuntoVittoria.azioneBonus(null);
	}

	@Test
	public void testAzioneBonusConValoriInRange() {
		int posizioneIniziale = giocoTester.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1);
		int incremento = 6;
		bonusPuntoVittoria = new BonusPuntoVittoria(giocoTester.getTabellone().getPercorsoVittoria(), incremento);
		bonusPuntoVittoria.azioneBonus(g1);
		assertEquals(posizioneIniziale + incremento,
				giocoTester.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
	}

	@Test
	public void testAzioneBonusConValoriFuoriRangePositivi() {
		int incremento = 936;
		bonusPuntoVittoria = new BonusPuntoVittoria(giocoTester.getTabellone().getPercorsoVittoria(), incremento);
		bonusPuntoVittoria.azioneBonus(g1);
		assertEquals(giocoTester.getTabellone().getPercorsoVittoria().getCaselle().size() - 1,
				giocoTester.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
	}

}

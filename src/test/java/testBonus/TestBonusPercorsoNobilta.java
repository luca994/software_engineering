/**
 * 
 */
package testBonus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.bonus.Bonus;
import server.model.bonus.BonusPercorsoNobilta;

/**
 * @author Massimiliano Ventura
 *
 */
public class TestBonusPercorsoNobilta {

	Gioco giocoTester;
	Bonus bonusNobilta;
	Giocatore g1;
	Giocatore g2;
	
	@Before
	public void setUp() {
		giocoTester = new Gioco();
		g1= new Giocatore("squinzio");
		g2= new Giocatore("radobaldo");
		giocoTester.getGiocatori().add(g1);
		giocoTester.getGiocatori().add(g2);
		giocoTester.inizializzaPartita();
	}

	@Test(expected = IllegalStateException.class)
	public void testCostruzioneBonusPercorsoNobiltaConParametroPassiNegativo(){
		bonusNobilta=new BonusPercorsoNobilta(giocoTester.getTabellone().getPercorsoNobilta(), -12);		
	}

	@Test(expected = IllegalStateException.class)
	public void testCostruzioneBonusBonusPercorsoNobiltaConPercorsoRicchezzaNullo() {
		bonusNobilta=new BonusPercorsoNobilta(null, 3);		
	}
	
	@Test(expected = NullPointerException.class)
	public void testAzioneBonusConGiocatoreNullo() {
		bonusNobilta=new BonusPercorsoNobilta(giocoTester.getTabellone().getPercorsoNobilta(), 3);
		bonusNobilta.azioneBonus(null);
	}
	@Test
	public void testAzioneBonusConValoriInRange() {
		int posizioneIniziale=giocoTester.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1);
		int incremento=6;
		bonusNobilta=new BonusPercorsoNobilta(giocoTester.getTabellone().getPercorsoNobilta(), incremento);
		bonusNobilta.azioneBonus(g1);
		assertEquals(posizioneIniziale+incremento,giocoTester.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1));
	}
	@Test
	public void testAzioneBonusConValoriFuoriRangePositivi() {
		int incremento=93;
		bonusNobilta=new BonusPercorsoNobilta(giocoTester.getTabellone().getPercorsoNobilta(), incremento);
		bonusNobilta.azioneBonus(g1);
		assertEquals(giocoTester.getTabellone().getPercorsoNobilta().getCaselle().size()-1,giocoTester.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1));
	}
}

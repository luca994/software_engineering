/**
 * 
 */
package testBonus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.Tabellone;
import server.model.bonus.Bonus;
import server.model.bonus.BonusMoneta;
import server.model.percorso.Percorso;

/**
 * @author Massimiliano Ventura
 *
 */
public class TestBonusMoneta {
	Gioco giocoTester;
	Bonus bonusMoneta;
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
	public void testCostruzioneBonusMonetaConParametroPassiNegativo(){
		bonusMoneta=new BonusMoneta(giocoTester.getTabellone().getPercorsoRicchezza(), -12);		
	}

	@Test(expected = IllegalStateException.class)
	public void testCostruzioneBonusMonetaConPercorsoRicchezzaNullo() {
		bonusMoneta=new BonusMoneta(null, 3);		
	}
	
	@Test(expected = NullPointerException.class)
	public void testAzioneBonusConGiocatoreNullo() {
		bonusMoneta=new BonusMoneta(giocoTester.getTabellone().getPercorsoRicchezza(), 3);
		bonusMoneta.azioneBonus(null);
	}
	@Test
	public void testAzioneBonusConValoriInRange() {
		int posizioneIniziale=giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1);
		int incremento=6;
		bonusMoneta=new BonusMoneta(giocoTester.getTabellone().getPercorsoRicchezza(), incremento);
		bonusMoneta.azioneBonus(g1);
		assertEquals(posizioneIniziale+incremento,giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
	}
	@Test
	public void testAzioneBonusConValoriFuoriRangePositivi() {
		int incremento=93;
		bonusMoneta=new BonusMoneta(giocoTester.getTabellone().getPercorsoRicchezza(), incremento);
		bonusMoneta.azioneBonus(g1);
		assertEquals(giocoTester.getTabellone().getPercorsoRicchezza().getCaselle().size()-1,giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
	}
	


}

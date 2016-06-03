/**
 * 
 */
package testBonus;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.Giocatore;
import server.model.bonus.Bonus;
import server.model.bonus.BonusCreator;

/**
 * @author Massimiliano Ventura
 *
 */
public class TestBonusCartaPolitica {
	
	private Giocatore g1 = new Giocatore("pippo");
	private BonusCreator creaBonusTester=new BonusCreator(null);
	private Bonus BonusTester;
	
	@Test(expected = IllegalStateException.class)
	public void testCreazioneBonusConParametroNegativo() {
		BonusTester=creaBonusTester.creaBonus("BonusCartaPolitica", -1, null);
	}
	@Test
	public void testCreazioneBonusConParametroPositivo() {
		BonusTester=creaBonusTester.creaBonus("BonusCartaPolitica", 66, null);
	}

	@Test(expected = NullPointerException.class)
	public void testAzioneBonusConGiocatoreNullo() {
		BonusTester=creaBonusTester.creaBonus("BonusCartaPolitica", 66, null);
		BonusTester.azioneBonus(null);
	}
	@Test
	public void testAzioneBonus(){
		
		for(int numeroCartePolitica=0; numeroCartePolitica<10;numeroCartePolitica++){
			BonusTester=creaBonusTester.creaBonus("BonusCartaPolitica", numeroCartePolitica, null);
			BonusTester.azioneBonus(g1);
			assertEquals(numeroCartePolitica,g1.getCartePolitica().size());
			g1.getCartePolitica().clear();
		}
	}
}

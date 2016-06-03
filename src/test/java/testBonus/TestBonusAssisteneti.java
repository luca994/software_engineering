/**
 * 
 */
package testBonus;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import server.model.Giocatore;
import server.model.bonus.Bonus;
import server.model.bonus.BonusCreator;

/**
 * @author Massimiliano Ventura
 *
 */
public class TestBonusAssisteneti {

	private Giocatore g1 = new Giocatore("pippo");
	private BonusCreator creaBonusTester=new BonusCreator(null);
	private Bonus BonusTester;
	 
	@Test(expected = IllegalStateException.class)
	public void testCreazioneBonusConParametroNegativo() {
		BonusTester=creaBonusTester.creaBonus("BonusAssistenti", -1, null);
	}
	@Test
	public void testCreazioneBonusConParametroPositivo() {
		BonusTester=creaBonusTester.creaBonus("BonusAssistenti", 66, null);
	}

	@Test(expected = NullPointerException.class)
	public void testAzioneBonusConGiocatoreNullo() {
		BonusTester=creaBonusTester.creaBonus("BonusAssistenti", 66, null);
		BonusTester.azioneBonus(null);
	}
	@Test
	public void testAzioneBonus(){
		
		for(int numeroAssistenti=0; numeroAssistenti<10;numeroAssistenti++){
			BonusTester=creaBonusTester.creaBonus("BonusAssistenti", numeroAssistenti, null);
			BonusTester.azioneBonus(g1);
			assertEquals(numeroAssistenti,g1.getAssistenti().size());
			g1.getAssistenti().clear();
		}
	}
}

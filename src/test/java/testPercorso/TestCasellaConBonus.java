package testPercorso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import server.model.bonus.Bonus;
import server.model.bonus.BonusAzionePrincipale;
import server.model.bonus.BonusCartaPolitica;
import server.model.percorso.Casella;
import server.model.percorso.CasellaConBonus;

public class TestCasellaConBonus {

	private Set<Bonus> bonus; 
	private Bonus b1;
	private Bonus b2;
	@Before
	public void oggettiTest(){
		bonus = new HashSet<>();
		b1 = new BonusAzionePrincipale();
		b2 = new BonusCartaPolitica(4);
		bonus.add(b1);
		bonus.add(b2);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCasellaConBonusWithNullBonus() {
		Casella casella = new CasellaConBonus(null);
		assertNotNull(casella);
	}
	
	@Test
	public void testCasellaConBonus() {
		Casella casella = new CasellaConBonus(bonus);
		assertNotNull(casella);
	}

	@Test
	public void testGetBonus() {
		CasellaConBonus casella = new CasellaConBonus(bonus);
		assertEquals(bonus, casella.getBonus());
	}

}

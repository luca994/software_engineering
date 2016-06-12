package testBonus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.bonus.BonusGettoneCitta;

public class TestBonusGettoneCitta {

	private Gioco gioco;
	
	@Before
	public void inizializzazione(){
		gioco = new Gioco();
		List<Giocatore> giocatori = new ArrayList<>();
		giocatori.add(new Giocatore("pippo"));
		giocatori.add(new Giocatore("paolo"));
		gioco.setGiocatori(giocatori);
		gioco.inizializzaPartita("0");
	}
	
	@Test
	public void testBonusGettoneCitta() {
		BonusGettoneCitta bonus = new BonusGettoneCitta(0, gioco);
		assertNotNull(bonus.getGioco());
		assertEquals(gioco, bonus.getGioco());
		assertNotNull(bonus.getCitta());
		assertEquals(0, bonus.getNumeroCitta());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testBonusGettoneCittaConNumeroCittaNegativo(){
		BonusGettoneCitta bonus = new BonusGettoneCitta(-2, gioco);
		assertNull(bonus.getGioco());
	}
	
	@Test
	public void testGetNumeroCitta() {
		BonusGettoneCitta bonus = new BonusGettoneCitta(1, gioco);
		assertEquals(1, bonus.getNumeroCitta());
	}

	@Test(expected=NullPointerException.class)
	public void testAzioneBonusConGiocatoreNull() {
		BonusGettoneCitta bonus = new BonusGettoneCitta(1, gioco);
		bonus.azioneBonus(null);
	}
	

	@Test
	public void testIsUgualeConBonusConNumeroCittaDiverso() {
		BonusGettoneCitta bonus1 = new BonusGettoneCitta(1, gioco);
		BonusGettoneCitta bonus2 = new BonusGettoneCitta(2, gioco);
		assertEquals(false, bonus1.isUguale(bonus2));
	}
	
	@Test
	public void testIsUgualeConCittaDiverse(){
		BonusGettoneCitta bonus1 = new BonusGettoneCitta(1, gioco);
		BonusGettoneCitta bonus2 = new BonusGettoneCitta(1, gioco);
		bonus1.getCitta().add(new Citta("roma", gioco.getTabellone().getRegioni().get(0)));
		bonus2.getCitta().add(new Citta("perugia", gioco.getTabellone().getRegioni().get(0)));
		assertEquals(false, bonus1.isUguale(bonus2));
	}
	
	@Test
	public void testIsUgualeConBonusUguali(){
		BonusGettoneCitta bonus1 = new BonusGettoneCitta(1, gioco);
		BonusGettoneCitta bonus2 = new BonusGettoneCitta(1, gioco);
		bonus1.getCitta().add(new Citta("roma", gioco.getTabellone().getRegioni().get(0)));
		bonus2.getCitta().add(new Citta("roma", gioco.getTabellone().getRegioni().get(0)));
		assertEquals(true, bonus1.isUguale(bonus2));
	}

	@Test
	public void testGetGioco() {
		BonusGettoneCitta bonus = new BonusGettoneCitta(0, gioco);
		assertEquals(gioco, bonus.getGioco());
	}

}

package testBonus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.bonus.BonusAssistenti;
import server.model.bonus.BonusGettoneCitta;
import server.model.componenti.Citta;

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
	
	@Test
	public void azioneBonusConParametriCorretti(){
		Giocatore giocatoreBonus = gioco.getGiocatori().get(0);
		BonusGettoneCitta bonus = new BonusGettoneCitta(1, gioco);
		Citta cittaBonus = gioco.getTabellone().cercaCitta("castrum");
		cittaBonus.getBonus().clear();
		cittaBonus.getBonus().add(new BonusAssistenti(1));
		cittaBonus.getEmpori().add(giocatoreBonus);
		bonus.getCitta().add(cittaBonus);
		int numAssistentiPrima = giocatoreBonus.getAssistenti().size();
		bonus.azioneBonus(giocatoreBonus);
		assertEquals(numAssistentiPrima+1, giocatoreBonus.getAssistenti().size());
	}
	
	@Test
	public void azioneBonusConDueCitta(){
		Giocatore giocatoreBonus = gioco.getGiocatori().get(0);
		BonusGettoneCitta bonus = new BonusGettoneCitta(2, gioco);
		Citta cittaBonus = gioco.getTabellone().cercaCitta("castrum");
		cittaBonus.getBonus().clear();
		cittaBonus.getBonus().add(new BonusAssistenti(1));
		cittaBonus.getEmpori().add(giocatoreBonus);
		bonus.getCitta().add(cittaBonus);
		Citta cittaBonus1 = gioco.getTabellone().cercaCitta("arkon");
		cittaBonus1.getBonus().clear();
		cittaBonus1.getBonus().add(new BonusAssistenti(1));
		cittaBonus1.getEmpori().add(giocatoreBonus);
		bonus.getCitta().add(cittaBonus1);
		int numAssistentiPrima = giocatoreBonus.getAssistenti().size();
		bonus.azioneBonus(giocatoreBonus);
		assertEquals(numAssistentiPrima+2, giocatoreBonus.getAssistenti().size());
	}
	
	@Test
	public void azioneBonusConCittaNull(){
		Giocatore giocatoreBonus = gioco.getGiocatori().get(0);
		BonusGettoneCitta bonus = new BonusGettoneCitta(1, gioco);
		int numAssistentiPrima = giocatoreBonus.getAssistenti().size();
		bonus.azioneBonus(giocatoreBonus);
		assertEquals(numAssistentiPrima, giocatoreBonus.getAssistenti().size());
		assertEquals(0, giocatoreBonus.getTessereValide().size());
		assertEquals(6, giocatoreBonus.getCartePolitica().size());
		assertEquals(1, giocatoreBonus.getAssistenti().size());
		assertEquals(10, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(giocatoreBonus));
		assertEquals(0, gioco.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(giocatoreBonus));
		assertEquals(0, gioco.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(giocatoreBonus));
	}

}

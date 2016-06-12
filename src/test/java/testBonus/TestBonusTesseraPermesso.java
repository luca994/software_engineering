package testBonus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.TesseraCostruzione;
import server.model.bonus.Bonus;
import server.model.bonus.BonusAzionePrincipale;
import server.model.bonus.BonusCartaPolitica;
import server.model.bonus.BonusTesseraPermesso;
import server.model.stato.giocatore.TurnoNormale;

public class TestBonusTesseraPermesso {

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
	public void testBonusTesseraPermesso() {
		BonusTesseraPermesso bonus = new BonusTesseraPermesso(gioco);
		assertNotNull(bonus.getGioco());
		assertEquals(gioco, bonus.getGioco());
		assertEquals(false, bonus.isTesseraCorretta());
	}

	@Test
	public void testGetSetTessera() {
		BonusTesseraPermesso bonus = new BonusTesseraPermesso(gioco);
		bonus.setTessera(gioco.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		assertEquals(gioco.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0), bonus.getTessera());
	}

	@Test(expected=NullPointerException.class)
	public void testAzioneBonusConGiocatoreNull() {
		BonusTesseraPermesso bonus = new BonusTesseraPermesso(gioco);
		bonus.azioneBonus(null);
	}
	
	@Test
	public void testAzioneBonusConTesseraNull(){
		BonusTesseraPermesso bonus = new BonusTesseraPermesso(gioco);
		Giocatore g1 = gioco.getGiocatori().get(0);
		g1.getStatoGiocatore().prossimoStato();
		bonus.setTesseraCorretta(true);
		bonus.azioneBonus(g1);
		assertEquals(0, g1.getTessereValide().size());
		assertEquals(6, g1.getCartePolitica().size());
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(10, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(0, gioco.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
		assertEquals(0, gioco.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1));
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniRapideEseguibili());
		assertEquals(false, bonus.isTesseraCorretta());
		assertEquals(null, bonus.getTessera());
	}

	@Test
	public void testAzioneBonusConTessera(){
		BonusTesseraPermesso bonus = new BonusTesseraPermesso(gioco);
		bonus.setTesseraCorretta(true);
		TesseraCostruzione tessera = gioco.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0);
		tessera.getBonus().clear();
		tessera.getBonus().add(new BonusCartaPolitica(1));
		bonus.setTessera(tessera);
		Giocatore g1 = gioco.getGiocatori().get(0);
		g1.getStatoGiocatore().prossimoStato();
		bonus.azioneBonus(g1);
		assertEquals(1, g1.getTessereValide().size());
		assertEquals(7, g1.getCartePolitica().size());
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(10, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(0, gioco.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
		assertEquals(0, gioco.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1));
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniRapideEseguibili());
		assertEquals(2, gioco.getTabellone().getRegioni().get(0).getTessereCostruzione().size());
		assertEquals(false, bonus.isTesseraCorretta());
		assertEquals(null, bonus.getTessera());
	}
	
	@Test
	public void testIsUgualeConBonusDiversi() {
		BonusTesseraPermesso bonus1 = new BonusTesseraPermesso(gioco);
		BonusAzionePrincipale bonus2 = new BonusAzionePrincipale();
		assertEquals(false, bonus1.isUguale(bonus2));
	}
	
	@Test
	public void testIsUgualeConTessereDiverse(){
		BonusTesseraPermesso bonus1 = new BonusTesseraPermesso(gioco);
		BonusTesseraPermesso bonus2 = new BonusTesseraPermesso(gioco);
		bonus1.setTessera(gioco.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		bonus2.setTessera(gioco.getTabellone().getRegioni().get(1).getTessereCostruzione().get(1));
		assertEquals(false, bonus1.isUguale(bonus2));
	}
	
	@Test
	public void testIsUgualeConBonusUguali(){
		BonusTesseraPermesso bonus1 = new BonusTesseraPermesso(gioco);
		BonusTesseraPermesso bonus2 = new BonusTesseraPermesso(gioco);
		Bonus bonusTessera1 = new BonusAzionePrincipale();
		Bonus bonusTessera2 = new BonusAzionePrincipale();
		Set<Bonus> setBonusTessera1 = new HashSet<>();
		setBonusTessera1.add(bonusTessera1);
		Set<Bonus> setBonusTessera2 = new HashSet<>();
		setBonusTessera2.add(bonusTessera2);
		Citta citta1 = new Citta("roma", gioco.getTabellone().getRegioni().get(0));
		Set<Citta> setCitta1 = new HashSet<>();
		setCitta1.add(citta1);
		Citta citta2 = new Citta("roma", gioco.getTabellone().getRegioni().get(0));
		Set<Citta> setCitta2 = new HashSet<>();
		setCitta2.add(citta2);
		bonus1.setTessera(new TesseraCostruzione(setBonusTessera1, setCitta1, gioco.getTabellone().getRegioni().get(1)));
		bonus2.setTessera(new TesseraCostruzione(setBonusTessera2, setCitta2, gioco.getTabellone().getRegioni().get(1)));
		assertEquals(true, bonus1.isUguale(bonus2));
	}

}

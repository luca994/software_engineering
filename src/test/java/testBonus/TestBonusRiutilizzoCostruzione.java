package testBonus;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.bonus.Bonus;
import server.model.bonus.BonusAzionePrincipale;
import server.model.bonus.BonusCartaPolitica;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.componenti.Citta;
import server.model.componenti.TesseraCostruzione;
import server.model.stato.giocatore.TurnoNormale;

public class TestBonusRiutilizzoCostruzione {

	private Gioco gioco;
	private Giocatore g1;
	
	@Before
	public void inizializzazione(){
		gioco = new Gioco();
		List<Giocatore> giocatori = new ArrayList<>();
		giocatori.add(new Giocatore("pippo"));
		giocatori.add(new Giocatore("paolo"));
		g1=giocatori.get(0);
		gioco.setGiocatori(giocatori);
		gioco.inizializzaPartita("0");
	}
	
	@Test
	public void testBonusRiutilizzoCostruzione() {
		BonusRiutilizzoCostruzione bonus = new BonusRiutilizzoCostruzione(gioco);
		assertEquals(gioco, bonus.getGioco());
		assertEquals(false, bonus.isTesseraCostruzioneCorretta());
	}

	@Test(expected=NullPointerException.class)
	public void testAzioneBonusConGiocatoreNull() {
		BonusRiutilizzoCostruzione bonus = new BonusRiutilizzoCostruzione(gioco);
		bonus.azioneBonus(null);
	}

	@Test
	public void testAzioneBonusConTesseraNull(){
		BonusRiutilizzoCostruzione bonus = new BonusRiutilizzoCostruzione(gioco);
		g1.getStatoGiocatore().prossimoStato();
		bonus.setTesseraCostruzioneCorretta(true);
		bonus.azioneBonus(g1);
		assertEquals(0, g1.getTessereValide().size());
		assertEquals(6, g1.getCartePolitica().size());
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(10, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(0, gioco.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
		assertEquals(0, gioco.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1));
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniRapideEseguibili());
		assertEquals(false, bonus.isTesseraCostruzioneCorretta());
		assertEquals(null, bonus.getTessera());
	}
	@Test
	public void testAzioneBonusConTessera(){
		BonusRiutilizzoCostruzione bonus = new BonusRiutilizzoCostruzione(gioco);
		g1.getStatoGiocatore().prossimoStato();
		bonus.setTesseraCostruzioneCorretta(true);
		TesseraCostruzione tessera = gioco.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0);
		tessera.getBonus().clear();
		tessera.getBonus().add(new BonusCartaPolitica(1));
		g1.getTessereUsate().add(tessera);
		bonus.setTessera(g1.getTessereUsate().get(0));
		bonus.azioneBonus(g1);
		assertEquals(0, g1.getTessereValide().size());
		assertEquals(1, g1.getTessereUsate().size());
		assertEquals(7, g1.getCartePolitica().size());
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(10, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(0, gioco.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
		assertEquals(0, gioco.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1));
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniRapideEseguibili());
		assertEquals(2, gioco.getTabellone().getRegioni().get(0).getTessereCostruzione().size());
		assertEquals(false, bonus.isTesseraCostruzioneCorretta());
		assertEquals(null, bonus.getTessera());
	}

	@Test
	public void testIsUgualeConBonusDiversi() {
		BonusRiutilizzoCostruzione bonus1 = new BonusRiutilizzoCostruzione(gioco);
		BonusAzionePrincipale bonus2 = new BonusAzionePrincipale();
		assertEquals(false, bonus1.isUguale(bonus2));
	}
	
	@Test
	public void testIsUgualeConTessereDiverse(){
		BonusRiutilizzoCostruzione bonus1 = new BonusRiutilizzoCostruzione(gioco);
		BonusRiutilizzoCostruzione bonus2 = new BonusRiutilizzoCostruzione(gioco);
		bonus1.setTessera(gioco.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		bonus2.setTessera(gioco.getTabellone().getRegioni().get(1).getTessereCostruzione().get(1));
		assertEquals(false, bonus1.isUguale(bonus2));
	}
	
	@Test
	public void testIsUgualeConBonusUguali(){
		BonusRiutilizzoCostruzione bonus1 = new BonusRiutilizzoCostruzione(gioco);
		BonusRiutilizzoCostruzione bonus2 = new BonusRiutilizzoCostruzione(gioco);
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

package testBonus;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.model.Giocatore;
import server.model.Gioco;
import server.model.bonus.BonusAssistenti;
import server.model.bonus.BonusAzionePrincipale;
import server.model.stato.giocatore.TurnoNormale;

public class TestBonusAzionePrincipale {

	private  Gioco gioco;
	
	@Before
	public void inizializzazione(){
		gioco = new Gioco();
		List<Giocatore> giocatori = new ArrayList<>();
		giocatori.add(new Giocatore("pippo"));
		giocatori.add(new Giocatore("paolo"));
		gioco.setGiocatori(giocatori);
		gioco.inizializzaPartita("0");
	}
	
	@Test(expected=NullPointerException.class)
	public void testAzioneBonusConGiocatoreNull() {
		BonusAzionePrincipale bonus = new BonusAzionePrincipale();
		bonus.azioneBonus(null);
	}
	
	@Test
	public void testAzioneBonus(){
		BonusAzionePrincipale bonus = new BonusAzionePrincipale();
		gioco.getGiocatori().get(0).getStatoGiocatore().prossimoStato();
		bonus.azioneBonus(gioco.getGiocatori().get(0));
		TurnoNormale statoGiocatore = (TurnoNormale) gioco.getGiocatori().get(0).getStatoGiocatore();
		assertEquals(2, statoGiocatore.getAzioniPrincipaliEseguibili());
	}

	@Test
	public void testIsUgualeConBonusDiversi() {
		BonusAzionePrincipale bonus1 = new BonusAzionePrincipale();
		BonusAssistenti bonus2 = new BonusAssistenti(1);
		assertEquals(false, bonus1.isUguale(bonus2));
	}
	
	@Test
	public void testIsUgualeConBonusUguali(){
		BonusAzionePrincipale bonus1 = new BonusAzionePrincipale();
		BonusAzionePrincipale bonus2 = new BonusAzionePrincipale();
		assertEquals(true, bonus1.isUguale(bonus2));
	}

}

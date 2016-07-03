/**
 * 
 */
package testController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.controller.Controller;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.AzioneFactory;
import server.model.bonus.Bonus;
import server.model.bonus.BonusAssistenti;
import server.model.bonus.BonusCartaPolitica;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.componenti.Assistente;
import server.model.componenti.CartaPolitica;
import server.model.componenti.Consigliere;
import server.model.componenti.Mercato;
import server.model.componenti.OggettoVendibile;
import server.model.componenti.TesseraCostruzione;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.Sospeso;
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;
import server.model.stato.giocatore.TurnoMercatoCompraVendita;
import server.model.stato.giocatore.TurnoNormale;
import server.model.stato.gioco.FaseTurnoMercatoAggiuntaOggetti;
import server.model.stato.gioco.FaseTurnoMercatoCompraVendita;
import server.model.stato.gioco.FaseTurnoSemplice;

/**
 * @author Luca
 *
 */
public class ControllerTest {

	private Gioco giocoTester;
	private Giocatore g1;
	private Giocatore g2;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("pippo");
		this.g1 = g1;
		Giocatore g2 = new Giocatore("paolo");
		this.g2 = g2;
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
		giocoTester.inizializzaPartita("6");
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#Controller(server.model.Gioco)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testControllerGiocoNull() {
		Controller cTest = new Controller(null);
		assertNotNull(cTest);
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#Controller(server.model.Gioco)}.
	 */
	@Test
	public void testControllerGiocoNotNull() {
		Controller cTest = new Controller(giocoTester);
		assertNotNull(cTest);
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#updateBonus(server.model.bonus.Bonus, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateBonus() {
		// fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreRegolareSaltaAzioneSecondaria() {
		giocoTester.setStato(new FaseTurnoSemplice(giocoTester));
		Controller cTest = new Controller(giocoTester);
		AzioneFactory aFac = new AzioneFactory(giocoTester);
		aFac.setTipoAzione("7");
		g1.getAssistenti().add(new Assistente());
		g1.getAssistenti().add(new Assistente());
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertEquals(3, g1.getAssistenti().size());
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniRapideEseguibili());
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		cTest.update(aFac.createAzione(), g1);
		assertEquals(2, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertEquals(0, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniRapideEseguibili());
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreRegolareEleggiConsigliere() {
		giocoTester.setStato(new FaseTurnoSemplice(giocoTester));
		Controller cTest = new Controller(giocoTester);
		AzioneFactory aFac = new AzioneFactory(giocoTester);
		Consigliere consT = giocoTester.getTabellone().getConsiglieriDisponibili().get(0);
		aFac.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		aFac.setConsigliere(consT);
		aFac.setTipoAzione("2");
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertTrue(!(giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().contains(consT)));
		cTest.update(aFac.createAzione(), g1);
		assertEquals(0, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertTrue(giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().contains(consT));
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreAzioniPrincipaliTerminateProvaAdEleggereConsigliere() {
		giocoTester.setStato(new FaseTurnoSemplice(giocoTester));
		Controller cTest = new Controller(giocoTester);
		AzioneFactory aFac = new AzioneFactory(giocoTester);
		Consigliere consT1 = giocoTester.getTabellone().getConsiglieriDisponibili().get(0);
		aFac.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		aFac.setConsigliere(consT1);
		aFac.setTipoAzione("2");
		g1.getStatoGiocatore().prossimoStato();
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertTrue(!(giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().contains(consT1)));
		cTest.update(aFac.createAzione(), g1);
		assertEquals(0, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertTrue(giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().contains(consT1));
		Consigliere consT2 = giocoTester.getTabellone().getConsiglieriDisponibili().get(0);
		assertNotEquals(consT2, consT1);
		aFac.setConsiglio(giocoTester.getTabellone().getRegioni().get(0).getConsiglio());
		aFac.setConsigliere(consT2);
		assertTrue(!(giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().contains(consT2)));
		cTest.update(aFac.createAzione(), g1);
		assertTrue(!(giocoTester.getTabellone().getRegioni().get(0).getConsiglio().getConsiglieri().contains(consT2)));
		assertEquals(0, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreAttesaSaltaAzioneSecondaria() {
		giocoTester.setStato(new FaseTurnoSemplice(giocoTester));
		Controller cTest = new Controller(giocoTester);
		AzioneFactory aFac = new AzioneFactory(giocoTester);
		aFac.setTipoAzione("7");
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
		cTest.update(aFac.createAzione(), g1);
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreAggiungiOggettoTurnoMercatoAggiuntaOggetti() {
		giocoTester.setStato(new FaseTurnoMercatoAggiuntaOggetti(giocoTester));
		Controller cTest = new Controller(giocoTester);
		Mercato mercTest = ((FaseTurnoMercatoAggiuntaOggetti) giocoTester.getStato()).getMercato();
		g1.setStatoGiocatore(new TurnoMercatoAggiuntaOggetti(g1, mercTest));
		OggettoVendibile oggettoTest = g1.generaListaOggettiVendibiliNonInVendita().get(0);
		oggettoTest.setPrezzo(10);
		oggettoTest.setGiocatore(g1);
		assertTrue(!mercTest.getOggettiInVendita().contains(oggettoTest));
		cTest.update(oggettoTest, g1);
		assertTrue(mercTest.getOggettiInVendita().contains(oggettoTest));
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreAggiungiOggettoAttesaTurno() {
		giocoTester.setStato(new FaseTurnoMercatoAggiuntaOggetti(giocoTester));
		Controller cTest = new Controller(giocoTester);
		Mercato mercTest = ((FaseTurnoMercatoAggiuntaOggetti) giocoTester.getStato()).getMercato();
		OggettoVendibile oggettoTest = g1.generaListaOggettiVendibiliNonInVendita().get(0);
		oggettoTest.setPrezzo(10);
		oggettoTest.setGiocatore(g1);
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
		assertTrue(!mercTest.getOggettiInVendita().contains(oggettoTest));
		cTest.update(oggettoTest, g1);
		assertTrue(!mercTest.getOggettiInVendita().contains(oggettoTest));
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreAcquistaOggettoTurnoMercatoCompraVendita() {
		giocoTester.setStato(new FaseTurnoMercatoAggiuntaOggetti(giocoTester));
		Controller cTest = new Controller(giocoTester);
		Mercato mercTest = ((FaseTurnoMercatoAggiuntaOggetti) giocoTester.getStato()).getMercato();
		g1.setStatoGiocatore(new TurnoMercatoAggiuntaOggetti(g1, mercTest));
		OggettoVendibile oggettoTest = g1.generaListaOggettiVendibiliNonInVendita().get(0);
		oggettoTest.setPrezzo(5);
		oggettoTest.setGiocatore(g1);
		assertTrue(!mercTest.getOggettiInVendita().contains(oggettoTest));
		cTest.update(oggettoTest, g1);
		assertTrue(mercTest.getOggettiInVendita().contains(oggettoTest));
		giocoTester.getStato().prossimoStato();
		g2.setStatoGiocatore(new TurnoMercatoCompraVendita(g2));
		assertTrue(giocoTester.getStato() instanceof FaseTurnoMercatoCompraVendita);
		assertTrue(g2.getStatoGiocatore() instanceof TurnoMercatoCompraVendita);
		assertEquals(11, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		assertTrue(!g2.generaListaOggettiVendibiliNonInVendita().contains(oggettoTest));
		cTest.update(oggettoTest, g2);
		assertEquals(11 - 5, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		giocoTester.getStato().prossimoStato();
		assertTrue(g2.generaListaOggettiVendibiliNonInVendita().contains(oggettoTest));
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreAcquistaCartaPoliticaTurnoMercatoCompraVendita() {
		giocoTester.setStato(new FaseTurnoMercatoAggiuntaOggetti(giocoTester));
		Controller cTest = new Controller(giocoTester);
		Mercato mercTest = ((FaseTurnoMercatoAggiuntaOggetti) giocoTester.getStato()).getMercato();
		g1.setStatoGiocatore(new TurnoMercatoAggiuntaOggetti(g1, mercTest));
		CartaPolitica oggettoTest = g1.getCartePolitica().get(0);
		oggettoTest.setPrezzo(5);
		oggettoTest.setGiocatore(g1);
		assertTrue(!mercTest.getOggettiInVendita().contains(oggettoTest));
		cTest.update(oggettoTest, g1);
		assertTrue(mercTest.getOggettiInVendita().contains(oggettoTest));
		giocoTester.getStato().prossimoStato();
		g2.setStatoGiocatore(new TurnoMercatoCompraVendita(g2));
		assertTrue(giocoTester.getStato() instanceof FaseTurnoMercatoCompraVendita);
		assertTrue(g2.getStatoGiocatore() instanceof TurnoMercatoCompraVendita);
		assertEquals(11, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		assertTrue(!g2.generaListaOggettiVendibiliNonInVendita().contains(oggettoTest));
		cTest.update(oggettoTest, g2);
		assertEquals(11 - 5, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		giocoTester.getStato().prossimoStato();
		assertTrue(g2.generaListaOggettiVendibiliNonInVendita().contains(oggettoTest));
	}
	
	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreAcquistaAssistenteTurnoMercatoCompraVendita() {
		giocoTester.setStato(new FaseTurnoMercatoAggiuntaOggetti(giocoTester));
		Controller cTest = new Controller(giocoTester);
		Mercato mercTest = ((FaseTurnoMercatoAggiuntaOggetti) giocoTester.getStato()).getMercato();
		g1.setStatoGiocatore(new TurnoMercatoAggiuntaOggetti(g1, mercTest));
		Assistente oggettoTest = g1.getAssistenti().get(0);
		oggettoTest.setPrezzo(5);
		oggettoTest.setGiocatore(g1);
		assertTrue(!mercTest.getOggettiInVendita().contains(oggettoTest));
		cTest.update(oggettoTest, g1);
		assertTrue(mercTest.getOggettiInVendita().contains(oggettoTest));
		giocoTester.getStato().prossimoStato();
		g2.setStatoGiocatore(new TurnoMercatoCompraVendita(g2));
		assertTrue(giocoTester.getStato() instanceof FaseTurnoMercatoCompraVendita);
		assertTrue(g2.getStatoGiocatore() instanceof TurnoMercatoCompraVendita);
		assertEquals(11, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		assertTrue(!g2.generaListaOggettiVendibiliNonInVendita().contains(oggettoTest));
		cTest.update(oggettoTest, g2);
		assertEquals(11 - 5, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		giocoTester.getStato().prossimoStato();
		assertTrue(g2.generaListaOggettiVendibiliNonInVendita().contains(oggettoTest));
	}
	
	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreAcquistaTesseraCostruzioneTurnoMercatoCompraVendita() {
		giocoTester.setStato(new FaseTurnoMercatoAggiuntaOggetti(giocoTester));
		Controller cTest = new Controller(giocoTester);
		Mercato mercTest = ((FaseTurnoMercatoAggiuntaOggetti) giocoTester.getStato()).getMercato();
		g1.setStatoGiocatore(new TurnoMercatoAggiuntaOggetti(g1, mercTest));
		TesseraCostruzione oggettoTest = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0);
		g1.getTessereValide().add(oggettoTest);
		oggettoTest.setPrezzo(5);
		oggettoTest.setGiocatore(g1);
		assertTrue(!mercTest.getOggettiInVendita().contains(oggettoTest));
		cTest.update(oggettoTest, g1);
		assertTrue(mercTest.getOggettiInVendita().contains(oggettoTest));
		giocoTester.getStato().prossimoStato();
		g2.setStatoGiocatore(new TurnoMercatoCompraVendita(g2));
		assertTrue(giocoTester.getStato() instanceof FaseTurnoMercatoCompraVendita);
		assertTrue(g2.getStatoGiocatore() instanceof TurnoMercatoCompraVendita);
		assertEquals(11, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		assertTrue(!g2.generaListaOggettiVendibiliNonInVendita().contains(oggettoTest));
		cTest.update(oggettoTest, g2);
		assertEquals(11 - 5, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		giocoTester.getStato().prossimoStato();
		assertTrue(g2.generaListaOggettiVendibiliNonInVendita().contains(oggettoTest));
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatorePassaTurnoTurnoMercatoAggiuntaOggetti() {
		giocoTester.setStato(new FaseTurnoMercatoAggiuntaOggetti(giocoTester));
		Controller cTest = new Controller(giocoTester);
		Mercato mercTest = ((FaseTurnoMercatoAggiuntaOggetti) giocoTester.getStato()).getMercato();
		g1.setStatoGiocatore(new TurnoMercatoAggiuntaOggetti(g1, mercTest));
		assertTrue(giocoTester.getStato() instanceof FaseTurnoMercatoAggiuntaOggetti);
		assertTrue(g1.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti);
		cTest.update("-", g1);
		assertTrue(g1.getStatoGiocatore() instanceof AttesaTurno);
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test(expected=IllegalStateException.class)
	public void testUpdateAzioneGiocatoreOggettoACaso() {
		Controller cTest = new Controller(giocoTester);
		cTest.update("TEST", g1);
	}
	
	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object, server.model.Giocatore)}
	 * .
	 */
	@Test
	public void testUpdateAzioneGiocatoreSospendiGiocatore() {
		giocoTester.setStato(new FaseTurnoSemplice(giocoTester));
		Controller cTest = new Controller(giocoTester);
		assertTrue(!(g1.getStatoGiocatore() instanceof Sospeso));
		cTest.update("Sospendi", g1);
		assertTrue(g1.getStatoGiocatore() instanceof Sospeso);
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(java.lang.Object)}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUpdateObject() {
		Controller cTest = new Controller(giocoTester);
		cTest.update("TEST");
	}

	/**
	 * Test method for
	 * {@link server.controller.Controller#update(server.model.bonus.Bonus, java.util.List)}
	 * .
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUpdateBonusListOfStringUnsupported() {
		Bonus bTest = new BonusCartaPolitica(2);
		List<String> listTest = new ArrayList<>();
		Controller cTest = new Controller(giocoTester);
		cTest.update(bTest, listTest);
	}
	
	@Test
	public void testUpdateBonusConBonusRiutilizzoCostruzioneConTesseraNull(){
		BonusRiutilizzoCostruzione bonus = new BonusRiutilizzoCostruzione(giocoTester);
		bonus.setTessera(null);
		Controller controller = new Controller(giocoTester);
		controller.updateBonus(bonus, g1);
		assertEquals(0, g1.getTessereValide().size());
		assertEquals(6, g1.getCartePolitica().size());
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(0, giocoTester.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
		assertEquals(0, giocoTester.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1));
		assertEquals(null, bonus.getTessera());
		assertEquals(true, bonus.isTesseraCostruzioneCorretta());
	}
	
	@Test
	public void testUpdateBonusConBonusRiutilizzoCostruzioneConTesseraNonCorretta(){
		BonusRiutilizzoCostruzione bonus = new BonusRiutilizzoCostruzione(giocoTester);
		TesseraCostruzione tesseraNonValida = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0);
		bonus.setTessera(tesseraNonValida);
		Controller controller = new Controller(giocoTester);
		controller.updateBonus(bonus, g1);
		assertEquals(0, g1.getTessereValide().size());
		assertEquals(6, g1.getCartePolitica().size());
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(0, giocoTester.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
		assertEquals(0, giocoTester.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1));
		assertEquals(tesseraNonValida, bonus.getTessera());
		assertEquals(false, bonus.isTesseraCostruzioneCorretta());
	}
	
	@Test
	public void testUpdateBonusConBonusRiutilizzoCostruzioneConTesseraCorretta(){
		BonusRiutilizzoCostruzione bonus = new BonusRiutilizzoCostruzione(giocoTester);
		TesseraCostruzione tesseraValida = giocoTester.getTabellone().getRegioni().get(0).getTessereCoperte().get(5);
		tesseraValida.getBonus().clear();
		tesseraValida.getBonus().add(new BonusAssistenti(2));
		g1.getTessereUsate().add(tesseraValida);
		bonus.setTessera(g1.getTessereUsate().get(0));
		Controller controller = new Controller(giocoTester);
		controller.updateBonus(bonus, g1);
		assertEquals(tesseraValida, bonus.getTessera());
		assertEquals(true, bonus.isTesseraCostruzioneCorretta());
	}
	
	@Test
	public void testUpdateBonusConBonusTesseraPermessoConTesseraNull(){
		BonusTesseraPermesso bonus = new BonusTesseraPermesso(giocoTester);
		bonus.setTessera(null);
		Controller controller = new Controller(giocoTester);
		controller.updateBonus(bonus, g1);
		assertEquals(0, g1.getTessereValide().size());
		assertEquals(6, g1.getCartePolitica().size());
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(0, giocoTester.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
		assertEquals(0, giocoTester.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1));
		assertEquals(0, g1.getTessereUsate().size());
		assertEquals(0, g1.getTessereValide().size());
		assertEquals(null, bonus.getTessera());
		assertEquals(true, bonus.isTesseraCorretta());
	}

	@Test
	public void testUpdateBonusConBonusTesseraPermessoConTesseraNonCorretta(){
		BonusTesseraPermesso bonus = new BonusTesseraPermesso(giocoTester);
		TesseraCostruzione tesseraNonValida = giocoTester.getTabellone().getRegioni().get(0).getTessereCoperte().get(2);
		bonus.setTessera(tesseraNonValida);
		Controller controller = new Controller(giocoTester);
		controller.updateBonus(bonus, g1);
		assertEquals(0, g1.getTessereValide().size());
		assertEquals(6, g1.getCartePolitica().size());
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(10, giocoTester.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		assertEquals(0, giocoTester.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
		assertEquals(0, giocoTester.getTabellone().getPercorsoNobilta().posizioneAttualeGiocatore(g1));
		assertEquals(tesseraNonValida, bonus.getTessera());
		assertEquals(false, bonus.isTesseraCorretta());
	}
	
	@Test
	public void testUpdateBonusConBonusTesseraPermessoConTesseraCorretta(){
		BonusTesseraPermesso bonus = new BonusTesseraPermesso(giocoTester);
		TesseraCostruzione tesseraValida = giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(1);
		tesseraValida.getBonus().clear();
		tesseraValida.getBonus().add(new BonusAssistenti(2));
		g1.getTessereUsate().add(tesseraValida);
		bonus.setTessera(g1.getTessereUsate().get(0));
		Controller controller = new Controller(giocoTester);
		controller.updateBonus(bonus, g1);
		assertEquals(tesseraValida, bonus.getTessera());
		assertEquals(true, bonus.isTesseraCorretta());
	}
	
}

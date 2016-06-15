/**
 * 
 */
package testAzioni;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eccezione.CartePoliticaIncorrette;
import eccezione.EmporioGiaCostruito;
import eccezione.FuoriDalLimiteDelPercorso;
import eccezione.NumeroAiutantiIncorretto;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.componenti.Assistente;
import server.model.componenti.TesseraCostruzione;
import server.model.stato.giocatore.TurnoNormale;

/**
 * @author Massimiliano Ventura
 *
 */
public class TestCambioTessereCostruzione {

	private Gioco giocoTester;
	private Giocatore g1;
	private AzioneFactory creaAzioniTester;
	private Azione azioneTester;

	@Before
	public void inizializzaOggettiPerTest() {
		Giocatore g1 = new Giocatore("frensis");
		this.g1 = g1;
		Giocatore g2 = new Giocatore("eric");
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		giocoTester = gioco;
		giocoTester.inizializzaPartita("0");
		creaAzioniTester = new AzioneFactory(giocoTester);
	}

	@Test(expected = NumeroAiutantiIncorretto.class)
	public void testEseguiAzioneSenzaAvereIlNumeroDiAiutantiCorretto()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		creaAzioniTester.setTipoAzione("5");
		creaAzioniTester.setRegione(this.giocoTester.getTabellone().getRegioneDaNome("mare"));
		azioneTester = creaAzioniTester.createAzione();
		g1.getAssistenti().clear();
		g1.getStatoGiocatore().prossimoStato();
		azioneTester.eseguiAzione(g1);
	}

	@Test
	public void testEseguiAzioneConNumeroDiAiutantiCorretto()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		creaAzioniTester.setTipoAzione("5");
		creaAzioniTester.setRegione(this.giocoTester.getTabellone().getRegioneDaNome("mare"));
		azioneTester = creaAzioniTester.createAzione();
		g1.getAssistenti().add(new Assistente());
		g1.getStatoGiocatore().prossimoStato();
		TesseraCostruzione tessera0 = giocoTester.getTabellone().getRegioneDaNome("mare").getTessereCostruzione()
				.get(0);
		TesseraCostruzione tessera1 = giocoTester.getTabellone().getRegioneDaNome("mare").getTessereCostruzione()
				.get(1);
		assertEquals(13, giocoTester.getTabellone().getRegioneDaNome("mare").getTessereCoperte().size());
		assertEquals(2, giocoTester.getTabellone().getRegioneDaNome("mare").getTessereCostruzione().size());
		azioneTester.eseguiAzione(g1);
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(13, giocoTester.getTabellone().getRegioneDaNome("mare").getTessereCoperte().size());
		assertEquals(2, giocoTester.getTabellone().getRegioneDaNome("mare").getTessereCostruzione().size());
		assertTrue(tessera0 != giocoTester.getTabellone().getRegioneDaNome("mare").getTessereCostruzione().get(0));
		assertTrue(tessera1 != giocoTester.getTabellone().getRegioneDaNome("mare").getTessereCostruzione().get(1));
		assertTrue(giocoTester.getTabellone().getRegioneDaNome("mare").getTessereCoperte().contains(tessera0));
		assertTrue(giocoTester.getTabellone().getRegioneDaNome("mare").getTessereCoperte().contains(tessera1));
		assertEquals(1, ((TurnoNormale)g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertEquals(0, ((TurnoNormale)g1.getStatoGiocatore()).getAzioniRapideEseguibili());
	}

	@Test(expected = NullPointerException.class)
	public void testCambioTessereCostruzioneConGiocatoreNullo()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		creaAzioniTester.setTipoAzione("5");
		creaAzioniTester.setRegione(this.giocoTester.getTabellone().getRegioneDaNome("mare"));
		azioneTester = creaAzioniTester.createAzione();
		azioneTester.eseguiAzione(null);
	}

}

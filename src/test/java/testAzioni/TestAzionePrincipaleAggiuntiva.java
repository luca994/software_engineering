/**
 * 
 */
package testAzioni;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import server.eccezioni.EccezioneConsiglioDeiQuattro;
import server.eccezioni.NumeroAiutantiIncorretto;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;
import server.model.componenti.Assistente;
import server.model.stato.giocatore.TurnoNormale;

/**
 * @author Massimiliano Ventura
 *
 */
public class TestAzionePrincipaleAggiuntiva {

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
			throws EccezioneConsiglioDeiQuattro {
		creaAzioniTester.setTipoAzione("7");
		azioneTester = creaAzioniTester.createAzione();
		g1.getStatoGiocatore().prossimoStato();
		azioneTester.eseguiAzione(g1);
	}

	@Test
	public void testEseguiAzioneConNumeroDiAiutantiCorretto()
			throws EccezioneConsiglioDeiQuattro {
		creaAzioniTester.setTipoAzione("7");
		azioneTester = creaAzioniTester.createAzione();
		g1.getAssistenti().add(new Assistente());
		g1.getAssistenti().add(new Assistente());
		g1.getAssistenti().add(new Assistente());
		g1.getStatoGiocatore().prossimoStato();
		azioneTester.eseguiAzione(g1);
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(2, ((TurnoNormale)g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertEquals(0, ((TurnoNormale)g1.getStatoGiocatore()).getAzioniRapideEseguibili());
	}

	@Test(expected= NullPointerException.class)
	public void testEseguiAzioneConGiocatoreNullo()
			throws EccezioneConsiglioDeiQuattro {
		creaAzioniTester.setTipoAzione("7");
		azioneTester = creaAzioniTester.createAzione();
		azioneTester.eseguiAzione(null);
	}

}

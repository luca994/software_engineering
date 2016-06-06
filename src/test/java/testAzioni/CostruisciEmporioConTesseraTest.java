/**
 * 
 */
package testAzioni;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eccezione.CartePoliticaIncorrette;
import eccezione.EmporioGiaCostruito;
import eccezione.FuoriDalLimiteDelPercorso;
import eccezione.NumeroAiutantiIncorretto;
import server.model.Citta;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.TesseraCostruzione;
import server.model.azione.Azione;
import server.model.azione.AzioneFactory;

/**
 * @author Massimiliano Ventura
 *
 */
public class CostruisciEmporioConTesseraTest {

	private Gioco giocoTester;
	private Giocatore g1;
	private Giocatore g2;
	private Giocatore g3;
	private AzioneFactory creaAzioniTester;
	private Azione azioneTester;
	private Citta citta;
	private TesseraCostruzione tessera;

	@Before
	public void setUp() throws Exception {
		this.g1 = new Giocatore("Rastagnolo");
		this.g2 = new Giocatore("Sendefino");
		this.g3 = new Giocatore("Sgranciofido");
		Gioco gioco = new Gioco();
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
		gioco.getGiocatori().add(g3);
		giocoTester = gioco;
		giocoTester.inizializzaPartita();
		creaAzioniTester = new AzioneFactory(giocoTester);

	}

	@Test
	public void testEseguiAzioneQuandoNonCeLEmporioEIlGiocatorePossiedeLaTessera()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		// Aggiungo una tessera valida a g1
		g1.getTessereValide().add(giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		giocoTester.getTabellone().getRegioni().get(0)
				.nuovaTessera(giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		// Setto il creatore dell'azione
		List<Citta> temp = new ArrayList<>(g1.getTessereValide().get(0).getCitta());// Creata
																					// e
																					// convertita
																					// solo
																					// localmente
																					// perchè
																					// è
																					// più
																					// comoda
																					// del
																					// set
		System.out.println(temp.size());
		creaAzioniTester.setCitta(temp.get(0));
		creaAzioniTester.setTesseraCostruzione(g1.getTessereValide().get(0));
		// Creo l'azione
		azioneTester = creaAzioniTester.createAzione("3");
		// Sposto lo stato del giocatore a TurnoNormale
		g1.getStatoGiocatore().prossimoStato();
		// Eseguo l'azione
		azioneTester.eseguiAzione(g1);
		// Controllo presenza emporio
		assertTrue(temp.get(0).getEmpori().contains(g1));

	}

}
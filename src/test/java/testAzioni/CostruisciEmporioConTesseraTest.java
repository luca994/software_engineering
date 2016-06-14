/**
 * 
 */
package testAzioni;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
import server.model.bonus.BonusMoneta;
import server.model.componenti.Citta;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.TurnoNormale;

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
		giocoTester.inizializzaPartita("0");
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
		List<Citta> temp = new ArrayList<>(g1.getTessereValide().get(0).getCitta());
		creaAzioniTester.setCitta(temp.get(0));
		creaAzioniTester.setTesseraCostruzione(g1.getTessereValide().get(0));
		// Creo l'azione
		creaAzioniTester.setTipoAzione("3");
		azioneTester = creaAzioniTester.createAzione();
		// Sposto lo stato del giocatore a TurnoNormale
		g1.getStatoGiocatore().prossimoStato();
		// Eseguo l'azione
		azioneTester.eseguiAzione(g1);
		// Controllo presenza emporio
		assertTrue(temp.get(0).getEmpori().contains(g1));
		// Verifico turni, in teoria al giocatore 1 manca ancora l'azione rapida
		// da fare
		assertTrue(g1.getStatoGiocatore() instanceof TurnoNormale);
		assertEquals(0, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniPrincipaliEseguibili());
		assertEquals(1, ((TurnoNormale) g1.getStatoGiocatore()).getAzioniRapideEseguibili());
		assertTrue(g2.getStatoGiocatore() instanceof AttesaTurno);
		assertTrue(g3.getStatoGiocatore() instanceof AttesaTurno);

	}

	@Test(expected = EmporioGiaCostruito.class)
	public void testEseguiAzioneQuandoLEmporioEGiaCostruitoEIlGiocatorePossiedeLaTessera()
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
		creaAzioniTester.setCitta(temp.get(0));
		creaAzioniTester.setTesseraCostruzione(g1.getTessereValide().get(0));
		// Aggiungo emporio del giocatore
		temp.get(0).getEmpori().add(g1);
		// Creo l'azione
		creaAzioniTester.setTipoAzione("3");
		azioneTester = creaAzioniTester.createAzione();
		// Sposto lo stato del giocatore a TurnoNormale
		g1.getStatoGiocatore().prossimoStato();
		// Eseguo l'azione

		azioneTester.eseguiAzione(g1);
	}

	@Test(expected = NullPointerException.class)
	public void testEseguiAzioneConGiocatoreNullo()
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

		creaAzioniTester.setCitta(temp.get(0));
		creaAzioniTester.setTesseraCostruzione(g1.getTessereValide().get(0));
		// Creo l'azione
		creaAzioniTester.setTipoAzione("3");
		azioneTester = creaAzioniTester.createAzione();
		// Sposto lo stato del giocatore a TurnoNormale
		g1.getStatoGiocatore().prossimoStato();
		// Eseguo l'azione
		azioneTester.eseguiAzione(null);
	}

	@Test(expected = NumeroAiutantiIncorretto.class)
	public void testEseguiAzioneSenzaSufficientiAiutanti()
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
		creaAzioniTester.setCitta(temp.get(0));
		creaAzioniTester.setTesseraCostruzione(g1.getTessereValide().get(0));
		// Aggiungo altri giocatori alla città
		temp.get(0).getEmpori().add(g2);
		temp.get(0).getEmpori().add(g3);
		// Creo l'azione
		creaAzioniTester.setTipoAzione("3");
		azioneTester = creaAzioniTester.createAzione();
		// Sposto lo stato del giocatore a TurnoNormale
		g1.getStatoGiocatore().prossimoStato();
		// Levo aiutanti
		g1.getAssistenti().clear();
		// Eseguo l'azione
		azioneTester.eseguiAzione(g1);
	}

	@Test
	public void testAggiuntaPuntiQuandoGliEmporiSonoFiniti()
			throws FuoriDalLimiteDelPercorso, CartePoliticaIncorrette, NumeroAiutantiIncorretto, EmporioGiaCostruito {
		// Aggiungo una tessera valida a g1 e sostituisco il bonus con uno che
		// non modifica i punti vittoria
		g1.getTessereValide().add(giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		g1.getTessereValide().get(0).getBonus().clear();
		g1.getTessereValide().get(0).getBonus()
				.add(new BonusMoneta(giocoTester.getTabellone().getPercorsoRicchezza(), 1));
		giocoTester.getTabellone().getRegioni().get(0)
				.nuovaTessera(giocoTester.getTabellone().getRegioni().get(0).getTessereCostruzione().get(0));
		// Setto il creatore dell'azione e modifico il bonus della citta che non
		// modifica i punti vittoria
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
		creaAzioniTester.setCitta(temp.get(0));
		temp.get(0).getBonus().clear();
		temp.get(0).getBonus().add(new BonusMoneta(giocoTester.getTabellone().getPercorsoRicchezza(), 1));

		creaAzioniTester.setTesseraCostruzione(g1.getTessereValide().get(0));
		// Aggiungo altri giocatori alla città
		// Creo l'azione
		creaAzioniTester.setTipoAzione("3");
		azioneTester = creaAzioniTester.createAzione();
		// Sposto lo stato del giocatore a TurnoNormale
		g1.getStatoGiocatore().prossimoStato();
		// Decremento empori
		for (int i = 0; i < 9; i++)
			g1.decrementaEmporiRimasti();
		// Eseguo l'azione
		azioneTester.eseguiAzione(g1);
		assertEquals(3, giocoTester.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));

	}

}
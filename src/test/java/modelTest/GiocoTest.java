package modelTest;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import junit.framework.TestCase;
import model.CartaPolitica;
import model.Città;
import model.Giocatore;
import model.Gioco;
import model.Regione;
import model.percorso.CasellaConBonus;

public class GiocoTest extends TestCase {

	private Gioco gioco;
	private List<Giocatore> giocatori;
	
	public void testGioco() {
		
		giocatori= new ArrayList<Giocatore>();
		Giocatore g1 = new Giocatore("pippo", Color.blue);
		Giocatore g2 = new Giocatore("sandro", Color.green);
		giocatori.add(g1);
		giocatori.add(g2);
		
		try {
			gioco=new Gioco(giocatori);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertNotNull(gioco.getTabellone());
		
		assertEquals(10, g1.getEmporiRimasti());
		assertEquals(10, g2.getEmporiRimasti());
		
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(2, g2.getAssistenti().size());
		
		assertNotNull(gioco.getTabellone().getPercorsoNobiltà());
		assertNotNull(gioco.getTabellone().getPercorsoVittoria());
		assertNotNull(gioco.getTabellone().getPercorsoRicchezza());
		
		assertEquals(10, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(g1, -10);
		assertEquals(0, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		

		gioco.getTabellone().getPercorsoNobiltà().muoviGiocatore(g1, 2);
		assertEquals(2, ((CasellaConBonus) gioco.getTabellone().getPercorsoNobiltà().getCaselle().get(gioco.getTabellone().getPercorsoNobiltà().posizioneAttualeGiocatore(g1))).getBonus().size());
		
		gioco.getTabellone().getPercorsoNobiltà().muoviGiocatore(g1, 16);
		assertEquals(1, ((CasellaConBonus) gioco.getTabellone().getPercorsoNobiltà().getCaselle().get(gioco.getTabellone().getPercorsoNobiltà().posizioneAttualeGiocatore(g1))).getBonus().size());
		
		
		assertEquals(11, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(g2, -2);
		assertEquals(9, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		System.out.println(gioco.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g2));
		System.out.println("prova");
		assertEquals(10, gioco.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
		
		assertEquals(6, g1.getCartePolitica().size());
		assertEquals(6, g2.getCartePolitica().size());
		
		//stampa il colore di ogni carta politica di ogni giocatore che sono quindi per ogni partita casuali
		for(Giocatore g: gioco.getGiocatori()){
			for(CartaPolitica c:g.getCartePolitica()){
				System.out.println(c.getColore().toString());
			}
		}
	}

}

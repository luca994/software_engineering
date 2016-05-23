package modelTest;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import junit.framework.TestCase;
import server.controller.Controller;
import server.model.Città;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.Regione;
import server.model.percorso.CasellaConBonus;
import server.view.View;

public class GiocoTest extends TestCase {

	private Gioco gioco;
	private List<Giocatore> giocatori;
	private View view;
	private Controller controller;
	
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
		view = new View(gioco);
		controller = new Controller(gioco, view);
		
		
		
		assertNotNull(gioco.getTabellone());
		
		assertEquals(10, g1.getEmporiRimasti());
		assertEquals(10, g2.getEmporiRimasti());
		
		assertEquals(1, g1.getAssistenti().size());
		assertEquals(2, g2.getAssistenti().size());
		
		assertNotNull(gioco.getTabellone().getPercorsoNobiltà());
		assertNotNull(gioco.getTabellone().getPercorsoVittoria());
		assertNotNull(gioco.getTabellone().getPercorsoRicchezza());
		
		for(Regione r:gioco.getTabellone().getRegioni()){
			for(Città c:r.getCittà()){
				if(!c.getEmpori().isEmpty()){
					for(Giocatore g:c.getEmpori()){
						assertEquals(Color.darkGray, g.getColore());
					}
				}
			}
		}
		
		gioco.getTabellone().cercaCittà("Arkon").aggiungiEmporio(g1);
		gioco.getTabellone().cercaCittà("Indur").aggiungiEmporio(g1);
		gioco.getTabellone().getPercorsoNobiltà().muoviGiocatore(g1, 16); //bonus gettone città
		
		assertEquals(10, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(g1, -10);
		assertEquals(0, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g1));
		

		gioco.getTabellone().getPercorsoNobiltà().muoviGiocatore(g1, -14);
		assertEquals(2, ((CasellaConBonus) gioco.getTabellone().getPercorsoNobiltà().getCaselle().get(gioco.getTabellone().getPercorsoNobiltà().posizioneAttualeGiocatore(g1))).getBonus().size());
		
		gioco.getTabellone().getPercorsoNobiltà().muoviGiocatore(g1, 16);
		assertEquals(1, ((CasellaConBonus) gioco.getTabellone().getPercorsoNobiltà().getCaselle().get(gioco.getTabellone().getPercorsoNobiltà().posizioneAttualeGiocatore(g1))).getBonus().size());
		
		
		assertEquals(11, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		gioco.getTabellone().getPercorsoRicchezza().muoviGiocatore(g2, -2);
		assertEquals(9, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		//System.out.println(gioco.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g2));
		//System.out.println("prova");
		assertEquals(8, gioco.getTabellone().getPercorsoVittoria().posizioneAttualeGiocatore(g1));
		
		assertEquals(6, g1.getCartePolitica().size());
		assertEquals(6, g2.getCartePolitica().size());
	}

}

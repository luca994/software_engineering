package modelTest.bonusTest;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import junit.framework.TestCase;
import server.controller.Controller;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.bonus.Bonus;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.view.View;

public class BonusRiutilizzoCostruzioneTest extends TestCase {

	private Gioco gioco;
	private List<Giocatore> giocatori;
	private View view;
	private Controller controller;
	
	public void test(){
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
		
		Bonus bonus= new BonusRiutilizzoCostruzione(gioco);
		bonus.azioneBonus(g1);
	}
}

package modelTest.bonusTest;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import controller.Controller;
import junit.framework.TestCase;
import model.Giocatore;
import model.Gioco;
import model.TesseraCostruzione;
import model.bonus.BonusTesseraPermesso;
import view.View;

public class BonusTesseraPermessoTest extends TestCase {

	private Gioco gioco;
	private List<Giocatore> giocatori;
	private View view;
	private Controller controller;
	
	public void test() {
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
		
		BonusTesseraPermesso bonus = new BonusTesseraPermesso(gioco);
		TesseraCostruzione temp = gioco.getTabellone().getRegioni().get(1).getTessereCostruzione().get(1);
		bonus.azioneBonus(g1);
		assertEquals(temp,g1.getTessereValide().get(0));
	}

}

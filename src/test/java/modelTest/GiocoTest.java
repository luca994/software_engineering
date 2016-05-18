package modelTest;

import java.awt.Color;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import junit.framework.TestCase;
import model.Città;
import model.Giocatore;
import model.Gioco;
import model.Regione;

public class GiocoTest extends TestCase {

	private Gioco gioco;
	private List<Giocatore> giocatori;
	private Color c;
	
	public void testGioco() {
		
		giocatori= new ArrayList<Giocatore>();
		Giocatore g1 = new Giocatore("pippo", c.blue);
		Giocatore g2 = new Giocatore("sandro", c.green);
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
		assertEquals(11, gioco.getTabellone().getPercorsoRicchezza().posizioneAttualeGiocatore(g2));
		
		assertEquals(6, g1.getCartePolitica().size());
		assertEquals(6, g2.getCartePolitica().size());
		
		for(Regione r:gioco.getTabellone().getRegioni()){
			for(Città c:r.getCittà()){
				for(Giocatore g:c.getEmpori()){
					System.out.println(g.getColore().toString());
				}
			}
		}
	}

}

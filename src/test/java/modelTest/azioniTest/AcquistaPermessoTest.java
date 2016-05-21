/**
 * 
 */
package modelTest.azioniTest;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.JDOMException;
import junit.framework.TestCase;
import model.CartaPolitica;
import model.Consiglio;
import model.Giocatore;
import model.Gioco;
import model.TesseraCostruzione;
import model.azione.AcquistaPermesso;
import model.percorso.Percorso;

/**
 * @author Luca
 *
 */
public class AcquistaPermessoTest extends TestCase {

	private Gioco gioco;
	private List<Giocatore> giocatori;
	
	/**
	 * Test method for {@link model.azione.AcquistaPermesso#AcquistaPermesso(model.TesseraCostruzione, java.util.List, model.Consiglio, model.percorso.Percorso)}.
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public void testAcquistaPermesso() throws JDOMException, IOException {
		giocatori= new ArrayList<Giocatore>();
		Giocatore g1 = new Giocatore("pippo", Color.blue);
		Giocatore g2 = new Giocatore("sandro", Color.green);
		giocatori.add(g1);
		giocatori.add(g2);
		gioco=new Gioco(giocatori);
		
		TesseraCostruzione tesseraTest = gioco.getTabellone().getRegioneDaNome("Mare").getTessereCostruzione().get(0);
		Consiglio consiglioTest = gioco.getTabellone().getRegioneDaNome("Mare").getConsiglio();
		Percorso percorsoRicchezzaTest = gioco.getTabellone().getPercorsoRicchezza();
		g1.getCartePolitica().remove(0);
		g1.getCartePolitica().remove(0);
		List<CartaPolitica> cartePoliticaTest = g1.getCartePolitica() ;
		AcquistaPermesso azione1 = new AcquistaPermesso(tesseraTest,cartePoliticaTest,consiglioTest,percorsoRicchezzaTest);
		azione1.eseguiAzione(g1);
	
	}


}

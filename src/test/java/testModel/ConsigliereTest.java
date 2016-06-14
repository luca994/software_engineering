/**
 * 
 */
package testModel;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import server.model.componenti.Consigliere;

/**
 * @author Luca
 *
 */
public class ConsigliereTest {

	/**
	 * Test method for {@link server.model.componenti.Consigliere#Consigliere(java.awt.Color)}.
	 */
	@Test
	public void testConsigliereNull() {
		Consigliere consigliereTester = new Consigliere(null);
		assertNotNull(consigliereTester);
	}
	
	/**
	 * Test method for {@link server.model.componenti.Consigliere#Consigliere(java.awt.Color)}.
	 */
	@Test
	public void testConsigliereValido() {
		Consigliere consigliereTester = new Consigliere(Color.darkGray);
		assertNotNull(consigliereTester);
	}

	/**
	 * Test method for {@link server.model.componenti.Consigliere#getColore()}.
	 */
	@Test
	public void testGetColore() {
		Consigliere consigliereTester = new Consigliere(Color.red);
		assertEquals(Color.red, consigliereTester.getColore());
	}

	/**
	 * Test method for {@link server.model.componenti.Consigliere#getColore()}.
	 */
	@Test
	public void testGetColoreNull() {
		Consigliere consigliereTester = new Consigliere(null);
		assertNull(consigliereTester.getColore());
	}
}

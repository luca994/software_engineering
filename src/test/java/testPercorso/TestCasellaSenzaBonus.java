package testPercorso;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import server.model.percorso.Casella;
import server.model.percorso.CasellaSenzaBonus;

public class TestCasellaSenzaBonus {

	@Test
	public void test() {
		Casella casella = new CasellaSenzaBonus();
		assertNotNull(casella);
	}

}

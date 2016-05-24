package server.model.stato;

import server.model.Gioco;

public class AttesaGioco extends StatoGioco {
	
	@Override
	public void prossimoStato(Gioco gioco){
		gioco.setStato(new Esecuzione());
	}
}
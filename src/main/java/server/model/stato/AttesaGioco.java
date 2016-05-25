package server.model.stato;

import server.model.Gioco;

public class AttesaGioco extends StatoGioco {
	
	@Override
	public void prossimoStato(Gioco gioco){
		FaseNormale prossimoStato=new FaseNormale(gioco);
		gioco.setStato(prossimoStato);
		prossimoStato.avviaTurni();
	}
}
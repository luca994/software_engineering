package model;

/**
 * @author Luca
 *
 */
public class EleggiConsigliere extends Azione {

	private Consigliere consigliere;
	private Consiglio consiglio;
	private PercorsoRicchezza percorsoRicchezza;
	
	
	//Setter
	public void setConsigliere(Consigliere consigliere) {
		this.consigliere = consigliere;
	}

	//Setter
	public void setConsiglio(Consiglio consiglio) {
		this.consiglio = consiglio;
	}



	@Override
	public void eseguiAzione (){
		this.consiglio.removeConsigliere();
		while(this.consiglio.addConsigliere(this.consigliere)==false);
		//se ritorna true il while viene eseguito una sola volta
		percorsoRicchezza.muoviGiocatoreAvanti(getGiocatore(), 4);
	}
}

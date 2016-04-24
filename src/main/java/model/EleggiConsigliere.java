package model;

/**
 * @author Luca
 *
 */
public class EleggiConsigliere extends Azione {

	private Consigliere consigliere;
	private Consiglio consiglio;
	
	
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
		this.consiglio.addConsigliere(this.consigliere);
		
	}
}

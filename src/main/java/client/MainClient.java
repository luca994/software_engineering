package client;

public class MainClient {
 
	public static void main(String[] args){
		GestoreClient gestoreClient = new GestoreClient();
		if(gestoreClient.getView()!=null){
			View view = gestoreClient.getView();
			view.startClient();
		}
		else{
			System.out.println("Errore nella creazione della view");
		}
	}
}

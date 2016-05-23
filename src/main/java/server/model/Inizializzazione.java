/**
 * 
 */
package server.model;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

/**
 * @author Massimiliano Ventura
 *
 */
public class Inizializzazione {
	public void inizializza() throws JDOMException, IOException{
		try{
			List<Giocatore> nuoviGiocatori=new ArrayList<Giocatore>();
			boolean avviaGioco=false;
			while(!avviaGioco){
			String nome = new String();
			Color colore;
			//prendo in input nome e colore giocatore dal controller con il metodo del controller, mi viene anche comunicato se
			//può iniziare il gioco settando a true avviaGioco
			//nuoviGiocatori.add(new Giocatore(nome, colore));
		}
			
		//Creo e avvio gioco
		new Gioco(nuoviGiocatori).gioco();
		
		}
		catch (JDOMException e){
			throw e;
		}
		catch (IOException e){
			throw e;
		}
	}	
	
}

/**
 * 
 */
package model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jdom2.JDOMException;

/**
 * @author Massimiliano Ventura
 *
 */
public class Inizializzazione {
	public void inizializza() throws JDOMException, IOException{
		try{
			Set<Giocatore> nuoviGiocatori=new HashSet<Giocatore>();
			boolean avviaGioco=false;
			while(!avviaGioco){
			String nome= new String();
			String colore= new String();
			//prendo in input nome e colore giocatore dal controller con il metodo del controller, mi viene anche comunicato se
			//può iniziare il gioco settando a true avviaGioco
			nuoviGiocatori.add(new Giocatore(nome, colore));
		}
			
		//Creo e avvio gioco
		new Gioco(nuoviGiocatori).gioco();
		
		}
		catch (JDOMException e){
			System.err.println("Errore nel parsing di un xml");
			throw e;
		}
		catch (IOException e){
			System.err.println("Errore nella lettura del file");
			throw e;
		}
	}	
	
}

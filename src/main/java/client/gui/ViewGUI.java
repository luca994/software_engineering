/**
 * 
 */
package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import client.View;
import client.cli.InputOutput;
import eccezione.NomeGiaScelto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import server.model.Giocatore;
import server.model.Tabellone;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.StatoGiocatore;
import server.model.stato.giocatore.TurnoMercato;
import server.model.stato.giocatore.TurnoNormale;

/**
 * @author Massimiliano Ventura
 *
 */
public class ViewGUI extends View implements Initializable {

	private Giocatore giocatore;
	private Tabellone tabelloneClient;
	private StatoGiocatore statoAttuale;
	private AtomicBoolean inserimentoAzione;

	@FXML
	private AnchorPane anchorPaneMare;
	@FXML
	private AnchorPane anchorPanePianura;
	@FXML
	private AnchorPane anchorPaneMontagna;
	@FXML
	private AnchorPane anchorPanePercorsi;

	@FXML
	private Button acquistaPermessoButton;

	@FXML
	private Button costruisciConReButton;

	@FXML
	private Button eleggiConsigliereButton;

	@FXML
	private Button costruisciEmporioTesseraButton;

	@FXML
	private Button ingaggiaAiutanteButton;

	@FXML
	private Button cambiaTessereButton;

	@FXML
	private Button consigliereRapidoButton;

	@FXML
	private Button principaleAggiuntivaButton;

	@FXML
	private Button saltaRapidaButton;

	@FXML
	private Label labelAzioneDaFare;
	
	@FXML
	private void ingaggiaAiutanteButtonAction(){};
	@FXML
	private void cambiaTessereButtonAction(){};
	@FXML
	private void consigliereRapidoButtonAction(){};
	@FXML
	private void principaleAggiuntivaButtonAction(){};
	@FXML
	private void saltaRapidaButtonAction(){};
	@FXML
	private void acquistaPermessoButtonAction(){};
	@FXML
	private void costruisciConReButtonAction(){};
	@FXML
	private void eleggiConsigliereButtonAction(){};
	@FXML
	private void costruisciEmporioTesseraButtonAction(){};
	@FXML
	private void confermaAzioneButtonAction(){};

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see client.View#riceviOggetto(java.lang.Object)
	 */
	@Override
	public synchronized void riceviOggetto(Object oggetto) {
		try {
			if (oggetto instanceof String) {
			/*	stampaMessaggio("Messaggio", (String) oggetto);*/
			}
			if (oggetto instanceof Giocatore)
				this.giocatore = (Giocatore) oggetto;
			if (oggetto instanceof Tabellone) {
				tabelloneClient = (Tabellone) oggetto;
				aggiornaStato();
				aggiornaGiocatore();
			}
			if (oggetto instanceof Exception) {
				stampaMessaggio("Errore", ((Exception) oggetto).getMessage());
				inserimentoAzione.set(true);
				if (oggetto instanceof NomeGiaScelto) {
					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen1.fxml"));
					stage.setTitle("CdQ: Configurazione");
					stage.setScene(new Scene((AnchorPane) loader.load()));
					Screen1Controller controller = loader.<Screen1Controller> getController();
					controller.setView(this);
					stage.show();
					Stage thisStage = (Stage) acquistaPermessoButton.getScene().getWindow();
					thisStage.close();
				}
			}
			if (oggetto instanceof BonusGettoneCitta) {
				labelAzioneDaFare.setText("Inserisci il nome di una città\ndove hai un emporio"
						+ " e di cui vuoi ottenere il bonus,\n se non hai un'emporio scrivi 'passa'");

				/*
				  ((BonusGettoneCitta) oggetto).getCitta().add(new
				 Citta(inputString, null));
				 this.getConnessione().inviaOggetto(oggetto);
				 */
			}
			if (oggetto instanceof BonusTesseraPermesso) {
				/*
				 InputOutput.stampa(
				 "Inserisci il numero della tessera permesso che vuoi ottenere"
				 * ); inserimentoBonus.set(true); semBonus.acquire(); try {
				 * TesseraCostruzione tmp =
				 * selezionaTesseraDaTabellone(Integer.parseInt(inputString));
				 * if (tmp != null) ((BonusTesseraPermesso)
				 * oggetto).setTessera(tmp); else { System.out.println(
				 * "Il numero inserito non è corretto"); riceviOggetto(oggetto);
				 * } } catch (NumberFormatException e) { System.out.println(
				 * "la stringa deve essere un numero"); riceviOggetto(oggetto);
				 * } this.getConnessione().inviaOggetto(oggetto);
				 */
			}
			if (oggetto instanceof BonusRiutilizzoCostruzione) {
				/*
				 * InputOutput.stampa(
				 * "inserisci 0 se la tessera è nella lista delle tessere valide, altrimenti 1. Scrivi 'passa' se non hai tessere"
				 * ); inserimentoBonus.set(true); semBonus.acquire(); String
				 * prov = inputString; InputOutput.stampa(
				 * "inserisci il numero della tessera da riciclare");
				 * semBonus.acquire(); try { if ("0".equals(prov)) {
				 * ((BonusRiutilizzoCostruzione) oggetto)
				 * .setTessera(giocatore.getTessereValide().get(Integer.parseInt
				 * (inputString))); } else if ("1".equals(prov)) {
				 * ((BonusRiutilizzoCostruzione) oggetto)
				 * .setTessera(giocatore.getTessereUsate().get(Integer.parseInt(
				 * inputString))); } else if ("passa".equals(prov))
				 * ((BonusRiutilizzoCostruzione) oggetto).setTessera(null);
				 * inserimentoAzione.set(true);
				 * this.getConnessione().inviaOggetto(oggetto); } catch
				 * (IndexOutOfBoundsException | NumberFormatException e) {
				 * InputOutput.stampa("numero tessera non corretto");
				 * riceviOggetto(oggetto); }
				 */
			}
		} catch (IOException e) {
			throw new IllegalStateException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see client.View#startClient()
	 */
	@Override
	public void startClient() {
		
			// fai robe
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScreenGUI.fxml"));
		try {
			Stage stage = new Stage();
			stage.setTitle("Gioco vediamo se va");
			stage.setScene(new Scene((AnchorPane) loader.load()));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		creazioneSfondiMappa();

	}

	public void stampaMessaggio(String nomeFinestra, String msg) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScreenMessaggioErrore.fxml"));
		try {
			Stage stage = new Stage();
			stage.setTitle(nomeFinestra);
			stage.setScene(new Scene((AnchorPane) loader.load()));
			MessaggioErroreController controller = loader.<MessaggioErroreController> getController();
			controller.setMsg(msg);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * takes the state of the player from the game
	 */
	public synchronized void aggiornaStato() {
		for (Giocatore g : tabelloneClient.getGioco().getGiocatori()) {
			if (this.giocatore.getNome().equals(g.getNome()))
				this.statoAttuale = g.getStatoGiocatore();
		}
		if (statoAttuale instanceof AttesaTurno) {
			for (Giocatore g : tabelloneClient.getGioco().getGiocatori())
				if (g.getStatoGiocatore() instanceof TurnoNormale || g.getStatoGiocatore() instanceof TurnoMercato) {
					InputOutput.stampa("Turno di " + g.getNome() + "..");
					InputOutput.stampa("");
				}
		}
		if (statoAttuale instanceof TurnoNormale || statoAttuale instanceof TurnoMercato) {
			InputOutput.stampa("E' il tuo turno");
			InputOutput.stampa("");
		}
	}

	private void aggiornaGUI() {
	}
	
	private void creazioneSfondiMappa(){
		BackgroundImage mare;
		BackgroundImage pianura;
		BackgroundImage montagna;
		BackgroundImage percorsi;
		//int num=Integer.parseInt(tabelloneClient.getNumeroMappa());
		int num=0;
		if(0==num){
			mare = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 418, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
		}else if(1==num){
			mare = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 418, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
		}else if(2==num){
			mare = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 418, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
		}else if(3==num){
			mare = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 418, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
		}else if(4==num){
			mare = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 418, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
		}else if(5==num){
			mare = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 418, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
		}else if(6==num){
			mare = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 418, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
		}else {
			mare = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 418, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 422, 381, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
		}
		percorsi = new BackgroundImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/percorsi.jpg").toString(), 1278, 258, false, true),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,	BackgroundSize.DEFAULT);
		anchorPanePercorsi.setBackground(new Background(percorsi));
		anchorPaneMare.setBackground(new Background(mare));
		anchorPanePianura.setBackground(new Background(pianura));
		anchorPaneMontagna.setBackground(new Background(montagna));
	}

	/**
	 * takes the player from the game and puts it in the attribute giocatore
	 */
	public synchronized void aggiornaGiocatore() {
		for (Giocatore g : tabelloneClient.getGioco().getGiocatori()) {
			if (g.getNome().equals(giocatore.getNome())) {
				giocatore = g;
				break;
			}
		}
	}

	public void disabilitazioneBottoniAzione(boolean value) {
		acquistaPermessoButton.setDisable(value);
		costruisciConReButton.setDisable(value);
		eleggiConsigliereButton.setDisable(value);
		costruisciEmporioTesseraButton.setDisable(value);
		ingaggiaAiutanteButton.setDisable(value);
		cambiaTessereButton.setDisable(value);
		consigliereRapidoButton.setDisable(value);
		principaleAggiuntivaButton.setDisable(value);
		saltaRapidaButton.setDisable(value);
		labelAzioneDaFare.setDisable(value);
	}

}

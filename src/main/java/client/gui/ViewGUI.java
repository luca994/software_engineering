/**
 * 
 */
package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import client.View;
import client.cli.InputOutput;
import eccezione.NomeGiaScelto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import server.model.Giocatore;
import server.model.Gioco;
import server.model.ParseColor;
import server.model.Tabellone;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.componenti.CartaColorata;
import server.model.componenti.CartaPolitica;
import server.model.componenti.Citta;
import server.model.componenti.Consigliere;
import server.model.componenti.Consiglio;
import server.model.componenti.Jolly;
import server.model.componenti.TesseraCostruzione;
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
	private Semaphore semInput = new Semaphore(0);
	private Citta cittaInput;
	private TesseraCostruzione tesseraInput;
	private Consiglio consiglioInput;
	private Consigliere consigliereInput;
	private List<CartaPolitica> cartePoliticaInput = new ArrayList<>();

	ObservableList<ImageView> tessereValide = FXCollections.observableArrayList();
	ObservableList<ImageView> cartePolitica = FXCollections.observableArrayList();
	ObservableList<ImageView> tessereUsate = FXCollections.observableArrayList();
	ObservableList<Color> consiglieriDisponibili = FXCollections.observableArrayList();
	ObservableList<ImageView> tessereCostruzioneValide = FXCollections.observableArrayList();
	ObservableList<ImageView> tessereCostruzioneUsate = FXCollections.observableArrayList();

	public void aggiornaCartePolitica() {
		cartePolitica.clear();
		for (CartaPolitica c : giocatore.getCartePolitica()) {
			if (c instanceof Jolly) {
				cartePolitica.add(new ImageView(new Image(
						getClass().getClassLoader().getResource("immaginiGUI/cartePolitica/jolly.jpg").toString(), 100,
						170, false, false)));
			} else {
				cartePolitica.add(new ImageView(new Image(getClass().getClassLoader()
						.getResource("immaginiGUI/cartePolitica/"
								+ ParseColor.colorIntToString(((CartaColorata) c).getColore().getRGB()) + ".jpg")
						.toString(), 100, 170, false, false)));
			}
		}
		cartePoliticaListView.setItems(cartePolitica);
	}

	public void aggiornaTessereCostruzioneGiocatore(){
		tessereCostruzioneUsate.clear();
		tessereCostruzioneValide.clear();
		for(TesseraCostruzione t: giocatore.getTessereValide()){
			tessereCostruzioneValide.add(new ImageView(new Image(getClass().getClassLoader().getResource("immaginiGUI/tessereCostruzione/tessera"+t.getId()+".jpg").toString(), 100, 100, false, false)));
		}
		for(TesseraCostruzione t: giocatore.getTessereUsate()){
			tessereCostruzioneUsate.add(new ImageView(new Image(getClass().getClassLoader().getResource("immaginiGUI/tessereCostruzione/tessera"+t.getId()+".jpg").toString(), 100, 100, false, false)));
		}
		tessereValideListView.setItems(tessereCostruzioneValide);
		tessereUsateListView.setItems(tessereCostruzioneUsate);
	}
	
	public void aggiornaConsiglieriDisponibili() {
		consiglieriDisponibili.clear();
		for (Consigliere c : tabelloneClient.getConsiglieriDisponibili()) {
			consiglieriDisponibili.add(ParseColor.colorAwtToFx(c.getColore()));
		}
		consigliereDisponibileComboBox.setItems(consiglieriDisponibili);

		consigliereDisponibileComboBox.setButtonCell(new ListCell<Color>() {
			private final Rectangle rectangle;
			{
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				rectangle = new Rectangle(60, 20);
			}

			@Override
			protected void updateItem(Color item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null || empty) {
					setGraphic(null);
				} else {
					rectangle.setFill(item);
					setGraphic(rectangle);
				}
			}
		});

		consigliereDisponibileComboBox.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {
			@Override
			public ListCell<Color> call(ListView<Color> p) {
				return new ListCell<Color>() {
					private final Rectangle rectangle;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						rectangle = new Rectangle(60, 20);
					}

					@Override
					protected void updateItem(Color item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setGraphic(null);
						} else {
							rectangle.setFill(item);
							setGraphic(rectangle);
						}
					}
				};
			}
		});
	}

	@FXML
	private ComboBox<Color> consigliereDisponibileComboBox;

	@FXML
	private ListView<ImageView> tessereValideListView;
	@FXML
	private ListView<ImageView> tessereUsateListView;
	@FXML
	private ListView<ImageView> cartePoliticaListView;

	@FXML
	private AnchorPane anchorPaneMare;
	@FXML
	private AnchorPane anchorPanePianura;
	@FXML
	private AnchorPane anchorPaneMontagna;
	@FXML
	private AnchorPane anchorPanePercorsi;

	@FXML
	private Button annullaAzioneButton;
	@FXML
	private Button confermaAzioneButton;
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
	private Button arkon;
	@FXML
	private Button burgen;
	@FXML
	private Button castrum;
	@FXML
	private Button dorful;
	@FXML
	private Button esti;
	@FXML
	private Button framek;
	@FXML
	private Button indur;
	@FXML
	private Button graden;
	@FXML
	private Button juvelar;
	@FXML
	private Button hellar;
	@FXML
	private Button kultos;
	@FXML
	private Button naris;
	@FXML
	private Button lyram;
	@FXML
	private Button osium;
	@FXML
	private Button merkatim;
	@FXML
	private Button tesseraMare0Button;
	@FXML
	private Button tesseraMare1Button;
	@FXML
	private Button tesseraPianura0Button;
	@FXML
	private Button tesseraPianura1Button;
	@FXML
	private Button tesseraMontagna0Button;
	@FXML
	private Button tesseraMontagna1Button;
	@FXML
	private Button consiglioMareButton;
	@FXML
	private Button consiglioPianuraButton;
	@FXML
	private Button consiglioMontagnaButton;
	@FXML
	private Button consiglioReButton;
	@FXML
	private Button messaggioChatButton;
	@FXML
	private Label labelAzioneDaFare;

	@FXML
	private void annullaAzioneButtonAction() {
	}

	@FXML
	private void messaggioChatButtonAction() {
	}

	@FXML
	private void ingaggiaAiutanteButtonAction() {
	};

	@FXML
	private void cambiaTessereButtonAction() {
	};

	@FXML
	private void confermaAzioneButtonAction() {
		Gioco gioco = new Gioco();
		Giocatore g1 = new Giocatore("pippo");
		this.giocatore = g1;
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(new Giocatore("paolo"));
		gioco.inizializzaPartita("0");
		tabelloneClient = gioco.getTabellone();
		g1.getTessereValide().add(tabelloneClient.getRegioni().get(0).getTessereCoperte().get(3));
		g1.getTessereUsate().add(tabelloneClient.getRegioni().get(0).getTessereCoperte().get(6));
		aggiornaConsiglieriDisponibili();
		aggiornaCartePolitica();
		aggiornaTessereCostruzioneGiocatore();
	}

	@FXML
	private void consigliereRapidoButtonAction() {
	}

	@FXML
	private void principaleAggiuntivaButtonAction() {
	}

	@FXML
	private void saltaRapidaButtonAction() {
	}

	@FXML
	private void acquistaPermessoButtonAction() {
	}

	@FXML
	private void costruisciConReButtonAction() {
	}

	@FXML
	private void eleggiConsigliereButtonAction() {
	}

	@FXML
	private void costruisciEmporioTesseraButtonAction() {
	}

	@FXML
	private void handleCittaButton(ActionEvent event) {
		String nomeCitta = ((Button) event.getSource()).getId();
		labelAzioneDaFare.setText(nomeCitta);
		cittaInput = tabelloneClient.cercaCitta(nomeCitta);
		semInput.release();
	}

	@FXML
	private void handleTesseraMareButton(ActionEvent event) {
		int numTessera = Integer.parseInt(((Button) event.getSource()).getText());
		labelAzioneDaFare.setText(String.valueOf(numTessera));
		tesseraInput = tabelloneClient.getRegioneDaNome("mare").getTessereCostruzione().get(numTessera);
		semInput.release();
	}

	@FXML
	private void handleTesseraPianuraButton(ActionEvent event) {
		int numTessera = Integer.parseInt(((Button) event.getSource()).getText());
		tesseraInput = tabelloneClient.getRegioneDaNome("pianura").getTessereCostruzione().get(numTessera);
		semInput.release();
	}

	@FXML
	private void handleTesseraMontagnaButton(ActionEvent event) {
		int numeroTessera = Integer.parseInt(((Button) event.getSource()).getText());
		tesseraInput = tabelloneClient.getRegioneDaNome("montagna").getTessereCostruzione().get(numeroTessera);
		semInput.release();
	}

	@FXML
	private void handleConsiglioButton(ActionEvent event) {
		String nomeConsiglio = ((Button) event.getSource()).getText();
		if ("re".equalsIgnoreCase(nomeConsiglio)) {
			consiglioInput = tabelloneClient.getRe().getConsiglio();
		} else {
			consiglioInput = tabelloneClient.getRegioneDaNome(nomeConsiglio).getConsiglio();
		}
		semInput.release();
	}

	@FXML
	private void handleConsigliereComboBox(ActionEvent event) {
		int posizione = ((ComboBox) event.getSource()).getSelectionModel().getSelectedIndex();
		consigliereInput = tabelloneClient.getConsiglieriDisponibili().get(posizione);
		labelAzioneDaFare.setText("Colore consigliere: "+ParseColor.colorIntToString(consigliereInput.getColore().getRGB()));
	}

	@FXML
	private void handleCartePoliticaList(MouseEvent event) {
		if(cartePoliticaInput.size()<4){
			int numeroCarta = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
			labelAzioneDaFare.setText(giocatore.getCartePolitica().get(numeroCarta).toString());
			cartePoliticaInput.add(giocatore.getCartePolitica().get(numeroCarta));
			cartePolitica.remove(numeroCarta);
			cartePoliticaListView.setItems(cartePolitica);
		}
		else
			labelAzioneDaFare.setText("Hai già selezionato 4 carte");
	}
	
	@FXML
	private void handleTessereCostruzioneValideList(MouseEvent event){
		int numeroTessera = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
		labelAzioneDaFare.setText(giocatore.getTessereValide().get(numeroTessera).toString());
		tesseraInput = giocatore.getTessereValide().get(numeroTessera);
		semInput.release();
	}

	@FXML
	private void handleTessereCostruzioneUsateList(MouseEvent event){
		int numeroTessera = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
		labelAzioneDaFare.setText(giocatore.getTessereUsate().get(numeroTessera).toString());
		tesseraInput = giocatore.getTessereUsate().get(numeroTessera);
		semInput.release();
	}
	
	private Citta impostaCitta() {
		try {
			disabilitazioneBottoniCitta(false);
			labelAzioneDaFare.setText("Clicca la città che vuoi utilizzare");
			semInput.acquire();
			Citta citta = cittaInput;
			cittaInput = null;
			disabilitazioneBottoniCitta(true);
			return citta;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Consigliere impostaConsigliere(){
		try{
			consigliereDisponibileComboBox.setDisable(false);
			confermaAzioneButton.setDisable(false);
			semInput.acquire();
			Consigliere consigliere = consigliereInput;
			consigliereInput = null;
			consigliereDisponibileComboBox.setDisable(true);
			confermaAzioneButton.setDisable(true);
			return consigliere;
		}catch(InterruptedException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private Consiglio impostaConsiglio() {
		try {
			disabilitazioneBottoniConsigli(false);
			labelAzioneDaFare.setText("Clicca sul consiglio da utilizzare");
			semInput.acquire();
			Consiglio consiglio = consiglioInput;
			consiglioInput = null;
			disabilitazioneBottoniConsigli(true);
			return consiglio;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	private TesseraCostruzione impostaTesseraCostruzioneAcquisto() {
		try {
			disabilitazioneBottoniTessereCostruzione(false);
			labelAzioneDaFare.setText("Clicca sulla tessera che vuoi comprare");
			semInput.acquire();
			TesseraCostruzione tessera = tesseraInput;
			tesseraInput = null;
			disabilitazioneBottoniTessereCostruzione(true);
			return tessera;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<CartaPolitica> impostaCartePolitica(){
		try{
			cartePoliticaListView.setDisable(false);
			semInput.acquire();
			cartePoliticaListView.setDisable(true);
			List<CartaPolitica> cartePolitica = new ArrayList<>(cartePoliticaInput);
			cartePoliticaInput = new ArrayList<>();
			return cartePolitica;
		}catch(InterruptedException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private TesseraCostruzione impostaTesseraCostruzioneGiocatore(boolean valida){
		try{
			if(valida)
				tessereValideListView.setDisable(false);
			else
				tessereUsateListView.setDisable(false);
			semInput.acquire();
			tessereValideListView.setDisable(true);
			tessereUsateListView.setDisable(true);
			TesseraCostruzione tessera = tesseraInput;
			tesseraInput = null;
			return tessera;
		}catch(InterruptedException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private void aggiornaGUI() {
		aggiornaCartePolitica();
		aggiornaConsiglieriDisponibili();
		aggiornaTessereCostruzioneGiocatore();
	}

	@Override
	public void run() {
	}

	@Override
	public synchronized void riceviOggetto(Object oggetto) {
		try {
			if (oggetto instanceof String) {
				// stampaMessaggio("Messaggio", (String) oggetto);
			}
			if (oggetto instanceof Giocatore)
				this.giocatore = (Giocatore) oggetto;
			if (oggetto instanceof Tabellone) {
				tabelloneClient = (Tabellone) oggetto;
				aggiornaStato();
				aggiornaGiocatore();
				aggiornaGUI();
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
				 * ((BonusGettoneCitta) oggetto).getCitta().add(new
				 * Citta(inputString, null));
				 * this.getConnessione().inviaOggetto(oggetto);
				 */
			}
			if (oggetto instanceof BonusTesseraPermesso) {
				/*
				 * InputOutput.stampa(
				 * "Inserisci il numero della tessera permesso che vuoi ottenere"
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		creazioneSfondiMappa();
		//cartePoliticaListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

	private void creazioneSfondiMappa() {
		BackgroundImage mare;
		BackgroundImage pianura;
		BackgroundImage montagna;
		BackgroundImage percorsi;
		// int num=Integer.parseInt(tabelloneClient.getNumeroMappa());
		int num = 0;
		if (0 == num) {
			mare = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 418, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
		} else if (1 == num) {
			mare = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 418, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
		} else if (2 == num) {
			mare = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 418, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
		} else if (3 == num) {
			mare = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 418, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
		} else if (4 == num) {
			mare = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 418, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
		} else if (5 == num) {
			mare = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 418, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
		} else if (6 == num) {
			mare = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 418, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
		} else {
			mare = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			pianura = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 418, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
			montagna = new BackgroundImage(
					new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 422, 381,
							false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
		}
		percorsi = new BackgroundImage(
				new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/percorsi.jpg").toString(), 1278,
						258, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				BackgroundSize.DEFAULT);
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

	/**
	 * takes the player from the game and puts it in the attribute giocatore
	 */
	/*
	 * public synchronized void aggiornaCarte() { CartaColorata c1 = new
	 * CartaColorata(Color.black); CartaColorata c2 = new
	 * CartaColorata(Color.cyan); CartaColorata c3 = new
	 * CartaColorata(Color.white); CartaColorata c4 = new
	 * CartaColorata(Color.orange); CartaColorata c5 = new
	 * CartaColorata(Color.magenta); CartaColorata c6 = new
	 * CartaColorata(Color.pink); Jolly jolly = new Jolly(); Integer b = 0, c =
	 * 0, w = 0, o = 0, m = 0, p = 0, j = 0; for (CartaPolitica cP :
	 * giocatore.getCartePolitica()) { if (cP.isUguale(c1)) b++; if
	 * (cP.isUguale(c2)) c++; if (cP.isUguale(c3)) w++; if (cP.isUguale(c4))
	 * o++; if (cP.isUguale(c5)) m++; if (cP.isUguale(c6)) p++; if
	 * (cP.isUguale(jolly)) j++; }
	 * labelCartaPoliticaBlack.setText(b.toString());
	 * labelCartaPoliticaCyan.setText(c.toString());
	 * labelCartaPoliticaWhite.setText(w.toString());
	 * labelCartaPoliticaOrange.setText(o.toString());
	 * labelCartaPoliticaMagenta.setText(m.toString());
	 * labelCartaPoliticaPink.setText(p.toString());
	 * labelCartaPoliticaJolly.setText(j.toString()); }
	 */

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

	public void disabilitazioneBottoniCitta(boolean value) {
		arkon.setDisable(value);
		burgen.setDisable(value);
		castrum.setDisable(value);
		dorful.setDisable(value);
		esti.setDisable(value);
		framek.setDisable(value);
		graden.setDisable(value);
		juvelar.setDisable(value);
		hellar.setDisable(value);
		indur.setDisable(value);
		kultos.setDisable(value);
		naris.setDisable(value);
		lyram.setDisable(value);
		osium.setDisable(value);
		merkatim.setDisable(value);
	}

	public void disabilitazioneBottoniTessereCostruzione(boolean value) {
		tesseraMare0Button.setDisable(value);
		tesseraMare1Button.setDisable(value);
		tesseraMontagna0Button.setDisable(value);
		tesseraMontagna1Button.setDisable(value);
		tesseraPianura0Button.setDisable(value);
		tesseraPianura1Button.setDisable(value);
	}

	public void disabilitazioneBottoniConsigli(boolean value) {
		consiglioMareButton.setDisable(value);
		consiglioMontagnaButton.setDisable(value);
		consiglioPianuraButton.setDisable(value);
		consiglioReButton.setDisable(value);
	}
}

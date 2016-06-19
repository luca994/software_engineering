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

import client.View;
import eccezione.NomeGiaScelto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
import server.model.ParseColor;
import server.model.Tabellone;
import server.model.azione.AzioneFactory;
import server.model.bonus.Bonus;
import server.model.bonus.BonusGettoneCitta;
import server.model.bonus.BonusRiutilizzoCostruzione;
import server.model.bonus.BonusTesseraPermesso;
import server.model.componenti.CartaColorata;
import server.model.componenti.CartaPolitica;
import server.model.componenti.Citta;
import server.model.componenti.Consigliere;
import server.model.componenti.Consiglio;
import server.model.componenti.Jolly;
import server.model.componenti.Regione;
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
	private Semaphore semInput;
	private Semaphore semInizializzazione;
	private Citta cittaInput;
	private TesseraCostruzione tesseraInput;
	private Consiglio consiglioInput;
	private Consigliere consigliereInput;
	private List<CartaPolitica> cartePoliticaInput = new ArrayList<>();
	private AzioneFactory azioneFactory = new AzioneFactory(null);

	ObservableList<ImageView> tessereValide = FXCollections.observableArrayList();
	ObservableList<ImageView> cartePolitica = FXCollections.observableArrayList();
	ObservableList<ImageView> tessereUsate = FXCollections.observableArrayList();
	ObservableList<Color> consiglieriDisponibili = FXCollections.observableArrayList();
	ObservableList<ImageView> tessereCostruzioneValide = FXCollections.observableArrayList();
	ObservableList<ImageView> tessereCostruzioneUsate = FXCollections.observableArrayList();

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
	private ImageView tesseraMare0Image;
	@FXML
	private Button tesseraMare1Button;
	@FXML
	private ImageView tesseraMare1Image;
	@FXML
	private Button tesseraPianura0Button;
	@FXML
	private ImageView tesseraPianura0Image;
	@FXML
	private Button tesseraPianura1Button;
	@FXML
	private ImageView tesseraPianura1Image;
	@FXML
	private Button tesseraMontagna0Button;
	@FXML
	private ImageView tesseraMontagna0Image;
	@FXML
	private Button tesseraMontagna1Button;
	@FXML
	private ImageView tesseraMontagna1Image;
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
	private void annullaAzioneButtonAction(ActionEvent event) {
	}

	@FXML
	private void messaggioChatButtonAction() {
	}

	@FXML
	private void ingaggiaAiutanteButtonAction() {
		disabilitazioneBottoniAzione(true);
		azioneFactory.setTipoAzione("4");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void cambiaTessereButtonAction() {
		disabilitazioneBottoniAzione(true);
		labelAzioneDaFare.setText("Clicca sul consiglio della regione nella quale vuoi cambiare le tessere");
		consiglioMareButton.setDisable(false);
		consiglioPianuraButton.setDisable(false);
		consiglioMontagnaButton.setDisable(false);
		Regione regioneAzione = impostaConsiglio().getRegione();
		consiglioMareButton.setDisable(true);
		consiglioPianuraButton.setDisable(true);
		consiglioMontagnaButton.setDisable(true);
		azioneFactory.setRegione(regioneAzione);
		azioneFactory.setTipoAzione("5");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void confermaAzioneButtonAction() {
		semInput.release();
		/*
		 * Gioco gioco = new Gioco(); Giocatore g1 = new Giocatore("pippo");
		 * this.giocatore = g1; gioco.getGiocatori().add(g1);
		 * gioco.getGiocatori().add(new Giocatore("paolo"));
		 * gioco.inizializzaPartita("0"); tabelloneClient =
		 * gioco.getTabellone();
		 * g1.getTessereValide().add(tabelloneClient.getRegioni().get(0).
		 * getTessereCoperte().get(3));
		 * g1.getTessereUsate().add(tabelloneClient.getRegioni().get(0).
		 * getTessereCoperte().get(6)); aggiornaGUI();
		 */
	}

	@FXML
	private void consigliereRapidoButtonAction() {
		disabilitazioneBottoniAzione(true);
		labelAzioneDaFare.setText("Clicca sul consiglio nel quale vuoi eleggere il consigliere");
		Consiglio consiglioAzione = impostaConsiglio();
		labelAzioneDaFare.setText("Scegli il consigliere che vuoi eleggere, poi clicca su conferma azione");
		Consigliere consigliereAzione = impostaConsigliere();
		labelAzioneDaFare.setText("");
		azioneFactory.setConsiglio(consiglioAzione);
		azioneFactory.setConsigliere(consigliereAzione);
		azioneFactory.setTipoAzione("6");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void principaleAggiuntivaButtonAction() {
		disabilitazioneBottoniAzione(true);
		azioneFactory.setTipoAzione("7");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void saltaRapidaButtonAction() {
		disabilitazioneBottoniAzione(true);
		azioneFactory.setTipoAzione("8");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void acquistaPermessoButtonAction() {
		disabilitazioneBottoniAzione(true);
		labelAzioneDaFare.setText("Clicca sul consiglio che vuoi soddisfare");
		Consiglio consiglioAzione = impostaConsiglio();
		labelAzioneDaFare.setText("Seleziona carte politica, clicca conferma azione quando hai finito");
		List<CartaPolitica> carteAzione = impostaCartePolitica();
		labelAzioneDaFare.setText("Clicca sulla tessera permesso che vuoi acquistare");
		TesseraCostruzione tesseraAzione = impostaTesseraCostruzioneAcquisto();
		azioneFactory.setConsiglio(consiglioAzione);
		azioneFactory.setCartePolitica(carteAzione);
		azioneFactory.setTesseraCostruzione(tesseraAzione);
		azioneFactory.setTipoAzione("0");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void costruisciConReButtonAction() {
		disabilitazioneBottoniAzione(true);
		labelAzioneDaFare.setText("Seleziona carte politica, clicca conferma azione quando hai finito");
		List<CartaPolitica> carteAzione = impostaCartePolitica();
		labelAzioneDaFare.setText("Clicca sulla città nella quale vuoi costruire");
		Citta cittaAzione = impostaCitta();
		azioneFactory.setCartePolitica(carteAzione);
		azioneFactory.setCitta(cittaAzione);
		azioneFactory.setTipoAzione("1");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void eleggiConsigliereButtonAction() {
		disabilitazioneBottoniAzione(true);
		labelAzioneDaFare.setText("Clicca sul consiglio nel quale vuoi eleggere il consigliere");
		Consiglio consiglioAzione = impostaConsiglio();
		labelAzioneDaFare.setText("Scegli il consigliere che vuoi eleggere, poi clicca su conferma azione");
		Consigliere consigliereAzione = impostaConsigliere();
		labelAzioneDaFare.setText("");
		azioneFactory.setConsiglio(consiglioAzione);
		azioneFactory.setConsigliere(consigliereAzione);
		azioneFactory.setTipoAzione("2");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void costruisciEmporioTesseraButtonAction() {
		disabilitazioneBottoniAzione(true);
		labelAzioneDaFare.setText("Clicca sulla tessera valida che vuoi utilizzare");
		TesseraCostruzione tesseraAzione = impostaTesseraCostruzioneGiocatore(true);
		labelAzioneDaFare.setText("Inserisci la città nella quale vuoi costruire");
		Citta cittaAzione = impostaCitta();
		azioneFactory.setTesseraCostruzione(tesseraAzione);
		azioneFactory.setCitta(cittaAzione);
		azioneFactory.setTipoAzione("3");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void handleCittaButton(ActionEvent event) {
		String nomeCitta = ((Button) event.getSource()).getId();
		cittaInput = tabelloneClient.cercaCitta(nomeCitta);
		semInput.release();
	}

	@FXML
	private void handleTesseraMareButton(ActionEvent event) {
		int numTessera = Integer.parseInt(((Button) event.getSource()).getText());
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
	}

	@FXML
	private void handleCartePoliticaList(MouseEvent event) {
		if (cartePoliticaInput.size() < 4) {
			int numeroCarta = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
			cartePoliticaInput.add(giocatore.getCartePolitica().get(numeroCarta));
			cartePolitica.remove(numeroCarta);
			cartePoliticaListView.setItems(cartePolitica);
		} else
			labelAzioneDaFare.setText("Hai già selezionato 4 carte");
	}

	@FXML
	private void handleTessereCostruzioneValideList(MouseEvent event) {
		int numeroTessera = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
		tesseraInput = giocatore.getTessereValide().get(numeroTessera);
		semInput.release();
	}

	@FXML
	private void handleTessereCostruzioneUsateList(MouseEvent event) {
		int numeroTessera = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
		tesseraInput = giocatore.getTessereUsate().get(numeroTessera);
		semInput.release();
	}

	public Citta impostaCitta() {
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

	private Consigliere impostaConsigliere() {
		try {
			consigliereDisponibileComboBox.setDisable(false);
			confermaAzioneButton.setDisable(false);
			semInput.acquire();
			Consigliere consigliere = consigliereInput;
			consigliereInput = null;
			consigliereDisponibileComboBox.setDisable(true);
			confermaAzioneButton.setDisable(true);
			return consigliere;
		} catch (InterruptedException e) {
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

	public TesseraCostruzione impostaTesseraCostruzioneAcquisto() {
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

	private List<CartaPolitica> impostaCartePolitica() {
		try {
			cartePoliticaListView.setVisible(true);
			;
			confermaAzioneButton.setDisable(false);
			semInput.acquire();
			cartePoliticaListView.setVisible(false);
			confermaAzioneButton.setDisable(true);
			List<CartaPolitica> cartePolitica = new ArrayList<>(cartePoliticaInput);
			cartePoliticaInput = new ArrayList<>();
			return cartePolitica;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	private TesseraCostruzione impostaTesseraCostruzioneGiocatore(boolean valida) {
		try {
			if (valida)
				tessereValideListView.setVisible(true);
			else
				tessereUsateListView.setVisible(true);
			semInput.acquire();
			tessereValideListView.setVisible(false);
			tessereUsateListView.setVisible(false);
			TesseraCostruzione tessera = tesseraInput;
			tesseraInput = null;
			return tessera;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public TesseraCostruzione impostaTesseraRiutilizzoCostruzione() {
		try {
			tessereValideListView.setVisible(true);
			tessereUsateListView.setVisible(true);
			semInput.acquire();
			tessereValideListView.setVisible(false);
			tessereUsateListView.setVisible(false);
			TesseraCostruzione tessera = tesseraInput;
			tesseraInput = null;
			return tessera;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void aggiornaGUI() {
		aggiornaCartePolitica();
		aggiornaConsiglieriDisponibili();
		aggiornaTessereCostruzioneGiocatore();
		aggiornaTessereTabellone();
	}

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

	public void aggiornaTessereCostruzioneGiocatore() {
		tessereCostruzioneUsate.clear();
		tessereCostruzioneValide.clear();
		for (TesseraCostruzione t : giocatore.getTessereValide()) {
			tessereCostruzioneValide.add(new ImageView(new Image(
					getClass().getClassLoader()
							.getResource("immaginiGUI/tessereCostruzione/tessera" + t.getId() + ".jpg").toString(),
					100, 100, false, false)));
		}
		for (TesseraCostruzione t : giocatore.getTessereUsate()) {
			tessereCostruzioneUsate.add(new ImageView(new Image(
					getClass().getClassLoader()
							.getResource("immaginiGUI/tessereCostruzione/tessera" + t.getId() + ".jpg").toString(),
					100, 100, false, false)));
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

	public void aggiornaTessereTabellone() {
		String id0 = tabelloneClient.getRegioni().get(0).getTessereCostruzione().get(0).getId();
		tesseraMare0Image.setImage(new Image(getClass().getClassLoader()
				.getResource("immaginiGUI/tessereCostruzione/tessera" + id0 + ".jpg").toString(), 80, 50, false,
				false));
		String id1 = tabelloneClient.getRegioni().get(0).getTessereCostruzione().get(1).getId();
		tesseraMare1Image.setImage(new Image(getClass().getClassLoader()
				.getResource("immaginiGUI/tessereCostruzione/tessera" + id1 + ".jpg").toString(), 80, 50, false,
				false));
		String id2 = tabelloneClient.getRegioni().get(1).getTessereCostruzione().get(0).getId();
		tesseraPianura0Image.setImage(new Image(getClass().getClassLoader()
				.getResource("immaginiGUI/tessereCostruzione/tessera" + id2 + ".jpg").toString(), 80, 50, false,
				false));
		String id3 = tabelloneClient.getRegioni().get(1).getTessereCostruzione().get(1).getId();
		tesseraPianura1Image.setImage(new Image(getClass().getClassLoader()
				.getResource("immaginiGUI/tessereCostruzione/tessera" + id3 + ".jpg").toString(), 80, 50, false,
				false));
		String id4 = tabelloneClient.getRegioni().get(2).getTessereCostruzione().get(0).getId();
		tesseraMontagna0Image.setImage(new Image(getClass().getClassLoader()
				.getResource("immaginiGUI/tessereCostruzione/tessera" + id4 + ".jpg").toString(), 80, 50, false,
				false));
		String id5 = tabelloneClient.getRegioni().get(2).getTessereCostruzione().get(1).getId();
		tesseraMontagna1Image.setImage(new Image(getClass().getClassLoader()
				.getResource("immaginiGUI/tessereCostruzione/tessera" + id5 + ".jpg").toString(), 80, 50, false,
				false));
	}

	@Override
	public void run() {
	}

	@Override
	public synchronized void riceviOggetto(Object oggetto) {

		try {
			semInizializzazione.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (oggetto instanceof String) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					labelAzioneDaFare.setText((String) oggetto);
				}
			});
		}
		if (oggetto instanceof Giocatore)
			this.giocatore = (Giocatore) oggetto;
		if (oggetto instanceof Tabellone) {
			tabelloneClient = (Tabellone) oggetto;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					aggiornaStato();
					aggiornaGiocatore();
					aggiornaGUI();
				}
			});
		}
		if (oggetto instanceof Exception) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					stampaMessaggio("Errore", ((Exception) oggetto).getMessage());
				}
			});
			if (oggetto instanceof NomeGiaScelto) {
				((Stage) confermaAzioneButton.getScene().getWindow()).close();
			}
		}
		if (oggetto instanceof Bonus) {
			ThreadBonus threadBonus = new ThreadBonus(this, oggetto, labelAzioneDaFare);
			Platform.runLater(threadBonus);

		}

		semInizializzazione.release();

	}

	@Override
	public void startClient() {
		semInput = new Semaphore(0);

		semInizializzazione.release();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		semInizializzazione = new Semaphore(0);
		creazioneSfondiMappa();
		/*
		 * disabilitazioneBottoniAzione(true);
		 * disabilitazioneBottoniCitta(true);
		 * disabilitazioneBottoniConsigli(true);
		 * disabilitazioneBottoniTessereCostruzione(true);
		 * tessereUsateListView.setDisable(true);
		 * tessereValideListView.setDisable(true);
		 * cartePoliticaListView.setDisable(true);
		 * consigliereDisponibileComboBox.setDisable(true);
		 */
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
			disabilitazioneBottoniAzione(true);
			for (Giocatore g : tabelloneClient.getGioco().getGiocatori())
				if (g.getStatoGiocatore() instanceof TurnoNormale || g.getStatoGiocatore() instanceof TurnoMercato) {
					labelAzioneDaFare.setText("Turno di " + g.getNome() + "..");
				}
		}
		if (statoAttuale instanceof TurnoNormale) {
			labelAzioneDaFare.setText("E' il tuo turno");
			disabilitazioneBottoniAzione(false);
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

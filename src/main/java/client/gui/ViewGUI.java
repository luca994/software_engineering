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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import server.model.componenti.Assistente;
import server.model.componenti.CartaColorata;
import server.model.componenti.CartaPolitica;
import server.model.componenti.Citta;
import server.model.componenti.Consigliere;
import server.model.componenti.Consiglio;
import server.model.componenti.Jolly;
import server.model.componenti.OggettoVendibile;
import server.model.componenti.TesseraCostruzione;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.StatoGiocatore;
import server.model.stato.giocatore.TurnoMercato;
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;
import server.model.stato.giocatore.TurnoMercatoCompraVendita;
import server.model.stato.giocatore.TurnoNormale;
import server.model.stato.gioco.FaseTurnoMercatoCompraVendita;

/**
 * @author Massimiliano Ventura
 *
 */
public class ViewGUI extends View implements Initializable {

	private boolean turnoCambiato;
	private Giocatore giocatore;
	private Tabellone tabelloneClient;
	private StatoGiocatore statoAttuale;
	private Semaphore semInizializzazione;
	private Citta cittaInput;
	private TesseraCostruzione tesseraInput;
	private Consiglio consiglioInput;
	private Consigliere consigliereInput;
	private List<CartaPolitica> cartePoliticaInput = new ArrayList<>();
	private AzioneFactory azioneFactory = new AzioneFactory(null);
	private Bonus bonus;
	private ScreenAcquistoMercatoController controllerMercato;

	ObservableList<ImageView> tessereValide = FXCollections.observableArrayList();
	ObservableList<ImageView> cartePolitica = FXCollections.observableArrayList();
	ObservableList<ImageView> tessereUsate = FXCollections.observableArrayList();
	ObservableList<Color> consiglieriDisponibili = FXCollections.observableArrayList();
	ObservableList<ImageView> tessereCostruzioneValide = FXCollections.observableArrayList();
	ObservableList<ImageView> tessereCostruzioneUsate = FXCollections.observableArrayList();

	ObservableList<Color> consiglioMare = FXCollections.observableArrayList();
	ObservableList<Color> consiglioPianura = FXCollections.observableArrayList();
	ObservableList<Color> consiglioMontagna = FXCollections.observableArrayList();
	ObservableList<Color> consiglioRe = FXCollections.observableArrayList();

	@FXML
	private ComboBox<Color> consigliereDisponibileComboBox;

	@FXML
	private ListView<ImageView> tessereValideListView;
	@FXML
	private ListView<ImageView> tessereUsateListView;
	@FXML
	private ListView<ImageView> cartePoliticaListView;
	@FXML
	private ListView<Color> consiglioMareListView;
	@FXML
	private ListView<Color> consiglioPianuraListView;
	@FXML
	private ListView<Color> consiglioMontagnaListView;
	@FXML
	private ListView<Color> consiglioReListView;

	@FXML
	private AnchorPane anchorPaneMare;
	@FXML
	private AnchorPane anchorPanePianura;
	@FXML
	private AnchorPane anchorPaneMontagna;
	@FXML
	private AnchorPane anchorPanePercorsi;

	@FXML
	private TextArea chatTextArea;
	@FXML
	private TextField chatTextField;

	@FXML
	private Rectangle playerColorRectangle;

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
	private Button dortid;
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
	private Label labelNumeroAssistenti;
	@FXML
	private Label labelStatoGioco;
	private Stage stageMercato;

	@FXML
	private void annullaAzioneButtonAction(ActionEvent event) {
		azioneFactory = new AzioneFactory(null);
		cartePoliticaInput = new ArrayList<>();
		consigliereInput = null;
		consiglioInput = null;
		disabilitazioneBottoniAzione(false);
		disabilitazioneBottoniConsigli(true);
		cartePoliticaListView.setDisable(true);
		confermaAzioneButton.setDisable(true);
		consigliereDisponibileComboBox.valueProperty().set(null);
		consigliereDisponibileComboBox.setDisable(true);
		consigliereDisponibileComboBox.setValue(null);
		cittaInput = null;
		tesseraInput = null;
		disabilitazioneBottoniCitta(true);
		disabilitazioneBottoniTessereCostruzione(true);
		tessereValideListView.setDisable(true);
		tessereUsateListView.setDisable(true);
		annullaAzioneButton.setDisable(true);
		aggiornaGUI();
	}

	@FXML
	private void messaggioChatButtonAction() {
		if (!"".equals(chatTextField.getText())) {
			this.getConnessione().inviaOggetto(new MessaggioChat(giocatore.getNome(), chatTextField.getText()));
			chatTextField.clear();
		}
	}

	@FXML
	private void ingaggiaAiutanteButtonAction(ActionEvent event) {
		disabilitazioneBottoniAzione(true);
		azioneFactory.setTipoAzione("4");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void cambiaTessereButtonAction(ActionEvent event) {
		disabilitazioneBottoniAzione(true);
		labelAzioneDaFare.setText("Clicca sul consiglio della regione nella quale vuoi cambiare le tessere, poi clicca su conferma azione");
		consiglioMareButton.setDisable(false);
		consiglioPianuraButton.setDisable(false);
		consiglioMontagnaButton.setDisable(false);
		azioneFactory.setTipoAzione("5");
		confermaAzioneButton.setDisable(false);
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void confermaAzioneButtonAction(ActionEvent event) {
		if (giocatore.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti) {
			this.getConnessione().inviaOggetto("-");
			cartePoliticaListView.setDisable(true);
			tessereValideListView.setDisable(true);
			labelNumeroAssistenti.setDisable(true);
			confermaAzioneButton.setDisable(true);
		} else if (bonus != null) {
			if (bonus instanceof BonusGettoneCitta) {
				((BonusGettoneCitta) bonus).getCitta().add(cittaInput);
			} else if (bonus instanceof BonusTesseraPermesso) {
				((BonusTesseraPermesso) bonus).setTessera(tesseraInput);
			} else {
				((BonusRiutilizzoCostruzione) bonus).setTessera(tesseraInput);
			}
			this.getConnessione().inviaOggetto(bonus);
			bonus = null;
		} else {
			azioneFactory.setCartePolitica(cartePoliticaInput);
			azioneFactory.setCitta(cittaInput);
			azioneFactory.setConsigliere(consigliereInput);
			azioneFactory.setConsiglio(consiglioInput);
			if ("5".equals(azioneFactory.getTipoAzione()))
				azioneFactory.setRegione(consiglioInput.getRegione());
			azioneFactory.setTesseraCostruzione(tesseraInput);
			this.getConnessione().inviaOggetto(azioneFactory);
			azioneFactory = new AzioneFactory(null);
			cartePoliticaInput = new ArrayList<>();
			consigliereInput = null;
			consiglioInput = null;
			disabilitazioneBottoniConsigli(true);
			cartePoliticaListView.setDisable(true);
			confermaAzioneButton.setDisable(true);
			annullaAzioneButton.setDisable(true);
			consigliereDisponibileComboBox.setDisable(true);
			disabilitazioneBottoniAzione(false);
		}
		cittaInput = null;
		tesseraInput = null;
		disabilitazioneBottoniCitta(true);
		disabilitazioneBottoniTessereCostruzione(true);
		tessereValideListView.setDisable(true);
		tessereUsateListView.setDisable(true);
	}

	@FXML
	private void consigliereRapidoButtonAction(ActionEvent event) {
		azioneFactory.setTipoAzione("6");
		confermaAzioneButton.setDisable(false);
		disabilitazioneBottoniAzione(true);
		labelAzioneDaFare.setText("Clicca sul consiglio nel quale vuoi eleggere il consigliere e scegli il consigliere che vuoi eleggere, poi clicca su conferma azione");
		consigliereDisponibileComboBox.setDisable(false);
		disabilitazioneBottoniConsigli(false);
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void principaleAggiuntivaButtonAction(ActionEvent event) {
		disabilitazioneBottoniAzione(true);
		azioneFactory.setTipoAzione("7");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void saltaRapidaButtonAction(ActionEvent event) {
		disabilitazioneBottoniAzione(true);
		azioneFactory.setTipoAzione("8");
		this.getConnessione().inviaOggetto(azioneFactory);
		azioneFactory = new AzioneFactory(null);
		disabilitazioneBottoniAzione(false);
	}

	@FXML
	private void acquistaPermessoButtonAction(ActionEvent event) {
		disabilitazioneBottoniAzione(true);
		confermaAzioneButton.setDisable(false);
		labelAzioneDaFare.setText("Clicca sul consiglio che vuoi soddisfare, seleziona carte politica, clicca sulla tessera permesso che vuoi acquistare, clicca conferma azione quando hai finito");
		consiglioMareButton.setDisable(false);
		consiglioMontagnaButton.setDisable(false);
		consiglioPianuraButton.setDisable(false);
		cartePoliticaListView.setDisable(false);
		disabilitazioneBottoniTessereCostruzione(false);
		azioneFactory.setTipoAzione("0");
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void costruisciConReButtonAction(ActionEvent event) {
		disabilitazioneBottoniAzione(true);
		confermaAzioneButton.setDisable(false);
		labelAzioneDaFare
				.setText("Seleziona carte politica, clicca sulla città nella quale vuoi costruire, clicca conferma azione quando hai finito");
		cartePoliticaListView.setDisable(false);
		disabilitazioneBottoniCitta(false);
		azioneFactory.setTipoAzione("1");
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void eleggiConsigliereButtonAction(ActionEvent event) {
		disabilitazioneBottoniAzione(true);
		confermaAzioneButton.setDisable(false);
		labelAzioneDaFare.setText("Clicca sul consiglio nel quale vuoi eleggere il consigliere scegli il consigliere che vuoi eleggere," + "\n" + " poi clicca su conferma azione");
		consigliereDisponibileComboBox.setDisable(false);
		disabilitazioneBottoniConsigli(false);
		azioneFactory.setTipoAzione("2");
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void costruisciEmporioTesseraButtonAction(ActionEvent event) {
		disabilitazioneBottoniAzione(true);
		confermaAzioneButton.setDisable(false);
		labelAzioneDaFare.setText("Clicca sulla tessera valida che vuoi utilizzare, inserisci la città nella quale vuoi costruire, clicca su conferma azione quando hai finito");
		tessereValideListView.setDisable(false);
		disabilitazioneBottoniCitta(false);
		azioneFactory.setTipoAzione("3");
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void handleCittaButton(ActionEvent event) {
		String nomeCitta = ((Button) event.getSource()).getId();
		cittaInput = tabelloneClient.cercaCitta(nomeCitta);
	}

	@FXML
	private void handleTesseraMareButton(ActionEvent event) {
		int numTessera = Integer.parseInt(((Button) event.getSource()).getText());
		tesseraInput = tabelloneClient.getRegioneDaNome("mare").getTessereCostruzione().get(numTessera);
	}

	@FXML
	private void handleTesseraPianuraButton(ActionEvent event) {
		int numTessera = Integer.parseInt(((Button) event.getSource()).getText());
		tesseraInput = tabelloneClient.getRegioneDaNome("pianura").getTessereCostruzione().get(numTessera);
	}

	@FXML
	private void handleTesseraMontagnaButton(ActionEvent event) {
		int numeroTessera = Integer.parseInt(((Button) event.getSource()).getText());
		tesseraInput = tabelloneClient.getRegioneDaNome("montagna").getTessereCostruzione().get(numeroTessera);
	}

	@FXML
	private void handleConsiglioButton(ActionEvent event) {
		String nomeConsiglio = ((Button) event.getSource()).getText();
		if ("re".equalsIgnoreCase(nomeConsiglio)) {
			consiglioInput = tabelloneClient.getRe().getConsiglio();
			azioneFactory.setConsiglioRe(true);
		} else {
			consiglioInput = tabelloneClient.getRegioneDaNome(nomeConsiglio).getConsiglio();
		}
	}

	@FXML
	private void handleConsigliereComboBox(ActionEvent event) {
		int posizione = ((ComboBox) event.getSource()).getSelectionModel().getSelectedIndex();
		consigliereInput = tabelloneClient.getConsiglieriDisponibili().get(posizione);
	}

	@FXML
	private void handleCartePoliticaList(MouseEvent event) {
		int numeroCarta = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
		if (giocatore.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti) {
			apriScreenPrezzo("Carta Politica", giocatore.getCartePolitica().get(numeroCarta));
		} else if (cartePoliticaInput.size() < 4) {
			cartePoliticaInput.add(giocatore.getCartePolitica().get(numeroCarta));
			cartePolitica.remove(numeroCarta);
			cartePoliticaListView.setItems(cartePolitica);
		} else
			labelAzioneDaFare.setText("Hai già selezionato 4 carte");
	}

	@FXML
	private void handleTessereCostruzioneValideList(MouseEvent event) {
		int numeroTessera = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
		if (giocatore.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti) {
			apriScreenPrezzo("Tessera Costruzione", giocatore.getTessereValide().get(numeroTessera));
		}
		tesseraInput = giocatore.getTessereValide().get(numeroTessera);
	}

	@FXML
	private void handleTessereCostruzioneUsateList(MouseEvent event) {
		int numeroTessera = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
		tesseraInput = giocatore.getTessereUsate().get(numeroTessera);
	}

	@FXML
	private void clickAssistenti(MouseEvent event) {
		apriScreenPrezzo("Assistente", giocatore.getAssistenti().get(0));
	}

	private void aggiornaGUI() {
		creazioneSfondiMappa();
		aggiornaAssistenti();
		aggiornaCartePolitica();
		aggiornaConsiglieriDisponibili();
		aggiornaTessereCostruzioneGiocatore();
		aggiornaTessereTabellone();
		aggiornaConsiglioMare();
		aggiornaConsiglioPianura();
		aggiornaConsiglioMontagna();
		aggiornaConsiglioRe();
		playerColorRectangle.setFill(ParseColor.colorAwtToFx(giocatore.getColore()));
	}

	public void aggiornaAssistenti() {
		int num = 0;
		for (Assistente a : giocatore.getAssistenti()) {
			if (a.getPrezzo() == 0)
				num++;
		}
		labelNumeroAssistenti.setText(String.valueOf(num));
		if (num == 0)
			labelNumeroAssistenti.setDisable(true);
	}

	public void aggiornaConsiglioMare() {
		consiglioMare.clear();
		for (Consigliere c : tabelloneClient.getRegioneDaNome("mare").getConsiglio().getConsiglieri()) {
			consiglioMare.add(ParseColor.colorAwtToFx(c.getColore()));
		}
		consiglioMareListView.setItems(consiglioMare);
		consiglioMareListView.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {
			@Override
			public ListCell<Color> call(ListView<Color> p) {
				return new ListCell<Color>() {
					private final Rectangle rectangle;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						rectangle = new Rectangle(15, 15);
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

	public void aggiornaConsiglioPianura() {
		consiglioPianura.clear();
		for (Consigliere c : tabelloneClient.getRegioneDaNome("pianura").getConsiglio().getConsiglieri()) {
			consiglioPianura.add(ParseColor.colorAwtToFx(c.getColore()));
		}
		consiglioPianuraListView.setItems(consiglioPianura);
		consiglioPianuraListView.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {
			@Override
			public ListCell<Color> call(ListView<Color> p) {
				return new ListCell<Color>() {
					private final Rectangle rectangle;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						rectangle = new Rectangle(15, 15);
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

	public void aggiornaConsiglioRe() {
		consiglioRe.clear();
		for (Consigliere c : tabelloneClient.getRe().getConsiglio().getConsiglieri()) {
			consiglioRe.add(ParseColor.colorAwtToFx(c.getColore()));
		}
		consiglioReListView.setItems(consiglioRe);
		consiglioReListView.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {
			@Override
			public ListCell<Color> call(ListView<Color> p) {
				return new ListCell<Color>() {
					private final Rectangle rectangle;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						rectangle = new Rectangle(15, 15);
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

	public void aggiornaConsiglioMontagna() {
		consiglioMontagna.clear();
		for (Consigliere c : tabelloneClient.getRegioneDaNome("Montagna").getConsiglio().getConsiglieri()) {
			consiglioMontagna.add(ParseColor.colorAwtToFx(c.getColore()));
		}
		consiglioMontagnaListView.setItems(consiglioMontagna);
		consiglioMontagnaListView.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {
			@Override
			public ListCell<Color> call(ListView<Color> p) {
				return new ListCell<Color>() {
					private final Rectangle rectangle;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						rectangle = new Rectangle(15, 15);
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

	public void aggiornaCartePolitica() {
		cartePolitica.clear();
		for (CartaPolitica c : giocatore.getCartePolitica()) {
			if (c instanceof Jolly && c.getPrezzo() == 0) {
				cartePolitica.add(new ImageView(new Image(
						getClass().getClassLoader().getResource("immaginiGUI/cartePolitica/jolly.jpg").toString(), 100,
						170, false, false)));
			} else if (c.getPrezzo() == 0) {
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
			if (t.getPrezzo() == 0) {
				tessereCostruzioneValide.add(new ImageView(new Image(
						getClass().getClassLoader()
								.getResource("immaginiGUI/tessereCostruzione/tessera" + t.getId() + ".jpg").toString(),
						150, 150, false, false)));
			}
		}
		for (TesseraCostruzione t : giocatore.getTessereUsate()) {
			tessereCostruzioneUsate.add(new ImageView(new Image(
					getClass().getClassLoader()
							.getResource("immaginiGUI/tessereCostruzione/tessera" + t.getId() + ".jpg").toString(),
					150, 150, false, false)));
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
		if (oggetto instanceof MessaggioChat) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					chatTextArea.appendText("\n" + "[" + ((MessaggioChat) oggetto).getAutore() + "]: "
							+ ((MessaggioChat) oggetto).getMsg());
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
					annullaAzioneButton.setDisable(true);
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
		semInizializzazione.release();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		turnoCambiato = true;
		semInizializzazione = new Semaphore(0);
		labelNumeroAssistenti.setDisable(true);
		cartePoliticaListView.setDisable(true);
		tessereUsateListView.setDisable(true);
		tessereValideListView.setDisable(true);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScreenAcquistoMercato.fxml"));
		stageMercato = new Stage();
		stageMercato.setTitle("Mercato");
		try {
			stageMercato.setScene(new Scene((AnchorPane) loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		controllerMercato = loader.<ScreenAcquistoMercatoController> getController();
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

	private void apriScreenPrezzo(String titolo, OggettoVendibile obj) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScreenPrezzo.fxml"));
		try {
			Stage stage = new Stage();
			stage.setTitle(titolo + ": inserisci il prezzo");
			stage.setScene(new Scene((AnchorPane) loader.load()));
			ScreenPrezzoController controller = loader.<ScreenPrezzoController> getController();
			controller.setConnessione(this.getConnessione());
			controller.setOggetto(obj);
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
			turnoCambiato = true;
			labelStatoGioco.setText("Attesa turno");
			for (Giocatore g : tabelloneClient.getGioco().getGiocatori())
				if (g.getStatoGiocatore() instanceof TurnoNormale || g.getStatoGiocatore() instanceof TurnoMercato) {
					labelAzioneDaFare.setText("Turno di " + g.getNome() + "..");
				}
		}
		if (statoAttuale instanceof TurnoNormale) {
			if (turnoCambiato) {
				labelAzioneDaFare.setText("E' il tuo turno");
				turnoCambiato = false;
			}
			disabilitazioneBottoniAzione(false);
			labelStatoGioco.setText("Turno normale");
		}
		if (statoAttuale instanceof TurnoMercatoAggiuntaOggetti) {
			labelStatoGioco.setText("Fase mercato aggiunta");
			if(turnoCambiato)
				labelAzioneDaFare.setText("E' il tuo turno");
			cartePoliticaListView.setDisable(false);
			tessereValideListView.setDisable(false);
			if (giocatore.getAssistenti().size() != 0)
				labelNumeroAssistenti.setDisable(false);
			confermaAzioneButton.setDisable(false);
		}
		if (statoAttuale instanceof TurnoMercatoCompraVendita) {
			labelStatoGioco.setText("Fase mercato acquisto");
			controllerMercato.setGiocatore(giocatore);
			controllerMercato
					.setMercato(((FaseTurnoMercatoCompraVendita) tabelloneClient.getGioco().getStato()).getMercato());
			if (turnoCambiato) {
				labelAzioneDaFare.setText("E' il tuo turno");
				controllerMercato.setConnessione(getConnessione());
				stageMercato.show();
				turnoCambiato = false;
			}
		}
	}

	private void creazioneSfondiMappa() {
		BackgroundImage mare;
		BackgroundImage pianura;
		BackgroundImage montagna;
		BackgroundImage percorsi;
		int num = Integer.parseInt(tabelloneClient.getNumeroMappa());

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

	public synchronized void disabilitazioneBottoniAzione(boolean value) {
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

	public synchronized void disabilitazioneBottoniCitta(boolean value) {
		arkon.setDisable(value);
		burgen.setDisable(value);
		castrum.setDisable(value);
		dortid.setDisable(value);
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

	public synchronized void disabilitazioneBottoniTessereCostruzione(boolean value) {
		tesseraMare0Button.setDisable(value);
		tesseraMare1Button.setDisable(value);
		tesseraMontagna0Button.setDisable(value);
		tesseraMontagna1Button.setDisable(value);
		tesseraPianura0Button.setDisable(value);
		tesseraPianura1Button.setDisable(value);
	}

	public synchronized void disabilitazioneBottoniConsigli(boolean value) {
		consiglioMareButton.setDisable(value);
		consiglioMontagnaButton.setDisable(value);
		consiglioPianuraButton.setDisable(value);
		consiglioReButton.setDisable(value);
	}

	/**
	 * @return the tessereValideListView
	 */
	public ListView<ImageView> getTessereValideListView() {
		return tessereValideListView;
	}

	/**
	 * @return the tessereUsateListView
	 */
	public ListView<ImageView> getTessereUsateListView() {
		return tessereUsateListView;
	}

}

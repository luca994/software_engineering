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

import client.view.MessaggioChat;
import client.view.View;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import server.eccezioni.NomeGiaScelto;
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
import server.model.componenti.Regione;
import server.model.componenti.TesseraCostruzione;
import server.model.stato.giocatore.AttesaTurno;
import server.model.stato.giocatore.StatoGiocatore;
import server.model.stato.giocatore.TurnoMercato;
import server.model.stato.giocatore.TurnoMercatoAggiuntaOggetti;
import server.model.stato.giocatore.TurnoMercatoCompraVendita;
import server.model.stato.giocatore.TurnoNormale;
import server.model.stato.gioco.FaseTurnoMercatoCompraVendita;
import server.model.tesserebonus.TesseraBonusCitta;
import server.model.tesserebonus.TesseraBonusRegione;

/**
 * @author Massimiliano Ventura
 *
 */
public class ViewGUI extends View implements Initializable {

	private boolean primoTabellone;
	private boolean turnoCambiato;
	private Giocatore giocatore;
	private Tabellone tabelloneClient;
	private StatoGiocatore statoAttuale;
	private Semaphore semInizializzazione;
	private Citta cittaInput;
	private TesseraCostruzione tesseraInput;
	private Consiglio consiglioInput;
	private Consigliere consigliereInput;
	private List<Rectangle> rettangoliVittoria;
	private List<Rectangle> rettangoliRicchezza;
	private List<Rectangle> rettangoliNobilta;
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

	ObservableList<Color> empArkon = FXCollections.observableArrayList();
	ObservableList<Color> empBurgen = FXCollections.observableArrayList();
	ObservableList<Color> empCastrum = FXCollections.observableArrayList();
	ObservableList<Color> empDortid = FXCollections.observableArrayList();
	ObservableList<Color> empFramek = FXCollections.observableArrayList();
	ObservableList<Color> empGraden = FXCollections.observableArrayList();
	ObservableList<Color> empHellar = FXCollections.observableArrayList();
	ObservableList<Color> empIndur = FXCollections.observableArrayList();
	ObservableList<Color> empJuvelar = FXCollections.observableArrayList();
	ObservableList<Color> empKultos = FXCollections.observableArrayList();
	ObservableList<Color> empLyram = FXCollections.observableArrayList();
	ObservableList<Color> empMerkatim = FXCollections.observableArrayList();
	ObservableList<Color> empNaris = FXCollections.observableArrayList();
	ObservableList<Color> empEsti = FXCollections.observableArrayList();
	ObservableList<Color> empOsium = FXCollections.observableArrayList();

	@FXML
	private ListView<Color> consiglieriDisponibiliListView;

	@FXML
	private Path pathVittoria;
	@FXML
	private Path pathRicchezza;
	@FXML
	private Path pathNobilta;

	@FXML
	private ImageView imgViewMare;
	@FXML
	private ImageView imgViewPianura;
	@FXML
	private ImageView imgViewMontagna;
	@FXML
	private ImageView imgViewPercorsi;
	@FXML
	private ImageView imgViewRe;

	@FXML
	private ListView<Color> emporiArkon;
	@FXML
	private ListView<Color> emporiBurgen;
	@FXML
	private ListView<Color> emporiCastrum;
	@FXML
	private ListView<Color> emporiDortid;
	@FXML
	private ListView<Color> emporiEsti;
	@FXML
	private ListView<Color> emporiFramek;
	@FXML
	private ListView<Color> emporiGraden;
	@FXML
	private ListView<Color> emporiHellar;
	@FXML
	private ListView<Color> emporiIndur;
	@FXML
	private ListView<Color> emporiJuvelar;
	@FXML
	private ListView<Color> emporiKultos;
	@FXML
	private ListView<Color> emporiLyram;
	@FXML
	private ListView<Color> emporiMerkatim;
	@FXML
	private ListView<Color> emporiNaris;
	@FXML
	private ListView<Color> emporiOsium;

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
	private AnchorPane anchorPaneMappa;
	@FXML
	private AnchorPane anchorPaneAzioni;

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
	private ImageView gettoneArkon;
	@FXML
	private ImageView gettoneBurgen;
	@FXML
	private ImageView gettoneCastrum;
	@FXML
	private ImageView gettoneDortid;
	@FXML
	private ImageView gettoneEsti;
	@FXML
	private ImageView gettoneFramek;
	@FXML
	private ImageView gettoneGraden;
	@FXML
	private ImageView gettoneHellar;
	@FXML
	private ImageView gettoneIndur;
	@FXML
	private ImageView gettoneKultos;
	@FXML
	private ImageView gettoneLyram;
	@FXML
	private ImageView gettoneMerkatim;
	@FXML
	private ImageView gettoneOsium;
	@FXML
	private ImageView gettoneNaris;
	@FXML
	private ImageView tesseraCopertaMareImage;
	@FXML
	private Button tesseraMare0Button;
	@FXML
	private ImageView tesseraMare0Image;
	@FXML
	private Button tesseraMare1Button;
	@FXML
	private ImageView tesseraMare1Image;
	@FXML
	private ImageView tesseraCopertaPianuraImage;
	@FXML
	private Button tesseraPianura0Button;
	@FXML
	private ImageView tesseraPianura0Image;
	@FXML
	private Button tesseraPianura1Button;
	@FXML
	private ImageView tesseraPianura1Image;
	@FXML
	private ImageView tesseraCopertaMontagnaImage;
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
	private ImageView tesseraBonusMareImageView;
	@FXML
	private ImageView tesseraBonusPianuraImageView;
	@FXML
	private ImageView tesseraBonusMontagnaImageView;
	@FXML
	private ImageView tesseraBonusFerroImageView;
	@FXML
	private ImageView tesseraBonusBronzoImageView;
	@FXML
	private ImageView tesseraBonusArgentoImageView;
	@FXML
	private ImageView tesseraBonusOroImageView;
	@FXML
	private ImageView tesseraPremioReImageView;
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
	private void annullaAzioneButtonAction() {
		azioneFactory = new AzioneFactory(null);
		cartePoliticaInput = new ArrayList<>();
		consigliereInput = null;
		consiglioInput = null;
		disabilitazioneBottoniAzione(false);
		disabilitazioneBottoniConsigli(true);
		cartePoliticaListView.setDisable(true);
		confermaAzioneButton.setDisable(true);
		consiglieriDisponibiliListView.setDisable(true);
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
		labelAzioneDaFare.setText(
				"Clicca sul consiglio della regione nella quale vuoi cambiare le tessere, poi clicca su conferma azione");
		consiglioMareButton.setDisable(false);
		consiglioPianuraButton.setDisable(false);
		consiglioMontagnaButton.setDisable(false);
		azioneFactory.setTipoAzione("5");
		confermaAzioneButton.setDisable(false);
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void confermaAzioneButtonAction() {
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
			consiglieriDisponibiliListView.setDisable(true);
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
	private void consigliereRapidoButtonAction() {
		azioneFactory.setTipoAzione("6");
		confermaAzioneButton.setDisable(false);
		disabilitazioneBottoniAzione(true);
		labelAzioneDaFare.setText(
				"Clicca sul consiglio nel quale vuoi eleggere il consigliere e scegli il consigliere che vuoi eleggere, poi clicca su conferma azione");
		consiglieriDisponibiliListView.setDisable(false);
		disabilitazioneBottoniConsigli(false);
		annullaAzioneButton.setDisable(false);
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
		confermaAzioneButton.setDisable(false);
		labelAzioneDaFare.setText(
				"Clicca sul consiglio che vuoi soddisfare, seleziona carte politica, clicca sulla tessera permesso che vuoi acquistare, clicca conferma azione quando hai finito");
		consiglioMareButton.setDisable(false);
		consiglioMontagnaButton.setDisable(false);
		consiglioPianuraButton.setDisable(false);
		cartePoliticaListView.setDisable(false);
		disabilitazioneBottoniTessereCostruzione(false);
		azioneFactory.setTipoAzione("0");
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void costruisciConReButtonAction() {
		disabilitazioneBottoniAzione(true);
		confermaAzioneButton.setDisable(false);
		labelAzioneDaFare.setText(
				"Seleziona carte politica, clicca sulla città nella quale vuoi costruire, clicca conferma azione quando hai finito");
		cartePoliticaListView.setDisable(false);
		disabilitazioneBottoniCitta(false);
		azioneFactory.setTipoAzione("1");
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void eleggiConsigliereButtonAction() {
		disabilitazioneBottoniAzione(true);
		confermaAzioneButton.setDisable(false);
		labelAzioneDaFare.setText(
				"Clicca sul consiglio nel quale vuoi eleggere il consigliere scegli il consigliere che vuoi eleggere, poi clicca su conferma azione");
		consiglieriDisponibiliListView.setDisable(false);
		disabilitazioneBottoniConsigli(false);
		azioneFactory.setTipoAzione("2");
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void costruisciEmporioTesseraButtonAction() {
		disabilitazioneBottoniAzione(true);
		confermaAzioneButton.setDisable(false);
		labelAzioneDaFare.setText(
				"Clicca sulla tessera valida che vuoi utilizzare, inserisci la città nella quale vuoi costruire, clicca su conferma azione quando hai finito");
		tessereValideListView.setDisable(false);
		disabilitazioneBottoniCitta(false);
		azioneFactory.setTipoAzione("3");
		annullaAzioneButton.setDisable(false);
	}

	@FXML
	private void handleCittaButton(ActionEvent event) {
		String nomeCitta = ((Button) event.getSource()).getId();
		cittaInput = tabelloneClient.cercaCitta(nomeCitta);
		labelAzioneDaFare.setText("Selezionata: " + nomeCitta);
	}

	@FXML
	private void handleTesseraMareButton(ActionEvent event) {
		int numTessera = Integer.parseInt(((Button) event.getSource()).getText());
		tesseraInput = tabelloneClient.getRegioneDaNome("mare").getTessereCostruzione().get(numTessera);
		labelAzioneDaFare.setText("Selezionata tessera mare numero " + numTessera);
	}

	@FXML
	private void handleTesseraPianuraButton(ActionEvent event) {
		int numTessera = Integer.parseInt(((Button) event.getSource()).getText());
		tesseraInput = tabelloneClient.getRegioneDaNome("pianura").getTessereCostruzione().get(numTessera);
		labelAzioneDaFare.setText("Selezionata tessera pianura numero " + numTessera);
	}

	@FXML
	private void handleTesseraMontagnaButton(ActionEvent event) {
		int numeroTessera = Integer.parseInt(((Button) event.getSource()).getText());
		tesseraInput = tabelloneClient.getRegioneDaNome("montagna").getTessereCostruzione().get(numeroTessera);
		labelAzioneDaFare.setText("Selezionata tessera montagna numero " + numeroTessera);
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
		labelAzioneDaFare.setText("Selezionato consiglio " + nomeConsiglio);
	}

	@SuppressWarnings("rawtypes")
	@FXML
	private void handleConsiglieriDisponibiliListView(MouseEvent event) {
		int posizione = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
		consigliereInput = tabelloneClient.getConsiglieriDisponibili().get(posizione);
	}

	@SuppressWarnings("rawtypes")
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

	@SuppressWarnings("rawtypes")
	@FXML
	private void handleTessereCostruzioneValideList(MouseEvent event) {
		int numeroTessera = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
		if (giocatore.getStatoGiocatore() instanceof TurnoMercatoAggiuntaOggetti) {
			apriScreenPrezzo("Tessera Costruzione", giocatore.getTessereValide().get(numeroTessera));
		}
		tesseraInput = giocatore.getTessereValide().get(numeroTessera);
	}

	@SuppressWarnings("rawtypes")
	@FXML
	private void handleTessereCostruzioneUsateList(MouseEvent event) {
		int numeroTessera = ((ListView) event.getSource()).getSelectionModel().getSelectedIndex();
		tesseraInput = giocatore.getTessereUsate().get(numeroTessera);
	}

	@FXML
	private void clickAssistenti() {
		apriScreenPrezzo("Assistente", giocatore.getAssistenti().get(0));
	}

	private void aggiornaGUI() {
		aggiornaAssistenti();
		aggiornaCartePolitica();
		aggiornaConsiglieriDisponibili();
		aggiornaTessereCostruzioneGiocatore();
		aggiornaTessereTabellone();
		aggiornaTessereBonus();
		aggiornaConsiglioMare();
		aggiornaConsiglioPianura();
		aggiornaConsiglioMontagna();
		aggiornaConsiglioRe();
		aggiornaRicchezza();
		aggiornaNobilta();
		aggiornaVittoria();
		aggiornaRe();
		aggiornaGettoni();
		aggiornaEmpori();
		playerColorRectangle.setFill(ParseColor.colorAwtToFx(giocatore.getColore()));
	}

	private void aggiornaRe() {
		for (Regione r : tabelloneClient.getRegioni())
			for (Citta c : r.getCitta())
				if (c.getRe() != null)
					for (Node n : anchorPaneMappa.getChildren())
						if (n.getId() != null && n instanceof Button)
							if (n.getId().contains(c.getNome().toLowerCase())) {
								imgViewRe.setLayoutX(n.getLayoutX());
								imgViewRe.setLayoutY(n.getLayoutY());
							}
	}

	private void aggiornaAssistenti() {
		int num = 0;
		for (Assistente a : giocatore.getAssistenti()) {
			if (a.getPrezzo() == 0)
				num++;
		}
		labelNumeroAssistenti.setText(String.valueOf(num));
		if (num == 0)
			labelNumeroAssistenti.setDisable(true);
	}

	private void aggiornaConsiglioMare() {
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

	private void aggiornaConsiglioPianura() {
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

	private void aggiornaConsiglioRe() {
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

	private void aggiornaConsiglioMontagna() {
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

	private void aggiornaCartePolitica() {
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

	private void aggiornaTessereCostruzioneGiocatore() {
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

	private void aggiornaConsiglieriDisponibili() {

		consiglieriDisponibili.clear();

		for (Consigliere c : tabelloneClient.getConsiglieriDisponibili()) {
			consiglieriDisponibili.add(ParseColor.colorAwtToFx(c.getColore()));
		}
		consiglieriDisponibiliListView.setItems(consiglieriDisponibili);

		consiglieriDisponibiliListView.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {
			@Override
			public ListCell<Color> call(ListView<Color> p) {
				return new ListCell<Color>() {
					private final Rectangle rectangle;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						rectangle = new Rectangle(20, 20);
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

	private void aggiornaTessereTabellone() {
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
		if (tabelloneClient.getRegioni().get(0).getTessereCoperte().size() > 0)
			tesseraCopertaMareImage.setImage(new Image(
					getClass().getClassLoader()
							.getResource("immaginiGUI/tessereCostruzione/tesseraCostruzioneMareRetro.jpg").toString(),
					80, 50, false, false));
		if (tabelloneClient.getRegioni().get(1).getTessereCoperte().size() > 0)
			tesseraCopertaPianuraImage.setImage(new Image(getClass().getClassLoader()
					.getResource("immaginiGUI/tessereCostruzione/tesseraCostruzionePianuraRetro.jpg").toString(), 80,
					50, false, false));
		if (tabelloneClient.getRegioni().get(2).getTessereCoperte().size() > 0)
			tesseraCopertaMontagnaImage.setImage(new Image(getClass().getClassLoader()
					.getResource("immaginiGUI/tessereCostruzione/tesseraCostruzioneMontagnaRetro.jpg").toString(), 80,
					50, false, false));
	}

	private void aggiornaGettoni() {
		String immagine;
		for (Node i : anchorPaneMappa.getChildren())
			for (Regione r : tabelloneClient.getRegioni())
				for (Citta c : r.getCitta()) {
					if (i.getId() != null && i.getId().contains(c.getNome()) && i.getId().contains("gettone")) {
						if (c.getEmpori().size() > 0)
							immagine = new String(String.valueOf(c.getNumGettone()));
						else
							immagine = new String("gettoneRetro");
						((ImageView) i).setImage(new Image(getClass().getClassLoader()
								.getResource("immaginiGUI/gettoniCittà/" + immagine + ".png").toString(), 56, 35, false,
								false));

					}
				}
	}

	private void aggiornaEmpori() {
		for (Regione r : tabelloneClient.getRegioni()) {
			for (Citta c : r.getCitta()) {
				switch (c.getNome()) {
				case "Arkon": {
					empArkon.clear();
					for (Giocatore g : c.getEmpori()) {
						empArkon.add(ParseColor.colorAwtToFx(g.getColore()));

					}
					emporiArkon.setItems(empArkon);
					setCelleEmpori(emporiArkon);
					break;
				}
				case "Burgen": {
					empBurgen = FXCollections.observableArrayList();
					for (Giocatore g : c.getEmpori()) {
						empBurgen.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiBurgen.setItems(empBurgen);
					setCelleEmpori(emporiBurgen);
					break;
				}
				case "Castrum": {
					empCastrum.clear();
					for (Giocatore g : c.getEmpori()) {
						empCastrum.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiCastrum.setItems(empCastrum);
					setCelleEmpori(emporiCastrum);
					break;
				}
				case "Dortid": {
					empDortid.clear();
					for (Giocatore g : c.getEmpori()) {
						empDortid.add(ParseColor.colorAwtToFx(g.getColore()));
					}

					emporiDortid.setItems(empDortid);
					setCelleEmpori(emporiDortid);
					break;
				}
				case "Esti": {
					empEsti.clear();
					for (Giocatore g : c.getEmpori()) {
						empEsti.add(ParseColor.colorAwtToFx(g.getColore()));
					}

					emporiEsti.setItems(empEsti);
					setCelleEmpori(emporiEsti);
					break;
				}
				case "Framek": {
					empFramek.clear();
					for (Giocatore g : c.getEmpori()) {
						empFramek.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiFramek.setItems(empFramek);
					setCelleEmpori(emporiFramek);
					break;
				}
				case "Graden": {
					empGraden.clear();
					for (Giocatore g : c.getEmpori()) {
						empGraden.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiGraden.setItems(empGraden);
					setCelleEmpori(emporiGraden);
					break;
				}
				case "Hellar": {
					empHellar.clear();
					for (Giocatore g : c.getEmpori()) {
						empHellar.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiHellar.setItems(empHellar);
					setCelleEmpori(emporiHellar);
					break;
				}
				case "Indur": {
					empIndur.clear();
					for (Giocatore g : c.getEmpori()) {
						empIndur.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiIndur.setItems(empIndur);
					setCelleEmpori(emporiIndur);
					break;
				}
				case "Juvelar": {
					empJuvelar.clear();
					for (Giocatore g : c.getEmpori()) {
						empJuvelar.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiJuvelar.setItems(empJuvelar);
					setCelleEmpori(emporiJuvelar);
					break;
				}
				case "Kultos": {
					empKultos.clear();
					for (Giocatore g : c.getEmpori()) {
						empKultos.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiKultos.setItems(empKultos);
					setCelleEmpori(emporiKultos);
				}
				case "Lyram": {
					empLyram.clear();
					for (Giocatore g : c.getEmpori()) {
						empLyram.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiLyram.setItems(empLyram);
					setCelleEmpori(emporiLyram);
					break;
				}
				case "Merkatim": {
					empMerkatim.clear();
					for (Giocatore g : c.getEmpori()) {
						empMerkatim.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiMerkatim.setItems(empMerkatim);
					setCelleEmpori(emporiMerkatim);
					break;
				}
				case "Naris": {
					empNaris.clear();
					for (Giocatore g : c.getEmpori()) {
						empNaris.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiNaris.setItems(empNaris);
					setCelleEmpori(emporiNaris);
					break;
				}
				case "Osium": {
					empOsium.clear();
					for (Giocatore g : c.getEmpori()) {
						empOsium.add(ParseColor.colorAwtToFx(g.getColore()));
					}
					emporiOsium.setItems(empOsium);
					setCelleEmpori(emporiOsium);
					break;
				}

				}

			}
		}
	}

	private void setCelleEmpori(ListView<Color> i) {
		i.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {
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

	private void aggiornaTessereBonus() {
		for (TesseraBonusCitta tessera : tabelloneClient.getTessereBonusCitta()) {
			String colore = ParseColor.colorIntToString(tessera.getColore().getRGB());
			if ("blue".equalsIgnoreCase(colore))
				tesseraBonusFerroImageView
						.setImage(new Image(
								getClass().getClassLoader()
										.getResource("immaginiGUI/tessereBonus/tesseraBonusFerro.jpg").toString(),
								57, 42, false, false));
			else if ("red".equalsIgnoreCase(colore))
				tesseraBonusBronzoImageView
						.setImage(new Image(
								getClass().getClassLoader()
										.getResource("immaginiGUI/tessereBonus/tesseraBonusBronzo.jpg").toString(),
								57, 42, false, false));
			else if ("gray".equalsIgnoreCase(colore))
				tesseraBonusArgentoImageView
						.setImage(new Image(
								getClass().getClassLoader()
										.getResource("immaginiGUI/tessereBonus/tesseraBonusArgento.jpg").toString(),
								57, 42, false, false));
			else if ("yellow".equalsIgnoreCase(colore))
				tesseraBonusOroImageView
						.setImage(
								new Image(
										getClass().getClassLoader()
												.getResource("immaginiGUI/tessereBonus/tesseraBonusOro.jpg").toString(),
										57, 42, false, false));
		}
		if (tabelloneClient.getTesserePremioRe().size() > 0) {
			tesseraPremioReImageView.setImage(new Image(
					getClass().getClassLoader()
							.getResource("immaginiGUI/tessereBonus/tesseraPremioRe"
									+ tabelloneClient.getTesserePremioRe().size() + ".jpg")
							.toString(),
					57, 42, false, false));
		}
		for (TesseraBonusRegione tessera : tabelloneClient.getTessereBonusRegione()) {
			String regione = tessera.getRegione().getNome();
			if ("mare".equalsIgnoreCase(regione))
				tesseraBonusMareImageView.setImage(new Image(getClass().getClassLoader()
						.getResource("immaginiGUI/tessereBonus/tesseraBonusMare.jpg").toString(), 79, 26, false,
						false));
			else if ("pianura".equalsIgnoreCase(regione))
				tesseraBonusPianuraImageView
						.setImage(new Image(
								getClass().getClassLoader()
										.getResource("immaginiGUI/tessereBonus/tesseraBonusPianura.jpg").toString(),
								79, 26, false, false));
			else if ("montagna".equalsIgnoreCase(regione))
				tesseraBonusMontagnaImageView
						.setImage(new Image(
								getClass().getClassLoader()
										.getResource("immaginiGUI/tessereBonus/tesseraBonusMontagna.jpg").toString(),
								79, 26, false, false));
		}
	}

	private void aggiornaVittoria() {
		for (Giocatore g : tabelloneClient.getGioco().getGiocatori()) {
			int index = tabelloneClient.getGioco().getGiocatori().indexOf(g);
			Rectangle recty = rettangoliVittoria.get(index);
			if (tabelloneClient.getPercorsoVittoria().posizioneAttualeGiocatore(g) == 0) {

				recty.setVisible(true);
				recty.setX(
						(3 * index) + 35
								+ ((MoveTo) pathVittoria.getElements()
										.get(tabelloneClient.getPercorsoVittoria().posizioneAttualeGiocatore(g)))
												.getX());
				recty.setY(
						(3 * index) + 17
								+ ((MoveTo) pathVittoria.getElements()
										.get(tabelloneClient.getPercorsoVittoria().posizioneAttualeGiocatore(g)))
												.getY());
			} else {

				recty.setVisible(true);
				recty.setX(
						(3 * index) + 35
								+ ((LineTo) pathVittoria.getElements()
										.get(tabelloneClient.getPercorsoVittoria().posizioneAttualeGiocatore(g)))
												.getX());
				recty.setY(
						(3 * index) + 17
								+ ((LineTo) pathVittoria.getElements()
										.get(tabelloneClient.getPercorsoVittoria().posizioneAttualeGiocatore(g)))
												.getY());
			}
		}
	}

	private void aggiornaNobilta() {
		for (Giocatore g : tabelloneClient.getGioco().getGiocatori()) {
			int index = tabelloneClient.getGioco().getGiocatori().indexOf(g);
			Rectangle recty = rettangoliNobilta.get(index);
			if (tabelloneClient.getPercorsoNobilta().posizioneAttualeGiocatore(g) == 0) {

				recty.setVisible(true);
				recty.setX(85 + ((MoveTo) pathNobilta.getElements()
						.get(tabelloneClient.getPercorsoNobilta().posizioneAttualeGiocatore(g))).getX());
				recty.setY(
						(5 * index) + 539
								+ ((MoveTo) pathNobilta.getElements()
										.get(tabelloneClient.getPercorsoNobilta().posizioneAttualeGiocatore(g)))
												.getY());
			} else {

				recty.setVisible(true);
				recty.setX(85 + ((LineTo) pathNobilta.getElements()
						.get(tabelloneClient.getPercorsoNobilta().posizioneAttualeGiocatore(g))).getX());
				recty.setY(
						(5 * index) + 539
								+ ((LineTo) pathNobilta.getElements()
										.get(tabelloneClient.getPercorsoNobilta().posizioneAttualeGiocatore(g)))
												.getY());
			}
		}
	}

	private void aggiornaRicchezza() {
		for (Giocatore g : tabelloneClient.getGioco().getGiocatori()) {
			int index = tabelloneClient.getGioco().getGiocatori().indexOf(g);
			Rectangle recty = rettangoliRicchezza.get(index);
			if (tabelloneClient.getPercorsoRicchezza().posizioneAttualeGiocatore(g) == 0) {

				recty.setVisible(true);
				recty.setX(64 + ((MoveTo) pathRicchezza.getElements()
						.get(tabelloneClient.getPercorsoRicchezza().posizioneAttualeGiocatore(g))).getX());
				recty.setY(
						(5 * index) + 580
								+ ((MoveTo) pathRicchezza.getElements()
										.get(tabelloneClient.getPercorsoRicchezza().posizioneAttualeGiocatore(g)))
												.getY());
			} else {

				recty.setVisible(true);
				recty.setX(64 + ((LineTo) pathRicchezza.getElements()
						.get(tabelloneClient.getPercorsoRicchezza().posizioneAttualeGiocatore(g))).getX());
				recty.setY(
						(5 * index) + 580
								+ ((LineTo) pathRicchezza.getElements()
										.get(tabelloneClient.getPercorsoRicchezza().posizioneAttualeGiocatore(g)))
												.getY());
			}
		}
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
		if (oggetto instanceof Exception)

		{
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					stampaMessaggio("Errore", ((Exception) oggetto).getMessage());
					annullaAzioneButton.setDisable(true);
				}
			});
			if (oggetto instanceof NomeGiaScelto) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						((Stage) confermaAzioneButton.getScene().getWindow()).close();
					}
				});

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
		primoTabellone = false;
		rettangoliVittoria = new ArrayList<>();
		rettangoliRicchezza = new ArrayList<>();
		rettangoliNobilta = new ArrayList<>();
		cartePoliticaInput = new ArrayList<>();
		azioneFactory = new AzioneFactory(null);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		turnoCambiato = true;
		imgViewRe.setImage(new Image(getClass().getClassLoader().getResource("immaginiGUI/corona.png").toString(), 50,
				50, false, false));
		semInizializzazione = new Semaphore(0);
		labelNumeroAssistenti.setDisable(true);
		cartePoliticaListView.setDisable(true);
		tessereUsateListView.setDisable(true);
		tessereValideListView.setDisable(true);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScreenAcquistoMercato.fxml"));
		stageMercato = new Stage();
		stageMercato.initStyle(StageStyle.UNDECORATED);
		stageMercato.setTitle("Mercato");
		try {
			stageMercato.setScene(new Scene((AnchorPane) loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		controllerMercato = loader.<ScreenAcquistoMercatoController> getController();
	}

	private void stampaMessaggio(String nomeFinestra, String msg) {
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

	private void inizializzaGrafica() {
		if (!primoTabellone) {
			creazioneSfondiMappa();
			imgViewRe.setVisible(true);
			for (Giocatore g : tabelloneClient.getGioco().getGiocatori()) {
				// Rettangoli ricchezza
				Rectangle rectyRicch = new Rectangle(15, 15);
				rectyRicch.setArcWidth(20);
				rectyRicch.setArcHeight(20);
				anchorPaneMappa.getChildren().add(rectyRicch);
				rectyRicch.setVisible(false);
				rectyRicch.setFill(ParseColor.colorAwtToFx(g.getColore()));
				rettangoliRicchezza.add(rectyRicch);

				// rettangoli nobiltà
				Rectangle rectyNob = new Rectangle(15, 15);
				rectyNob.setArcWidth(20);
				rectyNob.setArcHeight(20);
				anchorPaneMappa.getChildren().add(rectyNob);
				rectyNob.setVisible(false);
				rectyNob.setFill(ParseColor.colorAwtToFx(g.getColore()));
				rettangoliNobilta.add(rectyNob);

				// rettangoli vittoria
				Rectangle rectyVit = new Rectangle(15, 15);
				rectyVit.setArcWidth(20);
				rectyVit.setArcHeight(20);
				anchorPaneMappa.getChildren().add(rectyVit);
				rectyVit.setVisible(false);
				rectyVit.setFill(ParseColor.colorAwtToFx(g.getColore()));
				rettangoliVittoria.add(rectyVit);

			}
			primoTabellone = true;
		}
	}

	/**
	 * takes the state of the player from the game
	 */
	private synchronized void aggiornaStato() {
		inizializzaGrafica();
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
			if (turnoCambiato)
				labelAzioneDaFare.setText(
						"E' il tuo turno. Seleziona una carta politica, una tessera costruzione valida oppure clicca sul numero di assistenti per vendere un assistente, poi inserisci il prezzo.");
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
		Image mare;
		Image pianura;
		Image montagna;
		Image percorsi;
		int num = Integer.parseInt(tabelloneClient.getNumeroMappa());

		if (0 == num) {
			mare = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 427, 377,
					false, true);
			pianura = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 426,
					377, false, true);
			montagna = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 427,
					377, false, true);
		} else if (1 == num) {
			mare = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 427, 377,
					false, true);
			pianura = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 426,
					377, false, true);
			montagna = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 427,
					377, false, true);
		} else if (2 == num) {
			mare = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 427, 377,
					false, true);
			pianura = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 426,
					377, false, true);
			montagna = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 427,
					377, false, true);
		} else if (3 == num) {
			mare = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/01.jpg").toString(), 427, 377,
					false, true);
			pianura = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 426,
					377, false, true);
			montagna = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 427,
					377, false, true);
		} else if (4 == num) {
			mare = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 427, 377,
					false, true);
			pianura = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 426,
					377, false, true);
			montagna = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 427,
					377, false, true);
		} else if (5 == num) {
			mare = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 427, 377,
					false, true);
			pianura = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/02.jpg").toString(), 426,
					377, false, true);
			montagna = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 427,
					377, false, true);
		} else if (6 == num) {
			mare = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 427, 377,
					false, true);
			pianura = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 426,
					377, false, true);
			montagna = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/03.jpg").toString(), 427,
					377, false, true);
		} else {
			mare = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/11.jpg").toString(), 427, 377,
					false, true);
			pianura = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/12.jpg").toString(), 426,
					377, false, true);
			montagna = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/13.jpg").toString(), 427,
					377, false, true);
		}
		percorsi = new Image(getClass().getClassLoader().getResource("immaginiGUI/mappe/percorsi.jpg").toString(), 1280,
				263, false, true);
		imgViewPercorsi.setImage(percorsi);
		imgViewMare.setImage(mare);
		imgViewPianura.setImage(pianura);
		imgViewMontagna.setImage(montagna);
	}

	/**
	 * takes the player from the game and puts it in the attribute giocatore
	 */
	private synchronized void aggiornaGiocatore() {
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

	private synchronized void disabilitazioneBottoniConsigli(boolean value) {
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

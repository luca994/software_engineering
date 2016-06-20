/**
 * 
 */
package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.Connessione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import server.model.Giocatore;
import server.model.ParseColor;
import server.model.componenti.Assistente;
import server.model.componenti.CartaColorata;
import server.model.componenti.CartaPolitica;
import server.model.componenti.Jolly;
import server.model.componenti.Mercato;
import server.model.componenti.OggettoVendibile;
import server.model.componenti.TesseraCostruzione;

/**
 * @author Massimiliano Ventura
 *
 */
public class ScreenAcquistoMercatoController implements Initializable {

	private Mercato mercato;
	private Connessione connessione;
	private Giocatore giocatore;
	private ObservableList<ImageView> cartePolitica = FXCollections.observableArrayList();
	private ObservableList<ImageView> tessere = FXCollections.observableArrayList();
	private ObservableList<ImageView> assistenti = FXCollections.observableArrayList();
	private OggettoVendibile oggettoSelezionato;
	@FXML
	private ListView<ImageView> cartePoliticaListView;
	@FXML
	private ListView<ImageView> tessereListView;
	@FXML
	private ListView<ImageView> assistentiListView;
	@FXML
	private Label labelPrezzo;
	@FXML
	private Button compraButton;
	@FXML
	private Button fineButton;

	@FXML
	private void tesseraClick(MouseEvent e) {
		int numeroTessera = ((ListView) e.getSource()).getSelectionModel().getSelectedIndex();
		int indice = 0;
		for (OggettoVendibile o : mercato.getOggettiInVendita()) {
			if (indice == numeroTessera && o instanceof TesseraCostruzione) {
				labelPrezzo.setText(String.valueOf(o.getPrezzo()));
				oggettoSelezionato = o;
				break;
			}
			if (o instanceof TesseraCostruzione)
				indice++;
		}
	}

	@FXML
	private void cartaClick(MouseEvent e) {
		int numeroTessera = ((ListView) e.getSource()).getSelectionModel().getSelectedIndex();
		int indice = 0;
		for (OggettoVendibile o : mercato.getOggettiInVendita()) {
			if (indice == numeroTessera && o instanceof CartaPolitica) {
				labelPrezzo.setText(String.valueOf(o.getPrezzo()));
				oggettoSelezionato = o;
				break;
			}
			if (o instanceof CartaPolitica)
				indice++;
		}
	}

	@FXML
	private void assistenteClick(MouseEvent e) {
		int numeroTessera = ((ListView) e.getSource()).getSelectionModel().getSelectedIndex();
		int indice = 0;
		for (OggettoVendibile o : mercato.getOggettiInVendita()) {
			if (indice == numeroTessera && o instanceof Assistente) {
				labelPrezzo.setText(String.valueOf(o.getPrezzo()));
				oggettoSelezionato = o;
				break;
			}
			if (o instanceof Assistente)
				indice++;
		}
	}

	@FXML
	private void compraButtonAction(ActionEvent e) {
		connessione.inviaOggetto(oggettoSelezionato);
		oggettoSelezionato = null;
	}

	@FXML
	private void fineButtonAction(ActionEvent e) {
		connessione.inviaOggetto("-");
		Stage stage = ((Stage) fineButton.getScene().getWindow());
		stage.hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	private void aggiornaTessere() {
		tessere.clear();
		for (OggettoVendibile o : mercato.getOggettiInVendita()) {
			if (!o.getGiocatore().getNome().equals(giocatore.getNome()) && o instanceof TesseraCostruzione) {
				tessere.add(
						new ImageView(new Image(
								getClass().getClassLoader()
										.getResource("immaginiGUI/tessereCostruzione/tessera"
												+ ((TesseraCostruzione) o).getId() + ".jpg")
										.toString(),
								130, 130, false, false)));
			}
		}
		tessereListView.setItems(tessere);
	}

	private void aggiornaCartePolitica() {
		cartePolitica.clear();
		for (OggettoVendibile o : mercato.getOggettiInVendita()) {
			if (!o.getGiocatore().getNome().equals(giocatore.getNome()) && o instanceof CartaPolitica) {
				if (o instanceof Jolly)
					cartePolitica.add(new ImageView(new Image(
							getClass().getClassLoader().getResource("immaginiGUI/cartePolitica/jolly.jpg").toString(),
							130, 130, false, false)));
				else
					cartePolitica
							.add(new ImageView(new Image(
									getClass().getClassLoader()
											.getResource("immaginiGUI/cartePolitica/" + ParseColor.colorIntToString(
													((CartaColorata) o).getColore().getRGB()) + ".jpg")
											.toString(),
									76, 130, false, false)));
			}
		}
		cartePoliticaListView.setItems(cartePolitica);
	}

	private void aggiornaAssistenti() {
		assistenti.clear();
		for (OggettoVendibile o : mercato.getOggettiInVendita()) {
			if (!o.getGiocatore().getNome().equals(giocatore.getNome()) && o instanceof Assistente) {
				assistenti.add(new ImageView(
						new Image(getClass().getClassLoader().getResource("immaginiGUI/assistente.jpg").toString(), 50,
								130, false, false)));
			}
		}
		assistentiListView.setItems(assistenti);
	}

	/**
	 * @param mercato
	 *            the mercato to set
	 */
	public void setMercato(Mercato mercato) {
		this.mercato = mercato;
		aggiornaAssistenti();
		aggiornaCartePolitica();
		aggiornaTessere();
	}

	/**
	 * @param connessione
	 *            the connessione to set
	 */
	public void setConnessione(Connessione connessione) {
		this.connessione = connessione;
	}

	/**
	 * @param giocatore
	 *            the giocatore to set
	 */
	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}

}

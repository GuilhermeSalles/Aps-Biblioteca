package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Livros;

public class LivrosListController implements Initializable {

	@FXML
	private TableView<Livros> tableViewLivro;

	@FXML
	private TableColumn<Livros, Integer> tableColumnIsbn;
	
	@FXML
	private TableColumn<Livros, String> tableColumnTitulo;
	
	@FXML
	private TableColumn<Livros, Integer> tableColumnIdEditora;
	
	@FXML
	private TableColumn<Livros, Double> tableColumnPreco;
	
	@FXML
	private Button btBusca;
	
	@FXML
	private Button btAdiciona;
	
	@FXML
	public void onbtBuscaAction() {
		System.out.println("btBusca");
	}
	
	@FXML
	public void onbtAdicionaAction() {
		System.out.println("btAdiciona");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnIsbn.setCellValueFactory(new PropertyValueFactory<>("isbnLivro"));
		tableColumnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		tableColumnIdEditora.setCellValueFactory(new PropertyValueFactory<>("editoraId"));
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewLivro.prefHeightProperty().bind(stage.heightProperty());
		
	}
}

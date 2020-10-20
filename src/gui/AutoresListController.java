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
import model.entities.Autor;

public class AutoresListController implements Initializable {

	@FXML
	private TableView<Autor> tableViewAutor;

	@FXML
	private TableColumn<Autor, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Autor, String> tableColumnNome;
	
	@FXML
	private TableColumn<Autor, Integer> tableColumnSobreNome;
	
	@FXML
	private Button btBusca;
	
	@FXML
	private Button btAdiciona;
	
	@FXML
	public void onbtBuscaAction() {
		System.out.println("btBuscaAutor");
	}
	
	@FXML
	public void onbtAdicionaAction() {
		System.out.println("btAdicionaAutor");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("autorId"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnSobreNome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
		
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAutor.prefHeightProperty().bind(stage.heightProperty());
		
	}
}
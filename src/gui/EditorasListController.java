package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Editora;
import model.services.EditoraService;

public class EditorasListController implements Initializable {

	private EditoraService service;
	
	@FXML
	private TableView<Editora> tableViewEditora;

	@FXML
	private TableColumn<Editora, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Editora, String> tableColumnNome;
	
	@FXML
	private TableColumn<Editora, Integer> tableColumnUrl;
	
	@FXML
	private Button btBusca;
	
	@FXML
	private Button btAdiciona;
	
	private ObservableList<Editora> obsList;
	
	@FXML
	public void onbtBuscaAction() {
		System.out.println("btBuscaEditora");
	}
	
	@FXML
	public void onbtAdicionaAction() {
		System.out.println("btAdicionaEditora");
	}

	public void setEditoraService(EditoraService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idEditora"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeEditora"));
		tableColumnUrl.setCellValueFactory(new PropertyValueFactory<>("url"));
		
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewEditora.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service null");
		}
		List<Editora> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewEditora.setItems(obsList);
		
	}
}

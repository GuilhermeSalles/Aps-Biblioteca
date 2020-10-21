package gui;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Autor;
import model.services.AutorService;

public class AutoresListController implements Initializable {

	private AutorService service;
	
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
	
	private ObservableList<Autor> obsList;
	
	@FXML
	public void onbtBuscaAction() {
		System.out.println("btBuscaAutor");
	}
	
	@FXML
	public void onbtAdicionaAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		createdialogForm("/gui/AutorForm.fxml", parentStage);
	}

	public void setAutorService(AutorService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("autorId"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnSobreNome.setCellValueFactory(new PropertyValueFactory<>("segundoNome"));
		
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAutor.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service null");
		}
		List<Autor> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewAutor.setItems(obsList);
		
	}
	
	
	private void createdialogForm(String absoluteName,Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados do Autor");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar View", e.getMessage(), Alert.AlertType.ERROR);
		}
		
	}
}

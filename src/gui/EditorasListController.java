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
	public void onbtAdicionaAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Editora obj = new Editora();
		createdialogForm(obj, "/gui/EditoraForm.fxml", parentStage);
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
	
	private void createdialogForm(Editora obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			
			EditoraFormController controller = loader.getController();
			controller.setEntityEditora(obj);
			controller.setServiceEditora(new EditoraService());
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados da Editora");
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

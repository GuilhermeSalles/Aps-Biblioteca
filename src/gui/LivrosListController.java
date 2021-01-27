package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Livros;
import model.services.AutorService;
import model.services.EditoraService;
import model.services.LivroService;

public class LivrosListController implements Initializable {

	private LivroService service;
	
	@FXML
	private TableView<Livros> tableViewLivro;

	@FXML
	private TableColumn<Livros, Integer> tableColumnIsbn;
	
	@FXML
	private TableColumn<Livros, String> tableColumnTitulo;
	
	@FXML
	private TableColumn<Livros, String> tableColumnIdEditoraNome;
	
	@FXML
	private TableColumn<Livros, String> tableColumnAutorNome;
	
	@FXML
	private TableColumn<Livros, Double> tableColumnPreco;
	
	@FXML
	private TableColumn<Livros, Livros> tableColumnEDIT;
	
	@FXML
	private TableColumn<Livros, Livros> tableColumnREMOVE;
	
	@FXML
	private Button btAdiciona;
	
	private ObservableList<Livros> obsList;
	
	
	@FXML
	public void onbtAdicionaAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Livros obj = new Livros();
		createdialogFormAdd(obj, "/gui/LivroFormAdd.fxml", parentStage);
	}
	
	public void setLivrosService(LivroService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnIsbn.setCellValueFactory(new PropertyValueFactory<>("isbnLivro"));
		tableColumnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		tableColumnIdEditoraNome.setCellValueFactory(new PropertyValueFactory<>("editora"));
		tableColumnAutorNome.setCellValueFactory(new PropertyValueFactory<>("autor"));
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewLivro.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service null");
		}
		List<Livros> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewLivro.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}
	
	private void createdialogForm(Livros obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			LivroFormController controller = loader.getController();
			controller.setEntityLivro(obj);
			controller.setServices(new LivroService(), new EditoraService(), new AutorService());
			controller.loadObjectEditora();
			controller.loadObjectAutor();
			controller.updateFormDataLivro();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados do Livro");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar View", e.getMessage(), Alert.AlertType.ERROR);
		}

	}
	
	private void createdialogFormAdd(Livros obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			LivroFormAddController controller = loader.getController();
			controller.setEntityLivro(obj);
			controller.setServices(new LivroService(), new EditoraService(), new AutorService());
			controller.loadObjectEditora();
			controller.loadObjectAutor();
			controller.updateFormDataLivro();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Altere os dados do Livro");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar View", e.getMessage(), Alert.AlertType.ERROR);
		}
		
	}
	
	
	
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Livros, Livros>() {
			private final Button button = new Button("Alterar");

			@Override
			protected void updateItem(Livros obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createdialogForm(obj, "/gui/LivroForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Livros, Livros>() {
			private final Button button = new Button("Deletar");

			@Override
			protected void updateItem(Livros obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Livros obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que quer deletar?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				service.remove2(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover Objeto", null, e.getMessage(), AlertType.ERROR);
			}

		}
	}
}



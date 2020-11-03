package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.AutorService;
import model.services.EditoraService;
import model.services.LivroService;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemLivro;
	
	@FXML
	private MenuItem menuItemAutor;
	
	@FXML
	private MenuItem menuItemEditora;
	
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	private MenuItem menuItemSAutorBusca;
	
	@FXML
	private MenuItem menuItemSEditoraBusca;
	
	@FXML
	public void onMenuItemLivroAction() {
		loadView("/gui/LivroList.fxml",(LivrosListController controller) -> {
			controller.setLivrosService(new LivroService());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemAutorAction() {
		loadView("/gui/Autor.fxml", (AutoresListController controller) -> {
			controller.setAutorService(new AutorService());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemEditoraAction() {
		loadView("/gui/Editora.fxml", (EditorasListController controller) -> {
			controller.setEditoraService(new EditoraService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAutorBuscaAction() {
		loadView("/gui/AutorBusca.fxml", (AutorBuscaListController controller) -> {
			controller.setAutorBuscaService(new AutorService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemEditoraBuscaAction() {
		loadView("/gui/EditoraBusca.fxml", (EditoraBuscaListController controller) -> {
			controller.setEditoraBuscaService(new EditoraService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemSobreAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	
	}
	
	protected synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}

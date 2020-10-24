package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Autor;
import model.services.AutorService;

public class AutorFormController implements Initializable {

	private AutorService serviceAutor;

	private Autor entityAutor;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtSobreNome;

	@FXML
	private Label labelErrorNome;

	@FXML
	private Label labelErrorSobreNome;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	public void setServiceAutor(AutorService service) {
		this.serviceAutor = service;
	}

	public void setEntityAutor(Autor entity) {
		this.entityAutor = entity;
	}

	@FXML
	public void onBtSalvaAction(ActionEvent event) {
		if (entityAutor == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (serviceAutor == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entityAutor = getFormData();
			serviceAutor.saveOrUpdate(entityAutor);
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error Saving Object", null, e.getMessage(), Alert.AlertType.ERROR);
		} finally {
			MainViewController tbv = new MainViewController();
			tbv.loadView("/gui/Autor.fxml", (AutoresListController controller) -> {
				controller.setAutorService(new AutorService());
				controller.updateTableView();
			});
		}
	}

	private Autor getFormData() {
		Autor obj = new Autor();

		obj.setAutorId((Utils.tryParseToInt(txtId.getText())));
		obj.setNome((txtNome.getText()));
		obj.setSegundoNome((txtSobreNome.getText()));

		return obj;
	}

	@FXML
	public void onBtCancelaAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
		Constraints.setTextFieldMaxLength(txtSobreNome, 30);

	}

	public void updateFormData() {
		if (entityAutor == null) {
			throw new IllegalStateException("Entity was null");
		}

		txtId.setText(String.valueOf(entityAutor.getAutorId()));
		txtNome.setText(entityAutor.getNome());
		txtSobreNome.setText(entityAutor.getSegundoNome());
	}
}

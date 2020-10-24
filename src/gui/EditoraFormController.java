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
import model.entities.Editora;
import model.services.EditoraService;

public class EditoraFormController implements Initializable {

	private EditoraService serviceEditora;

	private Editora entityEditora;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtUrl;

	@FXML
	private Label labelErrorNome;

	@FXML
	private Label labelErrorUrl;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	public void setEntityEditora(Editora entity) {
		this.entityEditora = entity;
	}

	public void setServiceEditora(EditoraService service) {
		this.serviceEditora = service;
	}

	@FXML
	public void onBtSalvaAction(ActionEvent event) {
		if (entityEditora == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (serviceEditora == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entityEditora = getFormData();
			serviceEditora.saveOrUpdate(entityEditora);
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error Saving Object", null, e.getMessage(), Alert.AlertType.ERROR);
		} finally {
			MainViewController tbv = new MainViewController();
			tbv.loadView("/gui/Editora.fxml", (EditorasListController controller) -> {
				controller.setEditoraService(new EditoraService());
				controller.updateTableView();
			});
		}

	}

	private Editora getFormData() {
		Editora obj = new Editora();

		obj.setIdEditora(Utils.tryParseToInt(txtId.getText()));
		obj.setNomeEditora(txtNome.getText());
		obj.setUrl(txtUrl.getText());

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
		Constraints.setTextFieldMaxLength(txtUrl, 80);
	}

	public void updateFormData() {
		if (entityEditora == null) {
			throw new IllegalStateException("Entity was null");
		}

		txtId.setText(String.valueOf(entityEditora.getIdEditora()));
		txtNome.setText(entityEditora.getNomeEditora());
		txtNome.setText(entityEditora.getUrl());
	}
}

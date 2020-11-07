package gui;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import model.entities.Livros;
import model.exceptions.ValidationException;
import model.services.LivroService;

public class LivroFormController implements Initializable {

	private LivroService serviceLivro;

	private Livros entityLivro;

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

	public void setEntityLivro(Livros entity) {
		this.entityLivro = entity;
	}

	public void setServiceLivro(LivroService service) {
		this.serviceLivro = service;
	}

	@FXML
	public void onBtSalvaAction(ActionEvent event) {
		if (entityLivro == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (serviceLivro == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entityLivro = getFormData();
			serviceLivro.saveOrUpdate(entityLivro);
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error Saving Object", null, e.getMessage(), Alert.AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} finally {
			MainViewController tbv = new MainViewController();
			tbv.loadView("/gui/Livros.fxml", (LivrosListController controller) -> {
				controller.setLivrosService(new LivroService());
				controller.updateTableView();
			});
		}

	}

	private Livros getFormData() {
//		Livros obj = new Livros();
//
//		ValidationException exception = new ValidationException("Validação erro");
//
//		obj.setIsbnLivro(txtId.getText());
//
//		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
//			exception.addError("nomeLivros", "Campo não pode ser vazio.");
//		} else {
//			obj.setNome(txtNome.getText());
//		}
//
//		if (txtUrl.getText() == null || txtUrl.getText().trim().equals("")) {
//			exception.addError("Url", "Campo não pode ser vazio.");
//		} else {
//			obj.setUrl(txtUrl.getText());
//		}
//
//		if (exception.getErrors().size() > 0) {
//			throw exception;
//		}
//
//		return obj;
		return null;
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
		Constraints.setTextFieldMaxLength(txtUrl, 30);
	}

	public void updateFormDataLivro() {
		if (entityLivro == null) {
			throw new IllegalStateException("Entity was null");
		}
//
//		txtId.setText(String.valueOf(entityLivros.));
//		txtNome.setText(entityLivros.getNomeLivros());
//		txtUrl.setText(entityLivros.getUrl());
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("nomeLivros")) {
			labelErrorNome.setText(errors.get("nomeLivros"));
		}
		if (fields.contains("Url")) {
			labelErrorUrl.setText(errors.get("Url"));
		}
		
	}
}

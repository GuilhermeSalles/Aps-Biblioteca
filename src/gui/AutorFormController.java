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
import model.entities.Autor;
import model.exceptions.ValidationException;
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
		}  catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}finally {
			MainViewController tbv = new MainViewController();
			tbv.loadView("/gui/Autor.fxml", (AutoresListController controller) -> {
				controller.setAutorService(new AutorService());
				controller.updateTableView();
			});
		}
	}

	private Autor getFormData() {
		Autor obj = new Autor();

		ValidationException exception = new ValidationException("Validação erro");

		obj.setAutorId((Utils.tryParseToInt(txtId.getText())));

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nomeAutor", "Campo não pode ser vazio.");
		} else {
			obj.setNome(txtNome.getText());
		}

		if (txtSobreNome.getText() == null || txtSobreNome.getText().trim().equals("")) {
			exception.addError("sobrenome", "Campo não pode ser vazio.");
		} else {
			obj.setSegundoNome(txtSobreNome.getText());
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

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
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("nomeAutor")) {
			labelErrorNome.setText(errors.get("nomeAutor"));
		} 
		if (fields.contains("sobrenome")) {
			labelErrorSobreNome.setText(errors.get("sobrenome"));
		}
	}
}

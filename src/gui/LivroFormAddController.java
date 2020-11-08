package gui;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Autor;
import model.entities.Editora;
import model.entities.Livros;
import model.entities.LivrosAutores;
import model.exceptions.ValidationException;
import model.services.AutorService;
import model.services.EditoraService;
import model.services.LivroService;

public class LivroFormAddController implements Initializable {

	private Livros entityLivro;

	private LivroService serviceLivro;

	private AutorService serviceAutor;

	private EditoraService serviceEditora;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtTitulo;

	@FXML
	private TextField txtseq;

	@FXML
	private ComboBox<Editora> comboBoxEditora;

	@FXML
	private ComboBox<Autor> comboBoxAutor;

	@FXML
	private TextField txtPrice;

	@FXML
	private Label labelErrorNome;

	@FXML
	private Label labelErrorUrl;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	private ObservableList<Editora> obsListEditora;

	private ObservableList<Autor> obsListAutor;

	public void setEntityLivro(Livros entity) {
		this.entityLivro = entity;
	}

	public void setServices(LivroService serviceL, EditoraService serviceEdi, AutorService serviceAutor) {
		this.serviceLivro = serviceL;
		this.serviceEditora = serviceEdi;
		this.serviceAutor = serviceAutor;
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
			serviceLivro.save(entityLivro);
		
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error Saving Object", null, e.getMessage(), Alert.AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} finally {
			MainViewController tbv = new MainViewController();
			tbv.loadView("/gui/LivroList.fxml", (LivrosListController controller) -> {
				controller.setLivrosService(new LivroService());
				controller.updateTableView();
			});
		}

	}

	private Livros getFormData() {
		Livros obj = new Livros();
		LivrosAutores lAut = new LivrosAutores();

		ValidationException exception = new ValidationException("Validação erro");

		obj.setIsbnLivro(txtId.getText());

		if (txtTitulo.getText() == null || txtTitulo.getText().trim().equals("")) {
			exception.addError("titulo", "Campo não pode ser vazio.");
		} else {
			obj.setTitulo((txtTitulo.getText()));
		}

		obj.setPreco(Double.parseDouble(txtPrice.getText()));
		obj.setAutor(comboBoxAutor.getValue());
		obj.setEditora(comboBoxEditora.getValue());
		
		lAut.setAutorLivroAutor(comboBoxAutor.getValue().getAutorId());
		lAut.setIsbnLivrosAutor(txtId.getText());
		lAut.setSequenciaLivroA(txtseq.getText());
		
		
		obj.setLivrosAutores(lAut);

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
		Constraints.setTextFieldMaxLength(txtId, 30);
		Constraints.setTextFieldMaxLength(txtTitulo, 50);
		Constraints.setTextFieldMaxLength(txtPrice, 10);
		Constraints.setTextFieldInteger(txtseq);
		initializeComboBoxEditora();
		initializeComboBoxAutor();
	}

	public void updateFormDataLivro() {
		if (entityLivro == null) {
			throw new IllegalStateException("Entity was null");
		}

		if (entityLivro.getLivrosAutores() == null) {
			txtseq.setText(String.format("0", entityLivro.getLivrosAutores()));
		} else {
			txtseq.setText(entityLivro.getLivrosAutores().getSequenciaLivroA());
		}

		txtTitulo.setText(entityLivro.getTitulo());

		txtId.setText(entityLivro.getIsbnLivro());

		Locale.setDefault(Locale.US);
		txtPrice.setText(String.format("%.2f", entityLivro.getPreco()));

		if (entityLivro.getEditora() == null) {
			comboBoxEditora.getSelectionModel().selectFirst();
		} else {
			comboBoxEditora.setValue(entityLivro.getEditora());
		}

		if (entityLivro.getAutor() == null) {
			comboBoxAutor.getSelectionModel().selectFirst();
		} else {
			comboBoxAutor.setValue(entityLivro.getAutor());
		}
	}

	public void loadObjectEditora() {
		if (serviceEditora == null) {
			throw new IllegalStateException("ServiceEditora was Null");
		}
		List<Editora> list = serviceEditora.findAll();
		obsListEditora = FXCollections.observableArrayList(list);
		comboBoxEditora.setItems(obsListEditora);
	}

	public void loadObjectAutor() {
		if (serviceAutor == null) {
			throw new IllegalStateException("ServiceAutor was Null");
		}
		List<Autor> list = serviceAutor.findAll();
		obsListAutor = FXCollections.observableArrayList(list);
		comboBoxAutor.setItems(obsListAutor);
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("titulo")) {
			labelErrorNome.setText(errors.get("titulo"));
		}
	}

	private void initializeComboBoxEditora() {
		Callback<ListView<Editora>, ListCell<Editora>> factory = lv -> new ListCell<Editora>() {
			@Override
			protected void updateItem(Editora item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNomeEditora());
			}
		};
		comboBoxEditora.setCellFactory(factory);
		comboBoxEditora.setButtonCell(factory.call(null));
	}

	private void initializeComboBoxAutor() {
		Callback<ListView<Autor>, ListCell<Autor>> factory = lv -> new ListCell<Autor>() {
			@Override
			protected void updateItem(Autor item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNomeAutor());
			}
		};
		comboBoxAutor.setCellFactory(factory);
		comboBoxAutor.setButtonCell(factory.call(null));
	}

}

package gui.view.teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import connection.ConnectionError;
import connection.ConnectionException;
import gui.Controller;
import gui.Main;
import gui.SelectorController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import model.Aula;
import model.Profesor;

/**
 * Controlador de la pantalla de creación de profesores
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class TeacherViewController extends SelectorController<Aula> {

	@FXML
	TextField name;
	@FXML
	TextField surname1;
	@FXML
	TextField surname2;
	@FXML
	TextField NIF;
	@FXML
	TextArea description;
	@FXML
	TextField password;
	@FXML
	ChoiceBox<String> rights;
	private final Callback<ListView<Aula>, ListCell<Aula>> callback = new Callback<ListView<Aula>, ListCell<Aula>>() {

		@Override
		public ListCell<Aula> call(ListView<Aula> classrooms) {
			ListCell<Aula> cellsList = new ListCell<Aula>() {
				@Override
				protected void updateItem(Aula cla, boolean empty) {
					super.updateItem(cla, empty);
					if (cla != null) {
						setText(cla.getNombre());
					} else {
						setText("");
					}
				}
			};
			return cellsList;
		}
	};

	/**
	 * Inicializa los elementos gráficos
	 */
	@FXML
	private void initialize() {
		List<String> choices = new ArrayList<String>();
		choices.add("Sí");
		choices.add("No");
		this.rights.getItems().addAll(choices);
		this.rights.getSelectionModel().select("No");
	}

	/**
	 * Cancela la creación del profesor y vuelve atrás
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void cancel() throws IOException {
		if (cancelAlert()) {
			Main.setModifiedData(false);
			super.goBack();
		}

	}

	/**
	 * Comienza la transacción que inserta el nuevo profesor en la base de datos
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void acept() throws IOException {
		Profesor tea = new Profesor();
		tea.setNombre(this.name.getText());
		tea.setApellido1(this.surname1.getText());
		tea.setApellido2(this.surname2.getText());
		tea.setNif(this.NIF.getText());
		tea.setNotas(this.description.getText());
		tea.setPermisos(this.rights.getSelectionModel().getSelectedItem().equals("No") ? false : true);
		tea.setAulas(super.getSelectedObjects());

		try {
			if (this.password.getText().equals("")) {
				throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
			}else if(super.passPattern.matcher(this.password.getText()).find()) {
				throw new ConnectionException(ConnectionError.WRONF_PASSWORD);
			}else if(this.password.getText().length() < 8){
				throw new ConnectionException(ConnectionError.WRONG_PASSWORD_LENGTH);
			}else {
				tea.setContrasena(Controller.encryptPassword(this.password.getText()));
			}
			if (Main.getTeacherService().add(tea)) {
				Alert alert = new Alert(AlertType.INFORMATION, "El nuevo profesor se ha creado correctamente",
						ButtonType.OK);
				alert.initOwner(Main.getPrimaryStage());
				alert.showAndWait();
				Main.setModifiedData(false);
				Main.showManageView();
			}
		} catch (ConnectionException cEx) {
			Alert alert = new Alert(AlertType.ERROR, cEx.getError().getText(), ButtonType.OK);
			alert.initOwner(Main.getPrimaryStage());
			alert.showAndWait();
		}

	}

	/**
	 * Inicializa los datos de las aulas en las listas que permiten 
	 * seleccionarlas para poder relaccionarlas con el profesor.
	 * @param allClassrooms lista de todas las aulas
	 */
	public void setClassrooms(List<Aula> allClassrooms) {
		super.initialize(callback, allClassrooms, new SortClassroom());
	}

	/**
	 * Subclase para ordenar las aulas
	 * @author Mario Núñez Izquierdo
	 * @version 1.0
	 *
	 */
	private class SortClassroom implements Comparator<Aula> {
		@Override
		public int compare(Aula a1, Aula a2) {
			return String.CASE_INSENSITIVE_ORDER.compare(a1.getNombre(), a2.getNombre());
		}

	}

}

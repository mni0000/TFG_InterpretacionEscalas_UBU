package gui.view.teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import connection.ConnectionError;
import connection.ConnectionException;
import connection.manageService.ClassroomServiceImpl;
import connection.manageService.ManageService;
import gui.Main;
import gui.view.Controller;
import gui.view.SelectorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;
import model.Profesor;

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

	@FXML
	private void initialize() {
		List<String> choices = new ArrayList<String>();
		choices.add("S�");
		choices.add("No");
		this.rights.getItems().addAll(choices);
		this.rights.getSelectionModel().select("No");
	}

	@FXML
	private void cancel() throws IOException {
		if (cancelAlert()) {
			Main.setModifiedData(false);
			super.goBack();
		}

	}

	@FXML
	private void acept() throws IOException {
		Profesor tea = new Profesor();
		tea.setNombre(this.name.getText());
		tea.setApellido1(this.surname1.getText());
		tea.setApellido2(this.surname2.getText());
		tea.setNif(this.NIF.getText());
		tea.setNotas(this.description.getText());
		tea.setContrasena(this.password.getText());
		tea.setPermisos(this.rights.getSelectionModel().getSelectedItem().equals("No") ? false : true);
		tea.setAulas(super.getSelectedObjects());

		try {
			if (Main.getTeacherService().add(tea)) {
				Alert alert = new Alert(AlertType.INFORMATION, "El nuevo profesor se ha creado correctamente",
						ButtonType.OK);
				alert.showAndWait();
				Main.setModifiedData(false);
				Main.showManageView();
			}
		} catch (ConnectionException cEx) {
			Alert alert = new Alert(AlertType.ERROR, cEx.getError().getText(), ButtonType.OK);
			alert.showAndWait();
		}

	}

	public void setClassrooms(List<Aula> allClassrooms) {
		super.initialize(callback, allClassrooms, new SortClassroom());
	}

	private class SortClassroom implements Comparator<Aula> {
		@Override
		public int compare(Aula a1, Aula a2) {
			return String.CASE_INSENSITIVE_ORDER.compare(a1.getNombre(), a2.getNombre());
		}

	}

}

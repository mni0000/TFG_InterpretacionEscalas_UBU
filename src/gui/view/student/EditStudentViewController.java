package gui.view.student;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import connection.manageService.ClassroomServiceImpl;
import connection.manageService.ManageService;
import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;
import model.Profesor;

public class EditStudentViewController extends Controller {

	@FXML
	private TextField nombre;
	@FXML
	private TextField primerApellido;
	@FXML
	private TextField segundoApellido;
	@FXML
	private TextField NIF;
	@FXML
	private TextField direccion;
	@FXML
	private DatePicker fechaNacimiento;
	@FXML
	private ChoiceBox<Aula> aulaCB;
	
	private List<Aula> listAllClassrooms;
	private Alumno stu;
	
	@FXML
	private void loadClassrooms() {
		ObservableList<Aula> obsList = FXCollections.observableArrayList();
		obsList.addAll(this.listAllClassrooms);
		
		aulaCB.setItems(obsList);
		aulaCB.getSelectionModel().selectFirst();
		aulaCB.setConverter(new StringConverter<Aula>() {

			@Override
			public Aula fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Aula object) {
				return object.getNombre();
			}
			
		});
	}

	public void setStudentAndClassrooms(Alumno stu, List<Aula> allClassrooms) {
		this.stu = stu;
		this.listAllClassrooms = allClassrooms;
		this.fillFields();
	}

	@FXML
	private void fillFields() {
		this.nombre.setText(stu.getNombre());
		this.primerApellido.setText(stu.getApellido1());
		this.segundoApellido.setText(stu.getApellido2());
		this.NIF.setText(stu.getNif());

		Date fecha = stu.getFechaNacimiento();
		if (fecha != null) {
			this.fechaNacimiento
					.setValue(Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
		}
		
		this.loadClassrooms();
		Aula aula = stu.getAula();
		if(aula != null) {
			this.aulaCB.getSelectionModel().clearSelection();
			this.aulaCB.getSelectionModel().select(aula);
		}
	}

	@FXML
	private void cancel() throws IOException {
		if (cancelAlert()) {
			Main.setModifiedData(false);
			Main.showManageView();
		}
	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y sobreescibir los cambios del profesor.");
	}

}

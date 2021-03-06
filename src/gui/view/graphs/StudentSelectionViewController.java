package gui.view.graphs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gui.Main;
import gui.SelectorController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;

/**
 * Controlador de la pantallad e selección de alumnos para la generación de gráficos
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class StudentSelectionViewController extends SelectorController<Alumno> {

	@FXML
	ComboBox<Aula> classroom;
	private final Callback<ListView<Alumno>, ListCell<Alumno>> callback = new Callback<ListView<Alumno>, ListCell<Alumno>>(){
		
		@Override
		public ListCell<Alumno> call(ListView<Alumno> students){
			ListCell<Alumno> cellsList = new ListCell<Alumno>() {
				
				@Override
				protected void updateItem(Alumno stu, boolean empty) {
					super.updateItem(stu, empty);
					if(stu != null) {
						setText("" + stu.getApellido1() + " " + stu.getApellido2() + ", " + stu.getNombre());
					}else {
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
		this.classroom.setConverter(new StringConverter<Aula>() {

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
	
	/**
	 * Avanza a la siguiente pantalla del proceso de generación de gráficos,
	 * la pantalla de selección de aulas.
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void next() throws IOException {
		if(super.getSelectedObjects().size() > 0) {
			boolean flag = false;
			for(Alumno stu: super.getSelectedObjects()) {
				if(stu.getEvaluacions() == null || stu.getEvaluacions().size() == 0) {
					flag = true;
					Alert alert = new Alert(AlertType.INFORMATION, "El alumno " + stu.getNombre() + " " + stu.getApellido1() + " " + stu.getApellido2() + " no tiene ninguna evaluacion asignada. Asígnale una o más evaluaciones o deseleccionalo.");
					alert.initOwner(Main.getPrimaryStage());
					alert.setTitle("Alerta");
					alert.setHeaderText("");
					alert.show();
					break;
				}
			}
			if(!flag) {
				Main.showEvaluationSelectionView(super.getSelectedObjects());
			}
		}else {
			Alert alert = new Alert(AlertType.INFORMATION, "Debes seleccionar almenos 1 alumno antes de continuar.");
			alert.initOwner(Main.getPrimaryStage());
			alert.setTitle("Alerta");
			alert.setHeaderText("");
			alert.show();
		}
	}
	
	/**
	 * Cambia los alumnos mostrados según el aula selecionada
	 */
	@FXML
	private void switchDisplay() {
		List<Alumno> students = new ArrayList<Alumno>();
		students.addAll(this.classroom.getSelectionModel().getSelectedItem().getAlumnos());
		students.removeAll(super.getSelectedObjects());
		super.getDisplayedObjects().clear();
		super.getDisplayedObjects().addAll(students);
		super.sortObjects();
	}
	
	/**
	 * Establece las aulas del profesor actual, todas si es un administrador.
	 * @param classRooms aulas de las que se mostrarán los alumnos para seleccionar
	 */
	public void setClassrooms(List<Aula> classRooms) {
		List<Alumno> students = new ArrayList<Alumno>();
		Collections.sort(classRooms, new SortClassroom());
		students.addAll(classRooms.get(0).getAlumnos());
		super.initialize(callback, students, new SortStudent());
		this.classroom.getItems().addAll(classRooms);
		this.classroom.getSelectionModel().selectFirst();
	}
	
	/**
	 * Subclase para ordenar los alumnos
	 * @author Mario Núñez Izquierdo
	 * @version 1.0
	 *
	 */
	private class SortStudent implements Comparator<Alumno>{
		@Override
		public int compare(Alumno a1, Alumno a2) {
			String a1Display = "" + a1.getApellido1() + " " + a1.getApellido2() + ", " + a1.getNombre();
			String a2Display = "" + a2.getApellido1() + " " + a2.getApellido2() + ", " + a2.getNombre();
			return String.CASE_INSENSITIVE_ORDER.compare(a1Display, a2Display);	
		}
		
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

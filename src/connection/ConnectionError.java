package connection;
/**
 * Enumeración con los errores que se imprimirán cuando el usuario introduzca
 * valores erroneos en los campos de los objetos a insertar en la base de datos.
 * @author Mario Núñez Izquierdo
 * @version 1.0
 */
public enum ConnectionError {
	WRONG_NAME("ERROR: El nombre solo debe contener letras."),
	WRONG_CLASSROOM_NAME("ERROR: El nombre no puede contener carácteres especiales."),
	WRONG_SURNAME("ERROR: Los apellidos deben contener solo letras."),
	WRONG_DATE("ERROR: La fecha no puede ser posterior a la fecha actual."),
	WRONG_NIF("ERROR: El NIF debe tener un formato de 8 dígitos seguidos de una letra mayúscula."),
	WRONG_CAPACITY("ERROR: La capacidad debe ser un numero y no puede quedar vacía."),
	WRONG_PASSWORD_LENGTH("ERROR: La contraseña debe tener como mínimo 8 carácteres y hasta un máximo de 64."),
	WRONG_ADMIN_RIGHTS("ERROR: No puedes quitarle los permisos de edición a la cuenta de administrador"),
	WRONG_CODE("ERROR: La longitud del código debe ser inferior a 30 carácteres."),
	WRONF_PASSWORD("ERROR: La contraseña no puede contener ninguno de los carácteres que hay entre los paréntesis ( , ; . : \" \' ), los paréntesis sí pueden usarse."),
	THERE_ARE_NO_CLASSROOMS("ERROR: No hay ninguna aula a la que puedas añadir el alumno. Crea un aula primero y vuelve a intentarlo."),
	THERE_IS_NO_PUNTUATIONS("ERROR: No puedes crear una evaluación sin poner por al menos una nota a un item."),
	CANT_DELETE_ADMIN("ERROR: No puedes eliminar la cuenta de administrador (ADMIN)."),
	CANT_DELETE_CLASSROOM("ERROR: No puedes borrar este aula porque aún tiene alumnos asignados."),
	NAME_TOO_LONG("ERROR: La longitud del nombre debe ser inferior a 50 carácteres."),
	SURNAME_TOO_LONG("ERROR: La longitud de los apellidos debe ser inferior a 75 carácteres."),
	DIRECTION_TOO_LONG("ERROR: La longitud de la dirección no puede ser superior a 150 carácteres."),
	DESCRIPTION_TOO_LONG("ERROR: La longitud de la descripción no puede ser superior a 1000 carácteres."),
	CLASSROOM_IS_FULL("ERROR: La clase a la que estas intentando añadir el alumno esta llena."),
	FIELD_IS_EMPTY("ERROR: Los campos marcados con * son obligatorios y no pueden quedar en blanco."),
	CLASSROOM_ALREADY_EXISTS("ERROR: Ya existe un aula con el mismo nombre."),
	TEACHER_ALREADY_EXISTS("ERROR: Ya existe un profesor con el mismo NIF.");
	
	
	private String text;
	
	/**
	 * Constructor simple
	 * @param text texto de error asignado
	 */
	private ConnectionError(String text) {
		this.text = text;
	}
	
	/**
	 * Método getter
	 * @return texto de error asignado
	 */
	public String getText() {
		return this.text;
	}

}

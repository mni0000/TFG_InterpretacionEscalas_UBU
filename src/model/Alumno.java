package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the alumno database table.
 * 
 */
@Entity
@Table(name="ALUMNO", schema="public")
@NamedQueries({ 
	@NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a"),
	@NamedQuery(name = "Alumno.findByCodigo", query = "SELECT a FROM Alumno a WHERE a.codigo = :codigo"),
	@NamedQuery(name="Alumno.findById", query="SELECT a FROM Alumno a WHERE a.id = :id")
})
public class Alumno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ALUMNO_ID_GENERATOR", sequenceName="SEQ_ALUMNO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ALUMNO_ID_GENERATOR")
	private long id;

	private String apellido1;

	private String apellido2;

	private String codigo;

	private String direccion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nacimiento")
	private Date fechaNacimiento;

	private String nombre;

	private String notas;

	//bi-directional many-to-one association to Aula
	@ManyToOne
	@JoinColumn(name="id_aula")
	private Aula aula;

	//bi-directional many-to-one association to Evaluacion
	@OneToMany(mappedBy="alumno", fetch= FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Evaluacion> evaluacions = new ArrayList<Evaluacion>();

	public Alumno() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNotas() {
		return this.notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public Aula getAula() {
		return this.aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	public List<Evaluacion> getEvaluacions() {
		return this.evaluacions;
	}

	public void setEvaluacions(List<Evaluacion> evaluacions) {
		this.evaluacions = evaluacions;
	}

	public Evaluacion addEvaluacion(Evaluacion evaluacion) {
		getEvaluacions().add(evaluacion);
		evaluacion.setAlumno(this);

		return evaluacion;
	}

	public Evaluacion removeEvaluacion(Evaluacion evaluacion) {
		getEvaluacions().remove(evaluacion);
		evaluacion.setAlumno(null);

		return evaluacion;
	}
	
	@Override
	public boolean equals(Object stu) {
		if(stu instanceof Alumno && this.id == ((Alumno) stu).getId()) {
			return true;
		}else {
			return false;
		}
		
	}

}
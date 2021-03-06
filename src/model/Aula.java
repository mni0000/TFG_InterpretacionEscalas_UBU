package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the aula database table.
 * 
 */
@Entity
@Table(name="AULA", schema="public")
@NamedQueries({ 
	@NamedQuery(name="Aula.findAll", query="SELECT a FROM Aula a"),
	@NamedQuery(name="Aula.findByName", query="SELECT a FROM Aula a WHERE a.nombre = :name"),
	@NamedQuery(name="Aula.findById", query="SELECT a FROM Aula a WHERE a.id = :id")
})
public class Aula implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AULA_ID_GENERATOR", sequenceName="SEQ_AULA", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AULA_ID_GENERATOR")
	private long id;

	private int capacidad;

	private String nombre;

	private String notas;

	//bi-directional many-to-one association to Alumno
	@OneToMany(mappedBy="aula", fetch= FetchType.EAGER)
	private List<Alumno> alumnos = new ArrayList<Alumno>();

	//bi-directional many-to-many association to Profesor
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name="aula_profesor"
		, joinColumns={
			@JoinColumn(name="id_aula")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_profesor")
			}
		)
	private Set<Profesor> profesors = new HashSet<Profesor>();

	public Aula() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
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

	public List<Alumno> getAlumnos() {
		return this.alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public Alumno addAlumno(Alumno alumno) {
		getAlumnos().add(alumno);
		alumno.setAula(this);

		return alumno;
	}

	public Alumno removeAlumno(Alumno alumno) {
		getAlumnos().remove(alumno);
		alumno.setAula(null);

		return alumno;
	}

	public Set<Profesor> getProfesors() {
		return this.profesors;
	}

	public void setProfesors(Set<Profesor> profesors) {
		this.profesors = profesors;
	}
	
	@Override
	public boolean equals(Object cla) {
		if(cla instanceof Aula && this.id == ((Aula) cla).getId()) {
			return true;
		}else {
			return false;
		}
		
	}

}
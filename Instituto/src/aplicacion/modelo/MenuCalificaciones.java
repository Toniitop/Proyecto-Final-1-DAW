package aplicacion.modelo;

import java.time.LocalDate;

public class MenuCalificaciones {

	private String dniProfesor, dniEstudiante, asignatura, nombreEstudiante, apellidoEstudiante;
	private LocalDate fecha;
	private Boolean asistencia;
	private Double nota;
	
	public MenuCalificaciones(String dniProfesor, String dniEstudiante, String asignatura, String nombreEstudiante, String apellidoEstudiante,
			LocalDate fecha, Boolean asistencia, Double nota) {
		this.dniProfesor = dniProfesor;
		this.dniEstudiante = dniEstudiante;
		this.asignatura = asignatura;
		this.nombreEstudiante = nombreEstudiante;
		this.apellidoEstudiante = apellidoEstudiante;
		this.fecha = fecha;
		this.asistencia = asistencia;
		this.nota = nota;
	}
	
	public MenuCalificaciones(String dniProfesor, String dniEstudiante, String asignatura, LocalDate fecha,
			Boolean asistencia, Double nota) {
		this.dniProfesor = dniProfesor;
		this.dniEstudiante = dniEstudiante;
		this.asignatura = asignatura;
		this.fecha = fecha;
		this.asistencia = asistencia;
		this.nota = nota;
	}

	public String getDniProfesor() {
		return dniProfesor;
	}
	
	public String getDniEstudiante() {
		return dniEstudiante;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Boolean getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Boolean asistencia) {
		this.asistencia = asistencia;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public String getNombreEstudiante() {
		return nombreEstudiante;
	}

	public String getApellidoEstudiante() {
		return apellidoEstudiante;
	}
	
}

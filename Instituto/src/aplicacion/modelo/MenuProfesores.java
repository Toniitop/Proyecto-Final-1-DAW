package aplicacion.modelo;

public class MenuProfesores {

	private String nombre, apellido, asignatura, dni, direccion, email, telefono;
	private Integer id;
	
	public MenuProfesores(Integer id, String nombre, String apellido, String asignatura, String dni, String direccion,
			String email, String telefono) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.asignatura = asignatura;
		this.dni = dni;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
	}

	public MenuProfesores(String nombre, String apellido, String asignatura, String dni, String direccion, String email,
			String telefono) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.asignatura = asignatura;
		this.dni = dni;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getId() {
		return id;
	}
	
}

package aplicacion;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
	// Los atributos de la clase son todos los parámetros de la conexión
	private String nombreBBDD;
	private String usuario;
	private String password;
	private String url;

	// Y la propia conexión
	private Connection conexion;

	// Generamos un constructor que recibe todos los datos de la cadena de conexión
	// Realiza la conexión
	public Conexion(String nombreBBDD, String usuario, String password, String url) {
		super();
		this.nombreBBDD = nombreBBDD;
		this.usuario = usuario;
		this.password = password;
		this.url = url;

		try {
			// Lo primero es cargar el driver
			Class.forName("com.mysql.jdbc.Driver");
			// Generamos la conexión
			conexion = (Connection) DriverManager.getConnection(this.url + this.nombreBBDD, this.usuario,
					this.password);
			if (conexion != null)
				System.out.println("Conexión correcta");
		} catch (Exception e) {
			System.out.println("La conexión ha fallado");
			this.conexion = null;
		}
	}

	// Método que nos devuelve la conexión de la base de datos
	public Connection getConexion() {
		return this.conexion;
	}

}

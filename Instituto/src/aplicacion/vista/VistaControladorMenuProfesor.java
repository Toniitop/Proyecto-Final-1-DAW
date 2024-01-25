package aplicacion.vista;

import java.beans.EventHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import aplicacion.Conexion;
import aplicacion.modelo.MenuProfesores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class VistaControladorMenuProfesor implements Initializable {

	private Stage escenario;
	private Conexion miConexion;
	
	ObservableList<MenuProfesores> profesores = FXCollections.observableArrayList();	//guardo los datos de la clase menuProfesores
	ObservableList<String> listaAsignaturas = FXCollections.observableArrayList();	//guardo las asignaturas del chbox
	
	@FXML
	private ChoiceBox<String> chbAsignaturas;
	
	@FXML
	private Button btnInsertar;
	
	@FXML
	private Button btnEliminar;
	
	@FXML
	private Button btnActualizar;
	
	@FXML
	private Button btnSalir;
	
	@FXML
	private TextField txfNombre;
	
	@FXML
	private TextField txfApellido;
	
	@FXML
	private TextField txfDni;
	
	@FXML
	private TextField txfTelefono;
	
	@FXML
	private TextField txfDireccion;
	
	@FXML
	private TextField txfEmail;
	
	@FXML
	private Button btnSalir1;
	
	@FXML
	private TextField txfNombre1;
	
	@FXML
	private TextField txfApellido1;
	
	@FXML
	private TextField txfDni1;
	
	@FXML
	private TextField txfTelefono1;
	
	@FXML
	private ChoiceBox<String> chbAsignaturas1;
	
	@FXML
	private TextField txfDireccion1;
	
	@FXML
	private TextField txfEmail1;
	
	@FXML
	private Button btnSalir2;
	
	@FXML
	private TextField txfNombre2;
	
	@FXML
	private TextField txfApellido2;
	
	@FXML
	private TextField txfDni2;
	
	@FXML
	private TextField txfTelefono2;
	
	@FXML
	private TextField txfAsignatura2;
	
	@FXML
	private TextField txfDireccion2;
	
	@FXML
	private TextField txfEmail2;
	
	@FXML
	private TableView<MenuProfesores> tbvMenuProfesores;
	
	@FXML
	private TableColumn<MenuProfesores, Integer> tbcId;
	
	@FXML
	private TableColumn<MenuProfesores, String> tbcNombre;
	
	@FXML
	private TableColumn<MenuProfesores, String> tbcApellido;
	
	@FXML
	private TableColumn<MenuProfesores, String> tbcAsignatura;
	
	@FXML
	private TableColumn<MenuProfesores, String> tbcDni;
	
	@FXML
	private TableColumn<MenuProfesores, String> tbcDireccion;
	
	@FXML
	private TableColumn<MenuProfesores, String> tbcTelefono;
	
	@FXML
	private TableColumn<MenuProfesores, String> tbcEmail;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializarTabla();
		materias();
		insertarDatos();
		modificarDatos();
		eliminarProfesor();
		salir();
	}
	/**
	 * @param miConexion, se le pasa la conexion
	 */
	public void setConexion(Conexion miConexion) {
		this.miConexion = miConexion;
		cargarListado();
		//lo mismo aqui tengo que cargar la tabla
	}
	
	/**
	 * Limpio todos los txfield
	 */
	private void limpiarCampos() {
		this.txfNombre.setText(null);
		this.txfApellido.setText(null);
		this.txfDni.setText(null);
		this.txfDireccion.setText(null);
		this.txfTelefono.setText(null);
		this.txfEmail.setText(null);
		
		this.txfNombre1.setText(null);
		this.txfApellido1.setText(null);
		this.txfDni1.setText(null);
		this.txfDireccion1.setText(null);
		this.txfTelefono1.setText(null);
		this.txfEmail1.setText(null);
		
		this.txfNombre2.setText(null);
		this.txfApellido2.setText(null);
		this.txfDni2.setText(null);
		this.txfDireccion2.setText(null);
		this.txfTelefono2.setText(null);
		this.txfEmail2.setText(null);
	}
	
	/**
	 * Se añaden las columnas, asociandolas a la clase a la que pertenecen y se inicializa la tabla
	 * También llamo al metodo de ayuda texto, que genera un prompttext para algunos txfield y desactiva
	 * los txtfield del tab eliminar
	 */
	public void inicializarTabla() {
		this.tbcId.setCellValueFactory(new PropertyValueFactory<MenuProfesores, Integer>("id"));
		this.tbcNombre.setCellValueFactory(new PropertyValueFactory<MenuProfesores, String>("nombre"));
		this.tbcApellido.setCellValueFactory(new PropertyValueFactory<MenuProfesores, String>("apellido"));
		this.tbcAsignatura.setCellValueFactory(new PropertyValueFactory<MenuProfesores, String>("asignatura"));
		this.tbcDni.setCellValueFactory(new PropertyValueFactory<MenuProfesores, String>("dni"));
		this.tbcDireccion.setCellValueFactory(new PropertyValueFactory<MenuProfesores, String>("direccion"));
		this.tbcTelefono.setCellValueFactory(new PropertyValueFactory<MenuProfesores, String>("telefono"));
		this.tbcEmail.setCellValueFactory(new PropertyValueFactory<MenuProfesores, String>("email"));
		//agregamos a la observablelist la tabla
		this.tbvMenuProfesores.setItems(profesores);
		this.tbvMenuProfesores.refresh();
		ayudaTexto();
	}
	
	/**
	 * Les pongo un ejemplo a los textfield para que el usuario sepa que debe poner mas o menos
	 */
	public void ayudaTexto() {
		this.txfDni.setPromptText("11111111A");
		this.txfTelefono.setPromptText("123456789");
		this.txfEmail.setPromptText("Ejemplo@ejemplo.com");
		this.txfDni1.setPromptText("11111111A");
		this.txfTelefono1.setPromptText("123456789");
		this.txfEmail1.setPromptText("Ejemplo@ejemplo.com");	
		this.txfDni2.setPromptText("11111111A");
		this.txfTelefono2.setPromptText("123456789");
		this.txfEmail2.setPromptText("Ejemplo@ejemplo.com");	
		
		this.txfNombre2.setEditable(false);
		this.txfApellido2.setEditable(false);
		this.txfAsignatura2.setEditable(false);
		this.txfDni2.setEditable(false);
		this.txfDireccion2.setEditable(false);
		this.txfTelefono2.setEditable(false);
		this.txfEmail2.setEditable(false);
		
	}
	/**
	 * Es para cargar los datos  y que se muestren nada mas abrir, la tabla con todos los datos
	 * como una especia de refresh y/o actualización
	 */
	public void cargarListado() {
		//limpiamos la observableList
		this.profesores.clear();
		//Aquí se guarda el resultado de la consulta
		ResultSet resultado = null;
		//Hacemos la consulta
		String consulta = "SELECT * FROM profesores";
		//Generamos la sentencia sobre esta consulta
		try {
			PreparedStatement sentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(consulta);
			//Ejecutamos la sentencia
			resultado = sentencia.executeQuery();
			//Recorremos los resultados y los almacenamos en la observablelist
			while (resultado.next()) {
				MenuProfesores nuevoProfesor = new MenuProfesores(resultado.getInt("idProfesor"), resultado.getString("nombre"), resultado.getString("apellido"), resultado.getString("asignatura"), resultado.getString("dni"), resultado.getString("direccion"), resultado.getString("email"), resultado.getString("telefono"));
				//agregamos el nuevo profesor a la observablelist
				this.profesores.add(nuevoProfesor);
			}
			//se refresca la tabla
			this.tbvMenuProfesores.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * aqui inserto los datos de los txfield en la tabla mas tarde, también compruebo varias cosas
	 * como los mail, dni y tlfn
	 */
	private void insertarDatos() {
		this.btnInsertar.setOnAction(event -> {
			String nombre = this.txfNombre.getText().toUpperCase();
			String apellido = this.txfApellido.getText().toUpperCase();
			String dni = this.txfDni.getText().toUpperCase().toUpperCase();
			String tlfn =  this.txfTelefono.getText().toUpperCase();
			String asig = this.chbAsignaturas.getSelectionModel().getSelectedItem();
			String direc = this.txfDireccion.getText().toUpperCase();
			String mail = this.txfEmail.getText().toUpperCase();
			
			if (comprobarDni(dni) == false || comprobarTlfn(tlfn) == false || comprobarMail(mail) == false || this.txfNombre.getText().isEmpty() || this.txfApellido.getText().isEmpty() || this.txfDireccion.getText().isEmpty()) {
				if (txfDni.getText().isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("DNI Incorrecto");
					alert.setContentText("El número y/o letra es erróneo o ya está registrado.");
					alert.showAndWait();
				} else if (txfTelefono.getText().isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Teléfono Incorrecto");
					alert.setContentText("El número de teléfono es erróneo.");
					alert.showAndWait();
				} else if (txfEmail.getText().isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Email Incorrecto");
					alert.setContentText("El email introducido es erróneo.");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Error al introducir datos");
					alert.setContentText("Todos los campos deben estar rellenos y con valores válidos. Revise el formulario.");
					alert.showAndWait();
				}
			} else {
				//Escribimos la sentencia
				String sentencia = "INSERT INTO profesores (nombre, apellido, asignatura, dni, direccion, email, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparacionSentencia;
				try {
					preparacionSentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(sentencia);
					//Se establecen los parametros para la consulta
					preparacionSentencia.setString(1, nombre);
					preparacionSentencia.setString(2, apellido);
					preparacionSentencia.setString(3, asig);
					preparacionSentencia.setString(4, dni);
					preparacionSentencia.setString(5, direc);
					preparacionSentencia.setString(6, mail);
					preparacionSentencia.setString(7, tlfn);
					
					//Comprobamos que la consulta se ha insertado bien o no, 1 guay, 0 mal
					int filaAfectada = preparacionSentencia.executeUpdate();
					if (filaAfectada == 1) {
						cargarListado();
						limpiarCampos();
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Inserción de Profesor");
						alert.setHeaderText("INSERCIÓN DE PROFESOR CORRECTA");
						alert.showAndWait();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Importante, se ha generado un error");
						alert.setContentText("Error desconocido al insertar");
						alert.showAndWait();
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}); 
	}
	
	/**
	 * Método para comprobar el dni introducido
	 * @param dni dado, pasado por parametro
	 * @param letras, las que tiene el algoritmo del dni
	 * @param letraDni, se hace la division entre el dni dado y 23 y se obtiene el resto
	 * se comprueba el resto con la posicion de la letra en el string.
	 * @return devuelve true o false + limpia el campo correspondiente
	 */
	public boolean comprobarDni(String dni) {
		//Aqui compruebo que no se repita
		int filas = 0;
		ResultSet resultado;
		String consulta = "SELECT * FROM profesores WHERE dni='"+dni+"'";
		try {
			Statement sentencia = (Statement) this.miConexion.getConexion().createStatement();
			resultado = sentencia.executeQuery(consulta);
			if (resultado.last()) {
				filas = resultado.getRow();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Aqui compruebo que existe
		int miDni = Integer.parseInt(dni.substring(0, 8));
		String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		char letraDni = letras.charAt(miDni%23);
		if (letraDni == dni.charAt(8) && filas == 0) {
			//dni existe y no esta repetido
			return true;
		} else {
			//dni no existe y está repetido
			this.txfDni.clear();
			return false;
		}
		
	}
	
	/**
	 * 
	 * @param tlfn, se le pasa por parametro el telefono y se comprueba con \\d{9}, si contiene 9 caracteres 
	 * el \\d comprueba digitos del 0 al 9, y {9} es repetición de 9 veces
	 * 
	 * @return devuelve true si está bien el tlfn y false + limpia el campo correspondiente
	 */
	public boolean comprobarTlfn(String tlfn) {
		if(tlfn.matches("\\d{9}")) {
			return true;
		} else {
			txfTelefono.clear();
			return false;
		}
	}
	
	/**
	 * 
	 * @param mail, se le pasa por parametro y se comprueba si contiene @ en su string
	 * @return devuelve true si está bien el mail y false + limpia el campo correspondiente
	 */
	public boolean comprobarMail(String mail) {
		if (mail.contains("@")) {
			return true;
		} else {
			txfEmail.clear();
			return false;
		}
	}
	
	/**
	 * Recojo los valores de la tabla y los muestro en los textfield del tab de modificar y del tab de eliminar
	 * Una vez hecho esto, hago un evento en el boton actualizar, si confirma que quiere modificar, se guardan los datos 
	 * nuevos que haya modificado, se pasan a la consulta y se modifican y se muestran en la tabla de nuevo, ya actualizados
	 */
	private void modificarDatos() {
		this.tbvMenuProfesores.setOnMouseClicked(event -> {
			if (this.tbvMenuProfesores.getSelectionModel().getSelectedItem() != null) {
				String asig1 = this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getAsignatura().toString();
				//Aqui antes cambiar los txtfield
				this.txfNombre1.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getNombre().toUpperCase().toString());
				this.txfApellido1.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getApellido().toUpperCase().toString());
				this.txfDni1.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getDni().toUpperCase().toString());
				this.chbAsignaturas1.setValue(asig1);
				this.txfTelefono1.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getTelefono().toUpperCase().toString());
				this.txfDireccion1.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getDireccion().toUpperCase().toString());
				this.txfEmail1.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getEmail().toUpperCase().toString());
			
				this.txfNombre2.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getNombre().toUpperCase().toString());
				this.txfApellido2.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getApellido().toUpperCase().toString());
				this.txfDni2.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getDni().toUpperCase().toString());
				this.txfAsignatura2.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getAsignatura().toUpperCase().toString());
				this.txfTelefono2.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getTelefono().toUpperCase().toString());
				this.txfDireccion2.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getDireccion().toUpperCase().toString());
				this.txfEmail2.setText(this.tbvMenuProfesores.getSelectionModel().getSelectedItem().getEmail().toUpperCase().toString());
			
			}
		});
		
		this.btnActualizar.setOnAction(event -> {
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setTitle("Confirmación");
			alerta.setHeaderText("Actualizando...");
			alerta.setContentText("¿Estas seguro de que deseas actualizar los datos de este profesor/a?");
			Optional<ButtonType> action = alerta.showAndWait();
			//Si hemos pulsado en aceptar
			//Se genera la consulta de actualizacion
			//Se le dan los valores correspondientes
			if (action.get() == ButtonType.OK) {
				String consulta = "UPDATE profesores SET nombre = ?, apellido = ?, asignatura = ?, telefono = ?, direccion = ?, email = ? WHERE dni = ?";
				try {
					PreparedStatement sentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(consulta);
					//el texto que recoge de los textfield se lo paso en mayuscula
					sentencia.setString(1, this.txfNombre1.getText().toUpperCase());
					sentencia.setString(2, this.txfApellido1.getText().toUpperCase());
					sentencia.setString(3, this.chbAsignaturas1.getSelectionModel().getSelectedItem());
					sentencia.setString(4, this.txfTelefono1.getText().toUpperCase());
					sentencia.setString(5, this.txfDireccion1.getText().toUpperCase());
					sentencia.setString(6, this.txfEmail1.getText().toUpperCase());
					sentencia.setString(7, this.txfDni1.getText().toUpperCase());
					//se comprueba si se han afectado filas despues del executeUpdate, 0 es mal, 1 bien
					int filasEliminadas = sentencia.executeUpdate();
					if (filasEliminadas != 0) {
						cargarListado();
						limpiarCampos();
						this.tbvMenuProfesores.getSelectionModel().clearSelection();
						alerta = new Alert(AlertType.CONFIRMATION);
						alerta.setTitle("Información");
						alerta.setHeaderText("Tabla Actualizada");
						alerta.setContentText("Se ha actualizado la info del profesor/a.");
						alerta.showAndWait();
					} else {
						alerta = new Alert(AlertType.WARNING);
						alerta.setTitle("Información");
						alerta.setHeaderText("Importante");
						alerta.setContentText("No se ha podido actualizar la info del profesor/a.");
						alerta.showAndWait();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});			
	}
	
	/**
	 * Metodo que elimina un profesor, como ya están los textfield cargados del metodo anterior "modificar", se le da un evento
	 * al boton eliminar, se confima que desea eliminar los datos que le aparencen el los textfield y los elimina, pasandole
	 * la consulta a la base de datos y actualizando luego la tabla
	 */
	private void eliminarProfesor() {
		this.btnEliminar.setOnAction(event -> {
			String dni = this.txfDni2.getText();
			if (dni.isEmpty() == false) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("Confirmación");
				alert.setContentText("¿Estas seguro de que deseas eliminar este profesor/a?");
				Optional<ButtonType> action = alert.showAndWait();
				// Si hemos pulsado en aceptar
				if (action.get() == ButtonType.OK) {
					String consulta = "DELETE FROM profesores WHERE dni = ?";
					try {
						PreparedStatement sentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(consulta);
						sentencia.setString(1, dni);
						int filasEliminadas = sentencia.executeUpdate();
						if (filasEliminadas != 0) {
							cargarListado();
							limpiarCampos();
							this.txfAsignatura2.clear();
							this.tbvMenuProfesores.getSelectionModel().clearSelection();
							alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Información");
							alert.setHeaderText("Se ha eliminado a este profesor/a con éxito.");
							alert.showAndWait();
						} else {
							alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Información");
							alert.setHeaderText("No se ha podido eliminar este usuario.");
							alert.showAndWait();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Importante, se ha generado un error");
					alert.showAndWait();
				}
			}			
		});
	}
	
	/**
	 * Se añaden a la choicebox las asignaturas
	 */
	public void materias() {
		listaAsignaturas.add("MATEMÁTICAS");
		listaAsignaturas.add("LENGUA CASTELLANA Y LITERATURA");
		listaAsignaturas.add("CIENCIAS NATURALES");
		listaAsignaturas.add("FÍSICA Y QUÍMICA");
		listaAsignaturas.add("BIOLOGÍA");
		listaAsignaturas.add("HISTORIA");
		listaAsignaturas.add("GEOGRAFÍA");
		listaAsignaturas.add("EDUCACIÓN FÍSICA");
		listaAsignaturas.add("MÚSICA");
		listaAsignaturas.add("ARTES PLÁSTICAS");
		listaAsignaturas.add("TECNOLOGÍA");
		listaAsignaturas.add("INFORMÁTICA");
		listaAsignaturas.add("FILOSOFÍA");
		listaAsignaturas.add("ÉTICA");
		this.chbAsignaturas.setItems(listaAsignaturas);
		this.chbAsignaturas1.setItems(listaAsignaturas);
	}
	
	/**
	 * Se crean botones para salir en cada uno de los tab
	 */
	public void salir() {
		this.btnSalir.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Salir");
			alert.setHeaderText("Volverá al Menú");
			Optional<ButtonType> pulsarBoton = alert.showAndWait();
			if (pulsarBoton.get() == ButtonType.OK) {
				escenario.close();
			}
		});
		
		this.btnSalir1.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Salir");
			alert.setHeaderText("Volverá al Menú");
			Optional<ButtonType> pulsarBoton = alert.showAndWait();
			if (pulsarBoton.get() == ButtonType.OK) {
				escenario.close();
			}
		});
		
		this.btnSalir2.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Salir");
			alert.setHeaderText("Volverá al Menú");
			Optional<ButtonType> pulsarBoton = alert.showAndWait();
			if (pulsarBoton.get() == ButtonType.OK) {
				escenario.close();
			}
		});
	}

	/**
	 * Se carga el escenario
	 * @param escenario
	 */
	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}
	
}

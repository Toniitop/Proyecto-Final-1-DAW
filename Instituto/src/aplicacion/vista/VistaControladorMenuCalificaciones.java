package aplicacion.vista;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import aplicacion.Conexion;
import aplicacion.modelo.MenuCalificaciones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class VistaControladorMenuCalificaciones implements Initializable {

	private Stage escenario;
	private Conexion miConexion;
	ObservableList<MenuCalificaciones> notas = FXCollections.observableArrayList();	//guardo los datos de la clase menuCalificaciones
	ObservableList<String> listaAsignaturas = FXCollections.observableArrayList();	//guardo las asignaturas que se muestran en el chbox
	ObservableList<String> dniProfe = FXCollections.observableArrayList();	//guardo los dniProf de la tabla profesores
	ObservableList<String> dniAlumno = FXCollections.observableArrayList();	//guardo los dniEstu de la tabla estudiantes
	
	@FXML
	private TableView<MenuCalificaciones> tbvMenuCalificaciones;
	
	@FXML
	private TableColumn<MenuCalificaciones, Integer> tbcIdProfesor;
	
	@FXML
	private TableColumn<MenuCalificaciones, String> tbcNombre;
	
	@FXML
	private TableColumn<MenuCalificaciones, String> tbcApellido;
	
	@FXML
	private TableColumn<MenuCalificaciones, String> tbcAsignatura;
	
	@FXML
	private TableColumn<MenuCalificaciones, Double> tbcNota;
	
	@FXML
	private TableColumn<MenuCalificaciones, LocalDate> tbcFecha;
	
	@FXML
	private TableColumn<MenuCalificaciones, Boolean> tbcAsistencia;
	
	@FXML
	private BarChart<String, Double> bcGrafico;
	
	@FXML
	private CategoryAxis xAxisBarChart;
	
	@FXML
	private NumberAxis yAxisBarChart;
	
	@FXML
	private ChoiceBox<String> chbDniProfesor;
	
	@FXML
	private ChoiceBox<String> chbDniAlumno;
	
	@FXML
	private TextField txfNota;
	
	@FXML
	private ChoiceBox<String> chbAsignaturas;
	
	@FXML
	private DatePicker dtpFecha;
	
	@FXML
	private CheckBox cbAsistencia;
	
	@FXML
	private Button btnModificar;
	
	@FXML
	private Button btnEliminar;
	
	@FXML
	private Button btnInsertar;
	
	@FXML
	private Button btnSalir;
	
	@FXML
	private Button btnActu;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializarTabla();
		materias();
		insertarDatos();
		modificarDatos();
		iniciarBarChart();
		salir();
	}
	
	/**
	 * Limpio los txtfield, datepicker, choicebox y radiobutton
	 */
	private void limpiarCampos() {
		this.chbDniProfesor.getSelectionModel().clearSelection();
		this.chbAsignaturas.getSelectionModel().clearSelection();
		this.chbDniAlumno.getSelectionModel().clearSelection();
		this.txfNota.clear();
		this.dtpFecha.setValue(null);
		this.cbAsistencia.setSelected(false);
	}
	
	/**
	 * Inicializo la tabla, asociandolas a su clase correspondiente y agrego los datos a mi observablelist y la actualizo
	 */
	public void inicializarTabla() {
		this.tbcIdProfesor.setCellValueFactory(new PropertyValueFactory<MenuCalificaciones, Integer>("dniProfesor"));
		this.tbcNombre.setCellValueFactory(new PropertyValueFactory<MenuCalificaciones, String>("nombreEstudiante"));
		this.tbcApellido.setCellValueFactory(new PropertyValueFactory<MenuCalificaciones, String>("apellidoEstudiante"));
		this.tbcAsignatura.setCellValueFactory(new PropertyValueFactory<MenuCalificaciones, String>("asignatura"));
		this.tbcNota.setCellValueFactory(new PropertyValueFactory<MenuCalificaciones, Double>("nota"));
		this.tbcFecha.setCellValueFactory(new PropertyValueFactory<MenuCalificaciones, LocalDate>("fecha"));
		this.tbcAsistencia.setCellValueFactory(new PropertyValueFactory<MenuCalificaciones, Boolean>("asistencia"));
		
		this.tbvMenuCalificaciones.setItems(notas);
		this.tbvMenuCalificaciones.refresh();
	}

	/**
	 * Se carga el listado de todo lo que hay en la tabla, se guarda en una variable el valor del chbDniAlumno, el cual es modificado en el método cargarDniProfesoresYAlumnos()
	 * en un if, se comprueba si es nulo o no, si entra, se usa arrays para recorrer el string y dividirlo con .splits según el tipo de cadena que indiques
	 * al dividirlo se forman partes, debemos elegir la parte correspondiente del string que queramos y añadirsela a la variable que luego queramos usar
	 * 
	 * se realiza la consulta de todo lo que necesitamos para inicializar la tabla con los valores que queremos usar
	 * se le asignan los valores se hace la consulta y se guardan los valores en la observablelist, se refresca la tabla, sino entra al if, se alerta y se cierra el escenario
	 */
	public void cargarListado() {
	    this.notas.clear();
	    ResultSet resultado = null;
	    String alumnoSeleccionado = this.chbDniAlumno.getSelectionModel().getSelectedItem();

	    if (alumnoSeleccionado != null) {
	        String[] parts = alumnoSeleccionado.split(" - ");
	        String[] nombres = parts[1].split(", ");
	        String apeAlumno = nombres[0];
	        String nomAlumno = nombres[1];

	        String consulta = "SELECT estudiantes.nombre, estudiantes.apellido, calificaciones.* FROM estudiantes, calificaciones "
	                + "WHERE estudiantes.dni = calificaciones.dniEstudiante AND estudiantes.dni = ? AND estudiantes.apellido = ? AND estudiantes.nombre = ?";
	        try {
	            PreparedStatement sentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(consulta);
	            sentencia.setString(1, parts[0]);
	            sentencia.setString(2, apeAlumno);
	            sentencia.setString(3, nomAlumno);
	            resultado = sentencia.executeQuery();

	            while (resultado.next()) {
	                java.sql.Date sqlDate = resultado.getDate("fecha");
	                LocalDate localDate = sqlDate.toLocalDate();
	                MenuCalificaciones nuevaNota = new MenuCalificaciones(resultado.getString("dniProfesor"), resultado.getString("dniEstudiante"), resultado.getString("asignatura"), resultado.getString("nombre"),
	                        resultado.getString("apellido"), localDate, resultado.getBoolean("asistencia"),resultado.getDouble("nota"));
	                this.notas.add(nuevaNota);
	            }
	            this.tbvMenuCalificaciones.refresh();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	/**
	 * Metodo que inicializa los chbalumno y profesor, para limitar la entrada de datos erróneos, se usan los que ya existen de las otras tablas
	 * se usa un evento de escucha para que cuando se seleccione un alumno en el choicebox se muestren sus datos a traves del metodo caragarListado()
	 * también se obtienen los valores de la clase menuCalificaciones, necesarios para completar la tabla, se obtienen y se añaden a la observablelist
	 * y se refresca, se obtienen los datos del mismo modo que que en el método cargarListado, con el string.split y dividir los arrays en partes
	 */
	private void cargarDniProfesoresYAlumnos() {
		this.notas.clear();
		ResultSet resultadoP = null;
		String consultaP = "SELECT dni, apellido, nombre FROM profesores";
		try {
			PreparedStatement sentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(consultaP);
			resultadoP = sentencia.executeQuery();
			while (resultadoP.next()) {
				String dni = resultadoP.getString("dni");
				String ape = resultadoP.getString("apellido");
				String nom = resultadoP.getString("nombre");
				String todo = dni + " - " + ape + ", " + nom;
				this.dniProfe.add(todo);
			}
			this.chbDniProfesor.setItems(dniProfe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ResultSet resultadoA = null;
		String consultaA = "SELECT dni, apellido, nombre FROM estudiantes";
		try {
			PreparedStatement sentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(consultaA);
			resultadoA = sentencia.executeQuery();
			while (resultadoA.next()) {
				String dni = resultadoA.getString("dni");
				String ape = resultadoA.getString("apellido");
				String nom = resultadoA.getString("nombre");
				String todo = dni + " - " + ape + ", " + nom;
				this.dniAlumno.add(todo);
				this.chbDniAlumno.setItems(dniAlumno);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.chbDniAlumno.setOnAction(event -> {
			cargarListado();
		});
		
		ResultSet resultadoC = null;
		String alumnoSeleccionado = this.chbDniAlumno.getSelectionModel().getSelectedItem();

		if (alumnoSeleccionado != null) {
		    String [] parts = alumnoSeleccionado.split(" - ");
		    String [] nombres = parts[1].split(", ");
		    String apeAlumno = nombres[0];
		    String nomAlumno = nombres[1];

		    String consultaC = "SELECT * FROM calificaciones WHERE dniProfesor = ? AND dniEstudiante = ? AND nombre = ? AND apellido = ?";
		    try {
		        PreparedStatement sentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(consultaC);
		        sentencia.setString(1, this.chbDniProfesor.getSelectionModel().getSelectedItem().substring(0, 9)); 
		        sentencia.setString(2, this.chbDniAlumno.getSelectionModel().getSelectedItem().substring(0, 9));
		        sentencia.setString(3, nomAlumno); 
		        sentencia.setString(4, apeAlumno);
		        resultadoC = sentencia.executeQuery();
		        while (resultadoC.next()) {
		            java.sql.Date sqlDate = resultadoC.getDate("fecha");
		            LocalDate localDate = sqlDate.toLocalDate();
		            MenuCalificaciones nuevaNota = new MenuCalificaciones(
		                resultadoC.getString("dniProfesor"), resultadoC.getString("dniEstudiante"), resultadoC.getString("asignatura"), resultadoC.getString("nombre"),
		                resultadoC.getString("apellido"), localDate, resultadoC.getBoolean("asistencia"), resultadoC.getDouble("nota"));
		            this.notas.add(nuevaNota);
		        }
		        this.tbvMenuCalificaciones.refresh();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}		
	}
	
	/**
	 * Se seleccionan los datos de los choicebox y txtfield, se comprueban si son errones o vacios o nulos y sino lo son se insertan en la tabla
	 */
	private void insertarDatos() {
		this.btnInsertar.setOnAction(event -> {
			String dniP = this.chbDniProfesor.getSelectionModel().getSelectedItem().substring(0, 9);
			String dniA = this.chbDniAlumno.getSelectionModel().getSelectedItem().substring(0, 9);
			String asig = this.chbAsignaturas.getSelectionModel().getSelectedItem();
			Double nota = Double.parseDouble(this.txfNota.getText());
			LocalDate fecha = this.dtpFecha.getValue();
			java.sql.Date fechaSQL = java.sql.Date.valueOf(fecha);
			Boolean asiste = this.cbAsistencia.isSelected();
			
			if (dniP.isEmpty() || asig.isEmpty() || comprobarNota(nota) == false || fecha == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Fallo en la inserción de datos.");
				alert.setContentText("Asegúrese de que ha rellenado o elegido datos en los campos.");
				alert.showAndWait();
				limpiarCampos();
			} else {
				String sentencia = "INSERT INTO calificaciones (dniProfesor, dniEstudiante, asignatura, fecha, asistencia, nota) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement preparacionSentencia;
				try {
					preparacionSentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(sentencia);
					preparacionSentencia.setString(1, dniP);
					preparacionSentencia.setString(2, dniA);
					preparacionSentencia.setString(3, asig);
					preparacionSentencia.setDate(4, fechaSQL);
					preparacionSentencia.setBoolean(5, asiste);
					preparacionSentencia.setDouble(6, nota);
					
					int filaAfectada = preparacionSentencia.executeUpdate();
					if (filaAfectada == 1) {
						cargarListado();
						limpiarCampos();
						this.tbvMenuCalificaciones.refresh();
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Inserción de Calificaciones");
						alert.setHeaderText("INSERCIÓN DE NOTAS CORRECTA");
						alert.showAndWait();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Importante, se ha generado un error");
						alert.setContentText("Error desconocido al insertar.");
						alert.showAndWait();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	/**
	 * En este método se carga en los choicebox y txtfield los datos de la tabla seleccionados, de ahí que tenga que hacer las variables duplicadas en todos los
	 * eventos. Se modifica según dniProf y dniEstu los valores que se desea modificar, salvo estos indicados ahora,
	 * También se elimina según los dni indicados antes, todos los datos relacionados
	 */
	private void modificarDatos() {
		this.tbvMenuCalificaciones.setOnMouseClicked(event -> {
			if (this.tbvMenuCalificaciones.getSelectionModel().getSelectedItem() != null) {
				//cargo todos los datos en unas variables aparte, porque probando otras veces me ha dado fallo, se recogen los datos y cuando clickan se muestran
				String profe = this.tbvMenuCalificaciones.getSelectionModel().getSelectedItem().getDniProfesor().toString();
				LocalDate fechaN = this.tbvMenuCalificaciones.getSelectionModel().getSelectedItem().getFecha();
				java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaN);
				String asignatura = this.tbvMenuCalificaciones.getSelectionModel().getSelectedItem().getAsignatura().toString();
				this.chbDniProfesor.setValue(profe);
				this.chbAsignaturas.setValue(asignatura);
				this.txfNota.setText(this.tbvMenuCalificaciones.getSelectionModel().getSelectedItem().getNota().toString());		
				this.dtpFecha.setValue(fechaN);
				this.cbAsistencia.setSelected(false);				
			}
		});
		
		//se modifica según el dniProfesor y dniEstudiante, los valores que se han introducido nuevos
		this.btnModificar.setOnAction(event -> {
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setTitle("Confirmación");
			alerta.setHeaderText("Actualizando...");
			alerta.setContentText("¿Estas seguro de que deseas actualizar los datos de este alumno/a?");
			Optional<ButtonType> action = alerta.showAndWait();
			if (action.get() == ButtonType.OK) {
				if (this.dtpFecha.getValue() == null || this.chbAsignaturas.getSelectionModel().getSelectedItem() == null || this.txfNota.getText().isEmpty()) {
					alerta = new Alert(AlertType.WARNING);
					alerta.setTitle("ATENCIÖN");
					alerta.setHeaderText("Error");
					alerta.setContentText("Hay campos vacíos o modificados. No puede modificar los campos DNI Profesor o DNI Alumno.");
					alerta.showAndWait();
				} else {
					String consulta = "UPDATE calificaciones SET asignatura = ?, fecha = ?, asistencia = ?, nota = ? WHERE dniProfesor = ? AND dniEstudiante = ?";
					try {
						PreparedStatement sentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(consulta);
						sentencia.setString(1, this.chbAsignaturas.getSelectionModel().getSelectedItem());
						LocalDate fechaN = this.tbvMenuCalificaciones.getSelectionModel().getSelectedItem().getFecha();
						java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaN);
						sentencia.setDate(2, fechaSQL);
						sentencia.setBoolean(3, this.cbAsistencia.isSelected());
						sentencia.setDouble(4, Double.parseDouble(this.txfNota.getText()));
						sentencia.setString(5, this.tbvMenuCalificaciones.getSelectionModel().getSelectedItem().getDniProfesor().toString());
						sentencia.setString(6, this.chbDniAlumno.getSelectionModel().getSelectedItem().substring(0, 9));
						int filasEliminadas = sentencia.executeUpdate();
						if (filasEliminadas != 0) {
							cargarListado();
							limpiarCampos();
							this.tbvMenuCalificaciones.getSelectionModel().clearSelection();
							this.tbvMenuCalificaciones.refresh();
							alerta = new Alert(AlertType.CONFIRMATION);
							alerta.setTitle("Información");
							alerta.setHeaderText("Tabla Actualizada");
							alerta.setContentText("Se ha actualizado la info de calificaciones.");
							alerta.showAndWait();
						} else {
							alerta = new Alert(AlertType.WARNING);
							alerta.setTitle("Información");
							alerta.setHeaderText("Importante");
							alerta.setContentText("No se ha podido actualizar la tabla.");
							alerta.showAndWait();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		//se elimina según los dniProfesor y dniEstudiante, los valores en concreto de ese estudiante, para las notas, fecha, asignatura y asistencia
		//además se hace con control de datos, comprobando que no estén los campos vacíos
		this.btnEliminar.setOnAction(event -> {
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setTitle("Confirmación");
			alerta.setHeaderText("Eliminando...");
			alerta.setContentText("¿Estas seguro de que deseas eliminar este registro del este alumno " + this.chbDniAlumno.getSelectionModel().getSelectedItem().toString() + "?");
			Optional<ButtonType> action = alerta.showAndWait();
			if (action.get() == ButtonType.OK) {
				if (this.chbDniProfesor.getSelectionModel().getSelectedItem() == null) {
					alerta = new Alert(AlertType.WARNING);
					alerta.setTitle("ATENCIÖN");
					alerta.setHeaderText("Error");
					alerta.setContentText("Hay campos vacíos.");
					alerta.showAndWait();
				} else {
					String consulta = "DELETE FROM calificaciones WHERE dniProfesor = ? AND dniEstudiante = ?";
					try {
						PreparedStatement sentencia = (PreparedStatement) this.miConexion.getConexion().prepareStatement(consulta);
						sentencia.setString(1, this.tbvMenuCalificaciones.getSelectionModel().getSelectedItem().getDniProfesor().toString());
						sentencia.setString(2, this.chbDniAlumno.getSelectionModel().getSelectedItem().substring(0, 9));
						int filasEliminadas = sentencia.executeUpdate();
						if (filasEliminadas != 0) {
							cargarListado();
							limpiarCampos();
							this.tbvMenuCalificaciones.getSelectionModel().clearSelection();
							this.tbvMenuCalificaciones.refresh();
							alerta = new Alert(AlertType.CONFIRMATION);
							alerta.setTitle("Información");
							alerta.setHeaderText("Tabla Modificada");
							alerta.setContentText("Se ha actualizado la tabla, clickee de nuevo en un alumno para ver resultados.");
							alerta.showAndWait();
						} else {
							alerta = new Alert(AlertType.WARNING);
							alerta.setTitle("Información");
							alerta.setHeaderText("Importante");
							alerta.setContentText("No se ha podido actualizar la tabla.");
							alerta.showAndWait();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * Se inicia el grafico de barras, para mostrar las notas de los alumnos
	 * en un for se recorre el tamaño de la observable y se agrega a un objeto de menuCalificacciones que luego se muestra
	 */
	private void iniciarBarChart() {
		this.tbvMenuCalificaciones.setOnMouseClicked(event -> {
			//se le da nombre a los label del grafico
			this.xAxisBarChart.setLabel("Nombre");
			this.yAxisBarChart.setLabel("Notas");
			
			//se indica de que va a ser cada tipo de dato y sus nombres de variables
			XYChart.Series<String, Double> serieNota;
			
			//se instancia el objeto de series, se le da el tipo de dato y su nombre 
			serieNota = new Series<String, Double>();
			//se instancia para este objeto nuevos objetos de la observable list y se añaden a la grafica
			for(int i = 0; i < this.notas.size(); i++) {
				MenuCalificaciones nota = this.notas.get(i);
				serieNota.getData().add(new XYChart.Data<String, Double>(nota.getNombreEstudiante(), nota.getNota()));
				serieNota.setName(nota.getNombreEstudiante());
			}		
			//aqui se añaden a la grafica y se representa
			//this.bcGrafico.getData().clear();
			this.bcGrafico.getData().add(serieNota);
		});
	}
	
	/**
	 * Se comprueba que la nota pasada por parámetro esta comprendida entre 0 y 10
	 * @param nota
	 * @return devuelve false si no es nota en los parámetro o true si está bien
	 */
	private boolean comprobarNota(Double nota) {
		if (nota < 0 || nota > 10) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * Se añaden a la choicebox las asignaturas
	 * además de la funcionalidad del boton del gráfico
	 * para resetear el mismo
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
		
		this.btnActu.setOnMouseClicked(event -> {
			this.bcGrafico.getData().clear();
			this.tbvMenuCalificaciones.getSelectionModel().clearSelection();
		});
	}
	
	/**
	 * Salir del escenario
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
	}
	
	public void setConexion(Conexion miConexion) {
		this.miConexion = miConexion;
		cargarDniProfesoresYAlumnos();
	}
	
	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

}

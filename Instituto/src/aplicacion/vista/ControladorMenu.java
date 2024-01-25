package aplicacion.vista;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import aplicacion.Conexion;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorMenu implements Initializable {

	private Stage escenario;
	private Conexion miConexion;
	
	@FXML
	private Button btnAcercaDe;
	
	@FXML
	private Button btnSalir;
	
	@FXML
	private Button btnProfesores;
	
	@FXML
	private Button btnEstudiantes;
	
	@FXML
	private Button btnCalificaciones;
	
	@FXML
	private ImageView imvProf;
	
	@FXML
	private ImageView imvEstu;
	
	@FXML
	private ImageView imvCali;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		imagenes();
		menu();
	}
	
	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}
	
	public void setConexion(Conexion miConexion) {
		this.miConexion = miConexion;
	}
	
	/**
	 * Cargamos las imagenes
	 */
	@FXML
	public void imagenes() {
		Image imagenProf;
		imagenProf = new Image(getClass().getResourceAsStream("imagenes/profe.png"));
		Image imagenEstu = new Image(getClass().getResourceAsStream("imagenes/estu.png"));
		Image imagenCali = new Image(getClass().getResourceAsStream("imagenes/notas.png"));
		this.imvProf.setImage(imagenProf);
		this.imvEstu.setImage(imagenEstu);
		this.imvCali.setImage(imagenCali);
	}
	
	/**
	 * Se cargan en cada botón los menús de profesores, estudiantes y calificaciones
	 * Además los botones de acerca de, con la info de la app y el de salir de esta app.
	 * Además se agrega un icono en cada ventana nueva
	 */
	public void menu() {
		this.btnProfesores.setOnAction(event -> {
			try {
				Stage escenario = new Stage();
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(getClass().getResource("VistaMenuProfesor.fxml"));//
				AnchorPane root = cargador.load();
				
				VistaControladorMenuProfesor controlador = (VistaControladorMenuProfesor) cargador.getController();
				controlador.setConexion(miConexion);
				controlador.setEscenario(escenario);
				
				Scene scene = new Scene(root);
				Image imagenProf = new Image(getClass().getResourceAsStream("imagenes/profeDark.png"));
				escenario.getIcons().add(imagenProf);
				escenario.setScene(scene);
				escenario.setTitle("Menú Profesores");
				escenario.initModality(Modality.APPLICATION_MODAL);
				escenario.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		this.btnEstudiantes.setOnAction(event -> {
			try {
				Stage escenario = new Stage();
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(getClass().getResource("VistaMenuEstudiante.fxml"));//
				AnchorPane root = cargador.load();
				
				
				VistaControladorMenuEstudiante controlador = (VistaControladorMenuEstudiante) cargador.getController();
				controlador.setConexion(miConexion);
				controlador.setEscenario(escenario);
				
				Scene scene = new Scene(root);
				Image imagenEstu = new Image(getClass().getResourceAsStream("imagenes/estuDark.png"));
				escenario.getIcons().add(imagenEstu);
				escenario.setScene(scene);
				escenario.setTitle("Menú Estudiantes");
				escenario.initModality(Modality.APPLICATION_MODAL);
				escenario.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		this.btnCalificaciones.setOnAction(event -> {
			try {
				Stage escenario = new Stage();
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(getClass().getResource("VistaMenuCalificaciones.fxml"));//
				AnchorPane root = cargador.load();
				
				VistaControladorMenuCalificaciones controlador = (VistaControladorMenuCalificaciones) cargador.getController();
				controlador.setConexion(miConexion);
				controlador.setEscenario(escenario);
				
				Scene scene = new Scene(root);
				Image imagenNotas = new Image(getClass().getResourceAsStream("imagenes/notasDark.png"));
				escenario.getIcons().add(imagenNotas);
				escenario.setScene(scene);
				escenario.setTitle("Menú Calificaciones");
				escenario.initModality(Modality.APPLICATION_MODAL);
				escenario.show();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Atención");
				alert.setHeaderText("IMPORTANTE");
				alert.setContentText("Debe seleccionar el alumno que desea ver para visualizar los datos en la tabla.");
				alert.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		this.btnAcercaDe.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Acerca de..");
			alert.setHeaderText("Información:");
			alert.setContentText("Esta aplicación ha sido desarrollada por el alumno Antonio J. Ortiz Rodriguez, para ayudar a los estudiantes y profesores a gestionar el proceso de calificaciones y asistencia de "
					+ "	manera más eficiente. En ella podrás encontrar funciones para agregar nuevos estudiantes, crear y actualizar perfiles de profesores, y registrar y actualizar las calificaciones de los estudiantes. "
					+ "	Además, podrás registrar la asistencia de los estudiantes y generar informes sobre su desempeño académico."
					+ "	Se ha trabajado para garantizar que la aplicación sea fácil de usar y que cumpla con los más altos estándares de seguridad y privacidad de los datos de nuestros usuarios."
					+ "	Si tienes alguna duda o sugerencia, no dudes en ponerte en contacto a través de mi tutor Miguel."
					+ "	¡Gracias por utilizar mi aplicación!"
					+ "	versión: 1.0.0"
					+ "	Todos los derechos registrados. 2023.");
			alert.showAndWait();
		});
		
		this.btnSalir.setOnAction(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Salir");
			alert.setHeaderText("Va a volver al login");
			Optional<ButtonType> pulsarBoton = alert.showAndWait();
			if (pulsarBoton.get() == ButtonType.OK) {
				escenario.close();
			}
		});
		
	}
	

}

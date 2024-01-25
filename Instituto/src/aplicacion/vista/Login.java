package aplicacion.vista;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;

import aplicacion.Conexion;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login implements Initializable{
	//parametros de la conexion
	private Conexion miConexion = new Conexion("baseinstituto", "root", "", "jdbc:mysql://127.0.0.1:3306/");
	
	@FXML
	private ImageView imvInsti;
	
	@FXML
	private ImageView imvJunta;
	
	@FXML
	private ImageView imvLogin;

	@FXML
	private Button btnAcceder;
	
	@FXML
	private Button btnSalir;
	
	@FXML
	private TextField txfUsuario;
	
	@FXML
	private PasswordField psfContrasena;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		imagenes();
		acceder();
	}
	/**
	 * Este método es para poder entrar en el login con los parametros user: root, password: 1234
	 * Cualquier otro dará error, se reinician los txf despues de fallo o login correcto
	 * Se añade el botón salir, para cerrar el programa
	 */
	public void acceder() {	
		this.txfUsuario.setPromptText("Usuario");
		this.psfContrasena.setPromptText("Contraseña");
		this.btnAcceder.setOnAction(event -> {
			String usuario = this.txfUsuario.getText();
			String contrasena = this.psfContrasena.getText();
			if (usuario.isEmpty() || contrasena.isEmpty() || (!usuario.equals("root") && !contrasena.equals("1234"))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Login");
				alert.setHeaderText("Login Incorrecto");
				alert.setContentText("Hay algun campo vacío o incorrecto.");
				alert.showAndWait();
				this.txfUsuario.setText(null);
				this.psfContrasena.setText(null);
			} else if (usuario.equals("root") && contrasena.equals("1234")){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Login");
				alert.setHeaderText("Login Correcto");
				alert.showAndWait();
				ventanaPrincipal();
				this.txfUsuario.setText(null);
				this.psfContrasena.setText(null);
			}
		});
		
		this.btnSalir.setOnAction(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Salir");
			alert.setHeaderText("Va a salir del programa..");
			Optional<ButtonType> pulsarBoton = alert.showAndWait();
			if (pulsarBoton.get() == ButtonType.OK) {
				Platform.exit();
			}
		});
	}
	
	/**
	 * Este método es para mostrar una ventana de carga, asociada a su fxml
	 * Tambien tenemos una comprobación de la conexion a la base de datos
	 */
	@FXML
	public void ventanaPrincipal() {
		try {
			Stage escenario = new Stage();
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("VistaBarraProgreso.fxml"));//
			AnchorPane root = cargador.load();
			
			VistaControladorBarraProgreso controlador = (VistaControladorBarraProgreso) cargador.getController();
			this.miConexion = controlador.crearConexion(escenario);
			controlador.setEscenario(escenario);
			
			Scene scene = new Scene(root);
			escenario.initStyle(StageStyle.UNDECORATED);
			escenario.setScene(scene);
			escenario.initModality(Modality.WINDOW_MODAL);
			escenario.show();
			//hasta que no se termine de ejecutar esta ventana, no se va a empezar a ejecutar la siguiente, osea menuPrincipal
			escenario.setOnHidden(event -> menuPrincipal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (this.miConexion.getConexion()==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Importante, se ha generado un error");
			alert.setContentText("No se ha podido conectar con la base de datos");
			alert.showAndWait();
			Platform.exit();
		} else {
			
		}
	}

	/**
	 * Se carga el menú principal donde estarán los principales botones de esta app
	 */
	public void menuPrincipal() {
			try {
				Stage escenario = new Stage();
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(getClass().getResource("VistaMenu.fxml"));//
				AnchorPane root = cargador.load();
				ControladorMenu controlador = (ControladorMenu) cargador.getController();
				controlador.setConexion(miConexion);
				controlador.setEscenario(escenario);
				
				Scene scene = new Scene(root);
				Image imagenLogin = new Image(getClass().getResourceAsStream("imagenes/userlogin.png"));
				escenario.getIcons().add(imagenLogin);
				escenario.setScene(scene);
				escenario.setTitle("Menu Principal");
				escenario.initModality(Modality.WINDOW_MODAL);
				escenario.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Se cargan las imagenes
	 */
	@FXML
	public void imagenes() {
		Image imagenInsti = new Image(getClass().getResourceAsStream("imagenes/logo.png"));
		Image imagenJunta = new Image(getClass().getResourceAsStream("imagenes/juntaLogo.png"));
		Image imagenLogin = new Image(getClass().getResourceAsStream("imagenes/userlogin.png"));
		this.imvInsti.setImage(imagenInsti);
		this.imvJunta.setImage(imagenJunta);
		this.imvLogin.setImage(imagenLogin);
	}
	
}

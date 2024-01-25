package aplicacion.vista;

import java.net.URL;
import java.util.ResourceBundle;

import aplicacion.Conexion;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VistaControladorBarraProgreso implements Initializable {

	private Stage escenario;
	private Double valorBarra = 0.0;
	private Conexion miConexion;
	
	@FXML
	private ProgressBar pgbBarra;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		crearConexion(escenario);
	}
	
	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}
	/**
	 * Se establece la duración de la barra, además de instanciar el objeto de la clase timeline
	 * @return
	 */
	public Conexion crearConexion(Stage escenario) {
		this.escenario = escenario;
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), x -> pulso()));
		timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.play();
	   
	    //Instanciamos la conexion
	    this.miConexion = new Conexion("baseinstituto", "root", "", "jdbc:mysql://127.0.0.1:3306/");
	    return this.miConexion;
	}
	/**
	 * Cuando se llega a 1.0, se cierra esta ventana
	 */
	private void pulso() {
		this.pgbBarra.setProgress(Double.valueOf(valorBarra));
		valorBarra += 0.1;
		if (valorBarra >= 1.0) {
			this.escenario.close();
		}
	}
	
}

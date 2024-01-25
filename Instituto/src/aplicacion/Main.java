package aplicacion;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/**
 * 
 * @author Antonio J. Ortiz Rodriguez
 * 
 * En este main, se carga el primer stage y se le agrega un icono a la ventana
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("vista/VistaLogin.fxml"));
			Scene scene = new Scene(root);
			Image imagen = new Image(getClass().getResourceAsStream("/aplicacion/vista/imagenes/login.png"));
			primaryStage.getIcons().add(imagen);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

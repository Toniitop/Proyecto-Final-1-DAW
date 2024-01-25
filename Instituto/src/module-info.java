module instituto {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires mysql.connector.java;
	requires javafx.graphics;
	requires java.desktop;
	
	opens aplicacion to javafx.graphics, javafx.fxml, java.sql, javafx.base;
	opens aplicacion.modelo to javafx.graphics, javafx.fxml, java.sql, javafx.base;
	opens aplicacion.vista to javafx.graphics, javafx.fxml, java.sql, javafx.base;
}
